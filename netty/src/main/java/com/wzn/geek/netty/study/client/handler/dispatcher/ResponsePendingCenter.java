package com.wzn.geek.netty.study.client.handler.dispatcher;

import com.wzn.geek.netty.study.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author by will
 * @Classname ResponsePendingCenter
 * @Description TODO
 * @Date 2021/9/5 22:26
 */
public class ResponsePendingCenter {

    private Map<Long, OperationResultFuture> resultFutureMap = new ConcurrentHashMap<>();


    public void add(Long streamId, OperationResultFuture operationResultFuture) {
        this.resultFutureMap.put(streamId, operationResultFuture);
    }

    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture resultFuture = resultFutureMap.get(streamId);
        if (resultFuture != null) {
            resultFuture.setSuccess(operationResult);
            resultFutureMap.remove(streamId);
        }
    }


}
