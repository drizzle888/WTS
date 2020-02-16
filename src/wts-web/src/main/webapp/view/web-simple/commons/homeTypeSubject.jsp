<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 首页--分类考场 -->
<div>
	<div class="row">
		<div class="wts-hometype-box col-md-12">
			<ul class="wts-hometype-ul">
				<c:forEach items="${types}" var="node">
					<c:if test="${ctype!=node.type.id}">
						<li targetId="${node.type.id}">${node.type.name}</li>
					</c:if>
					<c:if test="${ctype==node.type.id}">
						<li targetId="${node.type.id}" class="wts-hometype-active">${node.type.name}</li>
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
							<%@ include file="homeTypeSubjectNode.jsp"%>
						</c:forEach></li>
				</c:if>
				<c:if test="${ctype==node.type.id}">
					<li id="${node.type.id}" class="active"><c:forEach
							items="${node.rooms}" var="room">
							<%@ include file="homeTypeSubjectNode.jsp"%>
						</c:forEach></li>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>