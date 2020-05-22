package fun.chenqi.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fun.chenqi.travel.model.ResultInfo;
import fun.chenqi.travel.service.CategoryService;
import java.io.IOException;

@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private CategoryService categoryService;

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义返回数据对象
        ResultInfo resultInfo = null;
        try {
            // 调用业务逻辑层获取分类列表的json数据
            String jsonData = categoryService.findAllCategory();
            // 构造返回数据对象
            resultInfo = new ResultInfo(true, jsonData, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false);
        }
        //将resultInfo对象转换为json格式数据
        String jsonData = new ObjectMapper().writeValueAsString(resultInfo);
        //输出jsonData到客户端
        response.getWriter().write(jsonData);


    }


}
