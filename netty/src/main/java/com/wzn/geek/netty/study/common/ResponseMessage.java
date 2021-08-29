package com.wzn.geek.netty.study.common;

/**
 * @author by will
 * @Classname RequestMessage
 * @Description TODO
 * @Date 2021/8/29 22:24
 */
public class ResponseMessage extends Message<Operation>{

    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationClazz();
    }

}
