package cn.itcast.shop.test;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.utils.BeanFactory;

public class IOCTest {

	@Test
	public void testFindList() {
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		List<Category> categoryList = categoryService.findAllCategory();
		for (Category category : categoryList) {
			Set<Product> productList = category.getProductList();
			for (Product product : productList) {
				System.out.println(product);
			}
		}
	}

	@Test
	public void testAdd() {
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");

		Category category = new Category();
		category.setCname("儿童玩具");

		categoryService.addCategory(category);
	}

	@Test
	public void testFindCategoryByCid() {
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");

		Category category = categoryService.findCategoryByCid(3);

		System.out.println(category);
	}

	@Test
	public void testUpdateCategory() {
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");

		Category category = new Category();
		category.setCid(1);
		category.setCname("珠宝黄金");
		categoryService.updateCategory(category);
	}

	@Test
	public void testDeleteCategoryByCid() {
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");

		try {
			categoryService.deleteCategoryByCid(1);
		} catch (Exception e) {
			System.out.println("删除异常");
		}
	}
}
