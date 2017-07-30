package cn.itcast.shop.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.dao.ProductDao;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;

public class ProductService {

	// 获取最热商品列表
	public List<Product> findHotProductList() {
		List<Product> hotProductList = null;
		ProductDao productDao = new ProductDao();
		try {
			hotProductList = productDao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProductList;
	}

	// 获取最新商品列表
	public List<Product> findNewProductList() {
		List<Product> hotProductList = null;
		ProductDao productDao = new ProductDao();
		try {
			hotProductList = productDao.findNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProductList;
	}

	public PageBean<Product> findProductByCid(String cid, int currentPage,
			int currentCount) {
		ProductDao productDao = new ProductDao();
		// 封装一个PageBean
		PageBean<Product> pageBean = new PageBean<Product>();

		// 总条数
		int totalCount = 0;
		try {
			totalCount = productDao.getCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		// 符合条件的商品列表
		int index = (currentPage - 1) * currentCount;
		List<Product> list = null;
		try {
			list = productDao.findProductByPage(cid, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setTotalCount(totalCount);
		pageBean.setList(list);

		return pageBean;
	}

	public Product findProductByPid(String pid) {
		Product product = null;
		ProductDao productDao = new ProductDao();
		try {
			product = productDao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public List<Product> findProductListByWord(String keyWord) {
		List<Product> productList = null;
		ProductDao productDao = new ProductDao();
		try {
			productList = productDao.findProductListByWord(keyWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

}
