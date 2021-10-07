package com.wzn.geek.netty.study.server.handler;

import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.RequestMessage;
import com.wzn.geek.netty.study.common.auth.AuthOperation;
import com.wzn.geek.netty.study.common.auth.AuthOperationResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        try {
            Operation operation = msg.getMessageBody();
            if (operation instanceof AuthOperation) {
                AuthOperation authOperation = (AuthOperation) operation;
                AuthOperationResult authOperationResult = authOperation.execute();
                if (authOperationResult.isPassAuth()) {
                    log.info("pass auth");
                } else {
                    log.error("fail to auth");
                    ctx.close();
                }
            } else {
                log.error("expect first msg is auth");
                ctx.close();
            }
        } catch (Exception e) {
            log.error("exception happen for: " + e.getMessage(), e);
            ctx.close();
        }
        finally {
            //授权完了就可以从管道中移除了，如果不移除，发别的operation消息也会走到这个里面来，会报  expect first msg is auth！
            ctx.pipeline().remove(this);
        }

    }
}
