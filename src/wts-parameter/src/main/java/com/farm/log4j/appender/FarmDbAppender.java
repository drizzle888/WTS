package com.farm.log4j.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.LoggingEvent;

import com.farm.parameter.service.AloneApplogServiceInter;
import com.farm.util.spring.BeanFactory;

public class FarmDbAppender extends AppenderSkeleton {

	@Override
	protected void append(LoggingEvent arg0) {
		String userid = MDC.get("USERID") != null ? MDC.get("USERID")
				.toString() : "NONE";
		String ip = MDC.get("IP") != null ? MDC.get("IP").toString() : "NONE";

		String message = arg0.getMessage() != null ? arg0.getMessage()
				.toString() : "";

		if (message.length() > 512) {
			message = message.substring(0, 512) + "... ...";
		}
		try {
			AloneApplogServiceInter logService = (AloneApplogServiceInter) BeanFactory
					.getBean("aloneApplogServiceImpl");
			logService.log(message, userid, arg0.getLevel().toString(), arg0
					.getLocationInformation().getMethodName(), arg0
					.getLocationInformation().getClassName(), ip);
		} catch (Exception e) {
			System.out.println("ERROR:日志系统异常，请修复!log error( " + message + ")");
		}
	}

	@Override
	public void close() {
		return;
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}
