<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>密码修改-<PF:ParameterValue key="config.sys.title" />
</title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<script charset="utf-8"
	src="<PF:basePath/>/text/lib/super-validate/validate.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>/text/lib/kindeditor/kindeditor-all-min.js"></script>
<link rel="stylesheet"
	href="<PF:basePath/>/text/lib/kindeditor/themes/default/default.css" />
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row column_box">
				<div class="col-sm-3 "></div>
				<div class="col-sm-6">
					<div class="row">
						<div class="col-sm-12" style="margin-bottom: 8px;">
							<span style="color: #D9534F;" class="glyphicon glyphicon-user  ">密码修改</span>
							<hr />
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<c:if test="${pageset.commitType=='1'}">
								<div class="alert alert-danger">${pageset.message}</div>
							</c:if>
							<c:if test="${pageset.commitType=='0'}">
								<div class="alert alert-success">修改成功</div>
							</c:if>
							<form role="form" action="webuser/editCurrentUserPwdCommit.do"
								id="registSubmitFormId" method="post">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="exampleInputEmail1"> 当前密码 <span
												class="alertMsgClass">*</span>
											</label>
											<div class="row">
												<div class="col-md-9">
													<input type="password" class="form-control" name="password"
														id="passwordId" placeholder="输入当前密码" />
												</div>
												<div class="col-md-3"></div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="exampleInputEmail1"> 新密码 <span
												class="alertMsgClass">*</span>
											</label>
											<div class="row">
												<div class="col-md-9">
													<input type="password" id="newPasswordId1"
														class="form-control" name="newPassword"
														placeholder="输入新密码" />
												</div>
												<div class="col-md-3"></div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="exampleInputEmail1"> 重复新密码 <span
												class="alertMsgClass">*</span>
											</label>
											<div class="row">
												<div class="col-md-9">
													<input type="password" id="newPasswordId2"
														class="form-control" placeholder="重复输入新密码" />
												</div>
												<div class="col-md-3"></div>
											</div>
										</div>
									</div>
								</div>
								<PF:IfParameterEquals key="config.sys.verifycode.able"
									val="true">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label for="exampleInputEmail1">验证码 <span
													class="alertMsgClass">*</span></label>
												<div class="input-group"
													style="width: 200px; text-align: left;">
													<input type="text" class="form-control" placeholder="验证码"
														id="checkcodeId" name="checkcode"
														style="float: left; width: 100px;"><img
														id="checkcodeimgId"
														style="cursor: pointer; height: 35px; width: 100px; float: right;"
														src="actionImg/Pubcheckcode.do" />
												</div>
											</div>
										</div>
									</div>
									<script type="text/javascript">
										$(function() {
											validateInput('checkcodeId',
													function(id, val, obj) {
														if (valid_isNull(val)) {
															return {
																valid : false,
																msg : '不能为空'
															};
														}
														return {
															valid : true,
															msg : '正确'
														};
													});
										});
									</script>
								</PF:IfParameterEquals>
								<div class="row">
									<div class="col-md-12">
										<button type="button" id="registSubmitButtonId"
											class="btn btn-primary btn-lg">提交</button>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<span class="alertMsgClass" id="errormessageShowboxId"></span>
									</div>
								</div>
								<hr/>
							</form>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12" style="color: red;">${MESSAGE}</div>
					</div>
				</div>
				<div class="col-sm-3 "></div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
	<script type="text/javascript">
		$(function() {
			$('#registSubmitButtonId').bind('click', function() {
				if (!validate('registSubmitFormId')) {
					$('#errormessageShowboxId').text('信息录入有误，请检查！');
				} else {
					if (confirm("是否提交数据?")) {
						$('#registSubmitFormId').submit();
						$('#registSubmitButtonId').addClass("disabled");
						$('#registSubmitButtonId').text("提交中...");
					}
				}
			});
			$('#checkcodeimgId')
					.bind(
							"click",
							function(e) {
								$('#checkcodeimgId').attr(
										"src",
										"actionImg/Pubcheckcode.do?time="
												+ new Date().getTime());
							});
			validateInput('passwordId', function(id, val, obj) {
				// 登录名
				if (valid_isNull(val)) {
					return {
						valid : false,
						msg : '不能为空'
					};
				}
				if (!valid_maxLength(val, 4 - 1)) {
					return {
						valid : false,
						msg : '不能小于4个字符'
					};
				}
				if (valid_maxLength(val, 16)) {
					return {
						valid : false,
						msg : '不能大于16个字符'
					};
				}

				var validCurrentUserPwd = false;
				$.ajax({
					type : "post",
					url : "webuser/validCurrUserPwd.do?password=" + val,
					dataType : "json",
					async : false,
					success : function(obj) {
						validCurrentUserPwd = obj.validCurrentUserPwd;
					}
				});
				if (!validCurrentUserPwd) {
					return {
						valid : false,
						msg : '当前密码错误'
					};
				}

				return {
					valid : true,
					msg : '正确'
				};
			});
			validateInput('newPasswordId1', function(id, val, obj) {
				// 用户名
				if (valid_isNull(val)) {
					return {
						valid : false,
						msg : '不能为空'
					};
				}
				if (!valid_maxLength(val, 2 - 1)) {
					return {
						valid : false,
						msg : '不能小于2个字符'
					};
				}
				if (valid_maxLength(val, 16)) {
					return {
						valid : false,
						msg : '不能大于32个字符'
					};
				}
				try{
					var regex = new RegExp(/${config_sys_password_update_regex}/);
					if (!regex.test(val)){
						return {
							valid : false,
							msg : '${config_sys_password_update_tip}'
						};
					}
				}catch(e){
					//正则表达式验证失败
					alert(e);
				}
				return {
					valid : true,
					msg : '正确'
				};
			});
			validateInput('newPasswordId2', function(id, val, obj) {
				// 重录密码
				if (valid_isNull(val)) {
					return {
						valid : false,
						msg : '不能为空'
					};
				}
				if ($('#newPasswordId1').val() != $('#newPasswordId2').val()) {
					return {
						valid : false,
						msg : '两次密码输入不一样'
					};
				}
				return {
					valid : true,
					msg : '正确'
				};
			});
		});
	</script>
</body>
</html>