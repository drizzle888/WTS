<%@page import="com.farm.parameter.service.impl.ConstantVarService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
</head>
<body style="background-color: #000000;">
	<div class="container" style="padding-top: 150px;">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4 text-center">
				<img src="actionImg/Publogo.do" style="width: 72px; height: 72px;"
					alt="wcp" class="img-circle">
			</div>
			<div class="col-md-4"></div>
		</div>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6 text-center">
				<h2 style="color: #ffffff;">
					WTS在线答题系统-V
					<PF:ParameterValue key="config.sys.version" />
				</h2>
				<p style="color: #bfbfbf; font-size: 14px;">
					页面载入中(<a href="<PF:defaultIndexPage/>" style="color: #ffffff;">立即加载</a>)......
				</p>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	setTimeout(function() {
		window.location = '<PF:defaultIndexPage/>';
	}, 1000);
</script>
</html>