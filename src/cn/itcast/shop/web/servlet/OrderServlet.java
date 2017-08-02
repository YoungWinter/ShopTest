package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.shop.domain.Cart;
import cn.itcast.shop.domain.CartItem;
import cn.itcast.shop.domain.Order;
import cn.itcast.shop.domain.OrderItem;
import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.OrderService;

public class OrderServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// 订单展示
	public String orderList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 获取当前用户
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 准备数据(查询该用户下的所有订单)
		OrderService orderService = new OrderService();
		List<Order> orderList = orderService.findOrderListByUser(user);

		// 将数据放入request域中
		request.setAttribute("orderList", orderList);
		// 页面转发

		return "/order_list.jsp";
	}

	// 订单处理
	public String dealOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 获取表单数据
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		address = new String(address.getBytes("ISO8859-1"), "UTF-8");
		String name = request.getParameter("name");
		name = new String(name.getBytes("ISO8859-1"), "UTF-8");
		String telephone = request.getParameter("telephone");

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
		response.setContentType("text/html;charset=UTF-8");
		response.sendRedirect(
				request.getContextPath() + "/order?method=orderList");

		return null;
	}

	// 保存订单
	public String saveOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 判断用户是否登录
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 获取Cart对象
		Cart cart = (Cart) session.getAttribute("cart");
		// 封装Order对象
		Order order = new Order();
		order.setAddress(null);
		order.setName(null);
		order.setOid(UUID.randomUUID().toString());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String ordertime = format.format(date);
		order.setOrdertime(ordertime);
		order.setState(0);
		order.setTelephone(null);
		order.setUser(user);
		order.setTotal(cart.getTotal());
		// 封装OrderItem,然后添加到Order对象中
		List<OrderItem> orderItems = order.getOrderItems();
		Map<String, CartItem> cartItemMap = cart.getCartItemMap();
		for (Entry<String, CartItem> entry : cartItemMap.entrySet()) {
			CartItem cartItem = entry.getValue();
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUID.randomUUID().toString());
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItems.add(orderItem);
		}
		// 调用OrderService对象保存订单
		OrderService orderService = new OrderService();
		orderService.saveOrder(order);

		// 将Order对象存到Session中
		session.setAttribute("order", order);
		response.setContentType("text/html;charset=UTF-8");
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");

		return null;
	}

	// 保存订单
	public String payOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 获取oid
		String oid = request.getParameter("oid");

		// 根据oid查询订单
		OrderService orderService = new OrderService();
		Order order = orderService.findPayingOrderByOid(oid);

		// 将Order对象存到Session中
		HttpSession session = request.getSession();
		session.setAttribute("order", order);
		response.setContentType("text/html;charset=UTF-8");
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");

		return null;
	}
}
