package fun.chenqi.service.impl;

import fun.chenqi.dao.IAccountDao;
import fun.chenqi.dao.impl.AccountDaoImpl;
import fun.chenqi.service.IAccountService;

import java.util.Date;

public class AccountServiceImpl implements IAccountService {

    IAccountDao accountDao = null;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void save() {

        System.out.println("12121212");
    }

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }


}
