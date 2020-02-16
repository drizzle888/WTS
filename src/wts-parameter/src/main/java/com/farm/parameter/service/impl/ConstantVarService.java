package com.farm.parameter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.farm.core.auth.util.DesUtil;
import com.farm.core.config.ReadKey;
import com.farm.core.time.TimeTool;
import com.farm.parameter.util.InfoUtil;
import com.farm.util.spring.HibernateSessionFactory;
import com.farm.util.web.WaterCodeUtils;
import com.farm.web.constant.FarmConstant;

@Service
public class ConstantVarService {
	private static Map<String, String> constant = new HashMap<String, String>();

	public static void registConstant(String key, String value) {
		constant.put(key, value);
		if (getValue("farm.constant.webroot.path") != null) {
			ReadKey.read(getValue("farm.constant.webroot.path"));
		}
	}

	public static List<Entry<String, String>> getEntrys() {
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (Entry<String, String> node : constant.entrySet()) {
			list.add(node);
		}
		return list;
	}

	public static String getValue(String key) {
		return constant.get(key);
	}

	public static void registConstant(String str, ServletContext context) {
		context.setAttribute("licenceInfo", InfoUtil.getIpAddress());
		context.setAttribute(FarmConstant.LCENCE_AUTH_WEB_KEY, getLicenceAuth());
	}

	private static String getLicenceAuth() {
		return "OSCHINA";
	}
}
