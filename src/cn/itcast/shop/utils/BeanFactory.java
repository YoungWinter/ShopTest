package cn.itcast.shop.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	public static Object getBean(String id) {

		// 生产对象---根据清单生产----配置文件----将每一个bean对象的生产的细节配到配置文件中
		// 使用dom4j的xml解析技术
		Object object = null;
		try {
			// 1、创建解析器
			SAXReader saxReader = new SAXReader();
			// 2、解析文档---bean.xml在工程的src下,在项目的WEB-INF\classes目录下
			String path = BeanFactory.class.getClassLoader().getResource("bean.xml").getPath();
			Document document = saxReader.read(path);
			// 3、获得元素---参数是xpath规则
			Element element = (Element) document.selectSingleNode("//bean[@id='" + id + "']");
			// 4、使用反射创建对象
			String className = element.attributeValue("class");
			object = Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return object;
	}
}
