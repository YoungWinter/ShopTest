package cn.itcast.shop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductService;

public class ProductListByCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取商品种类的ID
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");

		if (currentPageStr == null) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 12;

		// 根据商品种类ID查询商品列表
		ProductService productService = new ProductService();
		PageBean<Product> pageBean = productService.findProductByCid(cid, currentPage,
				currentCount);

		// 页面跳转
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
