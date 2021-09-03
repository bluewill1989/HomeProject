package com.wzn.geek.netty.study.order;

import com.wzn.geek.netty.study.common.Operation;
import com.wzn.geek.netty.study.common.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author by will
 * @Classname OrderOperation
 * @Description TODO
 * @Date 2021/8/29 22:38
 */
@Data
@AllArgsConstructor
public class OrderOperation  extends Operation {


    private int tableId;
    private String dish;

    @Override
    public OperationResult execute() {
        System.out.println("order's executing startup with orderRequest: " + toString());
        //execute order logic
        System.out.println("order's executing complete");
        OrderOperationResult orderResponse = new OrderOperationResult(tableId, dish, true);
        return null;
    }
}
