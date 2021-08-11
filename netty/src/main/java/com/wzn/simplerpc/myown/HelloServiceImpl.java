package com.wzn.simplerpc.myown;

import lombok.extern.slf4j.Slf4j;

/**
 * @author by will
 * @Classname HelloServiceImpl
 * @Description TODO
 * @Date 2021/4/7 10:52
 */
@Slf4j
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        log.info("hello方法进来了");
        return "hello: " + name;
    }
}
