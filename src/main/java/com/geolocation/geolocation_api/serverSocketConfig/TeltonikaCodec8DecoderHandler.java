package com.geolocation.geolocation_api.serverSocketConfig;

import com.geolocation.geolocation_api.entities.GpsDevice;
import com.geolocation.geolocation_api.entities.Position;
import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.entities.enums.ERole;
import com.geolocation.geolocation_api.notification.Notification;
import com.geolocation.geolocation_api.notification.NotificationStatus;
import com.geolocation.geolocation_api.repository.GpsDeviceRepository;
import com.geolocation.geolocation_api.repository.PositionRepository;
import com.geolocation.geolocation_api.repository.UserRepository;
import com.geolocation.geolocation_api.services.auth.AuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class TeltonikaCodec8DecoderHandler extends ChannelInboundHandlerAdapter {
    public static final int CODEC_GH3000 = 0x07;
    public static final int CODEC_8 = 0x08;
    public static final int CODEC_8_EXT = 0x8E;
    public static final int CODEC_12 = 0x0C;
    public static final int CODEC_13 = 0x0D;
    public static final int CODEC_16 = 0x10;
    private final PositionRepository positionRepository;
    private final SimpMessagingTemplate messagingTemplate;
    //private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final GpsDeviceRepository gpsDeviceRepository;
    private static final Logger logger = LoggerFactory.getLogger(TeltonikaCodec8DecoderHandler.class);
    private boolean imeiReceived = false;



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        try {
            if (!imeiReceived) {
                handleImei(byteBuf, ctx);
            } else {
                handleAvlData(byteBuf, ctx);
            }
        } catch (Exception e) {
            logger.error("Error processing message", e);
        } finally {
            byteBuf.release();  // Ensure ByteBuf is released
        }
    }

    private void handleImei(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        if (byteBuf.readableBytes() < 15) {
            logger.warn("Message too short to contain an IMEI");
            return;
        }

        byte[] imeiBytes = new byte[15];
        byteBuf.readBytes(imeiBytes);
        String imei = new String(imeiBytes, StandardCharsets.US_ASCII);
        logger.info("Received IMEI: {}", imei);

        sendImeiAck(ctx);
        imeiReceived = true;
    }

    private void handleAvlData(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        if (byteBuf.readableBytes() < 12) {
            logger.warn("Message too short, unable to parse AVL data");
            return;
        }

        decodeCodec8Message(byteBuf, ctx);
    }

    // Send an ACK (0x01) to acknowledge IMEI reception
    private void sendImeiAck(ChannelHandlerContext ctx) {
        ByteBuf ackBuf = ctx.alloc().buffer(1);
        ackBuf.writeByte(0x01);
        ctx.writeAndFlush(ackBuf);
        logger.info("Sent IMEI acknowledgment (0x01)");
    }

    // Decode Codec 8/Extended messages
    private void decodeCodec8Message(ByteBuf byteBuf, ChannelHandlerContext ctx) {
        int preamble = byteBuf.readInt();
        logger.info("Preamble: {}", Integer.toHexString(preamble));

        int dataLength = byteBuf.readInt();
        logger.info("Data Length: {}", dataLength);

        int codecId = byteBuf.readUnsignedByte();
        logger.info("Codec ID: {}", codecId);

        if (codecId != CODEC_8 && codecId != CODEC_8_EXT) {
            logger.warn("Unsupported Codec ID: {}", codecId);
            return;
        }

        int numberOfRecords = byteBuf.readUnsignedByte();
        logger.info("Number of Data Records: {}", numberOfRecords);

        for (int i = 0; i < numberOfRecords; i++) {
            decodeAvlData(byteBuf , codecId);
        }

        sendAck(byteBuf, dataLength, ctx);

        int crc16 = byteBuf.readUnsignedShort();
        logger.info("CRC16: {}", Integer.toHexString(crc16));
    }

    private void decodeAvlData(ByteBuf byteBuf , int codec) {

        if (byteBuf.readableBytes() < 15) {
            logger.warn("Message too short to contain an IMEI");
            return;
        }

        byte[] imeiBytes = new byte[15];
        byteBuf.readBytes(imeiBytes);
        String imei = new String(imeiBytes, StandardCharsets.US_ASCII);
        logger.info("Received IMEI: {}", imei);

        double latitude = 0;
        double longitude = 0;
        double altitude = 0; // value in meters
        double course  = 0;
        double speed = 0;
        Date timestamp = new Date();

        int globalMask = 0x0f;


        if (codec == CODEC_GH3000) {
            long time = byteBuf.readUnsignedInt() & 0x3fffffff;
            time += 1167609600;

            globalMask = byteBuf.readUnsignedByte();
            if (BitUtil.check(globalMask, 0)) {

                timestamp =new Date(time * 1000);
                int locationMask = byteBuf.readUnsignedByte();

                if (BitUtil.check(locationMask, 0)) {
                    latitude = byteBuf.readFloat();
                    longitude = byteBuf.readFloat();
                }
                if (BitUtil.check(locationMask, 1)) {
                    altitude = byteBuf.readUnsignedShort();
                }

                if (BitUtil.check(locationMask, 2)) {
                    course = byteBuf.readUnsignedByte() * 360.0 / 256;
                }

                if (BitUtil.check(locationMask, 3)) {
                    speed = UnitsConverter.knotsFromKph(byteBuf.readUnsignedByte());
                }
            }
            else {
                logger.warn("find last position flag not set");
            }



        }else {
            timestamp = new Date(byteBuf.readLong());
            latitude = byteBuf.readInt() / 10000000.0;
            longitude = byteBuf.readInt() / 10000000.0;
            altitude = byteBuf.readShort();
            course = byteBuf.readUnsignedShort() ;
            speed = UnitsConverter.knotsFromKph(byteBuf.readUnsignedShort());

        }
        Position position = new Position();
        position.setLatitude(latitude+"");
        position.setLongitude(longitude+"");
        position.setSpeed(String.valueOf(speed));
        position.setImei(imei);


        //sent to web socket to the admins
        List<User> users = userRepository.findByRole(ERole.ROLE_GENERAL_ADMIN);
        users.forEach(user -> {
            messagingTemplate.convertAndSendToUser(user.getId().toString(), "/positions", position);
        });
        // sent to web socket to Device's owner
        GpsDevice gpsDevice = gpsDeviceRepository.findByImei(imei).orElse(null);
        if(gpsDevice != null){
            messagingTemplate.convertAndSendToUser(gpsDevice.getUser().getId().toString(), "/positions", position);
        }

        positionRepository.save(position);

        logger.info("Timestamp: {}", timestamp);
        logger.info("Latitude: {}", latitude);
        logger.info("Longitude: {}", longitude);
        logger.info("Altitude: {}", altitude);
        logger.info("Course: {}", course);
        logger.info("Speed: {}", speed);
    }

    private void sendAck(ByteBuf byteBuf, int dataLength, ChannelHandlerContext ctx) {
        ByteBuf ackBuf = ctx.alloc().buffer(4);
        ackBuf.writeInt(dataLength);
        ctx.writeAndFlush(ackBuf);
        logger.info("Sent ACK with data length: {}", dataLength);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Error in TeltonikaCodec8DecoderHandler", cause);
        ctx.close();  // Close the channel on exception
    }
}