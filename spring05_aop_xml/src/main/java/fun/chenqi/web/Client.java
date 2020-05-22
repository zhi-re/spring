package fun.chenqi.web;

import fun.chenqi.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
        service.save();
    }
}
