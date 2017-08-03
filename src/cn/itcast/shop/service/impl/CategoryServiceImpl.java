package cn.itcast.shop.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.itcast.shop.dao.CategoryDao;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.utils.BeanFactory;
import cn.itcast.shop.utils.HibernateUtils;

public class CategoryServiceImpl implements CategoryService {

	private CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");

	@Override
	public List<Category> findAllCategory() {
		List<Category> categoryList = null;
		categoryList = categoryDao.findAllCategory();
		return categoryList;
	}

	@Override
	public void addCategory(Category category) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			categoryDao.addCategory(category);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		tx.commit();
	}

	@Override
	public Category findCategoryByCid(Integer cid) {
		Category category = null;
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			category = categoryDao.findCategoryByCid(cid);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		tx.commit();
		return category;
	}

	@Override
	public void updateCategory(Category category) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			categoryDao.updateCategory(category);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		tx.commit();
	}

	@Override
	public void deleteCategoryByCid(Integer cid) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			categoryDao.deleteCategoryByCid(cid);
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		}
		tx.commit();

	}

}
