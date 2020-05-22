package fun.chenqi.travel.dao;



import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import fun.chenqi.travel.model.Route;
import fun.chenqi.travel.model.RouteImg;
import fun.chenqi.travel.util.JdbcUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游线路数据访问类
 */
public class RouteDao {

    // 查询当前路线的总条数
    public static int getCountByCid(String cid, String rname) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        //String sql = "select count(*) from tab_route where rflag='1' and cid = ? and rname ";
        String sql = "select count(*) from tab_route where rflag='1' and cid = ? and rname LIKE '%" + rname + "%'";

        return jdbcTemplate.queryForObject(sql, int.class, cid);

    }

    public static List<Route> getRoutesByPage(String cid, int curPage, int pageSize, String rname) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        //String sql = "select * from tab_route where rflag= '1' and cid = ? limit ?,?";
        String sql = "select * from tab_route where rflag= '1' and cid = ? and rname LIKE '%" + rname + "%' limit ?,?";
        List<Route> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, (curPage - 1) * pageSize, pageSize);
        return list;
    }


    public Map<String, Object> findRouteByRid(int rid) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "SELECT * FROM tab_route r ,tab_seller s, tab_category c WHERE r.rflag=1 AND r.sid=s.sid AND r.cid=c.cid  AND r.rid =?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, rid);
        return map;
    }

    public List<RouteImg> findImglist(int rid) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "SELECT * FROM tab_route r, tab_route_img i WHERE r.rflag=1  AND r.rid=i.rid AND r.rid =?";
        List<RouteImg> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        return list;


    }

    public int addRoute(Route route) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "insert into tab_route2 values(null,?,?,?,?,?,?,?,?,?,?,?)";
        int i = jdbcTemplate.update(sql, route.getRname(), route.getPrice(), route.getRouteIntroduce(), route.getRflag(), route.getRdate(),
                route.getIsThemeTour(), route.getCount(), route.getCid(), route.getRimage(), route.getSid(), route.getSourceId());
        return i;

    }

    public int getCountByFavoriteRank(Map<String, Object> map) throws Exception {
       /* JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "select count(*) from tab_route where rflag = 1 and rname like '%" + map.get("rname") + "%'  and price between ? and ?";
        return jdbcTemplate.queryForObject(sql, int.class, map.get("starPrice"), map.get("endPrice"));*/
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        StringBuilder sqlBuilder = new StringBuilder("SELECT count(*) FROM tab_route WHERE rflag='1'");
        List<Object> list = new ArrayList<>();
        Object rname = map.get("rname");
        if (null != rname && !"".equals(rname.toString().trim())) {
            sqlBuilder.append(" and rname like ?");
            //将占位符的参数值加入动态参数列表集合
            list.add("%" + rname.toString().trim() + "%");
        }
        //判断搜索条件rname,如果有效就进行拼接过滤条件
        Object startPriceObj = map.get("startPrice");
        if (startPriceObj != null && !startPriceObj.toString().trim().equals("")) {
            sqlBuilder.append(" and price >= ?");
            //将占位符的参数值加入动态参数列表集合
            list.add(startPriceObj.toString());
        }
        //判断搜索条件rname,如果有效就进行拼接过滤条件
        Object endPriceObj = map.get("endPrice");
        if (endPriceObj != null && !endPriceObj.toString().trim().equals("")) {
            sqlBuilder.append(" and price <= ?");
            //将占位符的参数值加入动态参数列表集合
            list.add(endPriceObj.toString().trim());
        }

        //将参数动态列表结合List<Object>转换为参数数组Object[]
        Object[] params = list.toArray();
        //执行sql语句
        return jdbcTemplate.queryForObject(sqlBuilder.toString(), int.class, params);


    }

    /*  public List<Route> getRoutesByFavoriteRankByPage(int curPage, int pageSize, Map<String, Object> map) {
          JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
          String sql = "SELECT * FROM tab_route WHERE rflag = 1  AND rname LIKE '%" + map.get("rname") + "%' AND price >= ?  AND price <= ?  ORDER BY COUNT DESC LIMIT ?,?";
          List<Route> route = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), map.get("starPrice"), map.get("endPrice"), curPage, pageSize);
          return route;
      }*/
    public List<Route> getRoutesByFavoriteRankByPage(int curPage, int pageSize, Map<String, Object> map) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        //按照收藏数量降序的分页sql语句
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM tab_route WHERE rflag='1'");
        //定义动态参数列表集合
        List<Object> paramList = new ArrayList<Object>();
        //判断搜索条件rname,如果有效就进行拼接过滤条件
        Object rnameObj = map.get("rname");
        if (rnameObj != null && !rnameObj.toString().trim().equals("")) {
            sqlBuilder.append(" and rname like ?");
            //将占位符的参数值加入动态参数列表集合
            paramList.add("%" + rnameObj.toString().trim() + "%");
        }
        //判断搜索条件rname,如果有效就进行拼接过滤条件
        Object startPriceObj = map.get("startPrice");
        if (startPriceObj != null && !startPriceObj.toString().trim().equals("")) {
            sqlBuilder.append(" and price >= ?");
            //将占位符的参数值加入动态参数列表集合
            paramList.add(startPriceObj.toString());
        }
        //判断搜索条件rname,如果有效就进行拼接过滤条件
        Object endPriceObj = map.get("endPrice");
        if (endPriceObj != null && !endPriceObj.toString().trim().equals("")) {
            sqlBuilder.append(" and price <= ?");
            //将占位符的参数值加入动态参数列表集合
            paramList.add(endPriceObj.toString().trim());
        }
        sqlBuilder.append(" ORDER BY COUNT DESC LIMIT ?,?");


        //将start,length加入动态参数列表集合
        paramList.add(curPage);
        paramList.add(pageSize);
        //将参数动态列表结合List<Object>转换为参数数组Object[]
        Object[] params = paramList.toArray();
        //执行sql语句
        return jdbcTemplate.query(sqlBuilder.toString(), new BeanPropertyRowMapper<Route>(Route.class), params);
    }

    public static void main(String[] args) throws Exception {
        RouteDao dao = new RouteDao();
        Map<String, Object> map = new HashMap();
        map.put("rname", "秒杀");
        map.put("starPrice", 1);
        map.put("endPrice", 2000);
        List<Route> routesByFavoriteRankByPage = dao.getRoutesByFavoriteRankByPage(0, 4, map);
        for (Route route : routesByFavoriteRankByPage) {
            System.out.println(route);

        }

    }

}
