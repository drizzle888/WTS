<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><c:if test="${not empty test.TITLE}">${test.TITLE}-</c:if>练习题<PF:ParameterValue
		key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<link href="view/web-simple/subject/text/random.css" rel="stylesheet">
<script charset="utf-8"
	src="<PF:basePath/>text/lib/alert/sweetalert.min.js"></script>
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
							<c:if test="${test.ALLNUM>1}">
								<!-- 多条练习 -->
								<div class="pull-left">
									<img alt="随机出题" style="width: 64px; height: 64px;"
										src="text/img/random.png">
								</div>
								<div class="media-body">
									<div style="margin-left: 20px; margin-top: 10px;"
										class="pull-left">
										<div class="side_unit_info"
											style="font-size: 18px; font-weight: 700; margin-bottom: 8px; line-height: 1.2em;">
											当前第&nbsp;${index>test.ALLNUM?test.ALLNUM:index}&nbsp;题/共&nbsp;${test.ALLNUM}&nbsp;题&nbsp;&nbsp;
											<span style="color: green;">正确:${test.YESNUM}</span>
											&nbsp;/&nbsp;<span style="color: red;">错误:${test.NONUM}</span>&nbsp;/&nbsp;正确率:${test.YESPEN }%
										</div>
										<div class="side_unit_info">
											<c:if test="${not empty test.TITLE}">
											《<b>${test.TITLE}</b>》&nbsp;</c:if>
											<code>${subjectu.tipType.title}</code>
											/
											<code>${subjectu.subjectType.name}</code>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${test.ALLNUM==1}">
								<!-- 单条展示 -->
								<div class="pull-left">
									<img alt="题展示" style="width: 64px; height: 64px;"
										src="text/img/subject.png">
								</div>
								<div class="media-body">
									<div style="margin-left: 20px; margin-top: 10px;"
										class="pull-left">
										<div class="side_unit_info"
											style="font-size: 18px; margin-bottom: 8px; line-height: 1.2em;">
											题类型:&nbsp;<b style="color: black;">${subjectu.tipType.title}</b>&nbsp;,&nbsp;题库分类:<b
												style="color: black;">&nbsp;${subjectu.subjectType.name}&nbsp;</b>
										</div>
										<div class="side_unit_info">
											<c:if test="${not empty test.TITLE}">
											《<b>${test.TITLE}</b>》&nbsp;</c:if>
											<code>
												发布时间:
												<PF:FormatTime date="${subjectu.version.ctime}"
													yyyyMMddHHmmss="yyyy-MM-dd HH:mm" />
											</code>
											&nbsp;/&nbsp;
											<code>作者:${subjectu.version.cusername}</code>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="min-height: 300px;">
				<div class="container wts-paper-forms">
					<c:if test="${!empty test.MATERIAL }">
						<div class="sub-material">
							<div class="innerTitle">
								<img alt="" src="text/img/analysis.png">
								材料:${test.MATERIAL.title}
							</div>
							<div class="ke-content innerbox">${test.MATERIAL.text}</div>
						</div>
					</c:if>
					<!-- 题目展示区 -->
					<div id="${subjectu.version.id}-NAVI">
						<c:if test="${index<=test.ALLNUM}">
							<!-- 一级章节下的题目 1.填空，2.单选，3.多选，4判断，5问答-->
							<c:if test="${subjectu.version.tiptype=='1'}">
								<%@ include
									file="/view/exam/SubjectViews/vacancy/VacancyView.jsp"%>
							</c:if>
							<c:if test="${subjectu.version.tiptype=='2'}">
								<%@ include file="/view/exam/SubjectViews/select/SelectView.jsp"%>
							</c:if>
							<c:if test="${subjectu.version.tiptype=='3'}">
								<%@ include
									file="/view/exam/SubjectViews/checkbox/CheckboxView.jsp"%>
							</c:if>
							<c:if test="${subjectu.version.tiptype=='4'}">
								<%@ include file="/view/exam/SubjectViews/judge/JudgeView.jsp"%>
							</c:if>
							<c:if test="${subjectu.version.tiptype=='5'}">
								<%@ include
									file="/view/exam/SubjectViews/interlocution/InterlocutionView.jsp"%>
							</c:if>
							<c:if test="${subjectu.version.tiptype=='6'}">
								<%@ include file="/view/exam/SubjectViews/fileup/FileupView.jsp"%>
							</c:if>
						</c:if>
						<c:if test="${index>test.ALLNUM}">
							<div
								style="margin-top: 50px; padding: 20px; background-color: #ffffff; border: 1px dashed #ccc;">
								<h1 style="text-align: center;">
									<img alt="" src="text/img/complete.png"
										style="width: 2em; height: 2em; position: relative; top: -5px;">
									已经完成全部&nbsp;${test.ALLNUM}&nbsp;题&nbsp;&nbsp; <span
										style="color: green;">正确:${test.YESNUM }</span> &nbsp;/&nbsp;<span
										style="color: red;">错误:${test.NONUM }</span>&nbsp;/&nbsp;正确率:${test.YESPEN }%
								</h1>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="row"
				style="background-color: #ffffff; border-top: 1px solid #cccccc;">
				<div class="container">
					<div style="padding: 20px; text-align: center;">
						<c:if test="${index<=test.ALLNUM}">
							<div class="btn-group btn-group-sm" role="group" aria-label="..."
								style="margin-top: 4px;">
								<c:if test="${USEROBJ!=null }">
									<button type="button" class="btn btn-default"
										onclick="doBook(true)">
										<i id="book-y" class="glyphicon glyphicon-star"></i> <i
											id="book-n" class="glyphicon glyphicon-star-empty"></i>&nbsp;<span
											id="bookTitleId"></span>收藏(<span id="bookNumId">0</span>)
									</button>
									<button type="button" class="btn btn-default"
										onclick="doPraise()">
										<i class="glyphicon glyphicon-thumbs-up"></i>&nbsp;赞(<span
											id="praiseNumId">${subjectu.subject.praisenum}</span>)
									</button>
									<a type="button" class="btn btn-default"
										href="javascript:openCommentWin()"> <i
										style="color: #000000;" class="glyphicon glyphicon-comment"></i>&nbsp;评论(<span
										id="commentNumId">${subjectu.subject.commentnum}</span>)
									</a>
									<script type="text/javascript">
										$(function() {
											// 加载收藏信息
											doBook(false);
										});
									</script>
								</c:if>
								<button type="button" class="btn btn-default"
									onclick="openAnalysesWin()">
									<i class="glyphicon glyphicon-book"></i>&nbsp;查看解析
								</button>
							</div>
						</c:if>
						<div class="btn-group btn-group-sm" role="group" aria-label="..."
							style="margin-top: 4px;">
							<c:if test="${index>1 }">
								<a class="btn btn-default"
									href="websubject/PubRandomSubject.do?index=${index-1}&testid=${testid}">
									<i class="glyphicon glyphicon-arrow-left"></i>&nbsp; 上一题
								</a>
							</c:if>
							<c:if test="${index>test.ALLNUM}">
								<a class="btn btn-info"
									href="websubject/PubRandomSubject.do?testid=${test.TESTID}">
									<i style="color: #ffffff;" class="glyphicon glyphicon-refresh"></i>&nbsp;
									重做全部題目
								</a>
							</c:if>
							<c:if test="${index<=test.ALLNUM}">
								<button onclick="submitSubjectVar()" type="button"
									class="btn btn-success">
									<i class="glyphicon glyphicon-ok-sign"></i>&nbsp;提交答案&nbsp;
									<c:if test="${test.ALLNUM>1}">/&nbsp;下一题</c:if>
								</button>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 开始答题-->
	<div class="modal fade" id="submitVar-win" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">答案</h4>
				</div>
				<div id="result-y" class="modal-body" style="display: none;">
					<div class="doc_node_title_box"
						style="font-size: 16px; text-align: center;">
						<img alt="是否正確" style="width: 64px; height: 64px;"
							src="text/img/result-yeas.png">
					</div>
					<div class="doc_node_title_box"
						style="font-size: 16px; text-align: center;">正确</div>
				</div>
				<div id="result-c" class="modal-body" style="display: none;">
					<div class="doc_node_title_box"
						style="font-size: 16px; text-align: center;">
						<img alt="是否正確" style="width: 64px; height: 64px;"
							src="text/img/result-half.png">
					</div>
					<div class="doc_node_title_box"
						style="font-size: 16px; text-align: center;">
						得分:<span id="point-Span"></span>%
					</div>
				</div>
				<div id="result-n" class="modal-body" style="display: none;">
					<div class="doc_node_title_box"
						style="font-size: 16px; text-align: center;">
						<img alt="是否正確" style="width: 64px; height: 64px;"
							src="text/img/result-no.png">
					</div>
					<div class="doc_node_title_box"
						style="font-size: 16px; text-align: center;">错误</div>
				</div>
				<div id="rightBoxId" class="modal-body" style="display: none;">
					<div class="innerTitle">
						<img alt="" src="text/img/result-yeas.png"> 正确答案
					</div>
					<div class="ke-content innerbox"></div>
				</div>
				<div id="analysisBoxId" class="modal-body" style="display: none;">
					<div class="innerTitle">
						<img alt="" src="text/img/analysis.png"> 解析
					</div>
					<div class="ke-content innerbox"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="winShowAna" style="display: none;" type="button"
						onclick="loadAnalysesWin()" class="btn btn-default">
						<i class="glyphicon glyphicon-book"></i>&nbsp;查看解析
					</button>
					<c:if test="${test.ALLNUM>1}">
						<a id="winNext" style="display: none;"
							href="websubject/PubRandomSubject.do?index=${index+1}&testid=${test.TESTID}"
							class="btn btn-primary">下一题 &nbsp;<i style="color: #ffffff;"
							class="glyphicon glyphicon-arrow-right"></i></a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<!-- 题评论-->
	<div class="modal fade" id="submitComments-win" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<span class="glyphicon glyphicon-star column_title">&nbsp;最新评论
						</span>
					</h4>
				</div>
				<div id="analysisBoxId" class="modal-body"
					style="padding: 0px; padding-bottom: 50px;">
					<div id="commentShowBoxId"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
<script src="view/web-simple/subject/text/random.js"></script>
<script type="text/javascript">
	var testid = "${test.TESTID}";
	var versionId = "${subjectu.version.id}";
	var subjectId = "${subjectu.subject.id}";
</script>
</html>