<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/tip.tld" prefix="TIP"%>
<!-- 显示答案，显示分数 -->
<c:if test="${flag=='learn'}">
	<!-- 学习答卷时先只展示按钮，点击后再展示答案-->
	<div class="answerRightViewBox" subjectId="${subjectu.subject.id}"
		style="height: auto;text-align: center;">查看本题解析</div>
</c:if>
<c:if
	test="${(flag=='adjudge'&&(subjectu.cardSubject.complete=='0'||subjectu.cardSubject.complete=='1'))||flag==null}">
	<!-- 阅卷時顯示标准答案-->
	<div class="answerRightViewBox" subjectId="${subjectu.subject.id}"
		style="height: auto;">
		<c:forEach items="${subjectu.answers}" var="node" varStatus="status">
			<c:if test="${subjectu.version.tiptype=='1'}">
				<!-- 填空题正确答案 -->
				<div>
					<label for="${node.answer.id}-INPUT">第${node.answer.sort}空，正确答案：${node.answer.answer}</label>
					<c:if test="${node.answer.pointweight>0}">
						<label style="color: #666; font-size: 14px; font-weight: 200;">&nbsp;(得分权重:${node.answer.pointweight})</label>
					</c:if>
				</div>
			</c:if>
			<c:if
				test="${subjectu.version.tiptype=='2'||subjectu.version.tiptype=='3'||subjectu.version.tiptype=='4'}">
				<!-- 判断题/单选题/多选题正确答案 -->
				<c:if test="${node.answer.rightanswer=='1'}">
					<div>
						<label for="${node.answer.id}-INPUT">正确选项：<c:if
								test="${subjectu.version.tiptype!='4' }">
								<TIP:WordCode code="${status.index+1}" />.</c:if>${node.answer.answer}
						</label>
					</div>
				</c:if>
			</c:if>
			<c:if test="${subjectu.version.tiptype=='5'}">
				<!-- 問答題正确答案 -->
				<div>
					<label for="${node.answer.id}-INPUT">关键字：${node.answer.answer}
					</label> <label style="color: #666; font-size: 14px; font-weight: 200;">&nbsp;(得分权重:${node.answer.pointweight})</label>
				</div>
			</c:if>
			<c:if test="${subjectu.version.tiptype=='6'}">
				<!-- 問答題正确答案 -->
				<div>
					<label for="${node.answer.id}-INPUT">评价标准：${node.answer.answer}
					</label>
				</div>
			</c:if>
		</c:forEach>
	</div>
</c:if>
<c:if test="${flag=='adjudge'}">
	<!-- 阅卷時顯示分數-->
	<c:if test="${subjectu.cardSubject.complete=='0'}">
		<!-- 可编辑得分 -->
		<form class="form-inline">
			<div class="form-group has-success has-feedback">
				<div class="input-group">
					<span class="input-group-addon">得分</span> <input
						id="${subjectu.version.id}POINT" type="text"
						alt="${subjectu.point}" class="form-control wts-adjudge-point"
						value="${subjectu.cardSubject.point}"><span
						forInput='${subjectu.version.id}POINT'
						class="input-group-addon btn wts-adjudge-maxpoint">满分:${subjectu.point}</span>
				</div>
			</div>
		</form>
	</c:if>
	<c:if test="${subjectu.cardSubject.complete=='1'}">
		<!-- 不可编辑得分 -->
		<form class="form-inline">
			<div class="form-group has-warning has-feedback">
				<div class="input-group">
					<span class="input-group-addon">得分</span> <input type="text"
						id="${subjectu.version.id}POINT" alt="${subjectu.point}"
						class="form-control wts-adjudge-point"
						value="${subjectu.cardSubject.point}" disabled><span
						class="input-group-addon">满分: ${subjectu.point}</span>
				</div>
			</div>
		</form>
	</c:if>
</c:if>