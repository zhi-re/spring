package fun.chenqi.travel.service;


import fun.chenqi.travel.dao.UserDao;

import fun.chenqi.travel.model.User;
import fun.chenqi.travel.util.MailUtil;
import fun.chenqi.travel.util.Md5Util;
import fun.chenqi.travel.util.UuidUtil;


public class UserService {
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserDao userDao;

    // 注册业务
    public boolean register(User user, String contextPath) throws Exception {
        // 数据验证,用户名不能为空,如果用户名为空,
        if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
            // 抛出自定义异常UserNameNotNullException
            // throw new UserNameNotNullException("用户名不能为空！");
        }
        // 根据用户输入的用户名去查找数据库对应用户
        User dbUser = userDao.getUserByUserName(user.getUsername());
        if (null != dbUser) {
            // 如果数据库用户不为空,说明用户名已被注册,抛出自定义异常UserNameExistsException
            // throw new UserNameExistsException("用户名已存在！");
        }
        // 激活状态为未激活
        user.setStatus("N");
        // 生成激活码,用户激活
        user.setCode(UuidUtil.getUuid());
        // 对于密码进行加密
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        // 实现注册添加用户
        userDao.register(user);
        // 发送激活邮件,根据用户提供的注册邮箱发送激活邮件
        MailUtil.sendMail(user.getEmail(), "<a href='http://localhost:8080"
                + contextPath + "/user?action=active&code="
                + user.getCode() + "'>用户激活</a>");
        // return true;
        return true;
    }

    //    激活业务
    public boolean active(String code) {
        // 调用数据访问层进行激活,返回影响行数
        int row = userDao.active(code);
        // 返回激活结果,影响行数>0说明激活成功,否则激活失败
        if (row > 0) {
            return true;
        } else {
            return false;
        }

    }

    //    登录业务
    public User login(String username, String password) throws Exception {
        //1_如果用户名为空或者为空字符串.抛出用户名不为空的异常
        if (username == null || "".equals(username)) {
            //  throw new UserNameNotNullException("用户名不能为空！");
        }
        //2_根据用户名查找数据库用户对象
        User dbUser = userDao.getUserByUserName(username);
        //3_如果查询到的用户名为空,抛出用户名不存在异常
        if (dbUser == null) {
            //   throw new UserNameNotExistsException("用户名不存在！");
        } else {
            //4_验证密码,如果密码不匹配,抛出密码错误的异常
            if (!password.equals(dbUser.getPassword())) {
                //      throw new PasswordErrorException("密码错误！");
            }
        }
        //5_返回登录成功的用户
        System.out.println(dbUser);
        return dbUser;
    }
}
