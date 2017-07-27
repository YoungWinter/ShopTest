package cn.itcast.shop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

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

}