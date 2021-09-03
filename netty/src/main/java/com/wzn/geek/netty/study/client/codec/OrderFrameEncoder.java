package com.wzn.geek.netty.study.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author by will
 * @Classname OrderFrameEncoder
 * @Description 加上length为2的长度进去
 * @Date 2021/9/3 21:58
 */
public class OrderFrameEncoder extends LengthFieldPrepender {
    public OrderFrameEncoder() {
        super(2);
    }
}
