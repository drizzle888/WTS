<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页- <PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<jsp:include page="atext/include-web.jsp"></jsp:include>
<script src="view/web-simple/atext/script/wts-home.js"></script>
</head>
<body>
	<jsp:include page="commons/head.jsp"></jsp:include>
	<jsp:include page="commons/superContent.jsp"></jsp:include>
	<!-- /.carousel -->
	<div class="containerbox">
		<jsp:include page="commons/homeTypeSubject.jsp"></jsp:include>
		<c:if test="${!isHaveRoome }">
			<div
				style="max-width: 400px; margin: auto; background-color: #ffffff; border: 1px dashed #666; padding: 4px;">
				<div class="media">
					<div class="pull-left">
						<img alt="WCP商业版介绍预览图" src="text/img/exam100.png"
							style="width: 32px; height: 32px;">
					</div>
					<div class="media-body">
						<div style="margin-left: 4px; font-size: 18px; padding-top: 2px;"
							class="pull-left">当前无考试，请创建考试或联系管理员！</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<jsp:include page="commons/footServer.jsp"></jsp:include>
	<jsp:include page="commons/foot.jsp"></jsp:include>
</body>
</html>