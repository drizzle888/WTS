<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>评论-${doc.doc.title}<PF:ParameterValue
		key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/zh-CN.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
<style>
<!--
#message_wcp_box a {
	margin: 4px;
	border: 1px dotted #eee;
	padding: 2px;
	padding-left: 6px;
	padding-right: 6px;
	color: #717fa2;
	background-color: #fcfae6;
	text-decoration:underline;
}

#message_wcp_box a:hover {
	margin: 4px;
	color:#D9534F;
	border: 1px dotted #eee;
	padding: 2px;
	padding-left: 6px;
	padding-right: 6px; 
	background-color: #ededec;
}

.messagetitle {
	font-weight: 400;
}

.messagetitle i {
	font-weight: 700;
	margin-left: 4px;
	margin-right: 4px;
}
-->
</style>
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row">
				<div class="col-md-9">
					<div class="row">
						<div class="col-md-12" style="padding-top: 50px;">
							<h2>
								消息主题：<span class="messagetitle">${usermessage.title }<span>
							</h2>
							<hr />
							<div class="row" style="padding-top: 30px;">
								<div class="col-md-12"> 
									<div class="form-group">
										<div class="panel panel-default" style="padding: 20px;">
											<div class="panel-body messagetitle" id="message_wcp_box"
												style="line-height: 30px; font-size: 14px;">${usermessage.content }</div>
										</div>
										<br /> <a class="btn btn-primary"
											href="webuser/PubHome.do?type=usermessage&num=${num }&userid=${USEROBJ.id}">返回消息列表</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
	
</script>
</html>