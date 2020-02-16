package com.farm.web.init;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.farm.core.inter.domain.BusinessHandler;
import com.farm.core.inter.impl.BusinessHandleServer;
import com.farm.parameter.service.impl.XmlConfigFileService;
import com.farm.web.task.ServletInitJobInter;

/**
 * 加载系统自定义的注入对象（现有功能为消息接口实现类等...）
 * 
 * @author lenovo
 *
 */
public class BusinessHandlerInit implements ServletInitJobInter {
	private static final Logger log = Logger.getLogger(BusinessHandlerInit.class);
	private static final String FILENAME = "config/businessHandleConf.xml";

	@Override
	public void execute(ServletContext context) {
		log.info("加载自定义事件回调接口" + FILENAME + "配置");
		URL url = XmlConfigFileService.class.getClassLoader().getResource(FILENAME);
		File file = new File(url.getFile());
		try {
			Document document = Jsoup.parse(file, "UTF-8");
			Elements eles = document.getElementsByTag("server");
			for (Element server : eles) {
				// 读取所有server
				List<BusinessHandler> beanList = new ArrayList<>();
				Elements beanEles = server.getElementsByTag("bean");
				for (Element bean : beanEles) {
					BusinessHandler handler = new BusinessHandler();
					String stateStr = bean.attr("state");
					// 判断bean配置的状态是否为启用状态,未启用则不进行加载
					if (stateStr.toUpperCase().equals("TRUE")) {
						// 构造bean参数，将xml中bean标签下的子标签作为参数封装，在具体回调类中可以使用
						{
							Map<String, String> handlerContext = new HashMap<>();
							for (Element confnode : bean.children()) {
								handlerContext.put(confnode.tagName(), confnode.text());
							}
							handler.setContext(handlerContext);
						}
						// 构造实现类
						try {
							String classStr = bean.attr("class");
							Object obj = Class.forName(classStr).newInstance();
							handler.setBeanImpl(obj);
							beanList.add(handler);
							log.info("-----加载成功bean从配置文件" + FILENAME + "中加载实现类" + classStr + "到" + server.attr("id"));
						} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
							log.error("-----加载失败bean从配置文件" + FILENAME + "中加载失败");
						}
					}
				}
				BusinessHandleServer.addBeans(server.attr("id"), beanList);
			}
		} catch (IOException e) {
			log.error(e+e.getMessage(), e);
		}
	}
}
