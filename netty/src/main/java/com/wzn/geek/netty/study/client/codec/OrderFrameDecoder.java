package com.wzn.geek.netty.study.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author by will
 * @Classname OrderFrameDecoder
 * @Description TODO
 * @Date 2021/9/3 21:41
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {

    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
