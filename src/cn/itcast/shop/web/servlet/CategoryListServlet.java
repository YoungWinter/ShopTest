package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

public class CategoryListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CategoryService categoryService = new CategoryService();
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
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}