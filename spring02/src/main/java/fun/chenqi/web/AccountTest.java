package fun.chenqi.web;

import fun.chenqi.domain.Account;
import fun.chenqi.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Iterator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bean.xml"})
public class AccountTest {
    @Autowired
    @Qualifier("proxyAccountService")
    private IAccountService service;

    @Test
    public void ins() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
        Account account = new Account();
        account.setName("1234");
        account.setMoney(1000.0);
        service.insert(account);
    }

    @Test
    public void del() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
        int i = 5;
        service.delect(i);
    }

    @Test
    public void upd() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
        Account account = new Account();
        account.setId(1);
        account.setName("1222");
        account.setMoney(9000.0);
        service.update(account);
    }

    @Test
    public void sel() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext(" bean.xml ");
        Iterator<String> namesIterator = ((ClassPathXmlApplicationContext) ac).getBeanFactory().getBeanNamesIterator();
        while (namesIterator.hasNext()){
        System.out.println(namesIterator.next());
        }
        IAccountService service = (IAccountService) ac.getBean("accountService");
        service.select();
    }

    @Test
    public void zhuan() {
       // ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
       // IAccountService service = (IAccountService) ac.getBean("accountService");
       // IAccountService service = (IAccountService) ac.getBean("proxyAccountService");
        try {
            service.transfer("aaa", "bbb", 1000F);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
