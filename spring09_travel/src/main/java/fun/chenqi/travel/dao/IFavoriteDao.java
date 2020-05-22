package fun.chenqi.travel.dao;


import org.springframework.jdbc.core.JdbcTemplate;

import fun.chenqi.travel.model.Favorite;
import fun.chenqi.travel.model.Route;
import fun.chenqi.travel.model.User;
import java.util.List;
import java.util.Map;

public interface IFavoriteDao {
    Favorite isFavorite(int uid, int rid) throws Exception;

    void addFavorite(JdbcTemplate jdbcTemplate, Favorite favorite) throws Exception;

    void updateRouteFavoriteNum(JdbcTemplate jdbcTemplate, int rid) throws Exception;

    int findCount(int rid);

    int findCounts(User user) throws Exception;

    List<Map<String, Object>> findCurPage(User user, int startIndex, int pageSize);

    int findCountt();

    List<Route> findRoutesFavoriteRank(int startIndex, int pageSize) throws Exception;
}
