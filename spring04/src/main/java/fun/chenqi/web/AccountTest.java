package fun.chenqi.web;


import fun.chenqi.dao.config.SpringConfiguration;
import fun.chenqi.dao.domain.Account;
import fun.chenqi.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Iterator;

@RunWith(SpringJUnit4ClassRunner.class) // 使用@RunWith 注解替换原有运行器
@ContextConfiguration(classes = SpringConfiguration.class)
// 使用@ContextConfiguration 指定 spring 配置文件的位置
// classes 属性： 用于指定注解的类。 当不使用 xml 配置时，需要用此属性指定注解类的位置
// locations 属性： 用于指定配置文件的位置。如果是类路径下，需要用 classpath:表明
public class AccountTest {
    @Autowired
    private IAccountService service;

    @Test
    public void ins() throws SQLException {
        Account account = new Account();
        account.setName("1234");
        account.setMoney(1000.0);
        service.insert(account);
    }

    @Test
    public void del() throws SQLException {
        int i = 5;
        service.delect(i);
    }

    @Test
    public void upd() throws SQLException {
        Account account = new Account();
        account.setId(1);
        account.setName("1222");
        account.setMoney(9000.0);
        service.update(account);
    }

    @Test
    public void sel() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        Iterator<String> namesIterator = ((ClassPathXmlApplicationContext) ac).getBeanFactory().getBeanNamesIterator();
        while (namesIterator.hasNext()){
            System.out.println(namesIterator.next());
        }
        service.select();
    }
}
