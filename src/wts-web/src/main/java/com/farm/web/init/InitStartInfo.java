package com.farm.web.init;

import javax.servlet.ServletContext;

import com.farm.core.config.AppConfig;
import com.farm.web.task.ServletInitJobInter;

/**展示项目信息
 * @author macpl
 *
 */
public class InitStartInfo implements ServletInitJobInter {
	@Override
	public void execute(ServletContext context) {
		System.out.println("physics path:" + context.getRealPath(""));
		System.out.println("wts version:" + AppConfig.getString("config.sys.version"));
	}
}
