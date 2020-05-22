package fun.chenqi.dao.impl;


import fun.chenqi.dao.IAccountDao;
import fun.chenqi.domain.Account;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.SQLException;
import java.util.List;

public class AccountDaoImpl implements IAccountDao {


    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> select() throws SQLException {
        String sql = "select * from account";
        List<Account> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Account>(Account.class));
        if (!list.isEmpty()) {
            System.out.println(list);
            return list;
        }
        return null;


    }

    @Override
    public void update(Account account) throws SQLException {
        String sql = "update account set money = ? where id = ? ";
        int i = jdbcTemplate.update(sql, account.getMoney(), account.getId());
        if (i > 0) {
            System.out.println("OK!");
        }
    }

    @Override
    public void delete(int i) throws SQLException {
        String sql = "delete from account where id = ?";
        int j = jdbcTemplate.update(sql, i);
        if (j > 0) {
            System.out.println("OK!");
        }
    }

    @Override
    public void insert(Account account) throws SQLException {
        String sql = "insert into account values(null,?,?)";
        int j = jdbcTemplate.update(sql, account.getName(), account.getMoney());
        if (j > 0) {
            System.out.println("OK!");
        }

    }

    @Override
    public Account findAccountByName(String name) {
        String sql = "select * from account where name = ?";
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Account>(Account.class), name);
        if (account != null) {
            return account;
        } else {
            return null;
        }
    }
}
