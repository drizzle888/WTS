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
						<li targetId='${subjectu.version.id}-NAVI'
							class="wts-side-subjuct-unit ${subjectu.finishIs?'active':''}"
							id="${subjectu.version.id}-NAVIID">${status.index + 1}</li>
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
							<li targetId='${subjectu.version.id}-NAVI'
								class="wts-side-subjuct-unit ${subjectu.finishIs?'active':''}"
								id="${subjectu.version.id}-NAVIID">${status.index + 1}</li>
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
								<li targetId='${subjectu.version.id}-NAVI'
									class="wts-side-subjuct-unit ${subjectu.finishIs?'active':''}"
									id="${subjectu.version.id}-NAVIID">${status.index + 1}</li>
							</c:forEach>
						</ul>
					</c:if>
					<div class="wts-sidecard-split"></div>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</div>
	<div class="btn-group btn-group-justified wts-sidecard-buttons"
		style="margin-top: 0px; border-top: 1px dashed #ccc; margin-top: 10px;"
		role="group" aria-label="...">
		<div class="btn-group" role="group">
			<div class="btn wts-countdown-title" title="以服务器时间为准">
				开始答题
				<PF:FormatTime date="${card.starttime}" yyyyMMddHHmmss="HH:mm:ss" />
				- 结束答题
				<PF:FormatTime date="${card.endtime}" yyyyMMddHHmmss="HH:mm:ss" />
			</div>
		</div>
	</div>
	<c:if test="${flag=='answer'||flag==null}">
		<!-- 只有答題才限时倒计时 -->
		<div class="btn-group btn-group-justified wts-sidecard-buttons"
			style="margin-top: 0px;" role="group" aria-label="...">
			<div class="btn-group" role="group">
				<div class="btn wts-countdown-title" title="以服务器时间为准">
					<span id="countDownTimerBoxId" class="defaut"
						title="${CountDownSecond}"></span>
				</div>
			</div>
		</div>
	</c:if>
	<div class="btn-group btn-group-justified wts-sidecard-buttons"
		style="margin-top: 0px;" role="group" aria-label="...">
		<c:if test="${flag=='answer'||flag==null}">
			<!-- 答卷 -->
			<div class="btn-group" role="group">
				<button id="sideCardSaveButton" type="button" class="btn btn-info">保存</button>
			</div>
			<div class="btn-group" role="group">
				<button id="sideCardRefreshButton" type="button"
					class="btn btn-info">刷新</button>
			</div>
			<div class="btn-group" role="group">
				<button data-toggle="modal" data-target="#submitPaper-win"
					type="button" class="btn btn-danger">完成交卷</button>
			</div>
		</c:if>
		<c:if test="${flag=='checkup'}">
			<!-- 检查答卷 -->
			<div class="btn-group" role="group">
				<button id="sideBackPaperButton" type="button"
					class="btn btn-success">返回答题</button>
			</div>
			<div class="btn-group" role="group">
				<button data-toggle="modal" data-target="#submitPaper-win"
					type="button" class="btn btn-danger">完成交卷</button>
			</div>
		</c:if>

	</div>
</div>
<!-- 考卷提交框 -->
<div class="modal fade" id="submitPaper-win" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
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
				<div class="doc_node_title_box" style="font-size: 18px;"
					id="card-finish-numinfo">
					共<span class="wts-red" id="card-finish-numinfo-all">0</span>道题， 完成<span
						class="wts-red" id="card-finish-numinfo-compelet">0</span>道题!
				</div>
				<div class="doc_node_title_box" style="font-size: 16px;">提交答卷后将无法重新答题，确认提交?</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<c:if test="${flag=='answer'}">
					<!-- 答卷頁面 -->
					<c:if test="${room.writetype!='2'}">
						<!--答题人员类型: ${room.writetype} -->
						<a id="sideCardCheckupButton" class="btn btn-success">检查答卷!</a>
					</c:if>
					<c:if test="${room.writetype=='2'}">
						<!-- 匿名答题室 -->
					</c:if>
					<a id="sideCardSubmitButton" class="btn btn-primary">立即提交!</a>
				</c:if>
				<c:if test="${flag=='checkup'}">
					<a href="javascript:submitPaper()" class="btn btn-primary">立即提交!</a>
				</c:if>
			</div>
		</div>
	</div>
</div>
<!-- 考卷保存框 -->
<div class="modal fade" id="savePaper-win" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="myModalLabel">答卷保存中...</h5>
			</div>
			<div class="modal-body">
				<div class="progress">
					<div class="progress-bar progress-bar-success progress-bar-striped"
						id="savePaper-progress" role="progressbar" aria-valuenow="1"
						aria-valuemin="0" aria-valuemax="100" style="width: 1%">
						<span class="sr-only">保存中...</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>