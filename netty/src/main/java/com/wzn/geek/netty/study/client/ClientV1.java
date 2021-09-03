package com.wzn.geek.netty.study.client;

import com.wzn.geek.netty.study.client.codec.*;
import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.RequestMessage;
import com.wzn.geek.netty.study.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
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
public class ClientV1 {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);

        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

       try {
           bootstrap.group(loopGroup)
                   .handler(new ChannelInitializer<NioSocketChannel>() {
                       @Override
                       protected void initChannel(NioSocketChannel ch) throws Exception {
                           ChannelPipeline pipeline = ch.pipeline();
                           pipeline.addLast(new OrderFrameDecoder());

                           pipeline.addLast(new OrderFrameEncoder());

                           pipeline.addLast(new OrderProtocolEncoder());
                           pipeline.addLast(new OrderProtocolDecoder());

                           //注意encoder是队尾到队首，因此第一个执行要放在最后面
                           pipeline.addLast(new OperationToRequestMessageEncoder());


                           pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                       }
                   });
           ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888);
           channelFuture.sync();

           //发送消息
//           RequestMessage requestMessage = new RequestMessage(1111L, new OrderOperation(1, "三号台"));
           //和上面的对比
           Operation operation = new OrderOperation(1, "三号台");

           channelFuture.channel().writeAndFlush(operation);

           channelFuture.channel().closeFuture().sync();

       }
       finally {
           loopGroup.shutdownGracefully();
        }
    }

}