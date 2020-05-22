package fun.chenqi.util;

import org.aspectj.lang.ProceedingJoinPoint;

import java.sql.SQLException;

public class TransProxy {
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // 前置通知
    public void before() throws SQLException {
        System.out.println("前置");
        transactionManager.begin();
    }

    // 最终通知
    public void after() {
        System.out.println("最终");
        transactionManager.close();
    }

    // 后置通知
    public void afterTurning() {
        System.out.println("后置");
        transactionManager.commit();
        System.out.println("后置2");
    }

    // 异常通知
    public void afterThrowing() {
        System.out.println("异常");
        transactionManager.rollback();

    }

    // 环绕通知
    public Object aroundPrintLog(ProceedingJoinPoint pjp) {
        Object rtValue = null;
        try {
            transactionManager.begin();
            System.out.println("环绕通知执行了。。。前置");
            //获取参数
            Object[] args = pjp.getArgs();
            //执行方法
            rtValue = pjp.proceed(args);//明确的切入点方法调用
            transactionManager.commit();
            System.out.println("环绕通知执行了。。。后置");
            return rtValue;

        } catch (Throwable e) {
            transactionManager.rollback();
            System.out.println("环绕通知执行了。。。异常");
            throw new RuntimeException(e);
        } finally {
            transactionManager.close();
            System.out.println("环绕通知执行了。。。最终");
        }
    }

}
