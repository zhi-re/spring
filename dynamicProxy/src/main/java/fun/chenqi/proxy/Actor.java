package fun.chenqi.proxy;

// 一个演员
public class Actor implements IActor {
    @Override
    public void basicAct(Float money) {
        System.out.println("拿到钱 开始基本表演" + money);
    }

    @Override
    public void dangerAct(Float money) {
        System.out.println("拿到钱 开始危险表演" + money);
    }
}
