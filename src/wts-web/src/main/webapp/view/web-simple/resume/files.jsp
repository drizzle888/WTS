<%@page import="com.farm.parameter.FarmParameterService"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-简历附件-<PF:ParameterValue
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
<link rel="stylesheet"
	href="<PF:basePath/>view/web-simple/atext/style/web-forms.css" />
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container ">
			<div class="row  column_box">
				<div class="col-md-12">
					<h3>${USEROBJ.name}-简历附件<a
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
							简历附件&nbsp;&nbsp;&nbsp;&nbsp;
						</h3>
					</div>
					<div class="stream-list p-stream">
						<form role="form" action="resume/filesCommit.do" id="SubmitFormId"
							method="post">
							<table class="table resum_unit" style="font-size: 12px;">
								<tr>
									<td>
										<div class="panel-body" id="fileListId">
											<c:forEach var="farmDocfile" items="${farmDocfileList }">
												<div id="file_${farmDocfile.id }">
													<span>${farmDocfile.name }</span> &nbsp; <a
														href="${farmDocfile.url}" style="color: green;">下载</a> <a
														href="javascript:void(0)" style="color: green;"
														onclick="removeFile('${farmDocfile.id }');">删除</a>
													&nbsp;&nbsp;
												</div>
											</c:forEach>
										</div>
										<div class="panel-heading center-block webfile-buttonplus"
											style="height: 50px; width: 140px;">
											<div style="float: right;">
												<input type="button"
													class="btn btn-info btn-xs center-block"
													style="padding: 0px;" id="uploadButton" value="上传资源" />
											</div>
										</div>
										<div style="text-align: center;">
											<span
												style="color: green; font-size: 12px; font-weight: lighter;">允许上传附件类型为<PF:ParameterValue
													key="config.doc.upload.types" />，最大<%=Long.valueOf(FarmParameterService.getInstance().getParameter("config.doc.upload.length.max"))/1024/1024%>M
											</span>
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">
										<button type="button" id="SubmitButtonId" class="btn btn-info btn-lg">提交</button>
										<span class="alertMsgClass" id="errormessageShowboxId"></span>
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
	KindEditor
			.ready(function(K) {
				var uploadbutton = K
						.uploadbutton({
							button : K('#uploadButton')[0],
							fieldName : 'imgFile',
							url : basePath + 'actionImg/PubFPupload.do',
							afterUpload : function(data) {
								if (data.error === 0) {
									$('#fileListId').append(
											'<div id="file_'+data.id+'">');
									$('#file_' + data.id)
											.append(
													'<input type="hidden" name="fileId" value="'+data.id+'" />');
									$('#file_' + data.id)
											.append(
													'<span>'
															+ decodeURIComponent(data.fileName)
															+ '</span>');
									$('#file_' + data.id).append('&nbsp;');
									$('#file_' + data.id).append(
											'<a href="javascript:void(0)" style="color: green;" onclick="removeFile(\''
													+ data.id + '\');">删除</a>');
									$('#file_' + data.id)
											.append('&nbsp;&nbsp;');
									$('#file_' + data.id).append('</div>');
									$('#knowtitleId').val(
											decodeURIComponent(data.fileName));
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

	var operatortype = "${OPERATE}";
	$(function() {
		if (operatortype == '2') {
			alert('保存成功!');
		}
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

	//移除附件
	function removeFile(fileId) {
		if (!confirm("确认要删除？")) {
			return;
		}

		window.location = basePath +"resume/filesDel.do?fileId=" + fileId;
	}
</script>
</html>
