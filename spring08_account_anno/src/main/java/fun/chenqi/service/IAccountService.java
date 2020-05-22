package fun.chenqi.service;

import fun.chenqi.domain.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountService {

    void insert(Account account) throws SQLException;

    void delect(int i) throws SQLException;

    void update(Account account) throws SQLException;

    List<Account> select() throws SQLException;

    // 添加一个转账方法
    void transfer(String sourceName, String targetName, Float money) throws SQLException;
}
