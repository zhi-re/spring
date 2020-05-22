package fun.chenqi.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fun.chenqi.travel.model.ResultInfo;
import fun.chenqi.travel.model.User;
import fun.chenqi.travel.service.UserService;
import fun.chenqi.travel.util.Md5Util;

import java.io.IOException;

@WebServlet(name = "user", urlPatterns = "/user")
public class UserServlet extends BaseServlet {
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // 实例用户类
    private UserService userService;


    // 测试用户登录！
    @Test
    public void test() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        UserService service = (UserService) ac.getBean("userService");
        try {
            service.login("chenqi", "111");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 用户退出
    private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("/travel/index.html");

    }

    // 在首页显示登录用户信息
    private void getLoginUserData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1_定义返回数据对象
        ResultInfo resultInfo = null;
        //2_获取用户登录数据
        User user = (User) request.getSession().getAttribute("loginUser");
        //System.out.println(user);
        //3_判断登录数据有效性
        if (null == user) {
            //4_用户没有登录，直接存储false状态，代表获取数据失败
            resultInfo = new ResultInfo(false);
        } else {
            //5_用户有登录数据，状态为true，并且存储登录的数据进行返还
            resultInfo = new ResultInfo(true, user, null);
        }
        //6_将返回数据转换为json
        String jsonData = new ObjectMapper().writeValueAsString(resultInfo);
        System.out.println(jsonData);
        //7_返回给浏览器
        response.getWriter().write(jsonData);


    }

    // 登录
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //定义返回客户端数据对象
        ResultInfo resultInfo = null;
        //验证登录验证码
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        // 如果验证码正确
        if (check.equalsIgnoreCase(checkcode_server)) {
            try {
                //获取用户名与密码
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                //System.out.println(username+" "+password);
                //对密码加密码(由于数据库存的是加密密码，所以登录时也要加密)
                password = Md5Util.encodeByMd5(password);
                //调用业务逻辑登录方法
                User dbUser = userService.login(username, password);
                //判断登录是否成功
                if (dbUser != null) {
                    //登录成功，将用户信息写入session
                    request.getSession().setAttribute("loginUser", dbUser);
                    //实例化返回数据对象 resultInto
                    resultInfo = new ResultInfo(true);
                }
            }
            //捕获4个异常:用户名为空,用户名不存在或者错误,密码错误,运行时异常
            catch (Exception e) {
                // 用户名为空
                resultInfo = new ResultInfo(false, e.getMessage());
            }
            //获取返回浏览器的json数据
            String jsonData = new ObjectMapper().writeValueAsString(resultInfo);
            //输出到浏览器
            response.getWriter().write(jsonData);

        } else {
            // 验证码错误
            resultInfo = new ResultInfo(false, "验证码错误！");
            //获取返回浏览器的json数据
            String jsonData = new ObjectMapper().writeValueAsString(resultInfo);
            //输出到浏览器
            response.getWriter().write(jsonData);

        }


    }


    // 激活邮箱
    private void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取激活码
            String code = request.getParameter("code");
            //调用业务逻辑类UserService进行激活,返回flag
            boolean flag = userService.active(code);
            //激活成功,跳转到登录页面
            if (flag) {
                response.sendRedirect(request.getContextPath() + "/login.html");
            } else {
                //激活失败,显示激活失败
                response.getWriter().write("激活失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            //如果系统异常,用户处理不了,跳转到友好页面
            throw new RuntimeException(e);
        }

    }


    // 处理注册请求
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.定义返回结果对象
        ResultInfo resultInfo = null;
        // 2.验证验证码
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        if (check.equalsIgnoreCase(checkcode_server)) {
            //  System.out.println("验证码正确");
            try {
                // 3.获取数据并封装到user对象
                User user = new User();
                BeanUtils.populate(user, request.getParameterMap());
                // 4.调用service业务层注册用户
                boolean flag = userService.register(user, request.getContextPath());
                if (flag) {
                    resultInfo = new ResultInfo(true);
                }
            } catch (Exception e) {
                resultInfo = new ResultInfo(false, e.getMessage());

            }
            // 6.讲resultInfo转为json数据返回客户端
            String jsonData = new ObjectMapper().writeValueAsString(resultInfo);
            response.getWriter().write(jsonData);

        } else {
            resultInfo = new ResultInfo(false, "验证码错误");
            String jsonData = new ObjectMapper().writeValueAsString(resultInfo);
            response.getWriter().write(jsonData);
            // String username = request.getParameter("username");
            // System.out.println(username);
        }
    }

/*    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }*/


}
