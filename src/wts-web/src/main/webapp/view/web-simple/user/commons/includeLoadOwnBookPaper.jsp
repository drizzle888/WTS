<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<div class="table-responsive">
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th style="width: 40%;">答卷名称</th>
				<th style="width: 20%;">答題室</th>
				<th>收藏时间</th>
				<th>答卷类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result.resultList}" var="node">
				<tr>
					<td>${node.PAPERNAME}</td>
					<td>${node.ROOMNAME}</td>
					<td><PF:FormatTime date="${node.CTIME }"
							yyyyMMddHHmmss="yyyy-MM-dd HH:mm" /></td>
					<td>${node.PAPERMODELTITLE}</td>
					<td><a href="javascript:delPaperByAll('${node.ID}',true)">刪除</a>
						<a href="exam/roompage.do?roomid=${node.ROOMID}">进入答题室</a></td>
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
					style="position: relative; top: 2px;"></i> 取消本页关注 </a></li>
		</ul>
	</nav>
</div>
<script type="text/javascript">
	function delPaperByAll(id, isConfirm) {
		if (isConfirm) {
			if (!confirm("是否取消答卷收藏?")) {
				return;
			}
		}
		$.post("paperuserown/del.do?ids=" + id, {}, function() {
			loadBookPapers('${result.currentPage}');
		}, 'json');
	}
</script>