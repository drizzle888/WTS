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
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row column_box">
				<div class="col-md-2 hidden-xs hidden-sm clear-both"
					style="padding-top: 26px;">
					<jsp:include page="../operation/includeUserOperate.jsp"></jsp:include>
				</div>
				<div class="col-md-10 clear-both">
					<jsp:include page="../statis/commons/includeMyStatis.jsp"></jsp:include>
					<c:if test="${type=='AllSubject'}">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title"
									style="margin-left: 0; padding-left: 0px;">最近做题</h3>
							</div>
							<div class="panel-body" id="userAllSubjectsId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='ErrorSubject'}">
						<div class="panel panel-danger">
							<div class="panel-heading">
								<h3 class="panel-title"
									style="margin-left: 0; padding-left: 0px;">最近错题</h3>
							</div>
							<div class="panel-body" id="userErrorSubjectsId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='BookSubject'}">
						<div class="panel panel-success">
							<div class="panel-heading">
								<h3 class="panel-title"
									style="margin-left: 0; padding-left: 0px;">收藏题</h3>
							</div>
							<div class="panel-body" id="userBookSubjectsId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='AllPaper'}">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title"
									style="margin-left: 0; padding-left: 0px;">答卷记录(考卷/练习卷)</h3>
							</div>
							<div class="panel-body" id="userAllPapersId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='BookPaper'}">
						<div class="panel panel-success">
							<div class="panel-heading">
								<h3 class="panel-title"
									style="margin-left: 0; padding-left: 0px;">收藏答卷</h3>
							</div>
							<div class="panel-body" id="userBookPapersId">loading...</div>
						</div>
					</c:if>
					<c:if test="${type=='usermessage'}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title"
									style="margin-left: 0; padding-left: 0px;">用户消息</h3>
							</div>
							<div class="panel-body"><jsp:include
									page="commons/includeUserMessage.jsp"></jsp:include></div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
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