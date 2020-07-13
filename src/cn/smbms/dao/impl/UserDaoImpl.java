package cn.smbms.dao.impl;

import cn.smbms.dao.UserDao;
import cn.smbms.entity.User;
import cn.smbms.tools.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author lxd
 * @create 2020-07-07 21:31
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //编写sql
            String sql = "select * from user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    username, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = null;
        try {
            String sql = "select * from user";
            users = template.query(sql, new BeanPropertyRowMapper<>(User.class));
            return users;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    /**
     * 查询总记录数
     * @return
     */
    @Override
    public int findTotalCount() {
        String sql = "select count(*) from user";
        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public List<User> findByPage(int start, int rows) {
        String sql = "select * from user limit ? , ?";
        return template.query(sql,new BeanPropertyRowMapper<>(User.class),start,rows);
    }

    @Override
    public void modifiedPWD(String password, int id) {
        String sql = "update user set password = ? where id = ?";
        template.update(sql, password, id);
    }

    @Override
    public void modifiedInfo(User user) {
        String sql = "update user set username = ?, gender = ?, phone = ?, email = ? where id = ?";
        template.update(sql, user.getUsername(), user.getGender(), user.getPhone(), user.getEmail(), user.getId());
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
