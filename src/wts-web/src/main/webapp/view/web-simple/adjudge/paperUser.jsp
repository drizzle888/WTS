<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>阅卷-答题人 <PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<!-- /.carousel -->
	<div class="containerbox">
		<!-- 首页--分类考场 -->
		<div>
			<div
				style="background-color: #ffffff; border-bottom: 1px solid #cccccc;">
				<div class="container">
					<div style="padding: 20px;">
						<div class="row">
							<div class="col-sm-6">
								<div class="media" style="overflow: hidden;">
									<div class="pull-left hidden-xs">
										<img alt="答题室" style="width: 64px; height: 64px;"
											src="text/img/paper.png">
									</div>
									<div class="media-body">
										<div style="margin-top: 10px;" class="pull-left">
											<div class="side_unit_info"
												style="font-size: 16px; font-weight: 700; margin-bottom: 8px;">
												${paper.name}</div>
											<div class="side_unit_info">
												<c:if test="${room.timetype=='1'}">
													<b>答题日期:</b>永久有效,
										</c:if>
												<c:if test="${room.timetype=='2'}">
													<b>答题日期:</b>${room.starttime}&nbsp;至&nbsp;${room.endtime},
										</c:if>
												<b>答题时长:</b>${room.timelen}分,
												<c:if test="${room.writetype=='0'}">
													<b>答题人:</b>任何人
										</c:if>
												<c:if test="${room.writetype=='1'}">
													<b>答题人:</b>指定人
										</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="pull-right">
									<a href="adjudge/roompage.do?roomid=${room.id}"
										data-toggle="modal" type="button" class="btn btn-primary">返回答题室
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container" style="padding-top: 20px;">
			<div>
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" ${state=='all'?'class="active"':'' }><a
						href="adjudge/paperUser.do?paperid=${paper.id}&roomId=${room.id}">全部</a></li>
					<li role="presentation" ${state=='12345'?'class="active"':'' }><a
						href="adjudge/paperUser.do?paperid=${paper.id}&roomId=${room.id}&state=12345">待阅卷</a></li>
					<li role="presentation" ${state=='67'?'class="active"':'' }><a
						href="adjudge/paperUser.do?paperid=${paper.id}&roomId=${room.id}&state=67">已发布</a></li>
				</ul>
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-bordered"
					style="width: 100%; background-color: #ffffff;">
					<!-- 用户名称，得分，阅卷人，判卷时间，答题开始时间，答题交卷时间，状态 -->
					<tr>
						<th>用户名称</th>
						<th>答题时间</th>
						<th>交卷时间</th>
						<th>完成率</th>
						<th>得分</th>
						<th>阅卷人</th>
						<th>阅卷时间</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${result.resultList}" var="node">
						<tr>
							<td>${node.NAME}</td>
							<td>${node.STARTTIME}</td>
							<td>${node.ENDTIME}</td>
							<td><div class="progress" style="margin-bottom: 0px;">
									<div class="progress-bar" role="progressbar"
										aria-valuenow="${node.COMPLETENUM}" aria-valuemin="0"
										aria-valuemax="${node.ALLNUM}"
										style="width: ${node.COMPLETEPERCENT}%;">
										${node.COMPLETENUM}/${node.ALLNUM}</div>
								</div></td>
							<td>${node.POINT}</td>
							<td>${node.ADJUDGEUSERNAME}</td>
							<td>${node.ADJUDGETIME}</td>
							<td>
								<!--  --> <c:if test="${node.PSTATE=='7'}">
									<span style="font-size: 12px;" class="label label-primary">
										<c:if test="${node.PSTATETITLE==null}">未答题</c:if>
										${node.PSTATETITLE}
									</span>
								</c:if> <!--  --> <c:if test="${node.PSTATE!='7'}">
									<span style="font-size: 12px;" class="label label-default">
										<c:if test="${node.PSTATETITLE==null}">未答题</c:if>
										${node.PSTATETITLE}
									</span>
								</c:if> <!--  -->
							</td>
							<td style="text-align: center;">
								<!-- 1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩 --> 
								<c:if test="${node.PSTATE=='2'||node.PSTATE=='4'}">
									<a class="btn btn-default btn-xs" id="autoPointRunId"
										href="javascript:loadRemoteFunctionAndReload('adjudge/autoCount.do?cardid=${node.CARDID}','autoPointRunId')">自动计算</a>
								</c:if> 
								<!---->
								<c:if test="${node.PSTATE=='1'||node.PSTATE=='3'}">
									<a class="btn btn-default btn-xs" id="recoveryRunId"
										href="javascript:loadRemoteFunctionAndReload('adjudge/recovery.do?cardid=${node.CARDID}','recoveryRunId','确定收卷?')">强制收卷</a>
								</c:if> 
								<!---->
								<c:if test="${node.PSTATE=='6'}">
									<a class="btn btn-danger btn-xs" id="autoPointRunId"
										href="javascript:confirmRemoteFunction('adjudge/publicPoint.do?cardId=${node.CARDID}','得分发布后将无法变更，是否发布该得分?')">发布成绩</a>
								</c:if> 
								<!---->
								<c:if test="${node.PSTATE=='5'||node.PSTATE=='6'||node.PSTATE=='7'}">
									<a class="btn btn-success btn-xs"
										href="adjudge/adjudgePage.do?cardId=${node.CARDID}">人工阅卷</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="alert alert-warning">
				<p class="bg-warning">1.如果用戶完成的題目中只有客观题，系统自动阅卷后会将答题卡状态切换为阅卷完成的状态
				</p>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
</html>