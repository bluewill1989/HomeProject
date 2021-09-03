package com.wzn.geek.netty.study.client.codec;

import com.wzn.geek.netty.study.common.RequestMessage;
import com.wzn.geek.netty.study.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author by will
 * @Classname OrderProtocalEncoder
 * @Description TODO
 * @Date 2021/9/3 21:54
 */
public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        requestMessage.encode(byteBuf);

        out.add(byteBuf);
    }
}
