package com.wzn.simplerpc.myown;

import java.io.IOException;
import java.net.URI;

/**
 * @author by will
 * @Classname NameService
 * @Description TODO
 * @Date 2021/4/6 23:39
 */
public interface NameService {

    void registerService(String serviceName, URI uri) throws IOException;

    URI lookupService(String serviceName) throws IOException;
}
