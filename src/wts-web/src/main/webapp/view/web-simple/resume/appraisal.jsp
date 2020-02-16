<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-自我评价-<PF:ParameterValue
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
	<jsp:include page="../commons/head.jsp"></jsp:include>`
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row  column_box">
				<div class="col-md-12">
					<h3>${USEROBJ.name}-自我评价<a
							class="resum_button btn btn-info btn-sm"
							href="resume/view.do?userid=${USEROBJ.id}">返回</a>
					</h3>
				</div>
			</div>
			<div class="row " style="margin-top: 4px;">
				<div class="widget-box shadow-box" style="min-width: 800px;">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>自我评价
						</h3>
					</div>
					<div class="stream-list p-stream">
						<form role="form" action="resume/appraisalCommit.do"
							id="SubmitFormId" method="post">
							<input type="hidden" name="id" value="${resumeappraisal.id}">
							<table class="table resum_unit" style="font-size: 12px;">
								<tr>
									<td class="resum_title">自我评价</td>
									<td><textarea id="appraisalid" name="appraisal"
											class="form-control" style="height: 90px; width: 100%;">${resumeappraisal.appraisal}</textarea>
									</td>
								</tr>
								<tr>
									<td class="resum_title">职业目标</td>
									<td><textarea id="careergoalsid" name="careergoals"
											class="form-control" style="height: 90px; width: 100%;">${resumeappraisal.careergoals}</textarea>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: center;">
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
		//自我评价
		validateInput('appraisalid', function(id, val, obj) {
			if (valid_maxLength(val, 256)) {
				return {
					valid : false,
					msg : '长度不能大于' + 256
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//职业目标
		validateInput('careergoalsid', function(id, val, obj) {
			if (valid_maxLength(val, 256)) {
				return {
					valid : false,
					msg : '长度不能大于' + 256
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