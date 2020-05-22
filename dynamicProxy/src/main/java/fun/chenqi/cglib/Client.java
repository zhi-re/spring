package fun.chenqi.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Client {
    public static void main(String[] args) {
        //早期：直接去家里找
        Actor actor = new Actor();

        //通过经纪公司来找演员
        /**
         * 动态代理
         * 作用：不修改源码的基础上，对已有方法增强
         * 特点：字节码是随用随创建，随用随加载。
         * 基于子类的动态代理：
         * 提供者：第三方cglib库
         * 要求：被代理对象不能是最终类。不能被final修饰
         * 涉及的类：Enhancer
         * 涉及的方法：create
         * 方法的参数：
         * Class：字节码。被代理对象的字节码。固定写法。
         * Callback：如何代理的接口。谁用谁写。用于增强方法的。需要我们自己提供一个该接口的实现类。
         * 通常情况下可以写成匿名内部类。
         * 我们需要使用它的子接口：MethodInterceptor
         * 策略模式：
         * 数据已经有了
         * 目的明确。
         * 达成目标的过程即是策略。
         */
        Actor actorCglib = (Actor) Enhancer.create(actor.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                Object rtValue = null;
                Float money = (Float) args[0];
                if ("basicAct".equals(method.getName())) {
                    if (money > 1000F) {
                        rtValue = method.invoke(actor, money * 0.8F);
                    }
                }
                if ("dangerAct".equals(method.getName())) {
                    if (money > 1000F) {
                        rtValue = method.invoke(actor, money * 0.9F);
                    }
                }
                return rtValue;
            }
        });

        actorCglib.basicAct(2000F);
        actorCglib.dangerAct(2000F);

    }

}
