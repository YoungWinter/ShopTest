package cn.itcast.shop.dao;

import java.util.List;

import cn.itcast.shop.domain.Category;

public interface CategoryDao {

	public List<Category> findAllCategory();

	public void addCategory(Category category);

	public Category findCategoryByCid(Integer cid);

	public void updateCategory(Category category);

	public void deleteCategoryByCid(Integer cid);
}
