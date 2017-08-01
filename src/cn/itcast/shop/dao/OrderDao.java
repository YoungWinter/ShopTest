package cn.itcast.shop.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.shop.domain.Order;
import cn.itcast.shop.domain.OrderItem;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.domain.User;
import cn.itcast.shop.utils.DataSourceUtils;

public class OrderDao {

	// 向orders表插入数据
	public void saveOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		qr.update(conn, sql, order.getOid(), order.getOrdertime(), order.getTotal(),
				order.getState(), order.getAddress(), order.getName(), order.getTelephone(),
				order.getUser().getUid());
	}

	public void saveOrderItems(List<OrderItem> orderItems) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		for (OrderItem item : orderItems) {
			qr.update(conn, sql, item.getItemid(), item.getCount(), item.getSubtotal(),
					item.getProduct().getPid(), item.getOrder().getOid());
		}
	}

	// 通过oid查询订单
	public Order findOrderByOid(String oid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where oid=?";
		return qr.query(sql, new BeanHandler<Order>(Order.class), oid);
	}

	// 更新订单
	public void updateOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address=?,name=?,telephone=?,state=? where oid=?";
		qr.update(sql, order.getAddress(), order.getName(), order.getTelephone(), order.getState(),
				order.getOid());
	}

	// 通过user对象查询订单列表
	public List<Order> findOrderListByUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid=?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class), user.getUid());
	}

	// 通过订单对象查询订单下的订单项列表
	public List<OrderItem> findItemListByOid(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orderitem where oid=?";
		return qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), order.getOid());
	}

	// 通过订单项对象查询订单项下的产品对象
	public Product findProductByItemid(OrderItem orderItem) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select p.* from product p left outer join orderitem o on p.pid=o.pid where o.itemid=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class), orderItem.getItemid());
	}

	public List<OrderItem> findItemListByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orderitem where pid=?";
		return qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), pid);
	}

}
