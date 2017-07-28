package cn.itcast.shop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckImgValidate extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isTrue = false;
		String checkCode = request.getParameter("checkCode");
		String attribute = (String) request.getSession().getAttribute("checkcode_session");
		if (checkCode != null && checkCode.equals(attribute)) {
			isTrue = true;
		}
		String json = "{\"isExist\":" + isTrue + "}";
		response.getWriter().write(json);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}