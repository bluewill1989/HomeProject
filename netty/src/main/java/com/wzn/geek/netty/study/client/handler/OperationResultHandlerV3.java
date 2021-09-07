package com.wzn.geek.netty.study.client.handler;

import com.wzn.geek.netty.study.client.handler.dispatcher.ResponsePendingCenter;
import com.wzn.geek.netty.study.common.MessageHeader;
import com.wzn.geek.netty.study.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by will
 * @Classname OperationResultHandler
 * @Description 响应分发
 * @Date 2021/9/3 22:31
 */
@Slf4j
public class OperationResultHandlerV3 extends SimpleChannelInboundHandler<ResponseMessage> {

    private ResponsePendingCenter responsePendingCenter;

    public OperationResultHandlerV3(ResponsePendingCenter responsePendingCenter) {
        this.responsePendingCenter = responsePendingCenter;
    }

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
        //拿到server端的execute之后返回的result
        responsePendingCenter.set(messageHeader.getStreamId(), responseMessage.getMessageBody());

    }
}
