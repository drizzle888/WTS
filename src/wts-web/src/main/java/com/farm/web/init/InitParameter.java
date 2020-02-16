package com.farm.web.init;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.farm.parameter.FarmParameterService;
import com.farm.parameter.service.impl.ConstantVarService;
import com.farm.parameter.service.impl.PropertiesFileService;
import com.farm.parameter.service.impl.XmlConfigFileService;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.constant.FarmConstant;
import com.farm.web.task.ServletInitJobInter;

public class InitParameter implements ServletInitJobInter {
	private static final Logger log = Logger.getLogger(InitParameter.class);

	@Override
	public void execute(ServletContext context) {
		// 注册常量
		FarmParameterService.getInstance().getDictionary("123");//随便查点什么，为了初始化spring和hibernate 
		ConstantVarService.registConstant("farm.constant.session.key.logintime.", FarmConstant.SESSION_LOGINTIME);
		ConstantVarService.registConstant("farm.constant.session.key.current.org", FarmConstant.SESSION_ORG);
		ConstantVarService.registConstant("farm.constant.session.key.current.roles", FarmConstant.SESSION_ROLES);
		ConstantVarService.registConstant("farm.constant.session.key.current.actions", FarmConstant.SESSION_USERACTION);
		ConstantVarService.registConstant("farm.constant.session.key.current.menu", FarmConstant.SESSION_USERMENU);
		ConstantVarService.registConstant("farm.constant.session.key.current.user", FarmConstant.SESSION_USEROBJ);
		ConstantVarService.registConstant("farm.constant.session.key.current.userphoto",
				FarmConstant.SESSION_USERPHOTO);
		ConstantVarService.registConstant("farm.constant.session.key.go.url", FarmConstant.SESSION_GO_URL);
		ConstantVarService.registConstant("farm.constant.session.key.from.url", FarmConstant.SESSION_FROM_URL);
		ConstantVarService.registConstant("farm.constant.app.treecodelen",
				String.valueOf(FarmConstant.MENU_TREECODE_UNIT_LENGTH));
		ConstantVarService.registConstant("farm.constant.webroot.path", context.getRealPath(""));
		ConstantVarService.registConstant("farm.constant.webroot.path", context);
		// 注册配置文件
		PropertiesFileService.registConstant("jdbc");
		PropertiesFileService.registConstant("config/config");
		PropertiesFileService.registConstant("config/about");
		// 注册xml配置文件
		XmlConfigFileService.registConstant("WcpWebConfig.xml");
		XmlConfigFileService.registConstant("WcpInterConfig.xml");
		sysConstant();
		checkXml();
		// 加载xml配置文件到系统变量中(必须在加载完配置文件后才有意义)
		XmlConfigFileService.loadXmlToApplication(context);
		// 加载风格配置文件
		ThemesUtil.loadXmlConf();
	}

	private static void sysConstant() {
		ConstantVarService.registConstant("farm.sys.java.version","JDK"+ System.getProperty("java.version"));
		ConstantVarService.registConstant("farm.sys.java.home", System.getProperty("java.home"));
		ConstantVarService.registConstant("farm.sys.java.os.name", System.getProperty("os.name"));
		ConstantVarService.registConstant("farm.sys.java.os.version", System.getProperty("os.version"));
		ConstantVarService.registConstant("farm.sys.tomcat.dir", System.getProperty("catalina.base"));
		ConstantVarService.registConstant("farm.sys.logs.dir", System.getProperty("catalina.base")+File.separator+"wcplogs");
	}

	/**
	 * 检测xml中参数是否完整
	 */
	private static void checkXml() {
		log.info("检查XML配置文件参数：------start-----------------------------------");
		List<String> names = XmlConfigFileService.readCheckXmlConfig("config/xmlChecks.xml");
		for (String name : names) {
			checkXmlParameter(name);
		}
		log.info("检查XML配置文件参数：------end-----------------------------------");
	}

	private static void checkXmlParameter(String key) {
		if (XmlConfigFileService.getValue(key) != null) {
			log.info("-----OK:" + key);
		} else {
			log.info("[ERROR]:" + key);
		}
	}

	public static void main(String[] args) {
		// 检查xml配置文件
		// XmlConfigFileService.registConstant("WcpWebConfig.xml");
		// XmlConfigFileService.registConstant("WcpInterConfig.xml");
		// checkXml();
		// System.out.println(AppConfig.getString("config.about"));
	}
}
