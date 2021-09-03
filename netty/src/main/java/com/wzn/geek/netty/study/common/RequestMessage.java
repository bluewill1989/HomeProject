package com.wzn.geek.netty.study.common;

/**
 * @author by will
 * @Classname RequestMessage
 * @Description TODO
 * @Date 2021/8/29 22:24
 */
public class RequestMessage extends Message<Operation>{

    /**
     * 从请求数据根据opcdoe找到对应的业务类
     * @param opcode
     * @return
     */
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationClazz();
    }

    public RequestMessage(){}


    public RequestMessage(Long streamId, Operation operation){
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setStreamId(streamId);
        messageHeader.setOpCode(OperationType.fromOperation(operation).getOpCode());

        this.setMessageBody(operation);
        this.setMessageHeader(messageHeader);

    }
}
