package fun.chenqi.travel.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import fun.chenqi.travel.model.Favorite;
import fun.chenqi.travel.model.Route;
import fun.chenqi.travel.model.User;
import fun.chenqi.travel.util.JdbcUtils;
import java.util.List;
import java.util.Map;


public class FavoriteDaoImpl implements IFavoriteDao {

    @Override
    public Favorite isFavorite(int uid, int rid) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "select * from tab_favorite where uid = ? and rid = ?";
        try {
            Favorite favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), uid, rid);
            System.out.println(favorite);
            return favorite;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addFavorite(JdbcTemplate jdbcTemplate, Favorite favorite) throws Exception {
        String sql = "insert into tab_favorite values(?,?,?)";
        jdbcTemplate.update(sql, favorite.getRoute().getRid(), favorite.getDate(), favorite.getUser().getUid());

    }

    @Override
    public void updateRouteFavoriteNum(JdbcTemplate jdbcTemplate, int rid) throws Exception {
        String sql = "update tab_route set count = count+1 where rid = ?";
        jdbcTemplate.update(sql, rid);

    }

    @Override
    public int findCount(int rid) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "select * from tab_route where rid = ?";
        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return route.getCount();
    }


    public int findCounts(User user) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "select count(*) from tab_favorite where uid = ?";
        int i = jdbcTemplate.queryForObject(sql, int.class, user.getUid());
        return i;
    }

    @Override
    public List<Map<String, Object>> findCurPage(User user, int startIndex, int pageSize) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "SELECT * FROM tab_favorite f , tab_route r WHERE f.rid = r.rid AND f.uid = ? LIMIT ?,?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, user.getUid(), startIndex, pageSize);
        //System.out.println(list);
        return list;

    }

    @Override
    public int findCountt() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "SELECT COUNT(*) FROM  tab_route  WHERE rflag = 1";
        return jdbcTemplate.queryForObject(sql, int.class);
    }

    // 收藏排行榜
    @Override
    public List<Route> findRoutesFavoriteRank(int startIndex, int pageSize) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
        String sql = "SELECT * FROM  tab_route  WHERE rflag = 1 order by count desc limit ?,?";
        List<Route> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), startIndex, pageSize);
        return list;
    }

    public static void main(String[] args) throws Exception {
        FavoriteDaoImpl i = new FavoriteDaoImpl();
        // Favorite favorite = i.isFavorite(1, 1);
        //System.out.println(favorite);
        // User user = new User();
        // user.setUid(1);
        // i.findCurPage(user, 0, 4);
        List<Route> routesFavoriteRank = i.findRoutesFavoriteRank(0, 4);
        System.out.println(routesFavoriteRank);

    }

}
