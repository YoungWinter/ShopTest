package cn.itcast.shop.service;

import java.sql.SQLException;

import cn.itcast.shop.dao.UserDao;
import cn.itcast.shop.domain.User;

public class UserService {

	public boolean regist(User user) {
		int row = 0;
		UserDao userDao = new UserDao();
		try {
			row = userDao.regist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row > 0 ? true : false;
	}

}
