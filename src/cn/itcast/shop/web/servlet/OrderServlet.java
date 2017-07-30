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

	// 保存订单
	public String saveOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 判断用户是否登录
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 没有登录
			session.setAttribute("cartPage", "cartPage");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return null;
		}

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

		response.sendRedirect(request.getContextPath() + "/order_info.jsp");

		return null;
	}
}
