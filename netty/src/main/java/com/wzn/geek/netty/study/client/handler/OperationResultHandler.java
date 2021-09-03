package com.wzn.geek.netty.study.client.handler;

import com.wzn.geek.netty.study.common.*;
import com.wzn.geek.netty.study.order.OrderOperation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by will
 * @Classname OperationResultHandler
 * @Description TODO
 * @Date 2021/9/3 22:31
 */
@Slf4j
public class OperationResultHandler  extends SimpleChannelInboundHandler<ResponseMessage> {

    /**
     * 拿到处理返回的operation
     * @param ctx
     * @param responseMessage
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessage responseMessage) throws Exception {
        log.info("收到响应:{}",responseMessage);
        MessageHeader messageHeader = responseMessage.getMessageHeader();
        Class clazz = responseMessage.getMessageBodyDecodeClass(messageHeader.getOpCode());
        if (clazz.equals(OrderOperation.class)){
            log.info("收到的operation:{}",clazz);
            Operation orderOperation = (OrderOperation)responseMessage.getMessageBody();
            orderOperation.execute();
        }
    }
}
