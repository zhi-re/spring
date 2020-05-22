package fun.chenqi.service.impl;

import fun.chenqi.dao.IAccountDao;
import fun.chenqi.dao.domain.Account;
import fun.chenqi.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {


    // IAccountDao dao = new AccountDaoImpl();// 有依赖
    @Autowired
    IAccountDao dao; // 无依赖


    @Override
    public void insert(Account account) throws SQLException {
        dao.insert(account);

    }

    @Override
    public void delect(int i) throws SQLException {
        dao.delete(i);

    }

    @Override
    public void update(Account account) throws SQLException {

        dao.update(account);
    }

    @Override
    public List<Account> select() throws SQLException {
        return dao.select();

    }

    // 转账
    @Override
    public void transfer(String sourceName, String targetName, Float money) throws SQLException {
        Account accountByName1 = dao.findAccountByName(sourceName);
        Account accountByName2 = dao.findAccountByName(targetName);

        System.out.println("acc1" + accountByName1);
        System.out.println("acc2" + accountByName2);

        accountByName1.setMoney(accountByName1.getMoney() - money);
        accountByName2.setMoney(accountByName2.getMoney() + money);
        dao.update(accountByName1);
        int i = 1 / 0;
        dao.update(accountByName2);


    }
}
