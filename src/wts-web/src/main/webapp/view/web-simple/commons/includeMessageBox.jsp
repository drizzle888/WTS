<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:if test="${USEROBJ!=null}">
	<div class="widget-box shadow-box">
		<div class="title">
			<h3>
				<i class="glyphicon glyphicon-envelope"></i>&nbsp;未读消息&nbsp;(${unreadMsgNum==null?0:unreadMsgNum})
				<c:if test="${empty unreadMessages}">
					<a
						style="font-size: 14px; font-weight: lighter; color: #D9534F; float: right;"
						href="webuser/Home.do?type=usermessage">查看已读消息</a>
				</c:if>
			</h3>
		</div>
		<ul class="box-list" id="hots">
			<c:forEach items="${unreadMessages}" varStatus="status" var="node">
				<li>
					<div class="li-out">
						<span class="last"> </span> <span class="name"
							style="width: 250px;white-space:normal;"><i
							class="glyphicon glyphicon-envelope"></i> <a
							title="${node.NOTAGTITLE}"
							target="${config_sys_link_newwindow_target}"
							href="webusermessage/showMessage.do?id=${node.ID}">&nbsp;${node.TITLE}</a>
						</span>
					</div>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${!empty unreadMessages}">
			<div style="margin: 8px;">
				<a style="font-size: 14px; float: right; margin-bottom: 6px;"
					href="webuser/Home.do?type=usermessage">查看更多消息</a>
			</div>
		</c:if>
	</div>
</c:if>