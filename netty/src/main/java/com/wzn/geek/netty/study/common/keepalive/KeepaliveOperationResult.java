package com.wzn.geek.netty.study.common.keepalive;

import com.wzn.geek.netty.study.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
