<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<div class="table-responsive">
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th style="width: 50%;">答卷名称</th>
				<th>答题时间</th>
				<th>答卷类型</th>
				<th>完成情況</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result.resultList}" var="node">
				<tr>
					<td>${node.PAPERNAME}</td>
					<td><PF:FormatTime date="${node.CTIME }"
							yyyyMMddHHmmss="yyyy-MM-dd HH:mm" /></td>
					<td>${node.PAPERMODELTITLE}</td>
					<td><c:if test="${node.SCORE>=0}">${node.SCORE }分</c:if> <c:if
							test="${node.RPCENT>=0}">${node.RPCENT }%</c:if> <c:if
							test="${node.RPCENT<0&&node.SCORE<0}">${node.CARDSTATE}</c:if></td>
					<td><a href="javascript:delPaperByAll('${node.ID}',true)">刪除</a>
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
				<li><a href="javascript:loadAllPapers(${result.currentPage-1})"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>
			<li><a>${result.currentPage }</a></li>
			<c:if test="${result.currentPage<result.totalPage}">
				<li><a href="javascript:loadAllPapers(${result.currentPage+1})"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			<li><a href="javascript:delPaperByAll('${ownids}',true)"><i
					class="glyphicon glyphicon-trash"
					style="position: relative; top: 2px;"></i> 删除本页答卷 </a></li>
		</ul>
	</nav>
</div>
<script type="text/javascript">
	function delPaperByAll(id, isConfirm) {
		if (isConfirm) {
			if (!confirm("是否删除答卷记录?")) {
				return;
			}
		}
		$.post("paperuserown/del.do?ids=" + id, {}, function() {
			loadAllPapers('${result.currentPage}');
		}, 'json');
	}
</script>