<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div style="text-align: center; width: 150px;">
	<c:if test="${photourl!=null}">
		<img id="imgShowBoxId" src="${photourl}" alt="wcp"
			style="max-height: 120px; max-width: 120px;" class="img-thumbnail" />
	</c:if>
	<c:if test="${photourl==null}">
		<img id="imgShowBoxId" src="<PF:basePath/>text/img/none.png" alt="wcp"
			style="max-height: 120px; max-width: 120px;" class="img-thumbnail" />
	</c:if>
	<div
		style="background-color: #ffffff; font-size: 12px; border: dashed 1px #cccccc; padding: 6px; margin-bottom: 20px; margin-top: 10px;">
		<c:if test="${self}">
			<strong>我</strong>
		</c:if>
		<c:if test="${!self}">
			<strong>${user.name}</strong>
		</c:if>
		<c:if test="${org!=null}">
			<small>属于组织机构</small>
			<strong> ${org.name}</strong>
		</c:if>
		<c:if test="${fn:length(posts)>0}">
			<small>岗位为</small>
			<c:forEach items="${posts}" var="post">
				<strong> ${post.name}</strong>
			</c:forEach>
		</c:if>
	</div>
</div>
