<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>阅卷- <PF:ParameterValue key="config.sys.title" /></title>
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
			<div class="row"
				style="background-color: #ffffff; border-bottom: 1px solid #cccccc;">
				<div class="container">
					<div style="padding: 20px;">
						<div class="media" style="overflow: hidden;">
							<div class="pull-left">
								<img alt="答题室" style="width: 64px; height: 64px;"
									src="text/img/exam.png">
							</div>
							<div class="media-body">
								<div style="margin-left: 20px; margin-top: 10px;"
									class="pull-left">
									<div class="side_unit_info"
										style="font-size: 16px; font-weight: 700; margin-bottom: 8px;">
										${room.room.name}</div>
									<div class="side_unit_info">
										<c:if test="${room.room.timetype=='1'}">
											<b>答题日期:</b>永久有效,
										</c:if>
										<c:if test="${room.room.timetype=='2'}">
											<b>答题日期:</b>${room.room.starttime}&nbsp;至&nbsp;${room.room.endtime},
										</c:if>
										<b>答题时长:</b>${room.room.timelen}分,
										<c:if test="${room.room.writetype=='0'}">
											<b>答题人:</b>任何人,
										</c:if>
										<c:if test="${room.room.writetype=='1'}">
											<b>答题人:</b>指定人,
										</c:if>
										<b>业务分类:</b>${room.type.name}
									</div>
								</div>
							</div>
						</div>
						<c:if test="${!empty room.room.roomnote }">
							<div class="ke-content ke-content-borderbox">
								${room.room.roomnote}</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="container" style="padding-top: 20px;">
			<c:if test="${room.room.pshowtype=='2'}">
				<!-- 如果是抽卷答题室则允许统一查看房间全部用户答卷 -->
				<div class="col-md-12"
					style="padding-left: 8px; padding-right: 8px;">
					<div class="panel panel-default">
						<div class="panel-body" style="text-align: center; padding: 40px;">
							<a href="adjudge/roomUser.do?roomId=${room.room.id}"
								type="button" class="btn btn-default">~~~~~~全部人员阅卷(阅<span
								class="wts-red"><b>${allPaperNum.adjudgeUserNum}</b></span>人/答<span
								class="wts-red"><b>${allPaperNum.currentUserNum}</b></span>人<c:if
									test="${allPaperNum.allUserNum>0}">/共<span
										class="wts-red"><b>${allPaperNum.allUserNum}</b></span>人</c:if>)~~~~~~
							</a>
						</div>
					</div>
				</div>
			</c:if>
			<c:forEach items="${room.papers}" var="paper">
				<c:if test="${room.room.pshowtype!='3'}">
					<%@ include file="commons/includeInnerPaper.jsp"%></c:if>
				<c:if test="${room.room.pshowtype=='3'}">
					<%@ include file="commons/includeInnerPaper3.jsp"%></c:if>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
	function exportWordPaper(papaerid) {
		swal(
				{
					title : "答卷导出后需人工检查",
					text : "即将导出答卷为word文档，请注意答卷中部分内容格式有可能无法正确展示，使用前必须对答卷进行检查并手动调整，是否继续导出？",
					icon : "warning",
					buttons : true,
					dangerMode : true,
				}).then(
				function(willDelete) {
					if (willDelete) {
						window.location.href = basePath
								+ "paperExport/exportWord.do?paperid="
								+ papaerid + "&roomid=${room.room.id}";
						pAlert("答卷生成中，等待浏览器下载... (完成后请手动关闭此对话框)", 10000);
					} else {
						//取消
					}
				});
	}
</script>
</html>