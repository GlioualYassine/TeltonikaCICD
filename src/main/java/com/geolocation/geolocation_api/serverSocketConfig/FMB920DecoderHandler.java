package com.geolocation.geolocation_api.serverSocketConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FMB920DecoderHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(FMB920DecoderHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Check if the incoming message is a ByteBuf (Netty's buffer for reading binary data)
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;

            // Create a byte array to hold the data
            byte[] data = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(data); // Read the bytes from the buffer into the array

            // Decode the message according to the FMB920 protocol
            String decodedMessage = decodeFMB920Message(data);

            logger.info("Received and decoded message: {}", decodedMessage);

            // Optionally, you can process the decoded message further
            // For example, you might want to extract location data, status, etc.

        } else {
            logger.warn("Received unsupported message type: {}", msg.getClass().getSimpleName());
        }

        // Release the buffer if you're done with it
        if (msg instanceof ByteBuf) {
            ReferenceCountUtil.release(msg);
        }
    }

    private String decodeFMB920Message(byte[] data) {
        // Example: Parse the message assuming it starts with a device ID followed by latitude and longitude
        String message = new String(data);
        String[] parts = message.split(",");

        if (parts.length < 3) {
            return "Invalid message format";
        }

        String deviceId = parts[0];
        String latitude = parts[1];
        String longitude = parts[2];

        // Construct a formatted output
        return String.format("Device ID: %s, Latitude: %s, Longitude: %s", deviceId, latitude, longitude);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Error in FMB920DecoderHandler", cause);
        ctx.close(); // Close the channel on exception
    }
}
