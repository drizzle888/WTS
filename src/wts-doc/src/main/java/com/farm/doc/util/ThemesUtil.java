package com.farm.doc.util;

import com.farm.parameter.FarmParameterService;

public class ThemesUtil {
	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}
}
