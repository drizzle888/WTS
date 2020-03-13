<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@ taglib
	uri="/view/conf/tip.tld" prefix="TIP"%>
<link rel="stylesheet" type="text/css"
	href="<PF:basePath/>view/exam/subject/subject.css">
<style>
<!--
.has-feedback .form-control {
	padding-right: 8px;
}
-->
</style>
<!--考卷表单-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'" style="padding: 20px;">
		<div style="text-align: center;">
			<!-- 卷头部 -->
			<h1>${paper.info.name}</h1>
			<span style="font-weight: 700;">共${paper.rootChapterNum}道大题，${paper.subjectNum}道小题，满分${paper.allPoint}分，建议答题时间${paper.info.advicetime}分钟</span>
			<c:if test="${!empty paper.info.papernote}">
				<div class="ke-content ke-content-borderbox">${paper.info.papernote}</div>
			</c:if>
		</div>
		<c:forEach items="${paper.chapters}" var="chapter1">
			<div class="chapterBox">
				<!-- 一级章節头部 -->
				<h2>${chapter1.chapter.name}<span class="chapter-info">共${chapter1.subjectNum}道小题，${chapter1.allpoint}分</span>
				</h2>
				<c:if test="${not empty chapter1.chapter.textnote}">
					<div class="chapter-note ke-content ke-content-borderbox">${chapter1.chapter.textnote}</div>
				</c:if>
				<c:if test="${not empty chapter1.materials}">
					<c:forEach items="${chapter1.materials}" var="material">
						<div class="chapter-material">
							<div class="chapter-material-title">${material.title}</div>
							<div class="ke-content ke-content-borderbox">
								<TIP:InitHtmlContentTag html="${material.text}"></TIP:InitHtmlContentTag>
							</div>
						</div>
					</c:forEach>
				</c:if>
				<%@ include file="comment/IncludeLeve1Subjects.jsp"%>
				<c:forEach items="${chapter1.chapters}" var="chapter2">
					<div>
						<!-- 二级章節头部 -->
						<h3>${chapter2.chapter.name}<span class="chapter-info">共${chapter2.subjectNum}道小题，${chapter2.allpoint}分</span>
						</h3>
					</div>
					<c:if test="${not empty chapter2.chapter.textnote}">
						<div class="chapter-note ke-content ke-content-borderbox">${chapter2.chapter.textnote}</div>
					</c:if>
					<c:if test="${not empty chapter2.materials}">
						<c:forEach items="${chapter2.materials}" var="material">
							<div class="chapter-material">
								<div class="chapter-material-title">${material.title}</div>
								<div class="ke-content ke-content-borderbox">
									<TIP:InitHtmlContentTag html="${material.text}"></TIP:InitHtmlContentTag>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<%@ include file="comment/IncludeLeve2Subjects.jsp"%>
					<c:forEach items="${chapter2.chapters}" var="chapter3">
						<div>
							<!-- 三级章節头部 -->
							<h4>${chapter3.chapter.name}<span class="chapter-info">共${chapter3.subjectNum}道小题，${chapter3.allpoint}分</span>
							</h4>
						</div>
						<c:if test="${not empty chapter3.chapter.textnote}">
							<div class="chapter-note ke-content ke-content-borderbox">${chapter3.chapter.textnote}</div>
						</c:if>
						<c:if test="${not empty chapter3.materials}">
							<c:forEach items="${chapter3.materials}" var="material">
								<div class="chapter-material">
									<div class="chapter-material-title">${material.title}</div>
									<div class="ke-content ke-content-borderbox">
										<TIP:InitHtmlContentTag html="${material.text}"></TIP:InitHtmlContentTag>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<%@ include file="comment/IncludeLeve3Subjects.jsp"%>
					</c:forEach>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_cancle_formPaperView" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//关闭窗口
		$('#dom_cancle_formPaperView').bind('click', function() {
			$('#winPaperView').window('close');
		});
	});
//-->
</script>