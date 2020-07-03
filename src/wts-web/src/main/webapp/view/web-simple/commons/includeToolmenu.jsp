<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<ul class="nav navbar-nav navbar-right" style="margin-right: 10px;">
	<!-- 登录注册 -->
	<c:if test="${USEROBJ==null}">
		<li><a href="login/webPage.html"><span
				class="glyphicon glyphicon glyphicon-user"></span> 登录<PF:IfParameterEquals
					key="config.show.local.regist.able" val="true">/注册</PF:IfParameterEquals></a></li>
	</c:if>
	<c:if test="${USEROBJ!=null}">
		<li title="显示服务器时间"><a href="javascript:showServerTime()"><span
				class="glyphicon glyphicon-dashboard"></span>&nbsp;服务器时间</a></li>
		<!-- 登录後菜單 -->
		<li class="dropdown	"><a class="dropdown-toggle"
			data-toggle="dropdown" role="button" aria-haspopup="true"
			aria-expanded="false"> <span class="glyphicon glyphicon-user"></span>
				${USEROBJ.name}<span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="webuser/Home.do"><span
						class=" glyphicon glyphicon-list-alt"></span>&nbsp;我的空间</a></li>
				<c:if test="${USEROBJ!=null&&!empty USERMENU}">
					<li class="hidden-xs"><a href="frame/index.do"><span
							class="glyphicon glyphicon-cog"></span>&nbsp;管理控制台</a></li>
				</c:if>
				<li><a href="login/webout.do"><span
						class="glyphicon glyphicon-off"></span>&nbsp;注销</a></li>
			</ul></li>
	</c:if>

</ul>
<script type="text/javascript">
	function showServerTime() {
		$.post('home/PubCTime.do', {}, function(flag) {
			alert("当前服务器时间为："+flag.time);
		}, 'json');
	}
</script>