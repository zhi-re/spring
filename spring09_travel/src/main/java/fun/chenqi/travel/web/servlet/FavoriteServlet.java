package fun.chenqi.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fun.chenqi.travel.model.PageBean;
import fun.chenqi.travel.model.ResultInfo;
import fun.chenqi.travel.model.Route;
import fun.chenqi.travel.model.User;
import fun.chenqi.travel.service.IFavoriteService;
import java.io.IOException;

@WebServlet(name = "FavoriteServlet", urlPatterns = "/favorite")
public class FavoriteServlet extends BaseServlet {
    public void setServiceImpl(IFavoriteService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    // 使用接口编程
    private IFavoriteService serviceImpl ;

    // 添加收藏
    protected void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回结果对象
        ResultInfo resultInfo = null;
        try {
            // 获取cid
            String r = request.getParameter("rid");
            int rid = Integer.parseInt(r);
            User user = (User) request.getSession().getAttribute("loginUser");
            if (null == user) {
                //没有登录
                //第一个参数：true,代表正常返回数据
                //第二个参数：false,代表没有登录，添加收藏失败
                //第三个参数：错误消息
                resultInfo = new ResultInfo(true, false, null);
            } else {
                Route route = new Route();
                route.setRid(rid);

                //实现添加收藏:向数据库执行了2个操作,插入语句tab_favorite 更新tab_route的数量
                serviceImpl.addFavorite(user, route);

                int count = serviceImpl.findCount(rid);
                resultInfo = new ResultInfo(true, count, null);
            }
        } catch (Exception e) {
            resultInfo = new ResultInfo(false);
        }
        //将ResultInfo转换为json
        String jsonDate = new ObjectMapper().writeValueAsString(resultInfo);
        // System.out.println(jsonDate);
        //输出给浏览器
        response.getWriter().write(jsonDate);
    }


    // 查看我的收藏
    protected void findMyFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回结果对象
        ResultInfo resultInfo = null;
        try {
            //获取用户信息
            User user = (User) request.getSession().getAttribute("loginUser");
            //获取当前页
            String curPage = request.getParameter("curPage");
            int curPag = 1;
            if (!"".equals(curPage) && null != curPage) {
                curPag = Integer.parseInt(curPage);
            }
            //调用业务层方法,返回PageBean对象，
            //1_当前页中的产品数据
            //2_分页的参数信息
            PageBean pageBean = serviceImpl.getPageBean(user, curPag);
            resultInfo = new ResultInfo(true, pageBean, null);


        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false);
        }
        //将ResultInfo转换为json
        String jsonDate = new ObjectMapper().writeValueAsString(resultInfo);
        // System.out.println(jsonDate);
        //输出给浏览器
        response.getWriter().write(jsonDate);


    }
}
