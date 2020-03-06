<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 判卷时通过点击正确答案弹出 题解析 -->
<c:if test="${!empty material}">
	<!-- 如果有引用材料的话 -->
	<div style="margin-left: 26px; margin-right: 26px;"
		class="ke-content ke-content-borderbox">
		<div style="text-align: center; font-size: 18px; font-weight: 700;">
			<code>引用材料:</code>${material.title}</div>
		<div>${material.text}</div>
	</div>
</c:if>
<div id="subject_view_load_id"
	style="margin-left: 26px; margin-right: 26px;">
	<div style="text-align: center; color: #999;">load...</div>
</div>
<c:if test="${!empty analysis}">
	<!-- 如果有解析的話 -->
	<c:forEach items="${analysis}" var="node">
		<div style="margin-left: 26px; margin-right: 26px;"
			class="ke-content ke-content-borderbox">
			<div style="text-align: left; font-size: 18px; font-weight: 700;">
				<code>解析:${node.pcontent}</code>
			</div>
			<div>${node.text}</div>
		</div>
	</c:forEach>
</c:if>
<script type="text/javascript">
	$(function() {
		$('#subject_view_load_id').load('subject/view.do', {
			versionid : '${subjectu.version.id}'
		}, function() {
			$('#subject_view_load_id .subjectOrder').remove();
		});
	});
</script>