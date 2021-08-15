package com.wzn.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author by will
 * @Classname ChatServerHandler
 * @Description TODO
 * @Date 2021/8/15 9:53
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //GlobalEventExecutor.INSTANCE 全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        //将该客户加入聊天室的所有信息推送给其他现在客户端
        //该方法会将channelGroup中所有的channel遍历，并发送消息(新加入的不会收到，之前已经加入的会收到)
        channelGroup.writeAndFlush("[客户端]" + channel.localAddress() + "上线了，" + sdf.format(new Date()) + "\n");
        //将当前channel加入到ChannelGroup
        channelGroup.add(channel);
        System.out.println(ctx.channel().remoteAddress() + "上线了" + "\n");
        System.out.println("channelGroup Size="+channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        //获取当前channel
        Channel channel = channelHandlerContext.channel();
        //遍历全局channelGroup
        channelGroup.forEach( ch ->{
            if (channel != ch){ // 不是当前的channel，转发消息
                ch.writeAndFlush("[客户端]" + channel.remoteAddress() + "发送了消息：" + msg + "\n");
            }
            else {//回显自己发送的消息
                ch.writeAndFlush("[自己]发送了消息：" + msg + "\n");
            }
        });
    }


    //channel不活动，离线。。
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.localAddress() + "下线了，" + sdf.format(new Date()) + "\n");
        System.out.println(ctx.channel().remoteAddress() + "下线了" + "\n");
        System.out.println("channelGroup size=" + channelGroup.size());
    }
}
