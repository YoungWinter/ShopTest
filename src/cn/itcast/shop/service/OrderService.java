package cn.itcast.shop.service;

import java.sql.SQLException;

import cn.itcast.shop.dao.OrderDao;
import cn.itcast.shop.domain.Order;
import cn.itcast.shop.utils.DataSourceUtils;

public class OrderService {

	public void saveOrder(Order order) {
		OrderDao orderDao = new OrderDao();
		try {
			DataSourceUtils.startTransaction();
			orderDao.saveOrder(order);
			orderDao.saveOrderItems(order.getOrderItems());
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
