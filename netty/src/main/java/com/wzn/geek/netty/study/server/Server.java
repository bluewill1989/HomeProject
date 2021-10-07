package com.wzn.geek.netty.study.server;

import com.wzn.geek.netty.study.server.codec.OrderFrameDecoder;
import com.wzn.geek.netty.study.server.codec.OrderFrameEncoder;
import com.wzn.geek.netty.study.server.codec.OrderProtocolDecoder;
import com.wzn.geek.netty.study.server.codec.OrderProtocolEncoder;
import com.wzn.geek.netty.study.server.handler.AuthHandler;
import com.wzn.geek.netty.study.server.handler.MetricsHandler;
import com.wzn.geek.netty.study.server.handler.OrderServiceProcessHandler;
import com.wzn.geek.netty.study.server.handler.ServerIdleCheckHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
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
        serverBootstrap.option(NioChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.childOption(NioChannelOption.TCP_NODELAY, true);//关闭nagle
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        //线程起名字
//        NioEventLoopGroup bossGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("boss"));
//        NioEventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));

//        UnorderedThreadPoolEventExecutor businessGroup = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));


        //ipfilter
        IpSubnetFilterRule ipSubnetFilterRule = new IpSubnetFilterRule("127.1.1.1", 16, IpFilterRuleType.REJECT);
        //本地不能过
//        IpSubnetFilterRule ipSubnetFilterRule = new IpSubnetFilterRule("127.0.0.1", 8, IpFilterRuleType.REJECT);
        RuleBasedIpFilter ruleBasedIpFilter = new RuleBasedIpFilter(ipSubnetFilterRule);

        AuthHandler authHandler = new AuthHandler();

        //ssl
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        log.info("certificate position:" + selfSignedCertificate.certificate().toString());
        SslContext sslContext = SslContextBuilder.forServer(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey()).build();

        try {
            //metrics
            MetricsHandler metricsHandler = new MetricsHandler();


            serverBootstrap.group(loopGroup)

                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                          SSL功能
//                            pipeline.addLast("ssl", sslContext.newHandler(ch.alloc()));


                            pipeline.addLast(new OrderFrameDecoder());
                            //handler起名字
//                            pipeline.addLast("OrderFrameDecoder",new OrderFrameDecoder());
                            pipeline.addLast(new OrderFrameEncoder());

                            pipeline.addLast(new OrderProtocolEncoder());
                            pipeline.addLast(new OrderProtocolDecoder());



                            pipeline.addLast("ipFilter", ruleBasedIpFilter);


                            pipeline.addLast("metricHandler", metricsHandler);

                            pipeline.addLast("idleHandler", new ServerIdleCheckHandler());


                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));

                            pipeline.addLast("auth", authHandler);

                            pipeline.addLast(new OrderServiceProcessHandler());

                            //减少flush次数，增大系统的吞吐量
                            pipeline.addLast("flushEnhance", new FlushConsolidationHandler(10, true));


                            //使用executor，去掉43行注释
//                            pipeline.addLast(businessGroup, new OrderServerProcessHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
