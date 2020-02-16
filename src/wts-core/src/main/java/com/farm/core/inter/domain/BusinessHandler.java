package com.farm.core.inter.domain;

import java.util.Map;

/**
 * 业务处理器属性封装类，配合BusinessHandleServer使用,用在业务处理器的参数配置传递
 * 
 * @author lenovo
 *
 */
public class BusinessHandler {
	/**
	 * 实现类
	 */
	private Object beanImpl;
	/**
	 * 配置文件
	 */
	private Map<String, String> context;

	public Object getBeanImpl() {
		return beanImpl;
	}

	public void setBeanImpl(Object beanImpl) {
		this.beanImpl = beanImpl;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

}
