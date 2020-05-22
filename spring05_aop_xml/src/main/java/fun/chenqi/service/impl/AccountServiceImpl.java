package fun.chenqi.service.impl;

import fun.chenqi.service.IAccountService;

public class AccountServiceImpl implements IAccountService {
    public void save() {
        System.out.println("save OK！");
        int i = 1 / 0;
        System.out.println("hahah");
    }

    public void update() {
        System.out.println("update OK！");

    }

    public void delete() {
        System.out.println("delete OK！");

    }
}
