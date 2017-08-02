package cn.itcast.shop.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.itcast.shop.dao.OrderDao;
import cn.itcast.shop.domain.Order;
import cn.itcast.shop.domain.OrderItem;
import cn.itcast.shop.domain.User;
import cn.itcast.shop.utils.DataSourceUtils;

public class OrderService {

	// 保存订单
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

	public Order findOrderByOid(String oid) {
		Order order = null;
		OrderDao orderDao = new OrderDao();
		try {
			order = orderDao.findOrderByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	public void updateOrder(Order order) {
		OrderDao orderDao = new OrderDao();
		try {
			orderDao.updateOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Order> findOrderListByUser(User user) {
		List<Order> orderList = null;
		OrderDao orderDao = new OrderDao();
		try {
			// 根据用户的uid查询所有订单
			orderList = orderDao.findOrderListByUser(user);
			// 补全order对象中的orderItems
			for (Order order : orderList) {
				List<OrderItem> orderItems = null;
				// 根据oid查询所有orderItems
				orderItems = orderDao.findItemListByOid(order);
				// 补全所有orderItems中的product对象
				for (OrderItem orderItem : orderItems) {
					orderItem.setProduct(
							orderDao.findProductByItemid(orderItem));
				}
				order.setOrderItems(orderItems);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public Order findPayingOrderByOid(String oid) {
		OrderDao orderDao = new OrderDao();
		Order order = findOrderByOid(oid);
		List<OrderItem> orderItems = null;
		// 根据oid查询所有orderItems
		try {
			orderItems = orderDao.findItemListByOid(order);
			// 补全所有orderItems中的product对象
			for (OrderItem orderItem : orderItems) {
				orderItem.setProduct(orderDao.findProductByItemid(orderItem));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		order.setOrderItems(orderItems);
		return order;
	}

	public List<Order> findAllOrderList() {
		List<Order> orderList = null;
		OrderDao orderDao = new OrderDao();
		try {
			orderList = orderDao.findAllOrderList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		List<Map<String, Object>> info = null;
		OrderDao orderDao = new OrderDao();
		try {
			info = orderDao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

}
