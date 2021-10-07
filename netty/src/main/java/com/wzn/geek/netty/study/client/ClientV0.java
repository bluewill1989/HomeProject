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
import com.wzn.geek.netty.study.common.auth.AuthOperation;
import com.wzn.geek.netty.study.order.OrderOperation;
import com.wzn.geek.netty.study.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;

/**
 * @author by will
 * @Classname Client
 * @Description TODO
 * @Date 2021/9/3 22:12
 */
@Slf4j
public class ClientV0 {
    public static void main(String[] args) throws InterruptedException, SSLException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(NioChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        KeepaliveHandler keepaliveHandler = new KeepaliveHandler();

        SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();



        try {

            //下面这行，先直接信任自签证书，以避免没有看到ssl那节课程的同学运行不了；
            //学完ssl那节后，可以去掉下面这行代码，安装证书，安装方法参考课程，执行命令参考resources/ssl.txt里面
            sslContextBuilder.trustManager(InsecureTrustManagerFactory.INSTANCE);

            SslContext sslContext = sslContextBuilder.build();


           bootstrap.group(loopGroup)
                   .handler(new ChannelInitializer<NioSocketChannel>() {
                       @Override
                       protected void initChannel(NioSocketChannel ch) throws Exception {
                           ChannelPipeline pipeline = ch.pipeline();

                           pipeline.addLast(new ClientIdleCheckHandler());

//                           pipeline.addLast(sslContext.newHandler(ch.alloc()));


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

            AuthOperation authOperation = new AuthOperation("admin", "password");

            channelFuture.channel().writeAndFlush(new RequestMessage(IdUtil.nextId(), authOperation));


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
