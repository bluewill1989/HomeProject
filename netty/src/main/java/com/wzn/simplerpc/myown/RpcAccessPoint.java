package com.wzn.simplerpc.myown;

import java.net.URI;

/**
 * @author by will
 * @Classname RpcAccessPoint
 * @Description TODO
 * @Date 2021/4/6 23:37
 */
public interface RpcAccessPoint  extends  Cloneable{

    <T> T getRemoteService(URI uri, Class<T> serviceClass);

    <T> URI addServiceProvider(T service, Class<T> serviceClass);

    Cloneable startServer() throws Exception;

}
