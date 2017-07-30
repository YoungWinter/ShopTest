package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.UserService;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "";
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		UserService userService = new UserService();
		User loginUser = userService.login(user);
		if (loginUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", loginUser);
			String cartPage = (String) session.getAttribute("cartPage");
			if (cartPage != null && "cartPage".equals(cartPage)) {
				session.removeAttribute("cartPage");
				// 重定向到购物车页面
				result = "/cart.jsp";
			}
		} else {
			// 失败 转发到登录页面 提出提示信息
			request.setAttribute("loginInfo", "用户名或密码错误");
			result = "/login.jsp";
		}

		if ("/cart.jsp".equals(result)) {
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
			return null;
		} else if ("".equals(result)) {
			response.sendRedirect(request.getContextPath());
			return null;
		}

		return result;
	}
}