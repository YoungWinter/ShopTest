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

	public void active(String activeCode) {
		UserDao userDao = new UserDao();
		try {
			userDao.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUsername(String username) {

		Long row = null;
		UserDao userDao = new UserDao();
		try {
			row = userDao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row > 0 ? true : false;
	}

	public User login(User user) {
		User loginUser = null;
		UserDao userDao = new UserDao();
		try {
			loginUser = userDao.login(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginUser;
	}

}
