<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>个人信息-<PF:ParameterValue key="config.sys.title" /></title>
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
		<div class="container">
			<div class="row  column_box">
				<div class="col-sm-3  "></div>
				<div class="col-sm-6">
					<div class="row">
						<div class="col-sm-12" style="margin-bottom: 8px;">
							<h4>
								<span style="color: #D9534F;"
									class="glyphicon glyphicon-user   ">用户注册</span>
							</h4>
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
							<form role="form" action="webuser/editCurrentUser.do"
								id="registSubmitFormId" method="post">
								<input type="hidden" name="id" value="${user.id }" />
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="exampleInputEmail1">
												用户ID&nbsp;&nbsp;:&nbsp;&nbsp;${user.loginname } </label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="exampleInputEmail1"> 头像 <span
												class="alertMsgClass">*</span>
											</label>
											<div class="row">
												<div class="col-md-9">
													<c:if test="${photourl!=null}">
														<img id="imgEditBoxId" src="${photourl}" alt="头像"
															class="img-thumbnail"
															style="width: 200px; height: 200px;" />
														<img id="imgEditBoxId2" src="${photourl}" alt="头像"
															class="img-thumbnail" style="width: 64px; height: 64px;" />
													</c:if>
													<c:if test="${photourl==null}">
														<img id="imgEditBoxId"
															src="<PF:basePath/>text/img/none.png" alt="头像"
															class="img-thumbnail"
															style="width: 200px; height: 200px;" />
														<img id="imgEditBoxId2"
															src="<PF:basePath/>text/img/none.png" alt="头像"
															class="img-thumbnail" style="width: 64px; height: 64px;" />
													</c:if>
													<div style="padding: 20px; padding-left: 60px;">
														<input type="text" name="photoid" id="photoid"
															value="${photoid}" style="width: 0px; border: 0px;"
															readonly="readonly" /> <input type="button"
															id="uploadButton" value="上传头像" />
													</div>
												</div>
												<div class="col-md-3"></div>
											</div>
										</div>
									</div>
								</div>
								<PF:IfParameterEquals key="config.useredit.showName" val="true">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label for="exampleInputEmail1"> 姓名 <span
													class="alertMsgClass">*</span>
												</label>
												<div class="row">
													<div class="col-md-9">
														<input type="text" class="form-control" name="name"
															id="nameid" placeholder="输入真实姓名" value="${name}" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</PF:IfParameterEquals>
								<!--  因为没有引入个人简历，所以无法保存用户邮箱
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="exampleInputEmail1"> email <span
												class="alertMsgClass">*</span>
											</label>
											<div class="row">
												<div class="col-md-9">
													<div class="input-group">
														<div class="input-group-btn">
															<button type="button"
																class="btn btn-default dropdown-toggle"
																data-toggle="dropdown" aria-haspopup="true"
																aria-expanded="false">
																邮箱类型<span class="caret"></span>
															</button>
															<ul id="emailTypeId"
																class="dropdown-menu dropdown-menu-right">
																<  ! -- 数据字典中配置 USERINFO_EMAILTAG，value将作为此处展示内容--   >
																<PF:DictionaryHandle var="node" key="USERINFO_EMAILTAG">
																	<li><a>${node.value}</a></li>
																</PF:DictionaryHandle>
																<li><a>@126.com</a></li>
																<li><a>@163.com</a></li>
																<li><a>@qq.com</a></li>
																<li><a>@hotmail.com</a></li>
																<li><a>@gmail.com</a></li>
															</ul>
														</div>
														<input type="text" class="form-control" name="email"
															id="emailid" placeholder="输入email"
															value="${resumebase.emailcode}" />
													</div>
													<div id="emailid_lableId"></div>
												</div>
												<div class="col-md-3"></div>
											</div>
										</div>
									</div>
								</div> -->
								<PF:IfParameterEquals key="config.useredit.showOrg" val="true">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label for="exampleInputEmail1"> 组织机构 <span
													class="alertMsgClass">*</span>
												</label>
												<div class="row">
													<div class="col-md-9">
														<input type="text" class="form-control" id="orgnameId"
															readonly="readonly" placeholder="选择机构"
															value="${org.name}" /> <input type="hidden" name="orgid"
															id="orgid" value="${org.id}" />
													</div>
													<div class="col-md-3">
														<button class="btn btn-info btn-sm" data-toggle="modal"
															id="openChooseTypeButtonId" data-target="#myModal">
															选择</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</PF:IfParameterEquals>
								<div class="row">
									<div class="col-md-12">
										<button type="button" id="registSubmitButtonId"
											class="btn btn-primary btn-lg">提交用户信息</button>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<span class="alertMsgClass" id="errormessageShowboxId"></span>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="col-sm-3  "></div>
			</div>
			<br /> <br /> <br /> <br /> <br />
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<jsp:include page="../user/commons/includePubOrg.jsp"></jsp:include>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<script type="text/javascript">
		$(function() {
			$('#registSubmitButtonId').bind('click', function() {
				if (!validate('registSubmitFormId')) {
					$('#errormessageShowboxId').text('信息录入有误，请检查！');
				} else {
					if (confirm("是否提交数据?")) {
						$('#registSubmitButtonId').addClass("disabled");
						$('#registSubmitButtonId').text("提交中...");
						$('#registSubmitFormId').submit();
					}
				}
			});
			KindEditor.ready(function(K) {
				var uploadbutton = K.uploadbutton({
					button : K('#uploadButton'),
					fieldName : 'imgFile',
					url : basePath + 'actionImg/PubFPuploadImg.do',
					afterUpload : function(data) {
						if (data.error === 0) {
							$('#imgEditBoxId').attr('src', data.url);
							$('#imgEditBoxId2').attr('src', data.url);
							$('#photoid').val(data.id);
						} else {
							alert(data.message);
						}
					},
					afterError : function(str) {
						alert('自定义错误信息: ' + str);
					}
				});
				uploadbutton.fileBox.change(function(e) {
					uploadbutton.submit();
				});
			});
			validateInput('nameid', function(id, val, obj) {
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
						msg : '不能大于16个字符'
					};
				}
				return {
					valid : true,
					msg : '正确'
				};
			});
			validateInput('photoid', function(id, val, obj) {
				// 用户名
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
			validateInput('orgnameId', function(id, val, obj) {
				// 组织机构
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
			validateInput('emailid', function(id, val, obj) {
				if (valid_isNull(val)) {
					return {
						valid : false,
						msg : '不能为空'
					};
				}
				if (valid_maxLength(val, 32)) {
					return {
						valid : false,
						msg : '长度不能大于' + 32
					};
				}
				if (!isEmail(val)) {
					return {
						valid : false,
						msg : '格式不正确'
					};
				}
				return {
					valid : true,
					msg : '正确'
				};
			}, 'emailid_lableId');

			$('#openChooseTypeButtonId').bind('click', function() {
				$('#myModal').modal({
					keyboard : false
				})
			});
			$('#emailTypeId a').bind('click', function() {
				$('#emailid').val($(this).text());
			});

		});
	</script>
</body>
</html>