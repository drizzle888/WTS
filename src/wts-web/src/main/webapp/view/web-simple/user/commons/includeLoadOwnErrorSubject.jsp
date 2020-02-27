<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<div class="table-responsive">
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th style="width: 70%;">题目</th>
				<th>答题时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result.resultList}" var="node">
				<tr>
					<td>${node.TITLE }</td>
					<td><PF:FormatTime date="${node.CTIME }"
							yyyyMMddHHmmss="yyyy-MM-dd HH:mm" /></td>
					<td><c:if
							test="${node.CARDSTATE=='6'||node.CARDSTATE=='7'||empty node.CARDSTATE}">
							<span style="color: green;">公开</span>
						</c:if> <c:if
							test="${node.CARDSTATE!='6'&&node.CARDSTATE!='7'&& not empty node.CARDSTATE}">
							<span style="color: red;" title="答卷未完成阅卷期间，禁止查看答案"> 密封</span>
						</c:if></td>
					<td>
						<!--  --> <a href="javascript:delSubjectByError('${node.ID}')">刪除</a>
						<!--  --> <c:if
							test="${node.CARDSTATE=='6'||node.CARDSTATE=='7'||empty node.CARDSTATE}">
				&nbsp;&nbsp;<a target="_blank"
								href="websubject/PubOwnSubject.do?subjectOwnIds=${node.ID}">查看</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div style="text-align: center;">
	<nav aria-label="Page navigation">
		<ul class="pagination" style="margin: 0px;">
			<c:if test="${result.currentPage>1}">
				<li><a
					href="javascript:loadErrorSubjects(${result.currentPage-1})"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>
			<li><a>${result.currentPage }</a></li>
			<c:if test="${result.currentPage<result.totalPage}">
				<li><a
					href="javascript:loadErrorSubjects(${result.currentPage+1})"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			<li><a href="javascript:subjectsTest('${ownids}')"><i
					class="glyphicon glyphicon-play-circle"
					style="position: relative; top: 2px;"></i> 练习本页 </a></li>
			<li><a href="javascript:delSubjectByError('${ownids}',true)"><i
					class="glyphicon glyphicon-trash"
					style="position: relative; top: 2px;"></i> 删除本页 </a></li>
		</ul>
	</nav>
</div>
<script type="text/javascript">
	function delSubjectByError(id, isConfirm) {
		if (isConfirm) {
			if (!confirm("是否删除本页所有错题?")) {
				return;
			}
		}
		$.post("subjectuserown/del.do?ids=" + id, {}, function() {
			loadErrorSubjects('${result.currentPage}');
		}, 'json');
	}
</script>