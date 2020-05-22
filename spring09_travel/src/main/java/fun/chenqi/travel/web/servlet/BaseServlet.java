package fun.chenqi.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet", urlPatterns = "/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //当我们向服务端UserServlet发起请求的时候,tomcat创建UserServlet对象
        //执行init方法
        //执行sevice方法,UserServlet没有service方法,父类寻找Service,注意：子类UserServlet调用service
        //获取用户操作标识符
        String action = req.getParameter("action");
       // System.out.println(action); // getLoginUserData ...
       // System.out.println(this); //子类引用 fun.chenqi.travel.web.servlet.UserServlet@7295cc3e

        // 获取子类字节码对象
        Class clazz = this.getClass();
        //获取子类字节码上方法名称为action变量,参数HttpServletRequest.class,HttpServletResponse.class
        try {
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            // 暴力反射
            method.setAccessible(true);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
}
