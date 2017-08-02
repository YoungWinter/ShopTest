package cn.itcast.shop.web.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// 类型转换
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletRequest proxyHttpRequest = null;

		String method = httpRequest.getMethod();

		if ("GET".equals(method)) {

			// 动态代理
			proxyHttpRequest = (HttpServletRequest) Proxy.newProxyInstance(
					httpRequest.getClass().getClassLoader(), //
					httpRequest.getClass().getInterfaces(), //
					new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							String name = method.getName();
							if ("getParameter".equals(name)) {
								// 获得参数(含中文乱码)
								String invoke = (String) method
										.invoke(httpRequest, args);
								invoke = new String(
										invoke.getBytes("iso8859-1"), "UTF-8");
								return invoke;
							}
							return method.invoke(httpRequest, args);
						}
					});

		} else if ("POST".equals(method)) {
			proxyHttpRequest = httpRequest;
			proxyHttpRequest.setCharacterEncoding("UTF-8");

		} else {
			proxyHttpRequest = httpRequest;
		}

		chain.doFilter(proxyHttpRequest, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}