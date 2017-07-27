package cn.itcast.shop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.service.UserService;

public class ActiveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取邮件中的激活码
		String activeCode = request.getParameter("activeCode");

		// 根据激活码查找User对象，并修改User对象的状态值(state)
		UserService userService = new UserService();
		userService.active(activeCode);

		// 跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}