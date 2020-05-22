package fun.chenqi.util;

import fun.chenqi.service.IAccountService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 一个用于创建service代理对象的工厂：
//  此时只用它创建AccountServiceImpl的代理对象

public class BeanFactory {
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // 原始对象
    private IAccountService accountService;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    // 创建账户业务层实现类的代理对象
    public IAccountService getAccountService() {
        // 创建代理对象
        IAccountService proxyAccountService = (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 执行被代理对象的任何方法，都会经过该方法此处添加事务控制
                Object rtValue = null;
                try {
                    // 开启事务
                    transactionManager.begin();
                    rtValue = method.invoke(accountService, args);
                    transactionManager.commit();
                    return rtValue;
                } catch (Exception e) {
                    transactionManager.rollback();
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } finally {
                    transactionManager.close();
                }
            }
        });
        return proxyAccountService;
    }
}
