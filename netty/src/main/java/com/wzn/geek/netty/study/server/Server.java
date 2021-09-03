package com.wzn.geek.netty.study.server;

import com.wzn.geek.netty.study.server.codec.OrderFrameDecoder;
import com.wzn.geek.netty.study.server.codec.OrderFrameEncoder;
import com.wzn.geek.netty.study.server.codec.OrderProtocolDecoder;
import com.wzn.geek.netty.study.server.codec.OrderProtocolEncoder;
import com.wzn.geek.netty.study.server.handler.OrderServiceProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by will
 * @Classname Server
 * @Description TODO
 * @Date 2021/9/3 22:02
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();


        try {
            serverBootstrap.group(loopGroup)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new OrderFrameDecoder());
                            pipeline.addLast(new OrderFrameEncoder());

                            pipeline.addLast(new OrderProtocolEncoder());
                            pipeline.addLast(new OrderProtocolDecoder());

                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                            pipeline.addLast(new OrderServiceProcessHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
