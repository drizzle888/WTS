<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<th style="width: 70%;">题目</th>
			<th>答题时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${result.resultList}" var="node">
			<tr>
				<td>${node.TITLE }</td>
				<td><PF:FormatTime date="${node.CTIME }"
						yyyyMMddHHmmss="yyyy-MM-dd HH:mm" /></td>
				<td><a href="javascript:delSubjectByAll('${node.ID}')">刪除</a>&nbsp;&nbsp;<a
					target="_blank"
					href="websubject/PubSubject.do?subjectids=${node.SUBJECTID}">查看</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div style="text-align: center;">
	<nav aria-label="Page navigation">
		<ul class="pagination" style="margin: 0px;">
			<c:if test="${result.currentPage>1}">
				<li><a
					href="javascript:loadAllSubjects(${result.currentPage-1})"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>
			<li><a>${result.currentPage }</a></li>
			<c:if test="${result.currentPage<result.totalPage}">
				<li><a
					href="javascript:loadAllSubjects(${result.currentPage+1})"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			<li><a href="javascript:subjectsTest('${ids}')"><i
					class="glyphicon glyphicon-play-circle"
					style="position: relative; top: 2px;"></i> 开始练习本页所有题</a></li>
		</ul>
	</nav>
</div>
<script type="text/javascript">
	function delSubjectByAll(id) {
		$.post("subjectuserown/del.do?ids=" + id, {}, function() {
			loadAllSubjects('${result.currentPage}');
		}, 'json');
	}
</script>