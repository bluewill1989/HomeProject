package com.wzn.geek.netty.study.server.codec;

import com.wzn.geek.netty.study.common.RequestMessage;
import com.wzn.geek.netty.study.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author by will
 * @Classname OrderProtocalEncoder
 * @Description TODO
 * @Date 2021/9/3 21:54
 */
public class OrderProtocolEncoder extends MessageToMessageEncoder<ResponseMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMessage responseMessage, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        responseMessage.encode(byteBuf);

        out.add(byteBuf);
    }
}
