package com.farm.util.spring;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.farm.core.config.AppConfig;

//读取配置文件进行加载
public class BeanFactory {
	private static ApplicationContext appContext;

	public static Object getBean(String beanId) {
		getContext();
		return appContext.getBean(beanId);
	}

	public static ApplicationContext getContext() {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(AppConfig
					.getString("init.config.spring.configs").split(","));
		}
		return appContext;
	}

	public static Object getBean(String beanId, ServletContext servletContext) {
		//Context.MK = "FREE";
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		return ctx.getBean(beanId);
	}

}
