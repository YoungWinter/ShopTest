package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.service.ProductService;

public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 查询分类
		CategoryService categoryService = new CategoryService();
		List<Category> categoryList = categoryService.findAllCategory();
		request.setAttribute("categoryList", categoryList);

		ProductService productService = new ProductService();
		// 最热商品
		List<Product> hotProductList = productService.findHotProductList();
		// 最新商品
		List<Product> newProductList = productService.findNewProductList();
		// 把结果放入request域
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		// 页面跳转
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}