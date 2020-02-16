package com.farm.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 跨站攻击漏洞辅助程序，负责过滤js脚本
 * 
 * @author WangDong
 * @date Mar 14, 2017
 * 
 */
public class FilterXss implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String requestUrl = request.getRequestURL().toString();
		if (requestUrl.indexOf("PubUpBase64File") >= 0) {
			// 如果是上传base64位的截图则不继续xss校验
			chain.doFilter(arg0, arg1);
			return;
		}
		if (requestUrl.indexOf("addTextSubjects") >= 0) {
			// 批量提交试题，因为有大文本的格式数据
			chain.doFilter(arg0, arg1);
			return;
		}
		if (requestUrl.indexOf("saveSubjectVal") >= 0 || requestUrl.indexOf("savePaperVal") >= 0||requestUrl.indexOf("countPoint") >= 0) {
			// 答题过程中保存中间答案,因为有json的数据
			chain.doFilter(arg0, arg1);
			return;
		}
		if (requestUrl.indexOf("submitAdjudge") >= 0) {
			// 判卷時提交分數
			chain.doFilter(arg0, arg1);
			return;
		}
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) arg0), arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}