package fun.chenqi.service.impl;

import fun.chenqi.dao.IAccountDao;
import fun.chenqi.domain.Account;
import fun.chenqi.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
/**
 * 该注解的属性和 xml 中的属性含义一致。
 * 该注解可以出现在接口上，类上和方法上。
 * 出现接口上，表示该接口的所有实现类都有事务支持。
 * 出现在类上，表示类中所有方法有事务支持
 * 出现在方法上，表示方法有事务支持。
 * 以上三个位置的优先级：方法>类>接口
 */
public class AccountServiceImpl implements IAccountService {


    @Autowired
    IAccountDao dao;


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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    /**propagation属性：
     * REQUIRED:如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。一般的选 择（默认值）
     * SUPPORTS:支持当前事务，如果当前没有事务，就以非事务方式执行（没有事务）
     * MANDATORY：使用当前的事务，如果当前没有事务，就抛出异常
     * REQUERS_NEW:新建事务，如果当前在事务中，把当前事务挂起。
     * NOT_SUPPORTED:以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
     * NEVER:以非事务方式运行，如果当前存在事务，抛出异常
     * NESTED:如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行REQUIRED 类似的操作。
     */
    public void transfer(String sourceName, String targetName, Float money) throws SQLException {
        Account accountByName1 = dao.findAccountByName(sourceName);
        Account accountByName2 = dao.findAccountByName(targetName);
        accountByName1.setMoney(accountByName1.getMoney() - money);
        accountByName2.setMoney(accountByName2.getMoney() + money);
        dao.update(accountByName1);
        int i = 1 / 0;
        dao.update(accountByName2);

    }
}