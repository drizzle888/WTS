<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:forEach items="${chapter3.subjects}" var="subjectu"
	varStatus="status">
	<div id="${subjectu.version.id}-NAVI">
		<!-- 一级章节下的题目 1.填空，2.单选，3.多选，4判断，5问答-->
		<c:if test="${subjectu.version.tiptype=='1'}">
			<%@ include file="/view/exam/SubjectViews/vacancy/VacancyView.jsp"%>
		</c:if>
		<c:if test="${subjectu.version.tiptype=='2'}">
			<%@ include file="/view/exam/SubjectViews/select/SelectView.jsp"%>
		</c:if>
		<c:if test="${subjectu.version.tiptype=='3'}">
			<%@ include file="/view/exam/SubjectViews/checkbox/CheckboxView.jsp"%>
		</c:if>
		<c:if test="${subjectu.version.tiptype=='4'}">
			<%@ include file="/view/exam/SubjectViews/judge/JudgeView.jsp"%>
		</c:if>
		<c:if test="${subjectu.version.tiptype=='5'}">
			<%@ include
				file="/view/exam/SubjectViews/interlocution/InterlocutionView.jsp"%>
		</c:if>
		<c:if test="${subjectu.version.tiptype=='6'}">
			<%@ include
				file="/view/exam/SubjectViews/fileup/FileupView.jsp"%>
		</c:if>
	</div>
</c:forEach>