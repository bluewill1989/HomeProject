package com.wzn.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author by will
 * @Classname SocketServer
 * @Description TODO
 * @Date 2021/8/11 20:53
 */
@Slf4j
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待连接..");
            Socket clientSocket = serverSocket.accept();
            System.out.println("有客户端连接了!");
            handler(clientSocket);
        }
    }

    private static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备开始读数据..");
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("读取完毕!");
        if (read != -1) {
            System.out.println("从客户端接到的数据:" + new String(bytes, 0, read));
        }
        else{
            System.out.println("退出了！");
        }
        clientSocket.getOutputStream().write("hello，我收到了".getBytes(StandardCharsets.UTF_8));
        clientSocket.getOutputStream().flush();
    }
}
