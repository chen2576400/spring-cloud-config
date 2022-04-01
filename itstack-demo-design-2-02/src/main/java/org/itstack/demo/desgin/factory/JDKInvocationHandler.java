package org.itstack.demo.desgin.factory;

import org.itstack.demo.desgin.util.ClassLoaderUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 在JDK动态代理中，要实现代理逻辑类必须实现java.lang.reflect.InvocationHandler接口，里面定义了invoke方法，并提供接口数组用于下挂代理对象。
 */
public class JDKInvocationHandler implements InvocationHandler {
    //要被代理的真实对象
    private ICacheAdapter cacheAdapter;

    public JDKInvocationHandler(ICacheAdapter cacheAdapter) {
        this.cacheAdapter = cacheAdapter;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("在调度真实对象之前的服务");
        //这个地方的method是和  interfaceClass保持一致的 （如果是通过Class.getInterfaces()则要看该class有没有new,）
        Object object=ICacheAdapter.class.getMethod(method.getName(), ClassLoaderUtils.getClazzByArgs(args)).invoke(cacheAdapter, args);
        System.out.println("在调度真实对象之后的服务");
        return object;
    }

}
