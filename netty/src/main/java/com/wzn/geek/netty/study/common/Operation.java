package com.wzn.geek.netty.study.common;

/**
 * @author by will
 * @Classname Operation
 * @Description TODO
 * @Date 2021/8/29 22:19
 */
public abstract class Operation extends MessageBody {

    public abstract OperationResult execute();

}
