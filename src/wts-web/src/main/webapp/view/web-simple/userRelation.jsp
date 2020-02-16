<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账号绑定-<PF:ParameterValue key="config.sys.title" />
</title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="atext/include-web.jsp"></jsp:include>
</head>
<body>
	<script type="text/javascript">
		if (window != top) {
			$("#loginId").html(
					'<div style="font-size:25px;text-align:center;">'
							+ "无法在iframe中实现登录操作，正在跳转中。。。" + '<//div>');
			top.location.href = "login/webPage.html";
		}
	</script>
	<jsp:include page="commons/head.jsp"></jsp:include>
	<jsp:include page="commons/superContent.jsp"></jsp:include>
	<div class="containerbox" style="background-color: #fff;">
		<div class="container ">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="panel panel-default userbox"
						style="margin: auto; margin-top: 30px; margin-bottom: 100px; background-color: #fcfcfc;">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-12 text-center">
									<h1 style="color: #666; font-size: 16px;">${systitle}"${outusername}"用户账号绑定</h1>
								</div>
							</div>
							<div style="margin-top: 20px;">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" class="active"><a href="#home"
										id="oldlinkId" aria-controls="home" role="tab"
										data-toggle="tab">绑定老用户</a></li>
									<PF:IfParameterEquals key="config.show.out.regist.able" val="true">
										<li role="presentation"><a href="#profile" id="newlinkId"
											aria-controls="profile" role="tab" data-toggle="tab">创建新用户</a></li>
									</PF:IfParameterEquals>
								</ul>
								<div class="tab-content"
									style="padding: 16px; background-color: #ffffff; border: solid 1px #dddddd; border-top: solid 0px;">
									<div role="tabpanel" class="tab-pane active" id="home">
										<div class="row">
											<div class="col-sm-12">
												<form class="form-signin" role="form" id="bindOldFormId"
													action="login/PubbindOldUser.do" method="post">
													<div class="form-group">
														<label for="exampleInputEmail1"> 绑定用户登录名 </label> 
														<input type="text" class="form-control" placeholder="手机/邮箱/用户登录名"  style="margin-top: 4px;" id="loginNameId" name="name" required autofocus>
													</div>
													<div class="form-group">
														<label for="exampleInputEmail1"> 绑定用户登录密码 </label> <input
															type="password" class="form-control" placeholder="请录入密码"
															style="margin-top: 4px;" id="loginPassWId"
															name="password" required>
													</div>
													<PF:IfParameterEquals key="config.sys.verifycode.able"
														val="true">
															<div class="form-group">
																<label for="exampleInputEmail1">验证码</label>
																<div class="input-group">
																	<input type="text" class="form-control"
																		placeholder="请录入验证码" id="checkcodeId" name="checkcode">
																	<div class="input-group-addon" style="padding: 0px;">
																		<img id="checkcodeimgoldId"
																			style="cursor: pointer; height: 30px; width: 100px;" />
																	</div>
																</div>
															</div>
													</PF:IfParameterEquals>
													<input type="hidden" name="outuserid" value="${outuserid}">
													<input type="hidden" name="outusername"
														value="${outusername}">
													<div>
														<button class="btn  btn-primary text-left"
															id="bindOldButtonId" style="margin-top: 4px;"
															type="button">立即绑定</button>
													</div>
												</form>
											</div>
										</div>
									</div>
									<PF:IfParameterEquals key="config.show.out.regist.able" val="true">
										<div role="tabpanel" class="tab-pane" id="profile">
											<div class="row">
												<div class="col-sm-12">
													<form class="form-signin" role="form" id="bindNewFormId"
														action="login/PubbindNewUser.do" method="post">
														<div class="form-group">
															<label for="exampleInputEmail1">用户登录ID(登录名)</label>
															<input type="text" class="form-control" placeholder="请录入登录ID" style="margin-top: 4px;"  id="loginnameId" name="loginname" required autofocus> 
														</div>
														<PF:IfParameterEquals key="config.sys.verifycode.able"
															val="true">
																<div class="form-group">
																	<label for="exampleInputEmail1">验证码</label>
																	<div class="input-group">
																		<input type="text" class="form-control"
																			placeholder="请录入验证码" id="newcheckcodeId"
																			name="checkcode">
																		<div class="input-group-addon" style="padding: 0px;">
																			<img id="checkcodeimgnewId"
																				style="cursor: pointer; height: 30px; width: 100px;" />
																		</div>
																	</div>
																</div>
														</PF:IfParameterEquals>
														<input type="hidden" name="outuserid" value="${outuserid}">
														<input type="hidden" name="photourl" value="${photourl}">
														<input type="hidden" name="orgid" value="${orgid}">
														<input type="hidden" name="outusername"
															value="${outusername}">
														<div>
															<button class="btn btn-primary text-left"
																id="bindNewButtonId" style="margin-top: 4px;"
																type="button">创建新用户</button>
														</div>
													</form>
												</div>
											</div>
										</div>
									</PF:IfParameterEquals>
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
						</div>
					</div>
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</div>
	<jsp:include page="commons/footServer.jsp"></jsp:include>
	<jsp:include page="commons/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function() {
		$('#alertMessageErrorId').hide();
		$('#bindOldButtonId')
				.bind(
						'click',
						function() {
							//校验验证码
							if ($('#checkcodeId').length > 0
									&& !$('#checkcodeId').val()) {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入验证码');
								return;
							}
							if ($('#loginNameId').val()
									&& $('#loginPassWId').val()) {
								$('#bindOldFormId').submit();
							} else {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入用户名/密码');
							}
						});
		$('#bindNewButtonId')
				.bind(
						'click',
						function() {
							//校验验证码
							if ($('#newcheckcodeId').length > 0
									&& !$('#newcheckcodeId').val()) {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入验证码');
								return;
							}
							//校验登录名
							if (!$('#loginnameId').val()) {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入用户登录ID');
								return;
							}
							if ($('#loginnameId').val().length>16) {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>用户登录ID超长，需要小于16个字符');
								return;
							}
							$('#bindNewFormId').submit();
						});
	});
	$(function() {
		//初始验证码
		$('#checkcodeimgoldId').attr("src",
				"actionImg/Pubcheckcode.do?time=" + new Date());
		$('#checkcodeimgoldId').bind(
				"click",
				function(e) {
					$('#checkcodeimgoldId').attr("src",
							"actionImg/Pubcheckcode.do?time=" + new Date());
				});
		$('#oldlinkId').bind(
				"click",
				function(e) {
					$('#alertMessageErrorId').html('');
					$('#checkcodeimgoldId').attr("src",
							"actionImg/Pubcheckcode.do?time=" + new Date());
				});
		$('#newlinkId').bind(
				"click",
				function(e) {
					$('#alertMessageErrorId').html('');
					$('#checkcodeimgnewId').attr("src",
							"actionImg/Pubcheckcode.do?time=" + new Date());
				});
		$('#checkcodeimgnewId').bind(
				"click",
				function(e) {
					$('#checkcodeimgnewId').attr("src",
							"actionImg/Pubcheckcode.do?time=" + new Date());
				});
	});
</script>
</html>