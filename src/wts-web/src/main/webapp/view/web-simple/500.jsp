<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page language="java" pageEncoding="utf-8" isErrorPage="true"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>500-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="atext/include-web.jsp"></jsp:include>
<%
	System.out.println("errorUrl:" + request.getAttribute("URL"));
	System.out.println("errorMessage:" + exception.getMessage());
	System.out.println("errorCause:" + exception.getCause());
%>
</head>
<body>
	<jsp:include page="commons/head.jsp"></jsp:include><div
		class="super_content">
		<br />
	</div>
	<div class="containerbox" style="background-color: #fff;">
		<div class="container ">
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default userbox"
						style="margin: auto; margin-top: 100px; margin-bottom: 100px; background-color: #fcfcfc;">
						<div class="panel-body">
							<div class="text-center">
								<img src="<PF:basePath/>actionImg/PubHomelogo.do" alt="wcp"
									style="margin: 20px; max-width: 128px;" />
							</div>
							<div id="errorMessageId" class="text-center"
								style="margin: -4px; padding: 4px; color: #d13133; font-size: 64px;">
								500</div>
							<div id="errorMessageId" class="text-center"
								style="margin: -4px; padding: 4px; color: #666; font-size: 16px;">
								抱歉，我们的服务遇到一些问题</div>
							<div id="errorMessageId" class="text-center"
								style="margin: -4px; padding: 4px; color: #666; font-size: 12px;">
								错误信息为：
								<%=exception.getMessage() == null ? "" : exception.getMessage()%>
								<%=exception.getCause() == null ? "" : exception.getCause()%>
							</div>
							<div class="text-center" style="margin-top: 26px;">
								<a type="button" href="<PF:basePath/>"
									class="btn btn-danger btn-xs">系统首页</a>
								<c:if test="${USEROBJ!=null}">&nbsp;&nbsp;
									<a type="button" href="<PF:basePath/>webuser/Home.do"
										class="btn btn-default btn-xs">我的信息</a>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="commons/footServer.jsp"></jsp:include>
	<jsp:include page="commons/foot.jsp"></jsp:include>
</body>
</html>
