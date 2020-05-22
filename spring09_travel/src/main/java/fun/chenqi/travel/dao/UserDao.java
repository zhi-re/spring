package fun.chenqi.travel.dao;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import fun.chenqi.travel.model.User;

public class UserDao {
    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public User getUserByUserName(String username) {
        String sql = "select * from tab_user where username = ?";

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {
            //  e.printStackTrace();
        }
        return user;
    }

    public void register(User user) {
        String sql = "insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    public int active(String code) {
        String sql = "update tab_user set status = 'Y' where code = ? and status = 'N'";
        int i = jdbcTemplate.update(sql, code);
        return i;


    }
}
