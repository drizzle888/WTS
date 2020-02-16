package com.farm.web.task.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.farm.web.constant.FarmConstant;
import com.farm.web.task.ServletInitJobInter;

public class ServerLicenceInit implements ServletInitJobInter {

	private final static Logger log = Logger.getLogger(ServerLicenceInit.class);

	@Override
	public void execute(ServletContext context) {
		try {
			FarmConstant.LICENCE = read(
					new File(context.getRealPath("") + File.separator
							+ "licence.data")).replace("\n", "");
		} catch (Exception e) {
			FarmConstant.LICENCE = null;
		}
		try {
		} catch (Exception e) {
			System.out.println("info: case is "+FarmConstant.LICENCE + " for "+ ":false");
		}
	}

	public String read(File file) throws Exception {
		return "OSCHINA";
	}
}
