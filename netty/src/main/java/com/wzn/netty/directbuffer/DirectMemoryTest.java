package com.wzn.netty.directbuffer;

import java.nio.ByteBuffer;

/**
 * @author by will
 * @Classname DirectMemoryTest
 * @Description TODO
 * @Date 2021/8/15 19:28
 */
public class DirectMemoryTest {


    public static void  heapAccess() {
        long startTime  = System.currentTimeMillis();
        //分配堆内存
        ByteBuffer buffer = ByteBuffer.allocate(1025);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 200; j++) {
                buffer.putInt(j);
            }
            buffer.flip();

            for (int j = 0; j < 200; j++) {
                buffer.getInt();
            }
            buffer.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("堆内存访问:" + (endTime - startTime) + "ms");
    }

    public static void  directAccess() {
        long startTime  = System.currentTimeMillis();
        //分配堆内存
        ByteBuffer buffer = ByteBuffer.allocateDirect(1025);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 200; j++) {
                buffer.putInt(j);
            }
            buffer.flip();

            for (int j = 0; j < 200; j++) {
                buffer.getInt();
            }
            buffer.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("直接内存访问:" + (endTime - startTime) + "ms");
    }

    public static void main(String[] args) {
        heapAccess();
        directAccess();
    }
}
