package com.wzn.geek.netty.study.order;

import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by will
 * @Classname OrderOperation
 * @Description TODO
 * @Date 2021/8/29 22:38
 */
@Data
@Slf4j
@AllArgsConstructor
public class OrderOperation  extends Operation {


    private int tableId;
    private String dish;

    @Override
    public OrderOperationResult execute() {
        System.out.println("order's executing startup with orderRequest: " + toString());
        //execute order logic
        System.out.println("order's executing complete");
        OrderOperationResult orderResponse = new OrderOperationResult(5555, "好菜", true);
        System.out.println("准备返回OrderOperationResult");
        return orderResponse;
    }
}
