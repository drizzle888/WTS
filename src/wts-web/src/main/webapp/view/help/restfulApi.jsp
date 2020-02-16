<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>restfulAPI-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<script type="text/javascript" src="text/javascript/jquery-1.8.0.min.js"></script>
<link href="text/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="text/lib/bootstrap/css/bootstrap-theme.min.css"
	rel="stylesheet">
<script src="text/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="text/lib/bootstrap/respond.min.js"></script>
<script type="text/javascript">
	var basePath = '<PF:basePath/>';
	$(function() {
		$.ajaxSetup({
			cache : false
		});
	})
//-->
</script>
<style>
<!--
.container h1 {
	font-weight: 700;
	margin: 10px;
}

.container h3 {
	margin: 10px;
	color: #CE4844;
}

.container .protocol {
	color: #4fadc2;
	font-size: 24px;
	font-weight: 500px;
}

.container .demo {
	color: #4fadc2;
	max-width: 400px;
}

ul li {
	font-size: 18px;
	font-weight: 200;
}

h1 a {
	font-size: 12px;
	margin-left: 24px;
	padding-left: 24px;
	font-weight: 200;
}

.apimenu {
	
}

.apimenu li {
	margin: 0px;
	padding: 2px;
	line-height: 14px;
	font-size: 14px;
}

.apimenu li a {
	margin: 0px;
	padding: 4px;
}

.page-header {
	padding-top: 50px;
}

caption {
	text-align: left;
	color: #CE4844;
}
-->
</style>
</head>
<body class="container" style="padding: 16px;">
	<div class="row">
		<div class="col-sm-3 ">
			<nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix">
				<ul class="nav bs-docs-sidenav apimenu">
					<li><a href="helper/readme.do#fireRead">准备</a></li>
					<li><a href="helper/readme.do#secretApi">权限码secret----[<b>查询</b>]
					</a></li>
					<li><a href="helper/readme.do#orgGetApi">组织机构----[<b>查询</b>]
					</a></li>
					<li><a href="helper/readme.do#orgPostApi">组织机构----[<b>创建</b>]
					</a></li>
					<li><a href="helper/readme.do#orgPutApi">组织机构----[<b>更新</b>]
					</a></li>
					<li><a href="helper/readme.do#orgDeleteApi">组织机构----[<b>删除</b>]
					</a></li>
					<li><hr style="margin: 1px;" /></li>
					<li><a href="helper/readme.do#userGetApi">用户----[<b>查询</b>]
					</a></li>
					<li><a href="helper/readme.do#userPostApi">用户----[<b>创建</b>]
					</a></li>
					<li><a href="helper/readme.do#userPutApi">用户----[<b>更新</b>]
					</a></li>
					<li><a href="helper/readme.do#userDeleteApi">用户----[<b>删除</b>]
					</a></li>
					<li><hr style="margin: 1px;" /></li>
					<li><a href="helper/readme.do#loginInfoApi">免密登录----[<b>概述</b>]
					</a></li>
					<li><a href="helper/readme.do#registLoginApi">免密登录----[<b>授权注册</b>]
					</a></li>
					<li><a href="helper/readme.do#doLoginApi">免密登录----[<b>通过授权码登录</b>]
					</a></li>
					<li><a href="helper/readme.do#getLoginApi">免密登录----[<b>通过授权码获得登录名</b>]
					</a></li>
				</ul>
			</nav>
		</div>
		<div class="col-sm-9">
			<!-- 准备 -->
			<div id="fireRead" class="page-header">
				<jsp:include page="comment/firstRead.jsp"></jsp:include>
			</div>
			<!-- secret-->
			<div id="secretApi" class="page-header">
				<jsp:include page="comment/secretApi.jsp"></jsp:include>
			</div>
			<!-- 机构查询 -->
			<div id="orgGetApi" class="page-header">
				<jsp:include page="comment/orgGetApi.jsp"></jsp:include>
			</div>
			<!-- 机构创建 -->
			<div id="orgPostApi" class="page-header">
				<jsp:include page="comment/orgPostApi.jsp"></jsp:include>
			</div>
			<!-- 机构更新-->
			<div id="orgPutApi" class="page-header">
				<jsp:include page="comment/orgPutApi.jsp"></jsp:include>
			</div>
			<!-- 机构删除-->
			<div id="orgDeleteApi" class="page-header">
				<jsp:include page="comment/orgDeleteApi.jsp"></jsp:include>
			</div>
			<!-- 用户查询 -->
			<div id="userGetApi" class="page-header">
				<jsp:include page="comment/userGetApi.jsp"></jsp:include>
			</div>
			<!-- 用户创建 -->
			<div id="userPostApi" class="page-header">
				<jsp:include page="comment/userPostApi.jsp"></jsp:include>
			</div>
			<!-- 用户更新-->
			<div id="userPutApi" class="page-header">
				<jsp:include page="comment/userPutApi.jsp"></jsp:include>
			</div>
			<!-- 用户删除-->
			<div id="userDeleteApi" class="page-header">
				<jsp:include page="comment/userDeleteApi.jsp"></jsp:include>
			</div>
			<!-- 分类查询 -->
			<div id="categoryGetApi" class="page-header">
				<jsp:include page="comment/categoryGetApi.jsp"></jsp:include>
			</div>
			<!-- 免登概述-->
			<div id="loginInfoApi" class="page-header">
				<jsp:include page="comment/loginInfoApi.jsp"></jsp:include>
			</div>
			<!-- 注册登录权限码-->
			<div id="registLoginApi" class="page-header">
				<jsp:include page="comment/registLoginApi.jsp"></jsp:include>
			</div>
			<!-- 权限码登录-->
			<div id="doLoginApi" class="page-header">
				<jsp:include page="comment/doLoginApi.jsp"></jsp:include>
			</div>
			<!-- 权限码获得登录名-->
			<div id="getLoginApi" class="page-header">
				<jsp:include page="comment/getLoginApi.jsp"></jsp:include>
			</div>
		</div>
	</div>
</body>
</html>