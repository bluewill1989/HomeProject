package com.wzn.geek.netty.study.common.auth;

import com.wzn.geek.netty.study.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
