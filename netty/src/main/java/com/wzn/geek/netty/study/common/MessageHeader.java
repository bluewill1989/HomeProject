package com.wzn.geek.netty.study.common;

import lombok.Data;

/**
 * @author by will
 * @Classname MessageHeader
 * @Description TODO
 * @Date 2021/8/29 21:52
 */
@Data
public class MessageHeader {

    private int version = 1;
    private int opCode;
    private long streamId;
}
