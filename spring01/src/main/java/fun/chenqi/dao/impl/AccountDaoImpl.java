package fun.chenqi.dao.impl;

import fun.chenqi.dao.IAccountDao;

public class AccountDaoImpl implements IAccountDao {
    @Override
    public void saveAccount() {
        System.out.println("保存了账户信息！");
    }
}
