package fun.chenqi.proxy;

// 模拟经纪公司的标准
public interface IActor {
    /**
     * 基本表演
     */
    public void basicAct(Float money);

    /**
     * 危险的表演
     */
    public void dangerAct(Float money);

}
