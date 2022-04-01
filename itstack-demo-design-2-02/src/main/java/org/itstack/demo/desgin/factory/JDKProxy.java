package org.itstack.demo.desgin.factory;

import org.itstack.demo.desgin.util.ClassLoaderUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy {

    /**
     *
     * @param interfaceClass 要代理的真实对象
     * @param cacheAdapter   建立中介类实例(最终InvocationHandler的method都会代理的是接口)
     *                       1：如getProxy这里的代理中介类是全新的一个 cacheServerImpl,他这里和CacheAdapter无任何关系
     *                       那么这里的method就要通过 getMethod(String name, Class<?>... parameterTypes)
     *                       2：如getProxy1 这里代理中介类本身就是它的实例,所以可以直接method就是他本身即可执行
     *
     * @param <T>
     * @return              动态产生一个代理类
     * @throws Exception
     */
    public static <T> T getProxy(Class<T> interfaceClass, ICacheAdapter cacheAdapter) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] classes = interfaceClass.getInterfaces();//它能够获得这个对象所实现的所有接口(如果一个class有多个实现接口，那么数组长度就是实现接口数)
        Object o = Proxy.newProxyInstance(
                classLoader,/*类加载器*/
                classes,/*让代理对象和目标对象实现相同接口*/
                new InvocationHandler() {/*代理对象的方法最终都会被JVM导向它的invoke方法*/
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Method method1 = cacheAdapter.getClass().getMethod(method.getName(), ClassLoaderUtils.getClazzByArgs(args));
                        System.out.println(method.getName() + "方法开始执行...");
                        Object result = method1.invoke(cacheAdapter, args);
                        System.out.println(result);
                        System.out.println(method.getName() + "方法执行结束...");
                        return result;
                    }
                }
        );
        return  (T)o;
//        return (T) Proxy.newProxyInstance(classLoader, new Class[]{classes[0]}, new JDKInvocationHandler(cacheAdapter));
    }


    public static Object getProxy1(ICacheAdapter cacheAdapter) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?>[] classes = cacheAdapter.getClass().getInterfaces();
        Object o = Proxy.newProxyInstance(
                classLoader,/*类加载器*/
                classes,/*让代理对象和目标对象实现相同接口*/
                new InvocationHandler() {/*代理对象的方法最终都会被JVM导向它的invoke方法*/
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Method method1 = cacheAdapter.getClass().getMethod(method.getName(), ClassLoaderUtils.getClazzByArgs(args));
                        System.out.println(method.getName() + "方法开始执行...");
                        Object result = method.invoke(cacheAdapter, args);
                        System.out.println(result);
                        System.out.println(method.getName() + "方法执行结束...");
                        return result;
                    }
                }
        );
        return   o;
    }

    }
