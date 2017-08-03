package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Order;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.OrderService;
import cn.itcast.shop.service.ProductServiceImpl;
import cn.itcast.shop.service.impl.CategoryServiceImpl;

public class AdminServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// findOrderInfoByOid
	public String findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String oid = request.getParameter("oid");
		OrderService orderService = new OrderService();
		List<Map<String, Object>> orderInfo = orderService.findOrderInfoByOid(oid);
		Gson gson = new Gson();
		String json = gson.toJson(orderInfo);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);

		return null;
	}

	// 订单展示
	public String orderList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 准备数据(查询该用户下的所有订单)
		OrderService orderService = new OrderService();
		List<Order> orderList = orderService.findAllOrderList();

		// 将数据放入request域中
		request.setAttribute("orderList", orderList);
		// 页面转发

		return "admin/order/list.jsp";
	}

	// 删除商品
	public String delProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer pid = Integer.parseInt(request.getParameter("pid"));

		ProductServiceImpl productService = new ProductServiceImpl();
		productService.delProductByPid(pid);

		return "/admin?method=productList";
	}

	// 修改商品productEditUI
	public String productEditUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer pid = Integer.parseInt(request.getParameter("pid"));

		ProductServiceImpl productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);

		return "/admin/product/edit.jsp";
	}

	// 删除商品分类
	public String delCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		String delInfo = "";
		try {
			categoryService.deleteCategoryByCid(cid);
		} catch (Exception e) {
			delInfo = "删除失败";
			request.setAttribute("delInfo", delInfo);
			request.getRequestDispatcher("/admin?method=categoryList").forward(request, response);
		}

		delInfo = "删除成功";
		request.setAttribute("delInfo", delInfo);
		return "/admin?method=categoryList";
	}

	// 修改商品分类
	public String editCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		categoryService.updateCategory(category);
		return "/admin?method=categoryList";
	}

	// 修改商品分类页面categoryEditUI
	public String categoryEditUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		Category category = categoryService.findCategoryByCid(cid);
		request.setAttribute("category", category);
		return "/admin/category/edit.jsp";
	}

	// 添加商品分类addCategory
	public String addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cname = request.getParameter("cname");
		CategoryServiceImpl categoryService = new CategoryServiceImpl();

		Category category = new Category();
		category.setCname(cname);

		categoryService.addCategory(category);
		return "/admin?method=categoryList";
	}

	// 查询商品分类列表
	public String categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAllCategory();
		request.setAttribute("categoryList", categoryList);
		return "/admin/category/list.jsp";
	}

	// 查询商品分类列表2
	public String productForCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAllCategory();
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}

	// 查询列表
	public String productList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductServiceImpl productService = new ProductServiceImpl();
		List<Product> productList = productService.findAllProductList();
		request.setAttribute("productList", productList);
		return "/admin/product/list.jsp";
	}

}