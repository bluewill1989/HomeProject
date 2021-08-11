package com.wzn.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author by will
 * @Classname NioSelectorServer
 * @Description selectorIO多路复用
 * @Date 2021/8/11 21:55
 */
public class NioSelectorServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9000));
        ssc.configureBlocking(false);
        //打开Selector处理Channel，即创建epoll
        Selector selector = Selector.open();
        //ServerSocketChannel注册到selector上，并且selector对客户端accept连接感兴趣
        SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功！");

        while (true) {
            //阻塞等待需要处理的事件发生
            selector.select();

            //获取selector中注册的全部事件的SelectionKey实例(理解成当前已发生的事件实例)
            //KEY1:OP_ACCEPT --ServerSocketChannel
            //KEY2:OP_READ --SocketChannel
            //KEY3:OP_READ --SocketChannel
            //selector注册关心的事件
            //Set<SelectionKey>集合，selector里面已经注册好的发生的事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            //遍历selectionKey对时间进行处理
            while(iterator.hasNext()){
                SelectionKey selectionKey1 = iterator.next();
                //如果是OP_ACCEP事件，则进行连接获取和事件注册
                if (selectionKey1.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey1.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    //这里注册了读事件，如果需要客户端发送数据可以注册写事件
                    SelectionKey selKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功!");
                } else if (selectionKey1.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey1.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    if (len > 0) {
                        System.out.println("接收到消息:" + new String(byteBuffer.array()));
                    } else if (len == -1) { //如果客户端断开连接，关闭socket
                        System.out.println("客户端断开了连接!");
                        socketChannel.close();
                    }
                }
                //从事件集合里删除本次处理的key，防止下次select重复处理
                //比如连接建立了，这个事件就可以删掉了，如果是读事件，这次的读完也可以删掉了，因为该channel的读已经注册到了selector上，下次再读就会生成事件key！！
                iterator.remove();
            }
        }
    }
}
