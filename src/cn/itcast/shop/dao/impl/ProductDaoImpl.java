package cn.itcast.shop.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.shop.dao.ProductDao;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {
	// 获取最热商品列表
	@Override
	public List<Product> findHotProductList() {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot=? limit ?,?";
		try {
			productList = qr.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Product product : productList) {
			sql = "select category.cid,cname from category left outer join product on category.cid = product.cid where product.pid=?";
			try {
				product.setCategory(
						qr.query(sql, new BeanHandler<Category>(Category.class), product.getPid()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return productList;
	}

	// 获取最新商品列表
	@Override
	public List<Product> findNewProductList() {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		try {
			productList = qr.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Product product : productList) {
			sql = "select category.cid,cname from category left outer join product on category.cid = product.cid where product.pid=?";
			try {
				product.setCategory(
						qr.query(sql, new BeanHandler<Category>(Category.class), product.getPid()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return productList;
	}

	// 获取总条数
	@Override
	public int getCount(Integer cid) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long count = null;
		try {
			count = (Long) qr.query(sql, new ScalarHandler(), cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count.intValue();
	}

	// 分页查询
	@Override
	public List<Product> findProductByPage(Integer cid, int index, int currentCount) {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		try {
			productList = qr.query(sql, new BeanListHandler<Product>(Product.class), cid, index,
					currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Product product : productList) {
			sql = "select category.cid,cname from category left outer join product on category.cid = product.cid where product.pid=?";
			try {
				product.setCategory(
						qr.query(sql, new BeanHandler<Category>(Category.class), product.getPid()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return productList;
	}

	// 通过pid查询商品
	@Override
	public Product findProductByPid(Integer pid) {
		Product product = null;
		Category category = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		try {
			product = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql = "select category.cid,cname from category left outer join product on category.cid=product.cid where pid=?";
		try {
			category = qr.query(sql, new BeanHandler<Category>(Category.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		product.setCategory(category);
		return product;
	}

	@Override
	public List<Product> findProductListByWord(String keyWord) {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname like ? limit 0,8";
		try {
			productList = qr.query(sql, new BeanListHandler<Product>(Product.class),
					"%" + keyWord + "%");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Product product : productList) {
			sql = "select category.cid,cname from category left outer join product on category.cid = product.cid where product.pid=?";
			try {
				product.setCategory(
						qr.query(sql, new BeanHandler<Category>(Category.class), product.getPid()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return productList;
	}

	@Override
	public List<Product> findProductByCid(Integer cid) {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=?";
		try {
			productList = qr.query(sql, new BeanListHandler<Product>(Product.class), cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public List<Product> findAllProductList() {
		List<Product> productList = null;
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		try {
			productList = qr.query(sql, new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return productList;
	}

	@Override
	public void saveProduct(Product product) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		try {
			qr.update(sql, product.getPid(), product.getPname(), product.getMarket_price(),
					product.getShop_price(), product.getPimage(), product.getPdate(),
					product.getIs_hot(), product.getPdesc(), product.getPflag(),
					product.getCategory().getCid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(Product product) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		try {
			qr.update(sql, product.getPname(), product.getMarket_price(), product.getShop_price(),
					product.getPimage(), product.getPdate(), product.getIs_hot(),
					product.getPdesc(), product.getPflag(), product.getCategory().getCid(),
					product.getPid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delProductByPid(Integer pid) {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid=?";
		try {
			qr.update(sql, pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
