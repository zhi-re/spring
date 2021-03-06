package fun.chenqi.service.impl;

import fun.chenqi.dao.IAccountDao;
import fun.chenqi.domain.Account;
import fun.chenqi.service.IAccountService;
import fun.chenqi.util.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

public class AccountServiceImpl implements IAccountService {

    IAccountDao dao; // 无依赖

    /*如果不使用动态代理就放开这些注释*/
    //TransactionManager txManger;

    //public void setTransactionManager(TransactionManager txManger) {
    //    this.txManger = txManger;
    // }

    // set 注入
    public void setDao(IAccountDao dao) {
        this.dao = dao;
    }

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
        // try {
        //开启事务
        //  txManger.begin();

        Account accountByName1 = dao.findAccountByName(sourceName);
        Account accountByName2 = dao.findAccountByName(targetName);
        accountByName1.setMoney(accountByName1.getMoney() - money);
        accountByName2.setMoney(accountByName2.getMoney() + money);
        dao.update(accountByName1);
        int i = 1 / 0;
        dao.update(accountByName2);
        // 提交事务
        //  txManger.commit();
        // } catch (SQLException e) {
        //  e.printStackTrace();
        ///  txManger.rollback();
        //  throw new RuntimeException();
        //  } finally {
        //  txManger.close();
        //  }


    }
}
