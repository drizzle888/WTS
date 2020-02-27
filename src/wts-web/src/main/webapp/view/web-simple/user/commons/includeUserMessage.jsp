<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<div class="list-group">
	<c:forEach var="node" items="${docs.resultList}">
		<a href="webusermessage/showMessage.do?id=${node.ID}&num=${num}"
			class="list-group-item">
			<div class="row">
				<div class="col-md-7">
					<h5 class="list-group-item-heading">${node.TITLE}&nbsp;${node.READSTATE == "未读" ? "<span style='color: red;'>(未读)</span>" : "<!--node.READSTATE}-->"}</h5>
				</div>
				<div class="col-md-5">
					<p class="list-group-item-text" style="color: #8c92a4;">
						${node.USERMESSAGECTIME}</p>
				</div>
			</div>
		</a>
	</c:forEach>
</div>

<div class="row">
	<div class="col-xs-12">
		<c:if test="${fun:length(docs.resultList) > 0}">
			<ul class="pagination pagination-sm" style="float: left;">
				<c:forEach var="page" begin="${docs.startPage}"
					end="${docs.endPage}" step="1">
					<c:if test="${page==docs.currentPage}">
						<li class="active"><a>${page} </a></li>
					</c:if>
					<c:if test="${page!=docs.currentPage}">
						<li><a
							href="webuser/Home.do?type=${type}&num=${page}&userid=${userid}">${page}</a></li>
					</c:if>
				</c:forEach>
			</ul>
		</c:if>
		<ul class="wcp-pagination-buttons" style="float: right;">
			<li class="active"><a
				href="javascript:readAllMsgOk('webuser/readallmsg.do?type=${type}&num=${docs.currentPage}&userid=${userid}')"
				type="button" class="btn btn-info btn-sm">全部已读</a></li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	function readAllMsgOk(url) {
		var question = confirm("你确认要执行该操作吗？");
		if (question) {
			window.location.href = basePath + url;
		}
	}
</script>