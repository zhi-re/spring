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
import fun.chenqi.travel.service.RouteService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteService();

    public void setServiceImpl(IFavoriteService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    // 使用接口编程
    private IFavoriteService serviceImpl ;

    // 查询国内游信息
    protected void findRouteListByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回结果对象
        ResultInfo resultInfo = null;
        try {
            int curPage = 1; // 默认当前页为第一页
            // 获取用户当前访问第几页
            String curPagestr = request.getParameter("curPage");
            // 获取导航栏cid
            String cid = request.getParameter("cid");
            String rname = request.getParameter("rname");
            // System.err.println(rname);
            //判断用户请求第几页有效性
            if (curPagestr != null && !"".equals(curPagestr)) {
                //获取到了有效的用户请求第几页，将字符串转换为整型数字
                curPage = Integer.parseInt(curPagestr);
            }
            // 调用业务逻辑层根据当前页数据获取页面上所有数据PageBean对象
            PageBean<Route> pageBean = service.getPageBean(cid, curPage, rname);
            //将数据封装到resultInfo中
            resultInfo = new ResultInfo(true, pageBean, null);
        } catch (Exception e) {
            //
            resultInfo = new ResultInfo(false);
        }
        //将ResultInfo转换为json
        String jsonDate = new ObjectMapper().writeValueAsString(resultInfo);
        //    System.out.println(jsonDate);
        //{true,null,null}
        //输出给浏览器
        response.getWriter().write(jsonDate);
    }

    // 查询国内游旅游项目详情
    protected void findRouteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回结果对象
        ResultInfo resultInfo = null;
        try {
            // 获取cid
            String r = request.getParameter("rid");
            int rid = Integer.parseInt(r);
            // 调用service业务层 用route接收 route中携带者当前功能所需要的数据，来自数据库中的4个表的
            // 查询分类的数据 查询卖家的数据 查询产品的图片路径
            Route route = service.findRouteByRid(rid);
            resultInfo = new ResultInfo(true, route, null);

        } catch (Exception e) {
            //
            resultInfo = new ResultInfo(false);
        }
        //将ResultInfo转换为json
        String jsonDate = new ObjectMapper().writeValueAsString(resultInfo);
        // System.out.println(jsonDate);
        //输出给浏览器
        response.getWriter().write(jsonDate);
    }


    // 判断页面中点击收藏是否可编辑
    protected void isFavoriteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回结果对象
        ResultInfo resultInfo = null;
        try {
            // 获取cid
            String r = request.getParameter("rid");
            int rid = Integer.parseInt(r);

            User user = (User) request.getSession().getAttribute("loginUser");
            if (null == user) {
                //如果没有登录
                //第一个参数：true,代表正确返回结果
                //第二个参数：false,没有登录成功,代表当前旅游线路未收藏
                //第一个参数：null，代表没有错误消息
                resultInfo = new ResultInfo(true, false, null);
            } else {
                //如果登录,没有收藏
                //如果登录,收藏
                //接受rid,调用service,dao判断当前用户对当前的产品是否收藏过
                //代表是否收藏过 true/false
                boolean b = serviceImpl.isFavorite(user.getUid(), rid);
                resultInfo = new ResultInfo(true, b, null);
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

    // 收藏排行榜 findRoutesFavoriteRank
    protected void findRoutesFavoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回结果对象
        ResultInfo resultInfo = null;
        try {
            //获取当前页
            String curPage = request.getParameter("curPage");
            int curPag = 1;
            if (!"".equals(curPage) && null != curPage) {
                curPag = Integer.parseInt(curPage);
            }
            //调用业务层方法,返回PageBean对象，
            //1_当前页中的产品数据
            //2_分页的参数信息


        } catch (Exception e) {
            resultInfo = new ResultInfo(false);
        }
        //将ResultInfo转换为json
        String jsonDate = new ObjectMapper().writeValueAsString(resultInfo);
        // System.out.println(jsonDate);
        //输出给浏览器
        response.getWriter().write(jsonDate);

    }

    // findRoutesFavoriteRan
    // 收藏排行榜的按条件查询
    protected void findRoutesFavoriteRan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回结果对象
        ResultInfo resultInfo = null;
        try {
            //获取当前页
            String curPage = request.getParameter("curPage");
            int curPag = 1;
            if (!"".equals(curPage) && null != curPage) {
                curPag = Integer.parseInt(curPage);
            }
            //获取搜索条件数据并封装到Map<String,Object>中
            String rname = request.getParameter("rname");
            String starPrice = request.getParameter("starPrice");
            String endPrice = request.getParameter("endPrice");
            Map<String, Object> map = new HashMap<>();
            map.put("rname", rname);
            map.put("starPrice", starPrice);
            map.put("endPrice", endPrice);
            // Set<String> strings = map.keySet();
            //     for (String string : strings) {
            //       System.out.println(string + " " + map.get(string));
            //  }


            // 调用业务层方法,返回PageBean对象，
            PageBean<Route> pageBean = service.getPageBeanByFavoriteRank(curPag, map);
            resultInfo = new ResultInfo(true, pageBean, null);

        } catch (Exception e) {
            resultInfo = new ResultInfo(false);
        }
        //将ResultInfo转换为json
        String jsonDate = new ObjectMapper().writeValueAsString(resultInfo);
         System.out.println(jsonDate);
        //输出给浏览器
        response.getWriter().write(jsonDate);

    }

}
