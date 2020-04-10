<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/tip.tld" prefix="TIP"%>
<link rel="stylesheet" type="text/css"
	href="<PF:basePath/>view/exam/subject/subject.css">
<!-- 问答题 flag=(adjudge:判卷，answer：答卷，checkup：检查) -->
<div>
	<div class="subjectUnitViewBox">
		<div class="subjectOrder">${status.index + 1}</div>
		<div>
			<c:if test="${flag!='adjudge'}">
				<div>${subjectu.version.tipstr}<span class="subjectPoint">本题${subjectu.point}分</span>
				</div>
				<c:if test="${!empty subjectu.version.tipnote}">
					<div class="ke-content ke-content-borderbox">
						<TIP:InitHtmlContentTag html="${subjectu.version.tipnote}"></TIP:InitHtmlContentTag>
					</div>
				</c:if>
			</c:if>
			<c:if test="${flag=='answer'||flag==null}">
				<!-- 用户答题和预览时显示，填写答案 -->
				<div class="answerInterLocutionViewBox">
					<div>
						<!-- 用户答题控件 -->
						<textarea name="${subjectu.version.id}"
							class="form-control interlocutionInput" rows="3">${subjectu.val}</textarea>
					</div>
				</div>
			</c:if>
			<c:if test="${flag=='checkup'||flag=='adjudge'}">
				<!-- 答案检查和阅卷时显示，答案展示-->
				<div class="answerInterLocutionViewBox">
					<!-- 阅卷答案 -->
					<textarea readonly="readonly" style="width: 100%"
						class="interlocutionInput">${subjectu.val}</textarea>
				</div>
			</c:if>
			<%@ include file="../includePointInput.jsp"%>
		</div>
	</div>
</div>