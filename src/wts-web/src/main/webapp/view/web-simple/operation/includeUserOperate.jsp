<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:if test="${self}">
	<div class="btn-group-vertical btn-group-sm" role="group"
		aria-label="..." style="width: 150px;">
		<button type="button" class="btn btn-default" onclick="toUserEdit()">
			<span class="glyphicon glyphicon-edit"></span> 修改信息
		</button>
		<PF:IfParameterNoEquals key="config.sso.state" val="true">
			<button type="button" class="btn btn-default"
				onclick="toUserPwdEdit()">
				<span class="glyphicon glyphicon-lock"></span> 修改密码
			</button>
		</PF:IfParameterNoEquals>
	</div>
	<script type="text/javascript">
		function toUserEdit() {
			window.location.href = basePath + "webuser/edit.do";
		}
		function toUserPwdEdit() {
			window.location.href = basePath + "webuser/editCurrentUserPwd.do";
		}
	</script>
</c:if>
<div class="btn-group-vertical btn-group-sm" role="group"
	aria-label="..." style="width: 150px; margin-top: 8px;">
	<!-- 答题记录-->
	<c:if test="${type=='AllSubject'}">
		<a href="webuser/Home.do?type=AllSubject&userid=${userid}"
			class="btn btn-info"><span class="glyphicon glyphicon-pencil"></span>&nbsp;答题记录</a>
	</c:if>
	<!-- 答题记录-->
	<c:if test="${type!='AllSubject'}">
		<a href="webuser/Home.do?type=AllSubject&userid=${userid}"
			class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span>&nbsp;答题记录</a>
	</c:if>
	<!-- 错题记录-->
	<c:if test="${type=='ErrorSubject'}">
		<a href="webuser/Home.do?type=ErrorSubject&userid=${userid}"
			class="btn btn-info"><span
			class="glyphicon glyphicon-remove-sign"></span>&nbsp;错题记录</a>
	</c:if>
	<!-- 错题记录-->
	<c:if test="${type!='ErrorSubject'}">
		<a href="webuser/Home.do?type=ErrorSubject&userid=${userid}"
			class="btn btn-default"><span
			class="glyphicon glyphicon-remove-sign"></span>&nbsp;错题记录</a>
	</c:if>
	<!-- 收藏-->
	<c:if test="${type=='BookSubject'}">
		<a href="webuser/Home.do?type=BookSubject&userid=${userid}"
			class="btn btn-info"><span class="glyphicon glyphicon-star"></span>&nbsp;收藏题目</a>
	</c:if>
	<!-- 收藏-->
	<c:if test="${type!='BookSubject'}">
		<a href="webuser/Home.do?type=BookSubject&userid=${userid}"
			class="btn btn-default"><span class="glyphicon glyphicon-star"></span>&nbsp;收藏题目</a>
	</c:if>
	<c:if test="${type=='usermessage'}">
		<c:if test="${self}">
			<a href="webuser/Home.do?type=usermessage&userid=${userid}"
				class="btn btn-info"><span class="glyphicon glyphicon-envelope"></span>
				我的消息</a>
		</c:if>
	</c:if>
	<c:if test="${type!='usermessage'}">
		<c:if test="${self}">
			<a href="webuser/Home.do?type=usermessage&userid=${userid}"
				class="btn btn-default"><span
				class="glyphicon glyphicon-envelope"></span> 我的消息</a>
		</c:if>
	</c:if>
</div>
<div class="btn-group-vertical btn-group-sm" role="group"
	aria-label="..." style="width: 150px; margin-top: 8px;">
	<!-- 答卷记录-->
	<c:if test="${type=='AllPaper'}">
		<a href="webuser/Home.do?type=AllPaper&userid=${userid}"
			class="btn btn-info"><span class="glyphicon glyphicon-file"></span>&nbsp;答卷记录</a>
	</c:if>
	<!-- 答卷记录-->
	<c:if test="${type!='AllPaper'}">
		<a href="webuser/Home.do?type=AllPaper&userid=${userid}"
			class="btn btn-default"><span class="glyphicon glyphicon-file"></span>&nbsp;答卷记录</a>
	</c:if>
	<!-- 收藏答卷-->
	<c:if test="${type=='BookPaper'}">
		<a href="webuser/Home.do?type=BookPaper&userid=${userid}"
			class="btn btn-info"><span class="glyphicon glyphicon-book"></span>&nbsp;收藏答卷</a>
	</c:if>
	<!-- 收藏答卷-->
	<c:if test="${type!='BookPaper'}">
		<a href="webuser/Home.do?type=BookPaper&userid=${userid}"
			class="btn btn-default"><span class="glyphicon glyphicon-book"></span>&nbsp;收藏答卷</a>
	</c:if>
</div>