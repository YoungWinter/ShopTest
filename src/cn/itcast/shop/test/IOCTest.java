package cn.itcast.shop.test;

import java.util.List;

import org.junit.Test;

import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductService;
import cn.itcast.shop.utils.BeanFactory;

public class IOCTest {

	@Test
	public void test1() {
		ProductService productService = (ProductService) BeanFactory.getBean("productService");
		List<Product> productList = productService.findHotProductList();
		for (Product product : productList) {
			System.out.println(product);
		}

	}
}
