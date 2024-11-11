package com.geolocation.geolocation_api.serverSocketConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private boolean isAuthenticated = false;
    private int expectedIMEILength = -1;  // For tracking IMEI length

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (!isAuthenticated) {
            // Handle the identification packet (IMEI)
            handleIdentification(ctx, msg);
        } else {
            // Handle the data packet (GPS data)
            handleDataPacket(ctx, msg);
        }
    }

    private void handleIdentification(ChannelHandlerContext ctx, ByteBuf msg) {
        // Step 1: Read the IMEI length if it hasn't been read yet
        if (expectedIMEILength == -1) {
            if (msg.readableBytes() < 2) {
                // Not enough data to read the length (short is 2 bytes), wait for more data
                return;
            }
            expectedIMEILength = msg.readUnsignedShort();  // Read 2 bytes for IMEI length
        }

        // Step 2: Read the IMEI bytes once we know the length
        if (msg.readableBytes() < expectedIMEILength) {
            // Not enough data to read the full IMEI, wait for more data
            return;
        }

        byte[] imeiBytes = new byte[expectedIMEILength];
        msg.readBytes(imeiBytes);
        String imei = new String(imeiBytes);
        System.out.println("Received IMEI: " + imei);

        // Respond with acknowledgment (0x01) to confirm IMEI received
        ByteBuf ack = ctx.alloc().buffer(1);
        ack.writeByte(0x01);  // Send ACK byte
        ctx.writeAndFlush(ack);

        // Authentication is now complete
        isAuthenticated = true;
        expectedIMEILength = -1;  // Reset for next potential session
    }

    private void handleDataPacket(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if (msg.readableBytes() < 12) {
            System.out.println("Not enough data to process AVL packet");
            return;
        }

        // Step 1: Skip preamble (4 bytes)
        msg.skipBytes(4);

        // Step 2: Read data length
        int dataLength = msg.readInt();
        System.out.println("Data length: " + dataLength);

        if (msg.readableBytes() < dataLength) {
            System.out.println("Incomplete data, waiting for more");
            return;
        }

        // Step 3: Read Codec ID and Record Count
        byte codecId = msg.readByte();
        byte recordCount = msg.readByte();
        System.out.println("Codec ID: " + codecId + ", Record count: " + recordCount);

        if (recordCount == 0) {
            sendAck(ctx, recordCount);
            return;
        }

        // Step 4: Process each AVL record using Codec 8 Extended decoder
        //List<AvlRecord> avlRecords = new Codec8ExtendedDecoder().decode(msg);

        // Process each AVL record
        System.out.println("Decoded AVL Record: " + msg.toString());
       //for (AvlRecord record : avlRecords) {
         //   System.out.println("Decoded AVL Record: " + record);
        //}

        // Step 5: Send ACK to the client for the number of records received
        sendAck(ctx, recordCount);
    }

    private void sendAck(ChannelHandlerContext ctx, int recordCount) {
        ByteBuf ack = ctx.alloc().buffer(4);
        ack.writeInt(recordCount);
        ctx.writeAndFlush(ack);
        System.out.println("ACK sent for " + recordCount + " records");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
