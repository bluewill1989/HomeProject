package com.wzn.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author by will
 * @Classname SocketClient
 * @Description TODO
 * @Date 2021/8/11 21:40
 */
public class SocketClient {
    public static void main(String[] args) {
        try {
            Socket socketClient = new Socket("127.0.0.1", 9000);
            socketClient.getOutputStream().write("这是我发送的数据".getBytes(StandardCharsets.UTF_8));
//            BufferedReader br = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            InputStream in = socketClient.getInputStream();
            //读到buf字节数组中
            byte[] buf = new byte[1024];
            int len = in.read(buf);
            String content = new String(buf,0,len);
            System.out.println("Server:"+content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
