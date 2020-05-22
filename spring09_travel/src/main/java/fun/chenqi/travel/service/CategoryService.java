package fun.chenqi.travel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import fun.chenqi.travel.dao.CategoryDao;
import fun.chenqi.travel.model.Category;
import fun.chenqi.travel.util.JedisUtil;
import java.util.List;

public class CategoryService {
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    private CategoryDao categoryDao;

    public String findAllCategory() throws Exception {
        // 从jedis中获取分类列表数据
        // 实例化jedis对象
        Jedis jedis = JedisUtil.getJedis();
        // 从获取jedis中的key为"categoryList"的value数据
        String jsonData = jedis.get("categoryList");
        // 判读获取数据有效性
        if (jsonData == null || "".equals(jsonData.trim())) {
            // 如果jsonDate为空，说明redis中没有分类列表数据
            // 调用数据访问类从数据库获取分类列表数据
            List<Category> categoryList = categoryDao.findAllCategory();
            // 将categoryList转换为json格式数据
            jsonData = new ObjectMapper().writeValueAsString(categoryList);
            // 将从数据库获取的jsonData分类列表数据存储到redis中，为下一次获取做准备
            String set = jedis.set("categoryList", jsonData);
        }
        // 返回数据
        jedis.close();
        return jsonData;
    }
}
