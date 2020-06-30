package fun.chenqi.web;

import fun.chenqi.dao.domain.Account;
import fun.chenqi.service.IAccountService;
import fun.chenqi.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Iterator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bean.xml"})
public class AccountTest {
    @Autowired
    private IAccountService service;

    @Autowired
    private void service2() {
        System.out.println("hello");
    }

    @Test
    public void ins() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("src/bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
        Account account = new Account();
        account.setName("1234");
        account.setMoney(1000.0);
        service.insert(account);
    }

    @Test
    public void del() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("src/bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
        int i = 5;
        service.delect(i);
    }

    @Test
    public void upd() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("src/bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
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
        while (namesIterator.hasNext()) {
            System.out.println(namesIterator.next());
        }
        //IAccountService service = (IAccountService) ac.getBean("accountServiceImpl");
        System.out.println("-------------");
        service2();
        System.out.println("-------------");
        service.select();
    }

    @Test
    public void testImport() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        Boss service = (Boss) ac.getBean("boss");
        System.out.println(service);

    }

}
