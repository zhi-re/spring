package fun.chenqi.web;

import fun.chenqi.service.IAccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        Iterator<String> namesIterator = ((ClassPathXmlApplicationContext) ac).getBeanFactory().getBeanNamesIterator();
        while (namesIterator.hasNext()){
            System.out.println(namesIterator.next());
        }
        System.out.println();
        IAccountService service = (IAccountService) ac.getBean("accountService");
        service.save();
    }
}
