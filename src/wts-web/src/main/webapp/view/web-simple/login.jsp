<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录-<PF:ParameterValue key="config.sys.title" />
</title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="atext/include-web.jsp"></jsp:include>
</head>
<body>
	<script type="text/javascript">
		if (window != top) {
			$("#loginId").html(
					'<div style="font-size:25px;text-align:center;">'
							+ "无法在iframe中实现登录操作，正在跳转中。。。" + '<//div>');
			top.location.href = "login/webPage.html";
		}
	</script>
	<jsp:include page="commons/head.jsp"></jsp:include>
	<jsp:include page="commons/superContent.jsp"></jsp:include>
	<div class="containerbox" >
		<div class="container ">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="panel panel-default userbox"
						style="margin: auto; margin-top: 30px; margin-bottom: 100px; background-color: #fcfcfc;">
						<div class="panel-body">
							<jsp:include page="commons/loginbox.jsp"></jsp:include>
						</div>
					</div>
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</div>
	<jsp:include page="commons/footServer.jsp"></jsp:include>
	<jsp:include page="commons/foot.jsp"></jsp:include>
</body>
</html>