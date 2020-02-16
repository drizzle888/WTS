package com.farm.web.filter;

import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;

import com.farm.core.config.AppConfig;

/**
 * 跨站攻击漏洞辅助程序，负责过滤js脚本
 * 
 * @author wangdong
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private static final Logger log = Logger.getLogger(XssHttpServletRequestWrapper.class);
	private static Policy policy = null;
	private static boolean able = true;

	static {
		String filename = AppConfig.getString("config.safe.xss.conf");
		if (filename.toLowerCase().equals("false")) {
			able = false;
		} else {
			URL url = XssHttpServletRequestWrapper.class.getClassLoader().getResource("/config/xssconf/" + filename);
			String path = url.getFile();
			if (path.startsWith("file")) {
				path = path.substring(6);
			}
			try {
				policy = Policy.getInstance(path);
			} catch (PolicyException e) {
				log.error(e + e.getMessage(), e);
			}
		}
	}

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> request_map = super.getParameterMap();
		if (!able) {
			return request_map;
		}
		Iterator iterator = request_map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry me = (Map.Entry) iterator.next();
			String[] values = (String[]) me.getValue();
			for (int i = 0; i < values.length; i++) {
				System.out.println(values[i]);
				values[i] = xssClean(values[i]);
			}
		}
		return request_map;
	}

	@Override
	public String getParameter(String name) {
		String v = super.getParameter(name);
		if (!able) {
			return v;
		}
		if (v == null)
			return null;
		return xssClean(v);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] v = super.getParameterValues(name);
		if (!able) {
			return v;
		}
		if (v == null || v.length == 0) {
			return v;
		}
		for (int i = 0; i < v.length; i++) {
			if ("typefields".equals(name)||"text".equals(name)) {
				// 特殊例外:typefields是用户分类自定义json字符串不进行转义
			} else {
				v[i] = xssClean(v[i]);
			}
		}
		return v;
	}

	private String xssClean(String value) {
		AntiSamy antiSamy = new AntiSamy();
		value = decode(value);
		try {
			final CleanResults cr = antiSamy.scan(value, policy);
			// System.out.println("clean:" + cr.getCleanHTML());
			return cr.getCleanHTML();
		} catch (Exception e) {
			log.warn(e.getMessage());
			return "该内容不安全，请修改后重新发表，或者请降低系统安全级别";
		}

	}

	/**
	 * URL解码
	 * 
	 * @param value
	 * @return
	 */
	private String decode(String value) {
		try {
			for (int i = 0; i < 10; i++) {
				if (isContainUrlCode(value)) {
					value = URLDecoder.decode(value, "utf-8");
				} else {
					break;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log.warn(e1.getMessage());
		}
		return value;
	}

	/**
	 * 是否包含URL编码
	 * 
	 * @param val
	 * @return
	 */
	private boolean isContainUrlCode(String val) {
		// 要验证的字符串
		// 正则表达式规则
		String regEx = "%[a-zA-z0-9]{4}";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(val);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		boolean rs = matcher.find();
		return rs;
	}
}
