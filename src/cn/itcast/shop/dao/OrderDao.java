package cn.itcast.shop.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.shop.domain.Order;
import cn.itcast.shop.domain.OrderItem;
import cn.itcast.shop.utils.DataSourceUtils;

public class OrderDao {

	// 向orders表插入数据
	public void saveOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		qr.update(conn, sql, order.getOid(), order.getOrdertime(),
				order.getTotal(), order.getState(), order.getAddress(),
				order.getName(), order.getTelephone(),
				order.getUser().getUid());
	}

	public void saveOrderItems(List<OrderItem> orderItems) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		for (OrderItem item : orderItems) {
			qr.update(conn, sql, item.getItemid(), item.getCount(),
					item.getSubtotal(), item.getProduct().getPid(),
					item.getOrder().getOid());
		}
	}

}
