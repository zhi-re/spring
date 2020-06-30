package fun.chenqi.web;

import org.springframework.stereotype.Component;

/**
 * @Author: chenqi
 * @Date: 2020.6.30 10:30
 */
@Component
public class Car {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
