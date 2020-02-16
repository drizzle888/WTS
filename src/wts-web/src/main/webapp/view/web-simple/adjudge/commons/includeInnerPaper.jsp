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
					<c:if
						test="${paper.info.modeltype=='1'||paper.info.modeltype=='2' }">
						<img alt="答题室" style="width: 64px; height: 64px;"
							src="text/img/paper.png">
					</c:if>
					<c:if test="${paper.info.modeltype=='3'}">
						<img alt="练习室" style="width: 64px; height: 64px;"
							src="text/img/random.png">
					</c:if>
				</div>
				<div class="media-body">
					<div style="margin-left: 4px;" class="pull-left">
						<c:if
							test="${paper.info.modeltype=='1'||paper.info.modeltype=='2' }">
							<div class="side_unit_info">答题时长：${room.room.timelen}分</div>
							<div class="side_unit_info">题量：共${paper.rootChapterNum}道大题,${paper.subjectNum}道小题</div>
							<div class="side_unit_info">总分：${paper.allPoint}</div>
						</c:if>
						<c:if test="${paper.info.modeltype=='3'}">
							<div class="side_unit_info">题量：共${paper.subjectNum}道题</div>
							<div class="side_unit_info">随机答题,不计得分</div>
							<div class="side_unit_info">错题记录可在用户空间中查看</div>
						</c:if>
					</div>
				</div>
				<div style="padding-top: 20px;">
					<div class="btn-group btn-group-justified" role="group"
						aria-label="...">
						<div class="btn-group" role="group">
							<c:if
								test="${paper.info.modeltype=='1'||paper.info.modeltype=='2' }">
								<a
									href="adjudge/paperUser.do?paperid=${paper.info.id}&roomId=${room.room.id}"
									type="button" class="btn btn-default">阅卷管理 (阅卷<span
									class="wts-red"><b> ${paper.adjudgeUserNum} </b></span>人/参答<span
									class="wts-red"><b> ${paper.currentUserNum} </b></span>人<c:if
										test="${paper.allUserNum>0}">/共<span class="wts-red"><b>
												${paper.allUserNum} </b></span>人</c:if>)
								</a>
							</c:if>
							<c:if test="${paper.info.modeltype=='3'}">
								<a type="button" class="btn btn-default" disabled="disabled">练习题无需阅卷
								</a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
