package fun.chenqi.service.impl;

import fun.chenqi.service.IZhuRu;

import java.util.Date;

// 构造函数注入
// 顾名思义，就是使用类中的构造函数，给成员变量赋值。注意，赋值的操作不是我们自己做的，而是通过配置
// 的方式，让 spring 框架来为我们注入
public class ZhuRuImpl implements IZhuRu {

    private String name;
    private int age;
    private Date bir;

    public ZhuRuImpl(String name, int age, Date bir) {
        this.name = name;
        this.age = age;
        this.bir = bir;
    }

    @Override
    public void save() {
        System.out.println(name + " , " + age + " , " + bir);

    }
}
