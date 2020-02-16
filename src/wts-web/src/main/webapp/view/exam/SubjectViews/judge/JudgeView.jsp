<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/tip.tld" prefix="TIP"%>
<link rel="stylesheet" type="text/css"
	href="<PF:basePath/>view/exam/subject/subject.css">
<!--判断题 flag=(adjudge:判卷，answer：答卷，checkup：检查)-->
<div>
	<div class="subjectUnitViewBox">
		<div class="subjectOrder">${status.index + 1}</div>
		<div>
			<c:if test="${flag!='adjudge'}">
				<div>${subjectu.version.tipstr}<span class="subjectPoint">${subjectu.point}分</span>
				</div>
				<c:if test="${!empty subjectu.version.tipnote}">
					<div class="ke-content ke-content-borderbox">
						<TIP:InitHtmlContentTag html="${subjectu.version.tipnote}"></TIP:InitHtmlContentTag>
					</div>
				</c:if>
				<div style="margin: 8px;"></div>
			</c:if>
			<c:if test="${flag=='answer'||flag==null}">
				<!-- 用户答题和预览时显示，填写答案 -->
				<ul class="answerUnitViewBox alert alert-warning">
					<c:forEach items="${subjectu.answers}" var="node"
						varStatus="status">
						<li><label style="cursor: pointer;">
								${node.answer.answer} <input id="${node.answer.id}-INPUT"
								style="cursor: pointer;" type="radio"
								name="${subjectu.version.id}"
								${node.val=='true'?'checked="checked"':''}
								value="${node.answer.id}">
						</label></li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${flag=='checkup'||flag=='adjudge'}">
				<!-- 答案检查和阅卷时显示-->
				<c:if test="${subjectu.finishIs}">
					<div class="answerUnitViewBox alert alert-warning"
						style="height: auto; padding-left: 12px;">
						<div>
							<b>判断题-答案:</b>
						</div>
						<c:forEach items="${subjectu.answers}" var="node"
							varStatus="status">
							<c:if test="${node.val=='true'}">
								<div class="wts-select-unit" targetId="${node.answer.id}-INPUT">
									<div style="float: left; margin-left: -20px;">
										<TIP:WordCode code="${status.index+1}" />
										.
									</div>
									<div>${node.answer.answer}</div>
									<c:if test="${!empty node.answer.answernote}">
										<div class="ke-content ke-content-borderbox">${node.answer.answernote}</div>
									</c:if>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
			</c:if>
			<%@ include file="../includePointInput.jsp"%>
		</div>
	</div>
</div>

