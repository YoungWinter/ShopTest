package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.shop.domain.Cart;
import cn.itcast.shop.domain.CartItem;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductServiceImpl;

public class CartServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// 清空购物车
	public String clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
		return null;
	}

	// 删除购物车中的商品delCartItemFormCart
	public String delCartItemFormCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pid = request.getParameter("pid");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
			cartItemMap.remove(pid);
			cart.setCartItemMap(cartItemMap);
		}
		session.setAttribute("cart", cart);

		response.sendRedirect(request.getContextPath() + "/cart.jsp");
		return null;
	}

	// 将商品添加到购物车
	public String addProductToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获得pid和buyNum
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		String buyNumStr = request.getParameter("buyNum");
		int buyNum = 0;
		if (buyNumStr != null && !"".equals(buyNumStr)) {
			buyNum = Integer.parseInt(buyNumStr);
		}

		// 封装CartItem对象
		CartItem cartItem = new CartItem();
		ProductServiceImpl productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		cartItem.setProduct(product);
		cartItem.setBuyNum(buyNum);

		// 从Session域中获取Cart对象，如果没有就创建
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}

		// 从Cart对象中取出CartItem集合
		Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
		if (cartItemMap.containsKey(pid)) {
			// 如果存在该cartItem，则重新设置该cartItem的buyNum
			cartItemMap.get(pid).setBuyNum(cartItemMap.get(pid).getBuyNum() + buyNum);
		} else {
			// 如果不存在该cartItem，则添加该cartItem
			cartItemMap.put(pid, cartItem);
		}
		cart.setCartItemMap(cartItemMap);

		// 将Cart对象放入session中
		session.setAttribute("cart", cart);

		// 页面跳转
		response.sendRedirect(request.getContextPath() + "/cart.jsp");

		return null;
	}
}
