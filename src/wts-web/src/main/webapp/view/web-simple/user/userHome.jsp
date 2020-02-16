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
				<div class="col-md-2 hidden-xs hidden-sm clear-both">
					<jsp:include page="../statis/commons/includeMyStatis.jsp"></jsp:include>
					<jsp:include page="../operation/includeUserOperate.jsp"></jsp:include>
				</div>
				<div class="col-md-10 clear-both">
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
				+ "webuser/PubHome.do?userid=${id}&query.currentPage=" + page;
	}

	$(function() {
		loadAllSubjects(1);
		loadErrorSubjects(1);
		loadBookSubjects(1);
	});

	function loadAllSubjects(page) {
		$('#userAllSubjectsId').text('loading...');
		$('#userAllSubjectsId').load("webuser/loadOwnAllSubjects.do", {
			pagenum : page
		});
	}

	function loadErrorSubjects(page) {
		$('#userErrorSubjectsId').text('loading...');
		$('#userErrorSubjectsId').load("webuser/loadOwnErrorSubjects.do", {
			pagenum : page
		});
	}

	function loadBookSubjects(page) {
		$('#userBookSubjectsId').text('loading...');
		$('#userBookSubjectsId').load("webuser/loadBookSubjects.do", {
			pagenum : page
		});
	}

	function subjectsTest(ids) {
		window.location = "<PF:basePath/>websubject/PubSubject.do?subjectids="
				+ ids;
	}
</script>
</html>