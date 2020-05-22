package fun.chenqi.web;

import fun.chenqi.dao.IAccountDao;
import fun.chenqi.factory.StaticFactory;
import fun.chenqi.service.IAccountService;
import fun.chenqi.service.IZhuRu;
import fun.chenqi.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bean.xml"})
public class Client {
    public static void main(String[] args) {
        // 使用ApplicationContext接口，获取Spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 根据bean的ID获取对象
        IAccountService aService = (IAccountService) ac.getBean("accountService");
        aService.saveAccount();
        System.out.println(aService);


        IAccountService sf = (IAccountService) ac.getBean("accountServiceStatic");
        sf.saveAccount();
        System.out.println(sf);

        IAccountService inf = (IAccountService) ac.getBean("accountService1");
        inf.saveAccount();
        System.out.println(inf);


    }

    @Test
    public void test() {
        // 使用ApplicationContext接口，获取Spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 根据bean的ID获取对象
        IAccountService aService = (IAccountService) ac.getBean("accountService2");

        aService.saveAccount();
    }

    @Test
    public void zhuRu() {
        // 使用ApplicationContext接口，获取Spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 根据bean的ID获取对象
        IZhuRu zr = (IZhuRu) ac.getBean("zhuru01");
        zr.save();

    }
    @Test
    public void zhuRu02() {
        // 使用ApplicationContext接口，获取Spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 根据bean的ID获取对象
        IZhuRu zr = (IZhuRu) ac.getBean("zhuru02");
        zr.save();

    }
    @Test
    public void zhuRu03() {
        // 使用ApplicationContext接口，获取Spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        // 根据bean的ID获取对象
        IZhuRu zr = (IZhuRu) ac.getBean("zhuru03");
        zr.save();

    }

}
