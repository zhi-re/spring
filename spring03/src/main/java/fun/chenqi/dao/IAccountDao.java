package fun.chenqi.dao;

import fun.chenqi.dao.domain.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountDao {
    List<Account> select() throws SQLException;

    void update(Account account) throws SQLException;

    void delete(int i) throws SQLException;

    void insert(Account account) throws SQLException;


    // 按用户名查找
      Account  findAccountByName(String name);
}
