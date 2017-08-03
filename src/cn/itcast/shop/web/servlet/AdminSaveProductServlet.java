package cn.itcast.shop.web.servlet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductServiceImpl;

/**
 * 如果表单中有文件上传,可以使用该类
 * 
 * @author YangWentao
 *
 */
public class AdminSaveProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Product product = new Product();
		Category category = new Category();
		// 用于封装数据
		Map<String, Object> properties = new HashMap<String, Object>();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> fileItemList = null;
		try {
			fileItemList = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		if (fileItemList != null) {
			for (FileItem fileItem : fileItemList) {
				if (fileItem.isFormField()) {
					// 普通表单项
					String fieldName = fileItem.getFieldName();
					String fieldValue = fileItem.getString("UTF-8");

					properties.put(fieldName, fieldValue);
				} else {
					// 文件项
					String fileName = fileItem.getName();
					String path = getServletContext().getRealPath("upload");
					InputStream is = fileItem.getInputStream();
					OutputStream os = new FileOutputStream(path + "/" + fileName);
					IOUtils.copy(is, os);
					os.close();
					fileItem.delete();

					// 将上传的图片同步到工程目录下(该路径可配置)
					String sysPath = "D:/workspace/ItcastShop/WebContent/upload";
					InputStream fis = new FileInputStream(path + "/" + fileName);
					OutputStream fos = new FileOutputStream(sysPath + "/" + fileName);
					IOUtils.copy(fis, fos);
					fos.close();
					fis.close();

					properties.put("pimage", "upload/" + fileName);
				}
			}

			// 封装数据
			try {
				BeanUtils.populate(product, properties);
				BeanUtils.populate(category, properties);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}

			// 补全数据

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			product.setPdate(format.format(new Date()));
			product.setPflag(0);
			product.setCategory(category);

			// 保存数据
			ProductServiceImpl productService = new ProductServiceImpl();
			productService.saveProduct(product);

			// 页面跳转
			response.sendRedirect(request.getContextPath() + "/admin?method=productList");
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}