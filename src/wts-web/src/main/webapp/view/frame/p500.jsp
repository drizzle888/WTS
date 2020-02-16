<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.PrintStream"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page isErrorPage="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.io.*"%>
<%@page import="com.farm.core.auth.domain.LoginUser"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body style="text-align: center; background-color: #ffffff;">
	<div id="imgDivId"
		style="margin: auto; background-image: url('WEB-FACE/img/style/500.png'); width: 750px; height: 450px;">
	</div>
	<div onclick="showDetailMessager()" id="buttonTitleId"
		style="text-decoration: underline; font-weight: bold; cursor: pointer;">显示详细错误信息</div>
	<script type="text/javascript">
	<!--
		var isShow = true;
		function showDetailMessager() {
			if (isShow) {
				$('#messageDivId').slideDown("slow");
				$('#imgDivId').slideUp("slow");
				$('#buttonTitleId').text('关闭详细错误信息');
				isShow = false;
			} else {
				$('#messageDivId').slideUp("slow");
				$('#imgDivId').slideDown("slow");
				$('#buttonTitleId').text('显示详细错误信息');
				isShow = true;
			}

		}
	//-->
	</script>
	<div id="messageDivId" style="text-align: left; display: none;">
		<xmp> <%
 	try {
   		//全部内容先写到内存，然后分别从两个输出流再输出到页面和文件
   		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
   		PrintStream printStream = new PrintStream(byteArrayOutputStream);

   		printStream.println();
   		printStream.println("用户信息");
   		printStream.println("账号："
   				+ ((LoginUser)request.getSession().getAttribute("USEROBJ")).getName());
   		printStream
   				.println("访问的路径: "
   						+ request
   								.getAttribute("javax.servlet.forward.request_uri"));
   		printStream.println();

   		printStream.println("异常信息");
   		printStream.println(exception.getClass() + " : "
   				+ exception.getMessage());
   		printStream.println();

   		Enumeration<String> e = request.getParameterNames();
   		if (e.hasMoreElements()) {
   			printStream.println("请求中的Parameter包括：");
   			while (e.hasMoreElements()) {
   				String key = e.nextElement();
   				printStream.println(key + "="
   						+ request.getParameter(key));
   			}
   			printStream.println();
   		}

   		printStream.println("堆栈信息");
   		exception.printStackTrace(printStream);
   		printStream.println();

   		out.print(byteArrayOutputStream); //输出到网页

   		File dir = new File(request.getRealPath("/errorLog"));
   		if (!dir.exists()) {
   			dir.mkdir();

   		}
   		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmssS")
   				.format(new Date());
   		FileOutputStream fileOutputStream = new FileOutputStream(
   				new File(dir.getAbsolutePath() + File.separatorChar
   						+ "error-" + timeStamp + ".txt"));
   		new PrintStream(fileOutputStream).print(byteArrayOutputStream); //写到文件

   	} catch (Exception ex) {
   		ex.printStackTrace();
   	}
 %> </xmp>
	</div>
</body>
</html>