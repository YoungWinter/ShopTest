package cn.itcast.shop.service;

import java.util.List;

import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;

public interface ProductService {
	// 获取最热商品列表
	public List<Product> findHotProductList();

	// 获取最新商品列表
	public List<Product> findNewProductList();

	public PageBean<Product> findProductByCid(Integer cid, int currentPage, int currentCount);

	public Product findProductByPid(Integer pid);

	public List<Product> findProductListByWord(String keyWord);

	public List<Product> findAllProductList();

	public void saveProduct(Product product);

	public void updateProduct(Product product);

	public void delProductByPid(Integer pid);

}
