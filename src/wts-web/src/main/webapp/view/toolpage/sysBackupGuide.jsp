<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>系统备份向导</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body>
	<div class="demo-info" style="margin: 16px;">
		<h3>
			<span class="demo-tip icon-star"></span>1:备份数据库
		</h3>
		<div>
			1.通过数据库客户端或命令行备份数据库${database},数据库连接信息从jdbc.properties文件中查看<br />(jdbc.properties文件地址：${jdbcpath})
			
			<br />2.常用的mysql数据库客户端为navicat或HeidiSQL等...<br />3.最好备份到不同的物理计算机上
			<!--  -->
			<br />（需要还原备份时，将该数据库备份还原到该数据库中）
		</div>
	</div>
	<div class="demo-info" style="margin: 16px;">
		<h3>
			<span class="demo-tip icon-star"></span>2:备份附件文件
		</h3>
		<div>
			1.复制并备份文件夹${filepath}<br />2.最好备份到不同的物理计算机上
			<!--  -->
			<br />（需要还原备份时，将该文件备份还原到服务器的该目录中）
		</div>
	</div>
</body>
</html>