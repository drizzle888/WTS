<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>阅卷-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<link href="view/web-simple/adjudge/text/adjudgeCard.css"
	rel="stylesheet" />
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<!-- /.carousel -->
	<div class="containerbox">
		<div
			style="background-color: #ffffff; border-bottom: 1px solid #cccccc;">
			<div class="container" style="padding: 4px;">
				<div class="col-md-9" style="padding: 4px;">
					<div style="text-align: center;">
						<!-- 卷头部 -->
						<h1>${paper.info.name}</h1>
						<span style="font-weight: 700;">共${paper.rootChapterNum}道大题，${paper.subjectNum}道小题，满分${paper.allPoint}分，
							答题时间<span class="wts-red">${room.timelen}分钟</span>,答题人:<span
							class="wts-red">${user.name}</span>
						</span>
						<c:if test="${!empty paper.info.papernote}">
							<div class="ke-content ke-content-borderbox">${paper.info.papernote}</div>
						</c:if>
						<input type="hidden" id="roomId-Input" value="${room.id}">
						<input type="hidden" id="paperId-Input" value="${paper.info.id}">
						<input type="hidden" id="cardId-Input" value="${card.id}">
					</div>
					<div class="wts-paper-forms">
						<c:forEach items="${paper.chapters}" var="chapter1">
							<c:if test="${chapter1.vasNum>0}">
								<div class="chapterBox">
									<!-- 一级章節头部 -->
									<h2 id='${chapter1.chapter.id}-NAVI'>${chapter1.chapter.name}<span
											class="chapter-info">共${chapter1.subjectNum}道小题/完成${chapter1.vasNum}道题，共${chapter1.allpoint}分</span>
									</h2>
									<%@ include file="commons/IncludeLeve1Subjects.jsp"%>
									<c:forEach items="${chapter1.chapters}" var="chapter2">
										<c:if test="${chapter2.vasNum>0}">
											<div>
												<!-- 二级章節头部 -->
												<h3 id='${chapter2.chapter.id}-NAVI'>${chapter2.chapter.name}<span
														class="chapter-info">共${chapter2.subjectNum}道小题，${chapter2.allpoint}分</span>
												</h3>
											</div>
											<%@ include file="commons/IncludeLeve2Subjects.jsp"%>
											<c:forEach items="${chapter2.chapters}" var="chapter3">
												<c:if test="${chapter3.vasNum>0}">
													<div>
														<!-- 二级章節头部 -->
														<h4 id='${chapter3.chapter.id}-NAVI'>${chapter3.chapter.name}<span
																class="chapter-info">共${chapter3.subjectNum}道小题，${chapter3.allpoint}分</span>
														</h4>
													</div>
													<%@ include file="commons/IncludeLeve3Subjects.jsp"%>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
				<div class="col-md-3">
					<jsp:include page="commons/IncludeSideCard.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="commons/includeSubjectInfoWin.jsp"></jsp:include>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
	<script src="view/web-simple/adjudge/text/adjudgeCard.js"></script>
</body>
</html>