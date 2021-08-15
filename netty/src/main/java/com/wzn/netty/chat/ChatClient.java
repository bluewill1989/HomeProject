package com.wzn.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Scanner;

/**
 * @author by will
 * @Classname ChatClient
 * @Description TODO
 * @Date 2021/8/15 9:45
 */
public class ChatClient {
    public static void main(String[] args) throws Exception {
//        String host = args[0];
        String host = "127.0.0.1";
        int port = 9000;
//        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline() .addLast("logging", new LoggingHandler(LogLevel.INFO));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ChatClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

//            // Wait until the connection is closed.
//            f.channel().closeFuture().sync();


            Channel channel = f.channel();
            System.out.println("===" + channel.localAddress() + "=====");
            //客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String msg = scanner.nextLine();
                //通过channel发送消息到服务器
                channel.writeAndFlush(msg);
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
