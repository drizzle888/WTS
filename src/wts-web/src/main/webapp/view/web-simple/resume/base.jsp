<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-基本信息-<PF:ParameterValue
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
					<h3>${USEROBJ.name}-基本信息<a
							class="resum_button btn btn-info btn-sm"
							href="resume/view.do?userid=${USEROBJ.id}">返回</a>
					</h3>
				</div>
			</div>
			<div class="row " style="margin-top: 4px;">
				<div class="widget-box shadow-box" style="min-width: 800px;">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>基本信息
						</h3>
					</div>
					<div class="stream-list p-stream">
						<form role="form" action="resume/baseCommit.do" id="SubmitFormId"
							method="post">
							<input type="hidden" name="id" value="${base.id}">
							<table class="table table-striped  resum_unit"
								style="font-size: 12px;">
								<tr>
									<td class="resum_title">性别</td>
									<td style="min-width: 200px;"><select class="form-control"
										name="sex" id="sexid" val="${base.sex}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_SEX" isTextValue="0" />
									</select></td>
									<td class="resum_title">海外工作/学习经历</td>
									<td><select class="form-control" name="studyabroad"
										id="studyabroadid" val="${base.studyabroad}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_STUDYABROAD"
												isTextValue="0" />
									</select></td>
									<td rowspan="6"
										style="width: 220px; border-left: 1px solid #eeeeee;">
										<div class=" resum_unit"
											style="font-size: 12px; text-align: center; ">
											<div class="panel-heading center-block webfile-buttonplus"
												style="height: 50px; ">
												<input type="button"
													class="btn btn-info btn-xs center-block"
													style="padding: 0px;" id="uploadButton" value="上传照片" />
											</div>
											<input type="hidden" id="photoidid" name="photoid"
												value="${base.photoid}">
										</div>
										<div id="phoneBoxId">
											<c:if test="${base.photoid!=null&&base.photoid!=''}">
												<img src="${photourl}" alt="照片"
													class="img-thumbnail phoneimg">
											</c:if>
											<c:if test="${base.photoid==null||base.photoid==''}">
												<img src="text/img/photo.png" alt="..." class="phoneimg">
											</c:if>
										</div>
									</td>
								</tr>
								<tr>
									<td class="resum_title">出生日期</td>
									<td>
										<div class="input-group ">
											<div class="input-group-addon">
												<span class="glyphicon glyphicon-calendar"
													aria-hidden="true"></span>
											</div>
											<input type="text" class="form-control form_day"
												name="birthday" id="birthdayid" value="${base.birthday}">
										</div>
										<div id="birthdayMsgId"></div>
									</td>
									<td class="resum_title">参加工作年份</td>
									<td><div class="input-group ">
											<div class="input-group-addon">
												<span class="glyphicon glyphicon-calendar"
													aria-hidden="true"></span>
											</div>
											<input type="text" class="form-control form_year"
												name="dateyear" id="dateyearid" value="${base.dateyear}">
										</div>
										<div id="dateyearMsgId"></div></td>
								</tr>
								<tr>
									<td class="resum_title">最高学历</td>
									<td><select class="form-control" name="degreemax"
										id="degreemaxid" val="${base.degreemax}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_DEGREE" isTextValue="0" />
									</select></td>
									<td class="resum_title">户口所在地</td>
									<td><input type="text" class="form-control"
										id="registeredid" name="registered" value="${base.registered}"></td>
								</tr>
								<tr>
									<td class="resum_title">居住所在地</td>
									<td><input type="text" class="form-control" id="livestrid"
										name="livestr" value="${base.livestr}"></td>
									<td class="resum_title">政治面貌</td>
									<td><select class="form-control" name="zzmm" id="zzmmid"
										val="${base.zzmm}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_ZZMM" isTextValue="0" />
									</select></td>
								</tr>
								<tr>
									<td class="resum_title">婚姻状况</td>
									<td><select class="form-control" name="marriagesta"
										id="marriagestaid" val="${base.marriagesta}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_MARRIAGESTA"
												isTextValue="0" />
									</select></td>
									<td class="resum_title">身份证号</td>
									<td><input type="text" class="form-control" id="idcodeid"
										name="idcode" value="${base.idcode}"></td>
								</tr>
								<tr>
									<td class="resum_title">其他证件类型</td>
									<td><select class="form-control" name="othertype"
										id="othertypeid" val="${base.othertype}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_OTHERTYPE" isTextValue="0" />
									</select></td>
									<td class="resum_title">其他证件编号</td>
									<td><input type="text" class="form-control" id="otheridid"
										name="otherid" value="${base.otherid}"></td>
								</tr>
								<tr>
									<td class="resum_title">国籍</td>
									<td><select class="form-control" name="nationality"
										id="nationalityid" val="${base.nationality}">
											<option value=""></option>
											<PF:OptionDictionary index="RESUME_NATIONALITY"
												isTextValue="0" />
									</select></td>
									<td class="resum_title"></td>
									<td></td><td></td>
								</tr>
								<tr>
									<td class="resum_title">备注</td>
									<td colspan="4"><textarea id="pcontentid" name="pcontent"
											style="height: 90px; width: 100%;">${base.pcontent}</textarea></td>
								</tr>
								<tr>
									<td colspan="5" style="text-align: center;"><button
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
	KindEditor
			.ready(function(K) {
				var uploadbutton = K
						.uploadbutton({
							button : K('#uploadButton')[0],
							fieldName : 'imgFile',
							url : basePath + 'actionImg/PubFPuploadImg.do',
							afterUpload : function(data) {
								$('#photoidid').val(data.id);
								$('#phoneBoxId')
										.html(
												'<img src="'+data.url+'" alt="..." class="img-thumbnail phoneimg">');
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
		$('select', '#SubmitFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		$(".form_day").datetimepicker({
			format : "yyyy-mm-dd",
			showMeridian : true,
			autoclose : true,
			todayBtn : true,
			minView : 2,
			startView : 3
		});
		$(".form_year").datetimepicker({
			format : "yyyy",
			showMeridian : true,
			autoclose : true,
			todayBtn : true,
			minView : 4,
			startView : 4
		});
		//性别
		validateInput('sexid', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
		//出生日期
		validateInput('birthdayid', function(id, val, obj) {
			if (valid_RQcheckIsError(val)) {
				return {
					valid : false,
					msg : '日期格式为YYYY-MM-DD'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'birthdayMsgId');
		//参加工作年份
		validateInput('dateyearid', function(id, val, obj) {
			if (valid_maxLength(val, 4)) {
				return {
					valid : false,
					msg : '长度不能大于' + 4
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'dateyearMsgId');
		
		//最高学历
		validateInput('degreemaxid', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
		//居住所在地
		validateInput('livestrid', function(id, val, obj) {
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
		//户口所在地
		validateInput('registeredid', function(id, val, obj) {
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
		//婚姻状况
		validateInput('marriagestaid', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
		//国籍
		validateInput('nationalityid', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
		//政治面貌
		validateInput('zzmmid', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
		//身份证号
		validateInput('idcodeid', function(id, val, obj) {
			if (val.length > 0 && !isShenFenZheng(val)) {
				return {
					valid : false,
					msg : '身份证格式错误'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//其他证件类型
		validateInput('othertypeid', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
		//其他证件编号
		validateInput('otheridid', function(id, val, obj) {
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
		//海外工作/学习经历
		validateInput('studyabroadid', function(id, val, obj) {
			return {
				valid : true,
				msg : '正确'
			};
		});
		//备注
		validateInput('pcontentid', function(id, val, obj) {
			if (valid_maxLength(val, 1000)) {
				return {
					valid : false,
					msg : '长度不能大于' + 1000
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