<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<div class="table-responsive">
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th style="width: 50%;">消息名称</th>
				<th>接收时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="node" items="${docs.resultList}">
				<tr>
					<td>${node.TITLE}</td>
					<td>${node.USERMESSAGECTIME}</td>
					<td>${node.READSTATE == "未读" ? "<span style='color: red;'>未读</span>" : "已读"}</td>
					<td><a
						href="webusermessage/showMessage.do?id=${node.ID}&num=${num}">阅读</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<c:if test="${ empty docs.resultList}">
	<div style="text-align: center; color: #999999; padding-bottom: 20px;">暂无用户消息</div>
</c:if>
<div style="text-align: center;">
	<nav aria-label="Page navigation">
		<ul class="pagination" style="margin: 0px;">
			<c:if test="${docs.currentPage>1}">
				<li><a
					href="webuser/Home.do?type=${type}&num=${docs.currentPage-1}&userid=${userid}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>
			<li><a>${docs.currentPage }</a></li>
			<c:if test="${docs.currentPage<docs.totalPage}">
				<li><a
					href="webuser/Home.do?type=${type}&num=${docs.currentPage+1}&userid=${userid}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
			<li><a
				href="javascript:readAllMsgOk('webuser/readallmsg.do?type=${type}&num=${docs.currentPage}&userid=${userid}')"><i
					class="glyphicon glyphicon-play-circle"
					style="position: relative; top: 2px;"></i>全部已读</a></li>
		</ul>
	</nav>
</div>
<script type="text/javascript">
	function readAllMsgOk(url) {
		var question = confirm("你确认要执行该操作吗？");
		if (question) {
			window.location.href = basePath + url;
		}
	}
</script>