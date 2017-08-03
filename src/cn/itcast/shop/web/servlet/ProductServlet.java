package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductServiceImpl;
import cn.itcast.shop.service.impl.CategoryServiceImpl;
import cn.itcast.shop.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// 搜索商品
	public String searchProductListByWord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取关键字
		String keyWord = request.getParameter("word");
		// 根据关键字查询符合条件的商品集合
		List<Product> productList = null;
		ProductServiceImpl productService = new ProductServiceImpl();
		productList = productService.findProductListByWord(keyWord);

		Gson gson = new Gson();
		String json = gson.toJson(productList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}

	// 商品种类列表
	public String categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		List<Category> categoryList = null;
		String json = null;
		Gson gson = new Gson();
		// 先从缓存中查询categoryList 如果有直接使用 没有在从数据库中查询 存到缓存中
		Jedis jedis = JedisPoolUtils.getJedis();
		json = jedis.get("categoryList");
		if (json == null) {
			categoryList = categoryService.findAllCategory();
			json = gson.toJson(categoryList);
			jedis.set("categoryList", json);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}

	// 商品首页展示列表
	public String index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 查询分类
		CategoryServiceImpl categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAllCategory();
		request.setAttribute("categoryList", categoryList);

		ProductServiceImpl productService = new ProductServiceImpl();
		// 最热商品
		List<Product> hotProductList = productService.findHotProductList();
		// 最新商品
		List<Product> newProductList = productService.findNewProductList();
		// 把结果放入request域
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		// 页面跳转

		return "/index.jsp";
	}

	// 商品详情
	public String productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取商品的pid
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		String currentPage = request.getParameter("currentPage");

		// 根据pid查询商品对象
		ProductServiceImpl productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);

		// 创建或获取Cookie对象，然后向对象中设置当前商品的pid
		String pids = pid.toString();
		Cookie[] getCookies = request.getCookies();
		if (getCookies != null) {
			for (Cookie cookie : getCookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
					LinkedList<String> pidsList = new LinkedList<String>(
							Arrays.asList(pids.split(":")));
					if (pidsList.contains(pid)) {
						pidsList.remove(pid);
					}
					pidsList.addFirst(pid.toString());
					pids = "";
					for (int i = 0; i < pidsList.size() && i < 7; i++) {

						pids += pidsList.get(i);
						pids += ":";
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

		return "/product_info.jsp";
	}

	// 商品列表
	public String productListByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取商品种类的ID
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		String currentPageStr = request.getParameter("currentPage");

		if (currentPageStr == null || "".equals(currentPageStr)) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 12;

		// 根据商品种类ID查询商品列表
		ProductServiceImpl productService = new ProductServiceImpl();
		PageBean<Product> pageBean = productService.findProductByCid(cid, currentPage,
				currentCount);

		// 设置历史记录
		List<Product> historyList = new ArrayList<Product>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String[] pids = cookie.getValue().split(":");
					for (String pid : pids) {
						Product product = productService.findProductByPid(Integer.parseInt(pid));
						historyList.add(product);
					}
				}
			}
		}

		// 页面跳转
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("historyList", historyList);
		request.setAttribute("cid", cid);

		return "/product_list.jsp";
	}
}