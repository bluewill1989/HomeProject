package com.wzn.geek.netty.study.order;

import com.wzn.geek.netty.study.common.OperationResult;
import lombok.Data;

/**
 * @author by will
 * @Classname OrderOperationResult
 * @Description TODO
 * @Date 2021/8/29 22:40
 */

@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;

}
