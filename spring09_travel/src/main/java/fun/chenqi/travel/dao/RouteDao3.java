package fun.chenqi.travel.dao;



import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import fun.chenqi.travel.model.Route;
import fun.chenqi.travel.util.JdbcUtils;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 旅游线路数据访问类
 */
public class RouteDao3 {

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

    public static void main(String[] args) throws UnsupportedEncodingException {
        int i = getCountByCid("5", "秒杀");
        System.out.println(i);
        List<Route> list = getRoutesByPage("5", 1, 4, "秒杀");
        for (Route route : list) {
            System.out.println(route);


        }
    }
}
