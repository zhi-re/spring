package fun.chenqi.travel.dao;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import fun.chenqi.travel.model.Category;

import java.util.List;

public class CategoryDao {
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    JdbcTemplate jdbcTemplate ;

    public List<Category> findAllCategory() {
        String sql = "select * from tab_category ORDER BY cid";
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
        return list;
    }
}
