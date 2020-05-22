package fun.chenqi.service.impl;

import fun.chenqi.dao.IAccountDao;
import fun.chenqi.dao.domain.Account;
import fun.chenqi.dao.impl.AccountDaoImpl;
import fun.chenqi.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Component
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
}
