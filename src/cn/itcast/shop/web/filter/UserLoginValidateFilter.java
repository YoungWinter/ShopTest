package cn.itcast.shop.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.shop.domain.User;

public class UserLoginValidateFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// 判断用户是否登陆
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute("user");

		// 获取页面标志
		String cartPage = httpRequest.getParameter("cartPage");
		if (cartPage != null && "cartPage".equals(cartPage)) {
			session.setAttribute("cartPage", cartPage);
		}

		if (user != null) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}