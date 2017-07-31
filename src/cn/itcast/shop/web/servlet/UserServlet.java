package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.UserService;
import cn.itcast.shop.utils.CommonUtils;
import cn.itcast.shop.utils.MailUtils;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// 用户退出
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 从session中移除user对象
		if (user != null) {
			session.removeAttribute("user");
		}

		// 从cookie中移除user的信息
		Cookie cookie_username = new Cookie("cookie_username", "");
		cookie_username.setMaxAge(0);
		Cookie cookie_password = new Cookie("cookie_password", "");
		cookie_password.setMaxAge(0);
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);

		// 重定向到首页
		response.sendRedirect(request.getContextPath());

		return null;
	}

	// 用户登录
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
			// 获取是否自动登陆的选项
			// ***************判断用户是否勾选了自动登录*****************
			String autoLogin = request.getParameter("autoLogin");
			if ("autoLogin".equals(autoLogin)) {
				// 把用户的用户名和密码存入Cookie中
				String username = loginUser.getUsername();
				String encode_username = URLEncoder.encode(username, "UTF-8");
				Cookie cookie_username = new Cookie("cookie_username", encode_username);
				cookie_username.setMaxAge(60 * 60);

				Cookie cookie_psaaword = new Cookie("cookie_psaaword", loginUser.getPassword());
				cookie_psaaword.setMaxAge(60 * 60);

				response.addCookie(cookie_username);
				response.addCookie(cookie_psaaword);

			}
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

	// 用户注册
	public String register(HttpServletRequest request, HttpServletResponse response)
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
		String activeCode = CommonUtils.getUUID();
		user.setCode(activeCode);

		// 用户注册服务
		UserService userService = new UserService();
		boolean isRegistSuccess = userService.regist(user);

		response.setContentType("text/html;charset=UTF-8");
		if (isRegistSuccess) {

			// 发送激活邮件
			String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
					+ "<a href='http://localhost:8080/ItcastShop/user?method=active&activeCode="
					+ activeCode + "'>"
					+ "http://localhost:8080/ItcastShop/user?method=active&activeCode=" + activeCode
					+ "</a>";
			try {
				MailUtils.sendMail(user.getEmail(), "注册激活", emailMsg);
			} catch (MessagingException e) {
				System.out.println("连接邮箱服务器失败");
			}

			// 跳转到注册成功页面
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
			return null;
		}

		return "/registerFail.jsp";
	}

	// 用户激活
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取邮件中的激活码
		String activeCode = request.getParameter("activeCode");

		// 根据激活码查找User对象，并修改User对象的状态值(state)
		UserService userService = new UserService();
		userService.active(activeCode);

		// 跳转到登录页面
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return null;
	}

	// 校验用户名
	public String checkUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取要校验的用户名
		String username = request.getParameter("username");

		// 校验用户名
		UserService userService = new UserService();
		boolean isExist = userService.checkUsername(username);

		String json = "{\"isExist\":" + isExist + "}";
		response.getWriter().write(json);
		return null;
	}

	// 校验图片验证码
	public String checkImgValidate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isTrue = false;
		String checkCode = request.getParameter("checkCode");
		String attribute = (String) request.getSession().getAttribute("checkcode_session");
		if (checkCode != null && checkCode.equals(attribute)) {
			isTrue = true;
		}
		String json = "{\"isExist\":" + isTrue + "}";
		response.getWriter().write(json);
		return null;
	}

}