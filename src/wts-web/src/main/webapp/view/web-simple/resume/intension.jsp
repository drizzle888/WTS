<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-职业规划-<PF:ParameterValue
		key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
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
					<h3>${USEROBJ.name}-职业规划<a
							class="resum_button btn btn-info btn-sm"
							href="resume/view.do?userid=${USEROBJ.id}">返回</a>
					</h3>
				</div>
			</div>
			<div class="row " style="margin-top: 4px;">
				<div class="widget-box shadow-box" style="min-width: 800px;">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>
							职业规划
						</h3>
					</div>
					<div class="stream-list p-stream">
						<form role="form" action="resume/intensionCommit.do" id="SubmitFormId"
							method="post">
							<input type="hidden" name="id" value="${resumeintension.id}">
							<table class="table table-striped resum_unit" style="font-size: 12px;">
								<tr>
									<td class="resum_title">期望工作性质<span class="alertMsgClass">*</span></td>
									<td>
										<select class="form-control" name="worknature"
											id="worknatureid" val="${resumeintension.worknature}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_WORKNATURE" isTextValue="0" />
										</select>
									</td>
									<td class="resum_title">期望工作地点</td>
									<td>
										<input type="text" class="form-control" id="workaddrid"
											name="workaddr" value="${resumeintension.workaddr}">
									</td>
									<td class="resum_title">期望从事职业</td>
									<td>
										<select class="form-control" name="workoccupation"
											id="workoccupationid" val="${resumeintension.workoccupation}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_WORKOCCUPATION" isTextValue="0" />
										</select>
									</td>
								</tr>
								<tr>
									<td class="resum_title">期望从事行业</td>
									<td>
										<select class="form-control" name="workindustry"
											id="workindustryid" val="${resumeintension.workindustry}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_WORKINDUSTRY" isTextValue="0" />
										</select>
									</td>
									<td class="resum_title">期望月薪(税前)</td>
									<td>
										<input type="text" class="form-control" id="workpayid"
											name="workpay" value="${resumeintension.workpay}">
									</td>
									<td class="resum_title">工作状态</td>
									<td>
										<select class="form-control" name="workstat"
											id="workstatid" val="${resumeintension.workstat}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_WORKSTAT" isTextValue="0" />
										</select>
									</td>
								</tr>
								<tr>
									<td class="resum_title">备注</td>
									<td colspan="5">
										<textarea id="pcontentid" name="pcontent" class="form-control"
											style="height: 90px; width: 100%;">${resumeintension.pcontent}</textarea>
									</td>
								</tr>
								<tr>
									<td colspan="6" style="text-align: center;">
										<button type="button" id="SubmitButtonId" class="btn btn-info btn-lg">提交</button>
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
	$(function() {
		if (operatortype == '2') {
			alert('保存成功!');
		}
		$('select', '#SubmitFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		
		//期望工作性质
		validateInput('worknatureid', function(id, val, obj) {
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
		//期望月薪(税前)
		validateInput('workpayid', function(id, val, obj) {
			if (!valid_isNull(val) && isNaN(val)) {
				return {
					valid : false,
					msg : '不是数字'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//备注
		validateInput('pcontentid', function(id, val, obj) {
			if (valid_maxLength(val, 64)) {
				return {
					valid : false,
					msg : '长度不能大于' + 64
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