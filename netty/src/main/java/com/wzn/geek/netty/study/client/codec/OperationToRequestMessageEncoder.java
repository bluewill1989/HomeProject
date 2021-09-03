package com.wzn.geek.netty.study.client.codec;

import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author by will
 * @Classname OperationToRequestMessageEncoder
 * @Description 客户端专注于operation的逻辑，这里封装成requestmessage
 * @Date 2021/9/3 22:22
 */
public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Operation msg, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(1111L, msg);
        out.add(requestMessage);
    }
}
