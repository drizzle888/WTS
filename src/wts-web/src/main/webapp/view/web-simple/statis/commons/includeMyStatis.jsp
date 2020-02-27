<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.wcpstatis .inbox {
	background-color: #f3f3f3;
	border: 1px solid #ccc;
	margin: 20px;
	height: 72px;
	border-radius: 4px;
	text-align: center;
	margin-left: 10px;
	margin-right: 10px;
	padding-top: 4px;
}

.wcpstatis .inbox .titletag {
	font-size: 12px;
	color: #888;
}

.wcpstatis .inbox .numtag {
	font-size: 32px;
	font-weight: 700;
	color: #4A515B;
}

.wcpstatis .col-sm-2 {
	padding-left: 0px;
	padding-right: 0px;
}
</style>
<div class="column_box hidden-xs hidden-sm">
	<div class="panel panel-default wcpstatis"
		style="background-color: #FFFFFF;">
		<div class="panel-body">
			<div class="row" style="padding-right: 30px;">
				<div class="col-sm-2">
					<div style="text-align: center; padding: 4px;">
						<c:if test="${photourl!=null}">
							<img id="imgShowBoxId" src="${photourl}" alt="wcp"
								style="max-height: 128px; max-width: 128px;" class="img-thumbnail" />
						</c:if>
						<c:if test="${photourl==null}">
							<img id="imgShowBoxId" src="<PF:basePath/>text/img/none.png"
								alt="wcp" style="max-height: 128px; max-width: 128px;"
								class="img-thumbnail" />
						</c:if>
					</div>
				</div>
				<div class="col-sm-10">
					<div class="row">
						<div class="col-sm-12" style="padding-left: 24px;">
							<c:if test="${self}">
								<strong>我</strong>
							</c:if>
							<c:if test="${!self}">
								<strong>${user.name}</strong>
							</c:if>
							<c:if test="${org!=null}">
								<small>属于组织机构</small>
								<strong> ${org.name}</strong>
							</c:if>
							<c:if test="${fn:length(posts)>0}">
								<small>岗位为</small>
								<c:forEach items="${posts}" var="post">
									<strong> ${post.name}</strong>
								</c:forEach>
							</c:if>
							<small>于<PF:FormatTime date="${user.ctime}"
									yyyyMMddHHmmss="yyyy-MM-dd HH:mm" /> 建立账户
							</small>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3">
							<div class="inbox">
								<div class="numtag">${examStat.subjectnum}</div>
								<div class="titletag">答题总数</div>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="inbox">
								<div class="numtag">${examStat.errorsubnum}</div>
								<div class="titletag">错题总数</div>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="inbox">
								<div class="numtag">${examStat.papernum}</div>
								<div class="titletag">完成考卷总数</div>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="inbox">
								<div class="numtag">${examStat.testnum}</div>
								<div class="titletag">完成练习总数</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>