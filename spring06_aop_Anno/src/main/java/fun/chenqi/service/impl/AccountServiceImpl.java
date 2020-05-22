package fun.chenqi.service.impl;

import fun.chenqi.service.IAccountService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    public void save() {
        System.out.println("save OK！");
    }
    public void update() {
        System.out.println("update OK！");

    }
    public void delete() {
        System.out.println("delete OK！");

    }
}
