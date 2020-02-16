<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-通讯方式-<PF:ParameterValue
		key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/zh-CN.js"></script>
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-kindeditor.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-search-kindeditor.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/bootstrapdatepicker/bootstrap-datetimepicker.min.js"></script>
<link
	href="<PF:basePath/>text/lib/bootstrapdatepicker/bootstrap-datetimepicker.css"
	rel="stylesheet">
<link href="view/web-simple/atext/style/resume.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row  column_box">
				<div class="col-md-12">
					<h3>${USEROBJ.name}-通讯方式<a
							class="resum_button btn btn-info btn-sm"
							href="resume/view.do?userid=${USEROBJ.id}">返回</a>
					</h3>
				</div>
			</div>
			<div class="row " style="margin-top: 4px;">
				<div class="widget-box shadow-box" style="min-width: 800px;">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>通讯方式
						</h3>
					</div>
					<div class="stream-list p-stream">
						<form role="form" action="resume/addressCommit.do"
							id="SubmitFormId" method="post">
							<input type="hidden" name="id" value="${base.id}">
							<table class="table table-striped  resum_unit"
								style="font-size: 12px;">
								<tr>
									<td class="resum_title">电子邮箱<span class="alertMsgClass">*</span></td>
									<td><input type="text" class="form-control"
										id="emailcodeid" name="emailcode" value="${base.emailcode}"></td>

									<td class="resum_title">手机号码</td>
									<td><input type="text" class="form-control"
										id="mobilecodeid" name="mobilecode" value="${base.mobilecode}"></td>
								</tr>
								<tr>
									<td class="resum_title">qq号码</td>
									<td><input type="text" class="form-control" id="qqcodeid"
										name="qqcode" value="${base.qqcode}"></td>
									<td class="resum_title">微信号码</td>
									<td><input type="text" class="form-control" id="wxcodeid"
										name="wxcode" value="${base.wxcode}"></td>
								</tr>
								<tr>
									<td class="resum_title">电话号码</td>
									<td colspan="3"><input type="text" class="form-control"
										id="phonecodeid" name="phonecode" value="${base.phonecode}"></td>

								</tr>
								<tr>
									<td colspan="4" style="text-align: center;"><button
											type="button" id="SubmitButtonId" class="btn btn-info btn-lg">提交</button>
										<div style="margin-top: 4px;">
											<span class="alertMsgClass" id="errormessageShowboxId"></span>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>

<script type="text/javascript">
	var operatortype = "${OPERATE}";
	var operatorState = "${STATE}";
	//{MESSAGE=该手机号已经存在！, STATE=1, base=com.farm.resume.domain.Resumebase@6361a555, OPERATE=2}
	$(function() {
		if (operatortype == '2'&&operatorState=='0') {
			alert('保存成功!');
		}
		if (operatortype == '2'&&operatorState=='1') {
			alert('${MESSAGE}');
		}
		$('select', '#SubmitFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		//电子邮箱
		validateInput('emailcodeid', function(id, val, obj) {
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
		});
		//手机号码
		validateInput('mobilecodeid', function(id, val, obj) {
			if (!valid_isNumber(val)) {
				return {
					valid : false,
					msg : '手机号码必须为数字'
				};
			}
			if (valid_maxLength(val, 16)) {
				return {
					valid : false,
					msg : '长度不能大于' + 16
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//电话号码
		validateInput('phonecodeid', function(id, val, obj) {
			if (valid_maxLength(val, 32)) {
				return {
					valid : false,
					msg : '长度不能大于' + 32
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//qq号码
		validateInput('qqcodeid', function(id, val, obj) {
			if (valid_maxLength(val, 16)) {
				return {
					valid : false,
					msg : '长度不能大于' + 16
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//微信号码
		validateInput('wxcodeid', function(id, val, obj) {
			if (valid_maxLength(val, 16)) {
				return {
					valid : false,
					msg : '长度不能大于' + 16
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		$('#SubmitButtonId')
				.bind(
						'click',
						function() {
							if (!validate('SubmitFormId')) {
								$('#errormessageShowboxId').text('信息录入有误，请检查！');
							} else {
								if (confirm("是否提交数据?")) {
									$('#SubmitFormId').submit();
									$('#SubmitButtonId').addClass("disabled");			
									$('#SubmitButtonId').text("提交中...");
								}
							}
						});
	});
</script>

</html>