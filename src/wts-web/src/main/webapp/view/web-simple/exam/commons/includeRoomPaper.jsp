<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 固定答题试卷 -->
<div class="col-md-6" style="padding-left: 8px; padding-right: 8px;">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="media" style="height: 153px; overflow: hidden;">
				<div
					style="text-align: center; border-bottom: 1px dashed #ccc; margin-bottom: 8px; padding-bottom: 0px;">
					<div class="doc_node_title_box"
						style="font-size: 16px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
						<code style="float: left;">考卷</code>
						&nbsp;
						<c:if test="${empty paper.roomPaper.name}">${paper.info.name}</c:if>
						<c:if test="${not empty paper.roomPaper.name}">${paper.roomPaper.name}</c:if>
					</div>
				</div>
				<div class="pull-right">
					<img alt="答题室" style="width: 64px; height: 64px;"
						src="text/img/paper.png">
				</div>
				<div class="media-body">
					<div style="margin-left: 4px; height: 56px; overflow: hidden;"
						class="pull-left">
						<div class="side_unit_info"
							style="max-height: 38px; overflow: hidden;">
							<b>答题时长</b>：${room.room.timelen}分&nbsp;<b>题量</b>：共${paper.rootChapterNum}道大题&nbsp;${paper.subjectNum}道小题&nbsp;<b>总分</b>：${paper.allPoint}
						</div>
						<div style="margin-left: -6px;">
							<%@ include
								file="/view/web-simple/exam/commons/includeRoomMenus.jsp"%>
						</div>
					</div>
				</div>
				<div style="padding-top: 20px;">
					<div class="btn-group btn-group-justified" role="group"
						aria-label="...">
						<c:if test="${paper.card==null}">
							<div class="btn-group" role="group">
								<a data-toggle="modal" data-target="#${paper.info.id}-win"
									type="button" class="btn btn-default"> <c:if
										test="${paper.room.writetype=='2'}">匿名答题&nbsp;Go!</c:if> <c:if
										test="${paper.room.writetype!='2'}">开始答题&nbsp;Go!</c:if>
								</a>
							</div>
						</c:if>
						<c:if test="${paper.card!=null}">
							<c:if test="${paper.card.pstate=='1'}">
								<div class="btn-group" role="group">
									<a
										href="webpaper/card.do?paperid=${paper.info.id}&roomId=${room.room.id}"
										data-toggle="modal" type="button" class="btn btn-success">继续答题&nbsp;
										<span class="exam-dotime" style="font-size: 11px;"><PF:FormatTime
												date="${paper.card.starttime}" yyyyMMddHHmmss="HH:mm:ss" />-<PF:FormatTime
												date="${paper.card.endtime}" yyyyMMddHHmmss="HH:mm:ss" /> </span>
									</a>
								</div>
							</c:if>
							<c:if test="${paper.card.pstate=='3'||paper.card.pstate=='4'}">
								<div class="btn-group" role="group">
									<a type="button" class="btn btn-warning" disabled="disabled">已结束&nbsp;<span
										title="答题时间" class="exam-dotime" style="font-size: 11px;"><PF:FormatTime
												date="${paper.card.starttime}"
												yyyyMMddHHmmss="yyyy-MM-dd HH:mm:ss" /> - <PF:FormatTime
												date="${paper.card.endtime}" yyyyMMddHHmmss="HH:mm:ss" /> </span>
									</a>
								</div>
							</c:if>
							<c:if test="${paper.card.pstate=='2'||paper.card.pstate=='5'||paper.card.pstate=='6'}">
								<div class="btn-group" role="group">
									<a type="button" class="btn btn-success" disabled="disabled">
										已完成&nbsp;<span title="答题时间" class="exam-dotime"
										style="font-size: 11px;"><PF:FormatTime
												date="${paper.card.starttime}"
												yyyyMMddHHmmss="yyyy-MM-dd HH:mm:ss" /> - <PF:FormatTime
												date="${paper.card.endtime}" yyyyMMddHHmmss="HH:mm:ss" /> </span>
									</a>
								</div>
							</c:if>
							<c:if test="${paper.card.pstate=='7'}">
								<div class="btn-group" role="group">
									<a type="button" class="btn btn-success" disabled="disabled">
									<PF:IfParameterEquals key="config.exam.user.grade.queryable" val="true">
										得分：${paper.card.point}分</PF:IfParameterEquals>
										<PF:IfParameterNoEquals key="config.exam.user.grade.queryable" val="true">
										已完成
										</PF:IfParameterNoEquals>
										&nbsp;
										<span title="判卷时间"
										class="exam-dotime" style="font-size: 11px;"><PF:FormatTime
												date="${paper.card.adjudgetime}"
												yyyyMMddHHmmss="yyyy-MM-dd HH:mm:ss" /></span>
									</a>
								</div>
							</c:if>
							<c:if test="${room.room.restarttype=='2'}">
								<div class="btn-group" role="group">
									<a data-toggle="modal"
										data-target="#${paper.info.id}-restart-win" type="button"
										class="btn btn-danger">重新答題</a>
								</div>
							</c:if>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 开始答题(弹出窗口)-->
<div class="modal fade" id="${paper.info.id}-win" tabindex="-1"
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
				<div class="doc_node_title_box" style="font-size: 16px;">开始答题后，需要在${room.room.timelen}分钟内完成答题，确认是否开始?</div>
				<c:if test="${paper.room.writetype!='2'}">
					<div class="doc_node_title_box"
						style="font-size: 16px; color: #d13133;">答题人：${USEROBJ.name}</div>
				</c:if>
				<c:if test="${paper.room.writetype=='2'}">
					<div class="doc_node_title_box"
						style="font-size: 16px; color: #d13133;">答题人：匿名</div>
				</c:if>
				<div class="side_unit_info" style="font-size: 14px;">
					共<b>${paper.rootChapterNum}</b>道大题,<b>${paper.subjectNum}</b>道小题,总分<b>${paper.allPoint}</b>分
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<c:if test="${paper.room.writetype!='2'}">
					<a
						href="webpaper/card.do?paperid=${paper.info.id}&roomId=${room.room.id}"
						class="btn btn-primary">立即开始Go!</a>
				</c:if>
				<c:if test="${paper.room.writetype=='2'}">
					<a
						href="webpaper/Pubcard.do?paperid=${paper.info.id}&roomId=${room.room.id}"
						class="btn btn-primary">匿名开始Go!</a>
				</c:if>
			</div>
		</div>
	</div>
</div>
<!-- 重新答题(弹出窗口)-->
<div class="modal fade" id="${paper.info.id}-restart-win" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">将清空答题卡</h4>
			</div>
			<div class="modal-body">
				<div class="doc_node_title_box" style="font-size: 16px;">清空答题卡后,可重新答题!</div>
				<div class="side_unit_info" style="font-size: 14px;">
					数据清空后不可恢复，是否继续?</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<a
					href="webpaper/PubclearUserPaperCard.do?paperid=${paper.info.id}&roomid=${room.room.id}"
					class="btn btn-primary">继续清空答题卡</a>
			</div>
		</div>
	</div>
</div>