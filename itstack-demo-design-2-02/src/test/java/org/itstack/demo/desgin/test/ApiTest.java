package org.itstack.demo.desgin.test;

import org.itstack.demo.desgin.CacheService;
import org.itstack.demo.desgin.factory.ICacheAdapter;
import org.itstack.demo.desgin.factory.JDKProxy;
import org.itstack.demo.desgin.factory.impl.EGMCacheAdapter;
import org.itstack.demo.desgin.factory.impl.IIRCacheAdapter;
import org.itstack.demo.desgin.impl.CacheServiceImpl;
import org.junit.Test;

public class ApiTest {

/*
编写动态代理类的步骤

在创建动态代理类的时候，必须要实现InvocationHandler接口。
创建被代理的类及接口。
通过Proxy类的静态方法newInstance()方法创建一个代理及代理实例。
通过代理调用方法。
这个地方的代理类为CacheServiceImpl
*/

    @Test
    public void test_CacheService() throws Exception {

        CacheService proxy_EGM = JDKProxy.getProxy(CacheServiceImpl.class, new EGMCacheAdapter());
        proxy_EGM.set("user_name_01", "小傅哥");
        String val01 = proxy_EGM.get("user_name_01");
        System.out.println("测试结果：" + val01);
        CacheService proxy_IIR = JDKProxy.getProxy(CacheServiceImpl.class, new IIRCacheAdapter());
        proxy_IIR.set("user_name_01", "小傅哥");
        String val02 = proxy_IIR.get("user_name_01");
        System.out.println("测试结果：" + val02);


//        ICacheAdapter proxy_IIR = (ICacheAdapter) JDKProxy.getProxy1(new IIRCacheAdapter());
//        proxy_IIR.set("user_name_01", "小傅哥");
//        String val02 = proxy_IIR.get("user_name_01");
//        System.out.println("测试结果：" + val02);

    }

}
