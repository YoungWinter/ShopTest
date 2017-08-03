package cn.itcast.shop.service;

import java.util.List;

import cn.itcast.shop.domain.Category;

public interface CategoryService {

	public List<Category> findAllCategory();

	public void addCategory(Category category);

	public Category findCategoryByCid(Integer cid);

	public void updateCategory(Category category);

	public void deleteCategoryByCid(Integer cid);
}
