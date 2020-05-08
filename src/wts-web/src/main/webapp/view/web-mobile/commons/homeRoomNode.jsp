<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 首页--分类考场中的考场展示 -->
<div class="col-md-4" style="padding-left: 8px; padding-right: 8px;">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="media" style="height: 195px; overflow: hidden;">
				<div
					style="text-align: center; border-bottom: 1px dashed #ccc; margin-bottom: 8px; padding-bottom: 0px;">
					<c:if test="${room.room.pshowtype=='3'}">
						<!--练习题标志-->
						<img alt="" src="<PF:basePath/>text/img/testTag.png"
							style="width: 64px; height: 64px; position: absolute; left: 8px; top: 0px;">
					</c:if>
					<c:if test="${room.room.pshowtype=='4'}">
						<!--学习标志-->
						<img alt="" src="<PF:basePath/>text/img/leanTag.png"
							style="width: 64px; height: 64px; position: absolute; left: 8px; top: 0px;">
					</c:if>
					<c:if test="${room.room.timetype=='1'}"> 
						<div class="side_unit_info">永久有效</div>
					</c:if>
					<c:if test="${room.room.timetype=='2'}">
						<div class="side_unit_info">${room.room.starttime}&nbsp;至&nbsp;${room.room.endtime}</div>
					</c:if>
					<div class="doc_node_title_box"
						style="font-size: 16px;white-space:nowrap;">${room.room.name}</div>
				</div>
				<div class="pull-right">
					<c:if test="${empty room.room.imgid}">
						<img alt="答题室"
							style="width: 82px; height: 82px; margin-bottom: 8px; border-radius: 3px;"
							src="text/img/exam.png">
					</c:if>
					<c:if test="${ !empty room.room.imgid}">
						<img alt="答题室"
							style="width: 82px; height: 82px; margin-bottom: 8px; border-radius: 3px;"
							src="actionImg/Publoadimg.do?id=${room.room.imgid}">
					</c:if>
				</div>
				<div class="media-body">
					<div style="margin-left: 4px;" class="pull-left">
						<div class="side_unit_info">答题时长：${room.room.timelen}分</div>
						<c:if test="${room.room.writetype=='0'}">
							<div class="side_unit_info">答题人：任何人</div>
						</c:if>
						<c:if test="${room.room.writetype=='1'}">
							<div class="side_unit_info">答题人：指定人</div>
						</c:if>
						<c:if test="${room.room.writetype=='2'}">
							<div class="side_unit_info">答题人：匿名</div>
						</c:if>
						<div class="side_unit_info">业务分类：${room.type.name}</div>
						<c:if
							test="${room.room.pshowtype!='1'||room.room.ssorttype!='1'||room.room.osorttype!='1'}">
							<div class="side_unit_info"><b>随机</b>
								<c:if test="${room.room.pshowtype=='2'}"><code>/答卷</code></c:if>
								<c:if test="${room.room.ssorttype=='2'}"><code>/选项</code></c:if>
								<c:if test="${room.room.osorttype=='2'}"><code>/题序</code></c:if>
							</div>
						</c:if>
					</div>
				</div>
				<div>
					<div class="btn-group btn-group-justified" role="group">
						<c:if test="${room.currentUserAble&&room.currentTimeAble}">
							<div class="btn-group" role="group">
								<c:if test="${room.room.writetype=='2'}">
									<a href="exam/Pubroompage.do?roomid=${room.room.id}"
										type="button" class="btn btn-success">进入答题室(匿名)</a>
								</c:if>
								<c:if test="${room.room.writetype!='2'}">
									<a href="exam/roompage.do?roomid=${room.room.id}" type="button"
										class="btn btn-success">进入答题室</a>
								</c:if>
							</div>
						</c:if>
						<c:if test="${room.currentUserAble&&!room.currentTimeAble}">
							<div class="btn-group" role="group">
								<a type="button" class="btn btn-warning" disabled="disabled">无效答题时间</a>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>