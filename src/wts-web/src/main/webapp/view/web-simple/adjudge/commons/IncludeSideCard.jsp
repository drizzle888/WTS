<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="wts-sidecard" data-spy="affix" data-offset-top="60">
	<div class="title">答题卡</div>
	<div class="wts-sidecard-subjects"
		style="overflow: auto; max-height: 300px;">
		<c:forEach items="${paper.chapters}" var="chapter1">
			<!-- 一级章節头部 -->
			<h1 targetId='${chapter1.chapter.id}-NAVI'>${chapter1.chapter.name}<span
					class="chapter-info">${chapter1.allpoint}分</span>
			</h1>
			<c:if test="${!empty chapter1.subjects}">
				<ul>
					<c:forEach items="${chapter1.subjects}" var="subjectu"
						varStatus="status">
						<c:if test="${subjectu.finishIs}">
							<li targetId='${subjectu.version.id}-NAVI'
								class="${subjectu.cardSubject.complete=='1'?'active':''}"
								id="${subjectu.version.id}-NAVIID">${status.index + 1}</li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
			<div class="wts-sidecard-split"></div>
			<c:forEach items="${chapter1.chapters}" var="chapter2">
				<!-- 二级章節头部 -->
				<h2 targetId='${chapter2.chapter.id}-NAVI'>${chapter2.chapter.name}<span
						class="chapter-info">${chapter2.allpoint}分</span>
				</h2>
				<c:if test="${!empty chapter2.subjects}">
					<ul>
						<c:forEach items="${chapter2.subjects}" var="subjectu"
							varStatus="status">
							<c:if test="${subjectu.finishIs}">
								<li targetId='${subjectu.version.id}-NAVI'
									class="${subjectu.cardSubject.complete=='1'?'active':''}"
									id="${subjectu.version.id}-NAVIID">${status.index + 1}</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
				<div class="wts-sidecard-split"></div>
				<c:forEach items="${chapter2.chapters}" var="chapter3">
					<!-- 二级章節头部 -->
					<h3 targetId='${chapter3.chapter.id}-NAVI'>${chapter3.chapter.name}<span
							class="chapter-info">${chapter3.allpoint}分</span>
					</h3>
					<c:if test="${!empty chapter3.subjects}">
						<ul>
							<c:forEach items="${chapter3.subjects}" var="subjectu"
								varStatus="status">
								<c:if test="${subjectu.finishIs}">
									<li targetId='${subjectu.version.id}-NAVI'
										class="${subjectu.cardSubject.complete=='1'?'active':''}"
										id="${subjectu.version.id}-NAVIID">${status.index + 1}</li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if>
					<div class="wts-sidecard-split"></div>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</div>
	<div class="btn-group btn-group-justified wts-sidecard-buttons"
		style="margin-top: 0px;" role="group" aria-label="...">
		<div class="btn-group" role="group">
			<div class="btn ">
				当前得分：<span style="font-weight: 700; font-size: 18px;"
					id="paper-allpoint"></span>分
			</div>
		</div>
	</div>
	<div class="btn-group btn-group-justified wts-sidecard-buttons"
		style="margin-top: 0px;" role="group" aria-label="...">
		<!-- 答卷 -->
		<div class="btn-group" role="group">
			<a
				href="adjudge/paperUser.do?paperid=${paper.info.id}&roomId=${room.id}"
				type="button" class="btn btn-default">返回人员列表</a>
		</div>
		<div class="btn-group" role="group">
			<a id="wts-adjudge-submitButtonId" data-toggle="modal"
				data-target="#submitAdjudge-win" type="button" class="btn btn-info">完成阅卷</a>
		</div>
	</div>
</div>

<form method="post" id="adjudgeHidenFormId"
	action="adjudge/submitAdjudge.do">
	<!-- 答题卡ID -->
	<input type="hidden" name="cardId" value="${card.id}">
	<!-- 分数json -->
	<input type="hidden" name="jsons" value="${card.id}"
		id="adjudgeHidenFormJsonId">
</form>

<!-- 提交阅卷結果確認窗口  -->
<div class="modal fade" id="submitAdjudge-win" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">《${paper.info.name}》</h4>
			</div>
			<div class="modal-body">
				<div class="doc_node_title_box" style="font-size: 16px;">
					提交结果后答卷状态将切换为完成阅卷状态，但是依然可以重新阅卷。<br />确认提交?
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<a href="javascript:submitAdjudgePaper()" class="btn btn-primary">立即提交!</a>
			</div>
		</div>
	</div>
</div>