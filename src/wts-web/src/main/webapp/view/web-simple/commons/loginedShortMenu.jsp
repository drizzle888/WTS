<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 用户登录后再次进入登录页面时的快捷菜单，主要为了在手机端进入用户功能 -->
<c:if test="${USEROBJ!=null}">
	<div class="row">
		<div class="col-sm-12">
			<div class="third-part tracking-ad">
				<div class="text-center">
					<img src="${photourl}" alt="wcp" style="margin: 20px; max-width: 64px;" />
					<div>${user.name }</div>
				</div>
				<hr />
				<div class="list-group text-center">
					<a href="<PF:defaultIndexPage/>" class="list-group-item active"> 进入首页</a>
				</div> 
				<div class="list-group text-center">
					<a href="webuser/PubHome.do?type=usermessage&userid=${USEROBJ.id}" class="list-group-item">我的消息</a>
					<a href="login/webout.do" class="list-group-item">注销</a>
				</div>
			</div>
			<c:if test="${STATE=='1'}">
				<div class="text-center" id="romovealertMessageErrorId" style="margin: 4px; color: red;">
					<span class="glyphicon glyphicon-exclamation-sign"></span>
					${MESSAGE}
				</div>
			</c:if>
			<div class="text-center" id="alertMessageErrorId" style="margin: 4px; color: red;"></div>
		</div>
	</div>
</c:if>