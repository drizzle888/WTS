<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/tip.tld" prefix="TIP"%>
<link rel="stylesheet" type="text/css"
	href="<PF:basePath/>view/exam/subject/subject.css">
<!--填空题 flag=(adjudge:判卷，answer：答卷，checkup：检查)-->
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
			</c:if>
			<c:if test="${flag=='answer'||flag==null}">
				<!-- 用户答题和预览时显示，填写答案 -->
				<div class="answerVacancyViewBox">
					<div class="row" style="padding-top: 8px;">
						<c:forEach items="${subjectu.answers}" var="node">
							<div class="col-md-6">
								<div class="form-group has-warning has-feedback">
									<div class="input-group">
										<span class="input-group-addon">第${node.answer.sort}空答案：</span>
										<input type="text" name="${subjectu.version.id}"
											id="${node.answer.id}-INPUT" class="form-control"
											value="${node.val}">
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<c:if test="${flag=='checkup'||flag=='adjudge'}">
				<!-- 答案检查和阅卷时显示-->
				<div class="answerVacancyViewBox"
					style="height: auto;">
					<c:forEach items="${subjectu.answers}" var="node">
						<div>
							<label for="${node.answer.id}-INPUT">第${node.answer.sort}空答案：</label>${node.val}</div>
					</c:forEach>
				</div>
			</c:if>
			<%@ include file="../includePointInput.jsp"%>
		</div>
	</div>
</div>

