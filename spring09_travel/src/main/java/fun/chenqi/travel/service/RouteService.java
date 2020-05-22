package fun.chenqi.travel.service;


import org.apache.commons.beanutils.BeanUtils;

import fun.chenqi.travel.dao.RouteDao;
import fun.chenqi.travel.model.*;
import java.util.List;
import java.util.Map;

public class RouteService {
    public void setDao(RouteDao dao) {
        this.dao = dao;
    }

    private RouteDao dao;

    public PageBean<Route> getPageBean(String cid, int curPage, String rname) {
        // 定义PageBean对象
        PageBean pageBean = new PageBean();
        //当前页
        pageBean.setCurPage(curPage);
        //每页大小,每页3条数据
        int pageSize = 3;
        pageBean.setPageSize(pageSize);

        //获取当前分类总记录数---转换为动态获取数据库数据
        int count = dao.getCountByCid(cid, rname);
        pageBean.setCount(count);
        //根据分类查询当前页数据列表---转换为动态获取数据库数据
        List<Route> list = dao.getRoutesByPage(cid, curPage, pageSize, rname);
        pageBean.setData(list);
        //总页数
        int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        pageBean.setTotalPage(totalPage);
        return pageBean;

    }

    public  Route findRouteByRid(int rid) throws Exception {
        Map<String, Object> map = dao.findRouteByRid(rid);

        Route route = new Route();
        Category category = new Category();
        Seller seller = new Seller();
        //将map中的数据自动填充到route对象
        BeanUtils.populate(route, map);
        BeanUtils.populate(category, map);
        BeanUtils.populate(seller, map);

        //将分类数据封装在route对象
        route.setCategory(category);
        //将商家数据封装在route对象
        route.setSeller(seller);

        // 图片单独查询 用list保存 因为有多张图片
        List<RouteImg> list = dao.findImglist(rid);
        //将查询到当前产品图片路径封装到route对象
        route.setRouteImgList(list);
        //System.out.println(route);
        return route;

    }

    public  void main(String[] args) throws Exception {
        Route route = findRouteByRid(5);
        System.out.println(route);
    }

    // 通过爬虫插入数据
    public boolean addRoute(Route route) throws Exception {
        int i = dao.addRoute(route);
        if (i > 1) {
            return true;
        } else {
            return false;
        }
    }

    public PageBean<Route> getPageBeanByFavoriteRank(int curPag, Map<String, Object> map) throws Exception {

        int count = dao.getCountByFavoriteRank(map);
        // int curPage ,int count,int pageSize
        int pageSize = 4;
        PageBean<Route> pageBean = new PageBean<>(curPag, count, pageSize);
        //  System.out.println("startindex "+pageBean.getStartIndex());
        //  System.out.println("pagesize "+pageBean.getPageSize());
        //  System.out.println("totpage "+pageBean.getTotalPage());
        //  System.out.println("count "+pageBean.getCount());
        List<Route> list = dao.getRoutesByFavoriteRankByPage(pageBean.getCurPage(), pageBean.getPageSize(), map);
        pageBean.setData(list);

        return pageBean;

    }
}
