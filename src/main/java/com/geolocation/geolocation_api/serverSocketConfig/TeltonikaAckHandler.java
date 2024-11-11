package com.geolocation.geolocation_api.serverSocketConfig;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeltonikaAckHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TeltonikaAckHandler.class);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // This method is triggered after the message is fully read.
        logger.info("Message fully read, sending ACK.");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Error in TeltonikaAckHandler", cause);
        ctx.close();  // Close the channel on exception
    }
}
