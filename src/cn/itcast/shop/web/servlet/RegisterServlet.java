package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.UserService;
import cn.itcast.shop.utils.CommonUtils;

public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取表单参数
		Map<String, String[]> properties = request.getParameterMap();
		// 封装User对象
		User user = new User();
		try {
			// 自定义转换器(String->Date)
			ConvertUtils.register(new Converter() {

				@Override
				public Object convert(@SuppressWarnings("rawtypes") Class calzz, Object value) {
					Date date = null;
					// 将String转换成Date
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					try {
						date = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date;
				}
			}, Date.class);
			// 映射封转
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		// 补全数据
		user.setUid(CommonUtils.getUUID());
		user.setTelephone(null);
		user.setState(0);
		// 用户注册服务
		UserService userService = new UserService();
		boolean isRegistSuccess = userService.regist(user);
		response.setContentType("text/html;charset=UTF-8");
		if (isRegistSuccess) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}