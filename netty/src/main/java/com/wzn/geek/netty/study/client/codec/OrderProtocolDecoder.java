package com.wzn.geek.netty.study.client.codec;

import com.wzn.geek.netty.study.common.RequestMessage;
import com.wzn.geek.netty.study.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author by will
 * @Classname OrderProtocalDecoder
 * @Description TODO
 * @Date 2021/9/3 21:48
 */
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(in);
        out.add(responseMessage);
    }
}
