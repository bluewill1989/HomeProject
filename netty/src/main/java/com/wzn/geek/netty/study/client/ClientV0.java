package com.wzn.geek.netty.study.client;

import com.wzn.geek.netty.study.client.codec.OrderFrameDecoder;
import com.wzn.geek.netty.study.client.codec.OrderFrameEncoder;
import com.wzn.geek.netty.study.client.codec.OrderProtocolDecoder;
import com.wzn.geek.netty.study.client.codec.OrderProtocolEncoder;
import com.wzn.geek.netty.study.client.handler.ClientIdleCheckHandler;
import com.wzn.geek.netty.study.client.handler.KeepaliveHandler;
import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.OperationResult;
import com.wzn.geek.netty.study.common.RequestMessage;
import com.wzn.geek.netty.study.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by will
 * @Classname Client
 * @Description TODO
 * @Date 2021/9/3 22:12
 */
@Slf4j
public class ClientV0 {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(NioChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        KeepaliveHandler keepaliveHandler = new KeepaliveHandler();


        try {
           bootstrap.group(loopGroup)
                   .handler(new ChannelInitializer<NioSocketChannel>() {
                       @Override
                       protected void initChannel(NioSocketChannel ch) throws Exception {
                           ChannelPipeline pipeline = ch.pipeline();

                           pipeline.addLast(new ClientIdleCheckHandler());


                           pipeline.addLast(new OrderFrameDecoder());
                           pipeline.addLast(new OrderFrameEncoder());


                           pipeline.addLast(new OrderProtocolEncoder());
                           pipeline.addLast(new OrderProtocolDecoder());

                           pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                           pipeline.addLast(keepaliveHandler);

                       }
                   });
           ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888);
           channelFuture.sync();

           //发送消息
           RequestMessage requestMessage = new RequestMessage(1111L, new OrderOperation(1, "三号台"));
           channelFuture.channel().writeAndFlush(requestMessage);

           channelFuture.channel().closeFuture().sync();

       }
       finally {
           loopGroup.shutdownGracefully();
        }
    }

}
