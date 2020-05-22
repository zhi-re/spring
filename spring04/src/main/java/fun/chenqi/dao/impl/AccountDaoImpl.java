package fun.chenqi.dao.impl;


import fun.chenqi.dao.IAccountDao;
import fun.chenqi.dao.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountDaoImpl implements IAccountDao {
    @Autowired
    private QueryRunner qr;

    @Override
    public List<Account> select() throws SQLException {
        List<Account> list = null;
        try {
            String sql = "select * from account";
            list = qr.query(sql, new BeanListHandler<Account>(Account.class));
            for (Account account : list) {
                System.out.println(account);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Account account) throws SQLException {
        String sql = "update account set name = ? , money = ? where id = ?";
        int i = qr.update(sql, account.getName(), account.getMoney(), account.getId());
        if (i > 0) {
            System.out.println("OK!");
        }

    }

    @Override
    public void delete(int i) throws SQLException {
        String sql = "delete from account where id = ?";
        int a = qr.update(sql, i);
        if (a > 0) {
            System.out.println("OK!");
        }
    }

    @Override
    public void insert(Account account) throws SQLException {
        String sql = "insert into account values(null,?,?)";
        int i = qr.update(sql, account.getName(), account.getMoney());
        if (i > 0) {
            System.out.println("OK!");
        }

    }


}
