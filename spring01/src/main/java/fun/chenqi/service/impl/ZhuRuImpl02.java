package fun.chenqi.service.impl;

import fun.chenqi.service.IZhuRu;

import java.util.Date;

// set 方法注入
public class ZhuRuImpl02 implements IZhuRu {

    private String name;
    private int age;
    private Date bir;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBir(Date bir) {
        this.bir = bir;
    }

    @Override
    public void save() {
        System.out.println(name + " , " + age + " , " + bir);
    }
}
