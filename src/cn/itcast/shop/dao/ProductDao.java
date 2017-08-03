package cn.itcast.shop.dao;

import java.util.List;

import cn.itcast.shop.domain.Product;

public interface ProductDao {
	// 获取最热商品列表
	public List<Product> findHotProductList();

	// 获取最新商品列表
	public List<Product> findNewProductList();

	// 获取总条数
	public int getCount(String cid);

	// 分页查询
	public List<Product> findProductByPage(String cid, int index, int currentCount);

	// 通过pid查询商品
	public Product findProductByPid(String pid);

	public List<Product> findProductListByWord(String keyWord);

	public List<Product> findProductByCid(String cid);

	public List<Product> findAllProductList();

	public void saveProduct(Product product);

	public void updateProduct(Product product);

	public void delProductByPid(String pid);

}
