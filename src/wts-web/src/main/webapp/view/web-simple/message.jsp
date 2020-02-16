<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统消息-<PF:ParameterValue key="config.sys.title" /></title>
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
	<jsp:include page="commons/head.jsp"></jsp:include><div
		class="super_content">
		<br />
	</div>
	<div class="containerbox" style="background-color: #fff;">
		<div class="container ">
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default userbox"
						style="margin: auto; width: 300px; margin-top: 100px; margin-bottom: 30px; background-color: #fcfcfc;">
						<div class="panel-body" style="padding: 0px;overflow: hidden;">
							<div class="text-center">
								<img src="<PF:basePath/>actionImg/PubHomelogo.do" alt="wcp"
									style="margin: 20px; max-width: 128px;" />
							</div>
							<table class="table table-striped">
								<!--参数说明：LINKS= Map<title, url> -->
								<c:forEach items="${LINKS}" var="entry">
									<tr>
										<td class="active" colspan="2" style="text-align: center;">
											<c:if test="${fn:indexOf(entry.value,'javascript:')>=0}">
												<a class="btn btn-link" href="${entry.value}"> 
												 	<DOC:Describe maxnum="20" text="${entry.key}"></DOC:Describe>
												</a>
											</c:if>
											<c:if test="${fn:indexOf(entry.value,'javascript:')<0}">
												<a class="btn btn-link" href="<PF:basePath/>${entry.value}"> 
												 	<DOC:Describe maxnum="20" text="${entry.key}"></DOC:Describe>
												</a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</table>
							<table class="table">
								<tr class="success">
									<td class="active" colspan="2"
										style="text-align: center; color: #666;word-break:break-all;">${MESSAGE}</td>
								</tr>
								<!--参数说明： TYPE=FarmDoctype  -->
								<c:if test="${TYPE!=null }">
									<tr>
										<td class="active">所属分类：</td>
										<td><a href="webtype/view${TYPE.id}/Pub1.html">${TYPE.name}</a></td>
									</tr>
								</c:if>
								<!--参数说明： GROUP=FarmDocgroup  -->
								<c:if test="${GROUP!=null }">
									<tr>
										<td class="active">所属小组：</td>
										<td><a href="webgroup/join.do?groupId=${GROUP.id}">${GROUP.groupname}</a></td>
									</tr>
								</c:if>
								<tr>
									<td colspan="2" style="text-align: center;">
										<!-- userid --> <a type="button" href="<PF:basePath/>"
										class="btn btn-primary btn-xs">系统首页</a> <c:if
											test="${USEROBJ!=null}">&nbsp;&nbsp;
									<a type="button" href="<PF:basePath/>webuser/PubHome.do"
												class="btn btn-default btn-xs">我的信息</a>
										</c:if>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
<!--
${script}
//-->
</script>
	<jsp:include page="commons/footServer.jsp"></jsp:include>
	<jsp:include page="commons/foot.jsp"></jsp:include>
</body>
</html>
