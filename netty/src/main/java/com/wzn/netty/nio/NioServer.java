package com.wzn.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author by will
 * @Classname NioServer
 * @Description TODO
 * @Date 2021/8/11 21:18
 */
public class NioServer {

    //保存客户端连接
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9000));
        //设置为非阻塞,对应底下accept不会阻塞
        ssc.configureBlocking(false);
        System.out.println("服务启动成功!");

        while (true) {
            //NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数
            //注这里accept是不停的轮询
            SocketChannel socketChannel = ssc.accept();
            if (socketChannel != null) {
                System.out.println("连接成功！");
                //配置成非阻塞，则下面read方法不会阻塞，反之会阻塞
                socketChannel.configureBlocking(false);
                //保存客户端连接到List中
                channelList.add(socketChannel);
            }
            //遍历连接进行数据读取
            //注这里每次都要循环所有的channelList,不管其中由多少个真正有数据
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer buffer = ByteBuffer.allocate(128);
                int len = sc.read(buffer);
                if (len > 0) {
                    System.out.println("接收到消息:" + new String(buffer.array()));
                }
                else if (len == -1){
                    iterator.remove();
                    System.out.println("客户端断开了连接!");
                }
            }
        }
    }
}
