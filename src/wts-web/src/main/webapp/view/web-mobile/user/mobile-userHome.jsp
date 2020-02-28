<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${username}-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="/view/web-mobile/atext/include-mobile-web.jsp"></jsp:include>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
<style>
<!--
.media-body .inbox {
	border: 1px dashed #999999;
	background-color: #efefef;
	padding: 4px;
	margin: 4px;
	border-radius: 6px;
}
-->
</style>
</head>
<body>
	<jsp:include page="/view/web-simple/commons/head.jsp"></jsp:include>
	<jsp:include page="/view/web-simple/commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row column_box">
				<div
					style="background-color: #ffffff; padding: 8px; margin-bottom: 20px; border: 1px solid #ddd; text-align: left;">
					<div class="media" style="overflow: hidden;">
						<div class="pull-left">
							<c:if test="${photourl!=null}">
								<img id="imgShowBoxId" src="${photourl}" alt="wcp"
									style="max-height: 64px; max-width: 64px;" class="img-rounded" />
							</c:if>
							<c:if test="${photourl==null}">
								<img id="imgShowBoxId" src="<PF:basePath/>text/img/none.png"
									alt="wcp" style="max-height: 64px; max-width: 64px;"
									class="img-rounded" />
							</c:if>
						</div>
						<div class="media-body">
							<div style="font-size: 12px; text-align: center;">
								<div class="row">
									<div class="col-xs-6">
										<div class="inbox">
											<b> 完成答题:</b>${examStat.subjectnum}
										</div>
									</div>
									<div class="col-xs-6">
										<div class="inbox">
											<b>错题数量:</b>${examStat.errorsubnum}</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-6">
										<div class="inbox">
											<b>完成考卷:</b>${examStat.papernum}</div>
									</div>
									<div class="col-xs-6">
										<div class="inbox">
											<b>完成练习:</b>${examStat.testnum}</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div>
					<ul id="myTabs" class="nav nav-tabs" role="tablist"
						style="font-size: 12px;">
						<li role="presentation" ${type=='AllSubject'?'class="active"':''}><a
							href="webuser/Home.do?type=AllSubject">答题</a></li>
						<li role="presentation"
							${type=='ErrorSubject'?'class="active"':''}><a
							href="webuser/Home.do?type=ErrorSubject">错题</a></li>
						<li role="presentation" ${type=='BookSubject'?'class="active"':''}><a
							href="webuser/Home.do?type=BookSubject">藏題</a></li>
						<li role="presentation" ${type=='AllPaper'?'class="active"':''}><a
							href="webuser/Home.do?type=AllPaper">答卷</a></li>
						<li role="presentation" ${type=='BookPaper'?'class="active"':''}><a
							href="webuser/Home.do?type=BookPaper">藏卷</a></li>
					</ul>
				</div>
				<div class="col-md-12 clear-both"
					style="font-size: 12px; border-left: 1px solid #ddd; border-right: 1px solid #ddd; background-color: #ffffff;">
					<div style="color: green; text-align: center; padding: 4px;">#请左右滑动屏幕进行查看</div>
					<c:if test="${type=='AllSubject'}">
						<div class="panel ">
							<!-- 	最近做题 -->
							<div id="userAllSubjectsId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='ErrorSubject'}">
						<div class="panel ">
							<!-- 	最近错题 -->
							<div id="userErrorSubjectsId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='BookSubject'}">
						<div class="panel ">
							<!-- 	收藏题 -->
							<div id="userBookSubjectsId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='AllPaper'}">
						<div class="panel ">
							<!-- 	答卷记录(考卷/练习卷) -->
							<div id="userAllPapersId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='BookPaper'}">
						<div class="panel ">
							<!-- 	收藏答卷 -->
							<div id="userBookPapersId">loading...</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/view/web-simple/commons/footServer.jsp"></jsp:include>
	<jsp:include page="/view/web-simple/commons/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
	function knowBoxGoPage(page) {
		window.location = basePath
				+ "webuser/Home.do?userid=${id}&query.currentPage=" + page;
	}

	$(function() {
		if (document.getElementById("userAllSubjectsId")) {
			loadAllSubjects(1);
		}
		if (document.getElementById("userErrorSubjectsId")) {
			loadErrorSubjects(1);
		}
		if (document.getElementById("userBookSubjectsId")) {
			loadBookSubjects(1);
		}
		if (document.getElementById("userAllPapersId")) {
			loadAllPapers(1);
		}
		if (document.getElementById("userBookPapersId")) {
			loadBookPapers(1);
		}
	});

	//加载答卷收藏
	function loadBookPapers(page) {
		$('#userBookPapersId').text('loading...');
		$('#userBookPapersId').load("webuser/loadOwnBookPapers.do", {
			pagenum : page
		});
	}

	//加载答卷记录
	function loadAllPapers(page) {
		$('#userAllPapersId').text('loading...');
		$('#userAllPapersId').load("webuser/loadOwnAllPapers.do", {
			pagenum : page
		});
	}

	//加载答题记录
	function loadAllSubjects(page) {
		$('#userAllSubjectsId').text('loading...');
		$('#userAllSubjectsId').load("webuser/loadOwnAllSubjects.do", {
			pagenum : page
		});
	}
	//加载错题
	function loadErrorSubjects(page) {
		$('#userErrorSubjectsId').text('loading...');
		$('#userErrorSubjectsId').load("webuser/loadOwnErrorSubjects.do", {
			pagenum : page
		});
	}
	//加载关注题
	function loadBookSubjects(page) {
		$('#userBookSubjectsId').text('loading...');
		$('#userBookSubjectsId').load("webuser/loadBookSubjects.do", {
			pagenum : page
		});
	}
	//练习题目
	function subjectsTest(ids) {
		window.location = "<PF:basePath/>websubject/PubOwnSubject.do?subjectOwnIds="
				+ ids;
	}
</script>
</html>