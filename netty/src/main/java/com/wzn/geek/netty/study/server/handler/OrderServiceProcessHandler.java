package com.wzn.geek.netty.study.server.handler;

import com.wzn.geek.netty.study.common.*;
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
        //这里是真正的业务执行execute方法!!
        OperationResult operationResult = operation.execute();
        MessageHeader messageHeader = requestMessage.getMessageHeader();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageBody(operationResult);
        responseMessage.setMessageHeader(messageHeader);

       // ctx.writeAndFlush(responseMessage); //改成下面的方式,避免OOM

        if (ctx.channel().isActive() && ctx.channel().isWritable()) {
            ctx.writeAndFlush(responseMessage);
        } else {
            log.error("not writable now, message dropped");
        }
    }
}
