<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- class="navbar navbar-default|navbar-inverse" -->
<style>
<!--
.imgMenuIcon {
	width: 16px;
	height: 16px;
	margin-right: 4px;
	margin-left: -2px;
	margin-top: -2px;
}
-->
</style>
<div class="navbar navbar-inverse navbar-fixed-top " role="navigation"
	style="margin: 0px;">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand"
				style="color: #ffffff; font-weight: bold; padding: 5px;"
				href="<PF:defaultIndexPage/>"> <img
				src="<PF:basePath/>actionImg/Publogo.do" height="40" alt="WCP"
				align="middle" />
			</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<PF:IfParameterEquals key="config.sso.state" val="true">
					<li><a href="<PF:ParameterValue
								key="config.sso.home.url" />"><span
							class="glyphicon glyphicon-home"></span>&nbsp;<PF:ParameterValue
								key="config.sso.title" /></a></li>
				</PF:IfParameterEquals>
				<li><a href="home/Pubindex.html"><span
						class="glyphicon glyphicon-pencil"></span>&nbsp;答题室</a></li>
				<li><a href="search/pointSearch.html"><span
						class="glyphicon glyphicon-search"></span>&nbsp;成绩查询</a></li>
			</ul>
			<jsp:include page="includeToolmenu.jsp"></jsp:include>
		</div>
	</div>
	<!-- /.navbar-collapse -->
</div>
<jsp:include page="/view/web-simple/atext/include-html.jsp"></jsp:include>
<script type="text/javascript">
	function luceneSearch(key) {
		$('#wordId').val(key);
		$('#lucenesearchFormId').submit();
	}
	function luceneSearchGo(page) {
		$('#pageNumId').val(page);
		$('#lucenesearchFormId').submit();
	}
//-->
</script>