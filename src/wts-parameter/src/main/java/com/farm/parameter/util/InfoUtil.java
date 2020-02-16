package com.farm.parameter.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class InfoUtil {

	public static String getIpAddress() {
		String ip = null;
		try {
			InetAddress address = null;
			address = InetAddress.getLocalHost();
			ip = address.getHostAddress();
		} catch (UnknownHostException e) {
			ip = "IPNONE";
		}
		Properties props = System.getProperties();
		String osUser = System.getProperty("user.name");
		String osVersion = props.getProperty("os.version");
		return (ip +"-"+ osVersion+"-"+ osUser ).replaceAll("\\.", "-");
	}
	public static void main(String[] args) {
		System.out.println(getIpAddress());
	}
}