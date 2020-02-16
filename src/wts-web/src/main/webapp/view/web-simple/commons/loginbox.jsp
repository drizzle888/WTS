<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:if test="${USEROBJ==null}">
	<div class="row">
		<div class="col-xs-12 text-center">
			<h1 style="color: #666;font-size: 1.5em;"><PF:ParameterValue key="config.sys.title" />登录</h1>
			<hr />
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
			<div class="third-part tracking-ad  hidden-xs">
				<div class="text-center">
					<img src="<PF:basePath/>actionImg/PubHomelogo.do" alt="wcp"
						style="margin: 20px; max-width: 128px;" /> 
					<hr />
					<br/>
				</div>
				<!-- <span>第三方账号登录</span> <a href="qq/PubLogin.html" class="qq"></a>-->
			</div>
			<c:if test="${STATE=='1'}">
				<div class="text-center" id="romovealertMessageErrorId"
					style="margin: 4px; color: red;">
					<span class="glyphicon glyphicon-exclamation-sign"></span>
					${MESSAGE}
				</div>
			</c:if>
			<div class="text-center" id="alertMessageErrorId"
				style="margin: 4px; color: red;"></div>
		</div>
		<div class="col-sm-6" style="padding: 26px; padding-top: 4px;">
			<form class="form-signin" role="form" id="loginFormId"
				action="login/websubmit.do" method="post">
				<div class="form-group">
					<label for="exampleInputEmail1"> 登录名 </label> <input type="text"
						class="form-control" placeholder="手机/邮箱/用户登录名"
						value="${loginname}" style="margin-top: 4px;" id="loginNameId"
						name="name" required autofocus>
				</div>
				<div class="form-group">
					<label for="exampleInputEmail1"> 登录密码 </label> <input
						type="password" class="form-control" placeholder="请录入密码"
						style="margin-top: 4px;" id="loginPassWId" name="password"
						required>
				</div>
				<PF:IfParameterEquals key="config.sys.verifycode.able" val="true">
					<c:if test="${sessionScope.LOGINERRORNUM<=0}">
						<div class="form-group">
							<label for="exampleInputEmail1">验证码</label>
							<div class="input-group">
								<input type="text" class="form-control" placeholder="请录入验证码"
									id="checkcodeId" name="checkcode">
								<div class="input-group-addon" style="padding: 0px;">
									<img id="checkcodeimgId"
										style="cursor: pointer; height: 30px; width: 100px;"
										src="actionImg/Pubcheckcode.do" />
								</div>
							</div>
						</div>
					</c:if>
				</PF:IfParameterEquals>
				<!-- <input type="hidden" name="url" id="loginUrlId"> -->
				<div>
					<button class="btn  btn-primary text-left" id="loginButtonId"
						style="margin-top: 4px;" type="button">登录</button>
					<PF:IfParameterEquals key="config.show.local.regist.able" val="true">
						<a class="hidden-xs"
							style="float: right; text-decoration: underline; margin-top: 12px; margin-right: 8px;"
							type="button" href="webuser/PubRegist.do"> 注册 </a>
					</PF:IfParameterEquals>
				</div> 
			</form>
		</div>
	</div>
</c:if>
<jsp:include page="loginedShortMenu.jsp"></jsp:include>
<PF:IfParameterEquals key="config.sys.mode.debug" val="true">
<script type="text/javascript">
$(function() {
	$('#loginNameId').val('sysadmin');
	$('#loginPassWId').val('111111');
});
</script>
</PF:IfParameterEquals>
<script type="text/javascript">
	$(function() {
		$('#alertMessageErrorId').hide();
		$('#loginButtonId')
				.bind(
						'click',
						function() {
							//校验验证码
							if($('#checkcodeId').length>0&&!$('#checkcodeId').val()){
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入验证码');
								return;
							}
							if ($('#loginNameId').val()
									&& $('#loginPassWId').val()) {
								//$('#loginUrlId').val(document.referrer);
								$('#loginButtonId').addClass("disabled");
								$('#loginButtonId').text("提交中...");
								$('#loginFormId').submit();
							} else {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入用户名/密码');
							}
						});
		$('#checkcodeimgId').bind(
				"click",
				function(e) {
					$('#checkcodeimgId').attr("src",
							"actionImg/Pubcheckcode.do?time=" + new Date().getTime());
				});
		$('#loginNameId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
		$('#loginPassWId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
		$('#checkcodeId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
	});
//-->
</script>