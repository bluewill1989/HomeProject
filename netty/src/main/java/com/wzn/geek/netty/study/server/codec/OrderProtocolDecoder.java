package com.wzn.geek.netty.study.server.codec;

import com.wzn.geek.netty.study.common.Message;
import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
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
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.decode(in);
        out.add(requestMessage);
    }
}
