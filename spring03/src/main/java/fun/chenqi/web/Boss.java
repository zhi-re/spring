package fun.chenqi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: chenqi
 * @Date: 2020.6.30 10:29
 */
@Component
public class Boss {

    private Car car;

    public Car getCar() {
        return car;
    }
// 可以作用在普通方法上
//    @Autowired
//    public void setCar(Car car) {
//        this.car = car;
//    }

    // 可以作用在构造方法上
    @Autowired
    public Boss(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}