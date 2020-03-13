<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成绩查询-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<script src="view/web-simple/atext/script/wts-home.js"></script>
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<!-- /.carousel -->
	<div class="containerbox">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6"
				style="background-color: #ffffff; margin-top: 50px; margin-bottom: 50px;">
				<div class="row" style="margin: 20px;">
					<div class="col-md-3"></div>
					<div class="col-md-6" style="text-align: center;">
						<h3>${USEROBJ.name}-成绩查询</h3>
					</div>
					<div class="col-md-3"></div>
				</div>
				<div class="row" style="margin: 20px;">
					<div class="col-md-3"></div>
					<div class="col-md-6">
						<form action="search/pointSearch.html" method="post">
							<div class="input-group">
								<input type="text" class="form-control" id="searchWordInput"
									value="${word}" name="word" placeholder="请输入 答题室名称 / 答卷名称...">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button"
										onclick="$('#searchWordInput').val('')">
										<i class="glyphicon glyphicon-refresh "></i>
									</button>
									<button class="btn btn-default" type="submit">查询!</button>
								</span>
							</div>
						</form>
					</div>
					<div class="col-md-3"></div>
				</div>
				<div class="row">
					<div class="col-md-12 table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>答题室</th>
									<th>答卷</th>
									<th>得分</th>
									<th>答题时间</th>
									<th>状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${result.resultList }" var="node">
									<tr>
										<td>${node.ROOMNAME }</td>
										<td>${node.PAPERNAME }</td>
										<td>${node.POINT }</td>
										<td>${node.STARTTIME }</td>
										<td>${node.PSTATE }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row" style="margin: 20px;">
					<div class="col-md-12">
						<div style="text-align: center;">
							<nav aria-label="Page navigation">
								<ul class="pagination" style="margin: 0px;">
									<c:if test="${result.currentPage>1}">
										<li><a
											href="search/pointSearch.html?page=${result.currentPage-1}"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
									</c:if>
									<li><a>${result.currentPage }</a></li>
									<c:if test="${result.currentPage<result.totalPage}">
										<li><a
											href="search/pointSearch.html?page=${result.currentPage+1}"
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</c:if>
								</ul>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
</html>