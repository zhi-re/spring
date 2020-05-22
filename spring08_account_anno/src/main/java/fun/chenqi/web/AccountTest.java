package fun.chenqi.web;

import fun.chenqi.domain.Account;
import fun.chenqi.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bean.xml"})
public class AccountTest {
    @Autowired
    private IAccountService service;


    // 转账
    @Test
    public void zhuan() {
        try {
            service.transfer("aaa", "bbb", 1000F);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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
        // ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // IAccountService service = (IAccountService) ac.getBean("accountService");
        service.select();
    }

}
