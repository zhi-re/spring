package fun.chenqi.factory;

import fun.chenqi.service.IAccountService;
import fun.chenqi.service.impl.AccountServiceImpl;

public class StaticFactory {

    public static IAccountService createAccountService() {
        return new AccountServiceImpl();
     }
}
