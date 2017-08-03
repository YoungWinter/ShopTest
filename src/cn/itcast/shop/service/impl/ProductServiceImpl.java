package cn.itcast.shop.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.dao.OrderDao;
import cn.itcast.shop.dao.ProductDao;
import cn.itcast.shop.domain.OrderItem;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductService;
import cn.itcast.shop.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");

	// 获取最热商品列表
	@Override
	public List<Product> findHotProductList() {
		List<Product> hotProductList = null;

		hotProductList = productDao.findHotProductList();

		return hotProductList;
	}

	// 获取最新商品列表
	@Override
	public List<Product> findNewProductList() {
		List<Product> hotProductList = null;

		hotProductList = productDao.findNewProductList();

		return hotProductList;
	}

	@Override
	public PageBean<Product> findProductByCid(Integer cid, int currentPage, int currentCount) {

		// 封装一个PageBean
		PageBean<Product> pageBean = new PageBean<Product>();

		// 总条数
		int totalCount = 0;

		totalCount = productDao.getCount(cid);

		// 总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		// 符合条件的商品列表
		int index = (currentPage - 1) * currentCount;
		List<Product> list = null;
		list = productDao.findProductByPage(cid, index, currentCount);

		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setTotalCount(totalCount);
		pageBean.setList(list);

		return pageBean;
	}

	@Override
	public Product findProductByPid(Integer pid) {
		Product product = null;
		product = productDao.findProductByPid(pid);
		return product;
	}

	@Override
	public List<Product> findProductListByWord(String keyWord) {
		List<Product> productList = null;
		productList = productDao.findProductListByWord(keyWord);
		return productList;
	}

	@Override
	public List<Product> findAllProductList() {
		List<Product> productList = null;
		productList = productDao.findAllProductList();
		return productList;
	}

	@Override
	public void saveProduct(Product product) {
		productDao.saveProduct(product);
	}

	@Override
	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}

	@Override
	public void delProductByPid(Integer pid) {
		OrderDao orderDao = new OrderDao();
		List<OrderItem> itemList = null;
		try {
			itemList = orderDao.findItemListByPid(pid);
			if (itemList.isEmpty()) {
				productDao.delProductByPid(pid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
