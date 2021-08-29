package com.wzn.geek.netty.study.common;

import com.wzn.geek.netty.study.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * @author by will
 * @Classname Message
 * @Description T是继承了MessageBody的一个个业务实体类(opertaion--业务处理  opertaionResult--业务响应最后都是一个个messageBody)
 * @Date 2021/8/29 21:54
 */
@Data
public abstract class Message<T extends MessageBody> {

    private MessageHeader messageHeader;
    //请求  解析--然后传给对应的业务处理类，这里T就是一个opertaion
    //响应  将响应对应的业务类封装好，这里T就是一个operationResult
    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    /**
     * 通过opcode得到对应的messageBody类（每一个业务类都继承了messageBody），等于知道了数据对应是哪个业务类的，
     * 进而通过调用类的构造器把数据“赋值”给对应业务类
     * @param opcode
     * @return
     */
    public abstract Class<T> getMessageBodyDecodeClass(int opcode);


    /**
     * 将message encode 成bytes
     * @param byteBuf
     */
    public void encode(ByteBuf byteBuf){
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeLong(messageHeader.getStreamId());
        //messageBody用json写入
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    /**
     * 将bytes decode成message
     * @param byteBuf
     */
    public void decode(ByteBuf byteBuf){
        int version = byteBuf.readInt();
        int opCode = byteBuf.readInt();
        long streamId = byteBuf.readLong();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setVersion(version);
        messageHeader.setOpCode(opCode);
        messageHeader.setStreamId(streamId);
        this.messageHeader = messageHeader;

        //解析接下来的bytes数据,将jsonString直接转成bean
        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
        T body = JsonUtil.fromJson(byteBuf.toString(Charset.forName("UTF-8")), bodyClazz);
        this.messageBody = body;
    }
}
