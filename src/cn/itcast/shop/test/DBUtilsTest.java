package cn.itcast.shop.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import cn.itcast.shop.dao.ProductDaoImpl;
import cn.itcast.shop.domain.Product;

public class DBUtilsTest {

	@Test
	public void test() {
		ProductDaoImpl productDao = new ProductDaoImpl();
		List<Product> productList = null;
		try {
			productList = productDao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (productList != null) {
			for (Product product : productList) {
				System.out.println(product);
			}
		}
	}
}
