<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html>
<head><jsp:include page="/view/conf/include.jsp"></jsp:include>
<style type="text/css">
.messageTipbox {
	margin: auto;
	width: 250px;
	height: 100px;
	border: dotted 1px #ccc;
	background-color: #FCFCF2;
	padding: 16px;
	margin-top: 100px;
	color: #666;
	text-align: center;
}
</style>
</head>
<body style="text-align: center;">
	<div class="messageTipbox">
		<img alt="<PF:ParameterValue key="config.sys.title" />"
			src="<PF:basePath/>actionImg/Publogo.do"
			style="padding: 13px 5px 0 10px; max-height: 50px" />
		<p id="url_para" style="color: #888;">页面加载中...</p>
	</div>
</body>
</html>