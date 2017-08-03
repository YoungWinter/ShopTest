package cn.itcast.shop.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.itcast.shop.dao.CategoryDao;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.utils.HibernateUtils;

public class CategoryDaoImpl implements CategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAllCategory() {
		Session session = HibernateUtils.openSession();
		List<Category> categoryList = null;
		String hql = " from Category ";
		Query query = session.createQuery(hql);
		categoryList = query.list();
		return categoryList;
	}

	@Override
	public void addCategory(Category category) {
		Session session = HibernateUtils.getCurrentSession();
		session.save(category);
	}

	@Override
	public Category findCategoryByCid(Integer cid) {
		Session session = HibernateUtils.getCurrentSession();
		String hql = " from Category where cid=:cid ";
		Query query = session.createQuery(hql);
		query.setParameter("cid", cid);
		Category category = (Category) query.uniqueResult();
		return category;
	}

	@Override
	public void updateCategory(Category category) {
		Session session = HibernateUtils.getCurrentSession();
		Category updateCategory = session.get(Category.class, category.getCid());
		updateCategory.setCname(category.getCname());
		session.update(updateCategory);
	}

	@Override
	public void deleteCategoryByCid(Integer cid) {
		Session session = HibernateUtils.getCurrentSession();
		Category category = session.get(Category.class, cid);
		session.delete(category);
	}

}
