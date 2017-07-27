package cn.itcast.shop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.service.UserService;

public class CheckUsername extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取要校验的用户名
		String username = request.getParameter("username");

		// 校验用户名
		UserService userService = new UserService();
		boolean isExist = userService.checkUsername(username);

		String json = "{\"isExist\":" + "\"" + isExist + "\"}";

		response.getWriter().write(json);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}