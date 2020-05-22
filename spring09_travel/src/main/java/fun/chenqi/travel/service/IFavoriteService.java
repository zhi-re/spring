package fun.chenqi.travel.service;



import fun.chenqi.travel.model.PageBean;
import fun.chenqi.travel.model.Route;
import fun.chenqi.travel.model.User;

public interface IFavoriteService {
    boolean isFavorite(int uid, int rid) throws Exception;

    void addFavorite(User user, Route route) throws Exception;

    int findCount(int rid);

    PageBean getPageBean(User user, int curPag) throws Exception;

    PageBean<Route> findRoutesFavoriteRank(int curPag) throws Exception;
}
