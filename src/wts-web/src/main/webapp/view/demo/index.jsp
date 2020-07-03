<%@page import="com.farm.parameter.service.impl.ConstantVarService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title><PF:ParameterValue key="config.sys.title" /></title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="text/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="text/lib/bootstrap/css/bootstrap-theme.min.css"
	rel="stylesheet">
<script type="text/javascript" src="text/javascript/jquery-1.8.0.min.js"></script>
<script src="text/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="text/lib/bootstrap/respond.min.js"></script>
<script type="text/javascript"
	src="<PF:basePath/>text/lib/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript"
	src="<PF:basePath/>text/lib/kindeditor/zh-CN.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-multiimage-kindeditor.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-latex-kindeditor.js"></script>
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-multiimage-kindeditor.css">
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-kindeditor.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/htmltags.js"></script>
<!-- 附件批量上传组件 -->
<script src="<PF:basePath/>text/lib/fileUpload/jquery.ui.widget.js"></script>
<script
	src="<PF:basePath/>text/lib/fileUpload/jquery.iframe-transport.js"></script>
<script src="<PF:basePath/>text/lib/fileUpload/jquery.fileupload.js"></script>
<link href="<PF:basePath/>text/lib/fileUpload/jquery.fileupload.css"
	rel="stylesheet">
</head>
<body>
	<div style="text-align: left; padding: 20px;">
		<%=request.getHeaderNames()%><br />
		<%
			Enumeration<?> enum1 = request.getHeaderNames();
			while (enum1.hasMoreElements()) {
				String key = (String) enum1.nextElement();
				String value = request.getHeader(key);
		%>
		<%=key + "\t" + value%><br />
		<%
			}
		%>
	</div>
</body>
</html>