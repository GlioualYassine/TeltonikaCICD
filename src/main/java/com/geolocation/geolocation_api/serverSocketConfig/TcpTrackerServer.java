package com.geolocation.geolocation_api.serverSocketConfig;

import com.geolocation.geolocation_api.repository.PositionRepository;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@RequiredArgsConstructor
public class TcpTrackerServer {

    private static final Logger logger = LoggerFactory.getLogger(TcpTrackerServer.class);
    private final int port = 5027;  // The TCP port for communication
    private Channel serverChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private final PositionRepository positionRepository;
    @PostConstruct
    public void startServer() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))  // Logging incoming connections
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));  // Log activity
                            pipeline.addLast(new TeltonikaCodec8DecoderHandler(positionRepository));  // Decode FMB920 messages
                            pipeline.addLast(new TeltonikaAckHandler());  // Handle ACKs
                        }
                    });

            serverChannel = bootstrap.bind(new InetSocketAddress(port)).sync().channel();
            logger.info("TCP Server started, listening on port {}", port);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error starting TCP server: {}", e.getMessage());
            throw e;
        }
    }

    @PreDestroy
    public void stopServer() {
        if (serverChannel != null) {
            serverChannel.close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        logger.info("TCP Server has been stopped gracefully");
    }
}