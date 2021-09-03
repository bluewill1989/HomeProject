package com.wzn.geek.netty.study.server.handler;

import com.wzn.geek.netty.study.common.MessageHeader;
import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.RequestMessage;
import com.wzn.geek.netty.study.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by will
 * @Classname OrderServiceProcessHandler
 * @Description TODO
 * @Date 2021/9/3 22:00
 */
@Slf4j
public class OrderServiceProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        log.info("进来了:{}",requestMessage);
        Operation operation = requestMessage.getMessageBody();
        MessageHeader messageHeader = requestMessage.getMessageHeader();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageBody(operation);
        responseMessage.setMessageHeader(messageHeader);

        ctx.writeAndFlush(responseMessage);
    }
}
