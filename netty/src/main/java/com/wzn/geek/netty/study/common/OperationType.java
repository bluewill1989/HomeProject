package com.wzn.geek.netty.study.common;



import com.wzn.geek.netty.study.order.OrderOperation;
import com.wzn.geek.netty.study.order.OrderOperationResult;

import java.util.function.Predicate;

public enum OperationType {

//    AUTH(1, AuthOperation.class, AuthOperationResult.class),
//    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> responseClass) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = responseClass;
    }

    public int getOpCode(){
        return opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }

    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }

    public static OperationType fromOpCode(int type){
        return getOperationType(requestType -> requestType.opCode == type);
    }

    public static OperationType fromOperation(Operation operation){
        //高级的用法,对应下面比较精彩的一段代码
        return getOperationType(requestType -> requestType.operationClazz == operation.getClass());
    }

    /**
     * 比较精彩的一段代码
     * @param predicate
     * @return
     */
    private static OperationType getOperationType(Predicate<OperationType> predicate){
        OperationType[] values = values();
        for (OperationType operationType : values) {
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        throw new AssertionError("no found type");
    }

}
