package fun.chenqi.web;

import fun.chenqi.config.SpringConfiguration;
import fun.chenqi.domain.Account;
import fun.chenqi.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountTest {
    @Autowired
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
        int i = 13;
        service.delect(i);
    }

    @Test
    public void upd() throws SQLException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        IAccountService service = (IAccountService) ac.getBean("accountService");
        Account account = new Account();
        account.setId(14);
        account.setName("1234");
        account.setMoney(9000.0);
        service.update(account);
    }

    @Test
    public void sel() throws SQLException {
        service.select();
    }

    @Test
    public void zhuan() {
        try {
            service.transfer("aaa", "bbb", 100F);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
