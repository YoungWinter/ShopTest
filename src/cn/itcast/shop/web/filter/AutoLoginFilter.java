package cn.itcast.shop.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.UserService;

public class AutoLoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		// 判断用户是否已经登陆
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 获取cookie中的username和password
			String cookie_username = null;
			String cookie_psaaword = null;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("cookie_username".equals(cookie.getName())) {
						cookie_username = cookie.getValue();
					}
					if ("cookie_psaaword".equals(cookie.getName())) {
						cookie_psaaword = cookie.getValue();
					}
				}
			}

			if (cookie_username != null && cookie_psaaword != null) {
				User validateUser = new User();
				validateUser.setUsername(cookie_username);
				validateUser.setPassword(cookie_psaaword);

				// 去数据库校验该用户名和密码是否正确
				UserService userService = new UserService();
				User isValidateUser = userService.login(validateUser);
				if (isValidateUser != null) {
					session.setAttribute("user", isValidateUser);
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}