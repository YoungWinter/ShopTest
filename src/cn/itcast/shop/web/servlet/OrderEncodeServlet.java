package cn.itcast.shop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.domain.Order;
import cn.itcast.shop.service.OrderService;

public class OrderEncodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		// 获取表单数据
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		System.out.println(address);
		System.out.println(name);
		System.out.println(telephone);

		// 根据oid查询订单对象并封装数据
		OrderService orderService = new OrderService();
		Order order = orderService.findOrderByOid(oid);
		if (order != null) {
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			order.setState(1);
		}

		orderService.updateOrder(order);

		// 页面跳转
		response.sendRedirect(
				request.getContextPath() + "/order?method=orderList");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}