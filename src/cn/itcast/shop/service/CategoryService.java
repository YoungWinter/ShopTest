package cn.itcast.shop.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.dao.CategoryDao;
import cn.itcast.shop.domain.Category;

public class CategoryService {

	public List<Category> findAllCategory() {
		List<Category> categoryList = null;
		try {
			categoryList = new CategoryDao().findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

}
