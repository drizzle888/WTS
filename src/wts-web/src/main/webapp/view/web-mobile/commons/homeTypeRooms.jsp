<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 首页--分类考场 -->
<style>
.wts-hometype-box {
	padding: 0px;
	background: #ffffff;
	border-bottom: 0px solid #ccc;
}

.wts-hometype-box .nav-tabs .active a {
	background-color: #f9f6f1;
	border-top: 5px solid #d13133;
}

.wts-hometype-box .nav-tabs a {
	border-top: 5px solid #ffffff;
}

.wts-hometype-box .nav-tabs .active a:HOVER {
	background-color: #f9f6f1;
	border-top: 5px solid #d13133;
}
</style>
<div>
	<div class="row">
		<div class="wts-hometype-box col-md-12" style="padding-top: 10px;">
			<ul class="wts-mobile-hometype-ul  nav nav-tabs">
				<c:forEach items="${types}" var="node">
					<c:if test="${ctype!=node.type.id}">
						<li role="presentation" targetId="${node.type.id}"><a>${node.type.name}</a></li>
					</c:if>
					<c:if test="${ctype==node.type.id}">
						<li role="presentation" targetId="${node.type.id}" class="active"><a>${node.type.name}</a></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="container">
		<ul class="wts-subjects">
			<c:forEach items="${types}" var="node">
				<c:if test="${ctype!=node.type.id}">
					<li id="${node.type.id}"><c:forEach items="${node.rooms}"
							var="room">
							<%@ include
								file="/view/web-mobile/commons/homeRoomNode.jsp"%>
						</c:forEach></li>
				</c:if>
				<c:if test="${ctype==node.type.id}">
					<li id="${node.type.id}" class="active"><c:forEach
							items="${node.rooms}" var="room">
							<%@ include
								file="/view/web-mobile/commons/homeRoomNode.jsp"%>
						</c:forEach></li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>