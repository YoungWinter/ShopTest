package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductService;

public class ProductInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取商品的pid
		String pid = request.getParameter("pid");
		String currentPage = request.getParameter("currentPage");

		// 根据pid查询商品对象
		ProductService productService = new ProductService();
		Product product = productService.findProductByPid(pid);

		// 创建或获取Cookie对象，然后向对象中设置当前商品的pid
		String pids = pid;
		Cookie[] getCookies = request.getCookies();
		if (getCookies != null) {
			for (Cookie cookie : getCookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
					LinkedList<String> pidsList = new LinkedList<String>(
							Arrays.asList(pids.split("-")));
					if (pidsList.contains(pid)) {
						pidsList.remove(pid);
					}
					pidsList.addFirst(pid);
					pids = "";
					for (int i = 0; i < pidsList.size() && i < 7; i++) {

						pids += pidsList.get(i);
						pids += "-";
					}
					pids = pids.substring(0, pids.length() - 1);
				}
			}
		}

		Cookie cookies = new Cookie("pids", pids);
		response.addCookie(cookies);

		// 页面跳转
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.getRequestDispatcher("/product_info.jsp").forward(request,
				response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}