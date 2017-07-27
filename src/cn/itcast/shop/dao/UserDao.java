package cn.itcast.shop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.shop.domain.User;
import cn.itcast.shop.utils.DataSourceUtils;

public class UserDao {

	public int regist(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int update = runner.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
				user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(),
				user.getSex(), user.getState(), user.getCode());
		return update;
	}

	// 用户激活
	public void active(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state=? where code=?";
		qr.update(sql, 1, activeCode);
	}

	// 校验用户名是否存在
	public Long checkUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username=?";
		return (Long) qr.query(sql, new ScalarHandler(), username);
	}

}
