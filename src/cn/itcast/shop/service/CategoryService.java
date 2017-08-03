package cn.itcast.shop.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.dao.CategoryDao;
import cn.itcast.shop.dao.ProductDaoImpl;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Product;

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

	public void addCategory(Category category) {
		try {
			new CategoryDao().addCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Category findCategoryByCid(String cid) {
		CategoryDao categoryDao = new CategoryDao();
		Category category = null;
		try {
			category = categoryDao.findCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	public void updateCategory(Category category) {
		try {
			new CategoryDao().updateCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteCategoryByCid(String cid) {
		boolean isDel = false;
		CategoryDao categoryDao = new CategoryDao();
		ProductDaoImpl productDao = new ProductDaoImpl();
		List<Product> productList = null;
		try {
			productList = productDao.findProductByCid(cid);
			if (productList.isEmpty()) {
				categoryDao.deleteCategoryByCid(cid);
				isDel = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDel;
	}

}
