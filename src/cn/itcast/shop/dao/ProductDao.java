package cn.itcast.shop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.utils.DataSourceUtils;

public class ProductDao {

	// 获取最热商品列表
	public List<Product> findHotProductList() throws SQLException {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot=? limit ?,?";
		productList = qr.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
		for (Product product : productList) {
			sql = "select category.cid,cname from category left outer join product on category.cid = product.cid where product.pid=?";
			product.setCategory(
					qr.query(sql, new BeanHandler<Category>(Category.class), product.getPid()));
		}
		return productList;
	}

	// 获取最新商品列表
	public List<Product> findNewProductList() throws SQLException {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		productList = qr.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
		for (Product product : productList) {
			sql = "select category.cid,cname from category left outer join product on category.cid = product.cid where product.pid=?";
			product.setCategory(
					qr.query(sql, new BeanHandler<Category>(Category.class), product.getPid()));
		}
		return productList;
	}

	// 获取总条数
	public int getCount(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long count = (Long) qr.query(sql, new ScalarHandler(), cid);
		return count.intValue();
	}

	// 分页查询
	public List<Product> findProductByPage(String cid, int index, int currentCount)
			throws SQLException {
		List<Product> list = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		list = qr.query(sql, new BeanListHandler<Product>(Product.class), cid, index, currentCount);
		return list;
	}

}
