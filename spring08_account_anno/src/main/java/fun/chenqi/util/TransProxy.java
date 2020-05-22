package fun.chenqi.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("transProxy")
@Aspect // 表示当前类是一个切面类
public class TransProxy {
    @Autowired
    private TransactionManager transactionManager;

    /* public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
   }*/

    @Around("execution(* fun.chenqi.service.impl.*.transfer(..))")
    public Object aroundPrintLog(ProceedingJoinPoint pjp) {
        Object rtValue = null;
        try {
            System.out.println("环绕通知执行了。。。前置");
            transactionManager.begin();

            //获取参数
            Object[] args = pjp.getArgs();
            //执行方法
            rtValue = pjp.proceed(args);//明确的切入点方法调用

            System.out.println("环绕通知执行了。。。后置");
            transactionManager.commit();

            return rtValue;

        } catch (Throwable e) {
            System.out.println("环绕通知执行了。。。异常");
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            transactionManager.close();
            System.out.println("环绕通知执行了。。。最终");
        }
    }

}
