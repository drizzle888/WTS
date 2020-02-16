<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>荣誉-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<script type="text/javascript" src="text/javascript/echarts.min.js"></script>
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row  column_box">
				<c:if test="${USEROBJ!=null}">
					<div class="col-md-12">
						<jsp:include page="commons/includeMyStatis.jsp"></jsp:include>
					</div>
				</c:if>
			</div>
			<div class="row">
				<PF:IfParameterEquals key="config.show.group" val="true">
					<div class="col-md-4">
						<jsp:include page="commons/includeGoodUser.jsp"></jsp:include>
					</div>
					<div class="col-md-4">
						<jsp:include page="commons/includeGoodGroup.jsp"></jsp:include>
					</div>
					<div class="col-md-4">
						<jsp:include page="commons/includeManyDocUser.jsp"></jsp:include>
					</div>
				</PF:IfParameterEquals>
				<PF:IfParameterNoEquals key="config.show.group" val="true">
					<div class="col-md-6">
						<jsp:include page="commons/includeGoodUser.jsp"></jsp:include>
					</div>
					<div class="col-md-6">
						<jsp:include page="commons/includeManyDocUser.jsp"></jsp:include>
					</div>
				</PF:IfParameterNoEquals>
			</div>
			<div class="row">
				<div class="col-md-6">
					<jsp:include page="commons/includeGoodDoc.jsp"></jsp:include>
				</div>
				<div class="col-md-6">
					<jsp:include page="commons/includeBadDoc.jsp"></jsp:include>
				</div>
			</div>
			<PF:IfParameterEquals key="config.show.question" val="true">
				<div class="row">
					<div class="col-md-6">
						<jsp:include page="commons/includeTopQuestionFinish.jsp"></jsp:include>
					</div>
					<div class="col-md-6">
						<jsp:include page="commons/includeTopQuestionIng.jsp"></jsp:include>
					</div>
				</div>
			</PF:IfParameterEquals>
			<div class="row">
				<div class="col-md-12">
					<jsp:include page="commons/includeTimeLine.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
</html>