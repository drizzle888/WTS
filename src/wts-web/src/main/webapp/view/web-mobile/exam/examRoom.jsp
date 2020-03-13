<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>移动答题室- <PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<jsp:include page="/view/web-mobile/atext/include-mobile-web.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/view/web-simple/commons/head.jsp"></jsp:include>
	<jsp:include page="/view/web-simple/commons/superContent.jsp"></jsp:include>
	<!-- /.carousel -->
	<div class="containerbox">
		<!-- 首页--分类考场 -->
		<div>
			<div class="row"
				style="background-color: #ffffff; border-bottom: 1px solid #cccccc;">
				<div class="container"
					style="padding-top: 20px; padding-bottom: 20px;">
					<div>
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
							<c:if test="${room.room.writetype=='2'}">
								<b>答题人:</b>匿名人,
										</c:if>
							<b>业务分类:</b>${room.type.name}
							<c:if
								test="${room.room.pshowtype!='1'||room.room.ssorttype!='1'||room.room.osorttype!='1'}">
								<b></b>
								<c:if test="${room.room.pshowtype=='2'}">
									<code>随机答卷</code>
								</c:if>
								<c:if test="${room.room.ssorttype=='2'}">
									<code>随机选项</code>
								</c:if>
								<c:if test="${room.room.osorttype=='2'}">
									<code>随机题序</code>
								</c:if>
							</c:if>
						</div>
					</div>
					<c:if test="${!empty room.room.roomnote }">
						<div class="ke-content ke-content-borderbox"
							style="margin-top: 10px;">${room.room.roomnote}</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="container" style="padding-top: 20px;">
			<c:forEach items="${room.papers}" var="paper">
				<c:if
					test="${paper.info.modeltype=='1'||paper.info.modeltype=='2' }">
					<%@ include
						file="/view/web-simple/exam/commons/includeRoomPaper.jsp"%>
				</c:if>
				<c:if test="${paper.info.modeltype=='3'}">
					<%@ include
						file="/view/web-simple/exam/commons/includeRoomPaper3.jsp"%>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="/view/web-simple/commons/footServer.jsp"></jsp:include>
	<jsp:include page="/view/web-simple/commons/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function() {
		$('.exam-dotime').hide();
	});
	//收藏答卷
	function bookPaper(boxid, roomid, paperid, isdo) {
		$.post("webpaper/book.do", {
			'roomid' : roomid,
			'paperid' : paperid,
			'isDo' : isdo
		}, function(flag) {
			if (flag.STATE == 0) {
				$('#' + boxid + ' #bookNumId').text(flag.num);
				if (flag.isBook) {
					$('#' + boxid + ' #book-y').show();
					$('#' + boxid + ' #book-n').hide();
					$('#' + boxid + ' #bookTitleId').text("已");
				} else {
					$('#' + boxid + ' #book-n').show();
					$('#' + boxid + ' #book-y').hide();
					$('#' + boxid + ' #bookTitleId').text("未");
				}
			} else {
				pAlert(flag.MESSAGE);
			}
		}, 'json');
	}
</script>
</html>