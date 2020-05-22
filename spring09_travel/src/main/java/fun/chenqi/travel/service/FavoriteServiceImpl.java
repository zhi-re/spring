package fun.chenqi.travel.service;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;

import fun.chenqi.travel.dao.IFavoriteDao;
import fun.chenqi.travel.model.Favorite;
import fun.chenqi.travel.model.PageBean;
import fun.chenqi.travel.model.Route;
import fun.chenqi.travel.model.User;
import fun.chenqi.travel.util.JdbcUtils;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FavoriteServiceImpl implements IFavoriteService {

    public void setDaoImpl(IFavoriteDao daoImpl) {
        this.daoImpl = daoImpl;
    }

    IFavoriteDao daoImpl ;

    //
    @Override
    public boolean isFavorite(int uid, int rid) throws Exception {
        Favorite favorite = daoImpl.isFavorite(uid, rid);
        if (null == favorite) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void addFavorite(User user, Route route) throws Exception {
        //利用事务调用dao层2个功能,插入语句,更新语句
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setRoute(route);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        favorite.setDate(simpleDateFormat.format(new Date()));

        //获取数据源
        DataSource dataSource = JdbcUtils.getDataSource();
        //实例jdbcTemplate
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //启动事务管理器（获取datasource操作数据库连接对象并绑定到当前线程中）
        TransactionSynchronizationManager.initSynchronization();
        //从数据源中获取jdbcTemplate操作的当前连接对象
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            //设置连接不自动提交事务
            connection.setAutoCommit(false);
            //调用数据访问接口添加收藏
            daoImpl.addFavorite(jdbcTemplate, favorite);
            //更新旅游线路收藏数量+1
            daoImpl.updateRouteFavoriteNum(jdbcTemplate, route.getRid());
            //手动提交事务
            connection.commit();
        } catch (Exception e) {
            //事务回滚
            connection.rollback();
            System.out.println("回滾了");
            throw e;//抛出异常，说明执行失败
        } finally {
            try {
                //释放当前线程与连接对象的绑定
                TransactionSynchronizationManager.clearSynchronization();
                //重置当前连接为自动提交事务
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int findCount(int rid) {
        return daoImpl.findCount(rid);
    }


    @Override
    public PageBean getPageBean(User user, int curPag) throws Exception {
        //创建PageBean对象 1_分页参数 2_当前页中的产品信息
        //int curPage ,int count,int pageSize
        int count = daoImpl.findCounts(user);
        //1_分页的参数计算好
        PageBean pageBean = new PageBean(curPag, count, 4);
        //2_查询当前页中的产品信息
        List<Map<String, Object>> list = daoImpl.findCurPage(user, pageBean.getStartIndex(), pageBean.getPageSize());
        //希望pageBean存放list,list中存放的Favorite对象
        List<Favorite> favoriteList = convertMapToList(list);
        pageBean.setData(favoriteList);

        return pageBean;
    }

    private List<Favorite> convertMapToList(List<Map<String, Object>> list) throws InvocationTargetException, IllegalAccessException {
        List<Favorite> flist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Route route = new Route();
            BeanUtils.populate(route, list.get(i));

            Favorite favorite = new Favorite();
            BeanUtils.populate(favorite, list.get(i));

            favorite.setRoute(route);
            flist.add(favorite);
        }
        return flist;
    }

    // 收藏排行榜
    @Override
    public PageBean<Route> findRoutesFavoriteRank(int curPag) throws Exception {
        //创建PageBean对象 1_分页参数 2_当前页中的产品信息
        //int curPage ,int count,int pageSize
        int count = daoImpl.findCountt();
        //1_分页的参数计算好
        PageBean<Route> pageBean = new PageBean(curPag, count, 4);
        //2_查询当前页中的产品信息
        List<Route> list = daoImpl.findRoutesFavoriteRank(pageBean.getStartIndex(), pageBean.getPageSize());
        pageBean.setData(list);
        return pageBean;
    }


}
