<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="col-md-6" style="padding-left: 8px; padding-right: 8px;">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="media" style="height: 153px; overflow: hidden;">
				<div
					style="text-align: center; border-bottom: 1px dashed #ccc; margin-bottom: 8px; padding-bottom: 0px;">
					<div class="doc_node_title_box"
						style="font-size: 16px; white-space: nowrap;">${paper.info.name}</div>
				</div>
				<div class="pull-right">
					<img alt="答题室" style="width: 64px; height: 64px;"
						src="text/img/paper.png">
				</div>
				<div class="media-body">
					<div style="margin-left: 4px;" class="pull-left">
						<div class="side_unit_info">答题时长：${room.room.timelen}分</div>
						<div class="side_unit_info">题量：共${paper.rootChapterNum}道大题,${paper.subjectNum}道小题</div>
						<div class="side_unit_info">总分：${paper.allPoint}</div>
					</div>
				</div>
				<div style="padding-top: 20px;">
					<div class="btn-group btn-group-justified" role="group"
						aria-label="...">
						<div class="btn-group" role="group">
							<c:if test="${room.room.pshowtype=='2'}">
								<!-- 随机答卷只显示当前人数，不显示总人数 -->
								<a
									href="adjudge/paperUser.do?paperid=${paper.info.id}&roomId=${room.room.id}"
									type="button" class="btn btn-default">阅卷管理 (阅<span
									class="wts-red"><b>${paper.adjudgeUserNum}</b></span>人/答<span
									class="wts-red"><b>${paper.currentUserNum}</b></span>人)
								</a>
							</c:if>
							<c:if test="${room.room.pshowtype=='1'}">
								<!-- 普通答卷-->
								<a
									href="adjudge/paperUser.do?paperid=${paper.info.id}&roomId=${room.room.id}"
									type="button" class="btn btn-default">阅卷管理 (阅<span
									class="wts-red"><b>${paper.adjudgeUserNum}</b></span>人/答<span
									class="wts-red"><b>${paper.currentUserNum}</b></span>人<c:if
										test="${paper.allUserNum>0}">/共<span class="wts-red"><b>${paper.allUserNum}</b></span>人</c:if>)
								</a>
							</c:if>
						</div>
						<div class="btn-group" role="group">
							<button onclick="exportWordPaper('${paper.info.id}')"
								type="button" class="btn btn-info">
								<i class="glyphicon glyphicon-download-alt"></i>&nbsp;导出答卷
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>