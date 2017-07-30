package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("all")
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// 获取方法名
			String methodName = request.getParameter("method");
			// 通过反射获得this对象的字节码对象
			Class clazz = this.getClass();
			Method method = clazz.getMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
			String path = (String) method.invoke(this, request, response);
			if (path != null) {
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}