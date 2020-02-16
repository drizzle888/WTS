<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-工作经历-<PF:ParameterValue
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
					<h3>${USEROBJ.name}-工作经历<a
							class="resum_button btn btn-info btn-sm"
							href="resume/view.do?userid=${USEROBJ.id}">返回</a><a
							class="resum_button btn btn-info btn-sm"
							href="resume/workEdit.do?userid=${USEROBJ.id}&pagetype=1">新增</a>
					</h3>
				</div>
			</div>
			<div class="row " style="margin-top: 4px;">
				<div class="widget-box shadow-box" style="min-width: 800px;">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>教育经历
						</h3>
					</div>
					<div class="stream-list p-stream">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th style="width: 100px;">企业名称</th>
									<th>工作时间</th>
									<th>职位名称</th>
									<th>行业类别</th>
									<th>企业性质</th>
									<th>企业规模</th>
									<th>职位月薪(税前)</th>
									<th style="width: 200px; text-align: right;">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${works}" var="none">
									<tr>
										<td></td>
										<td>${none.name}</td>
										<td>${none.datestart}至${none.dateend}</td>
										<td>${none.postname}</td>
										<td>${none.type}</td>
										<td>${none.eproperty}</td>
										<td>${none.escale}</td>
										<td>${none.salary}</td>
										<td><a class="resum_button btn btn-info btn-xs"
											href="resume/workEdit.do?userid=${USEROBJ.id }&workid=${none.id}&pagetype=2">编辑</a><a
											class="resum_button btn btn-info btn-xs delbutton"
											para="userid=${USEROBJ.id }&workid=${none.id}&pagetype=3">删除</a></td>
									</tr>
									<!-- 
									<tr>
										<td></td>
										<th>工作描述</th>
										<td colspan="7">${none.worknote}</td>
										<td></td>
									</tr> -->
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<c:if test="${pagetype=='1'||pagetype=='2'}">
				<div class="row ">
					<div class="widget-box shadow-box" style="min-width: 800px;">
						<div class="stream-list p-stream">
							<form role="form" action="resume/workCommit.do" id="SubmitFormId"
								method="post">
								<input type="hidden" name="id" value="${work.id}">
								<table class="table table-striped resum_unit" style="font-size: 12px;">
									<tr>
										<td class="resum_title">企业名称<span class="alertMsgClass">*</span></td>
										<td style="width: 25%"><input type="text"
											class="form-control form_year" name="name" id="nameid"
											value="${work.name}"></td>
										<td class="resum_title"></td>
										<td style="width: 25%"></td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">工作时间<span class="alertMsgClass">*</span></td>
										<td><div class="input-group ">
												<div class="input-group-addon">
													<span class="glyphicon glyphicon-calendar"
														aria-hidden="true"></span>
												</div>
												<input type="text" class="form-control form_day"
													name="datestart" id="datestartid" value="${work.datestart}">
												<div class="input-group-addon">至</div>
												<div class="input-group-addon">
													<span class="glyphicon glyphicon-calendar"
														aria-hidden="true"></span>
												</div>
												<input type="text" class="form-control form_day"
													name="dateend" id="dateendid" value="${work.dateend}">
											</div>
											<div id="dateendMsgId"></div>
										</td>
										<td class="resum_title">职位名称<span class="alertMsgClass">*</span></td>
										<td><input type="text" class="form-control form_year"
											name="postname" id="postnameid" value="${work.postname}"></td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">行业类别<span class="alertMsgClass">*</span></td>
										<td><select class="form-control" name="type" id="typeid"
											val="${work.type}">
												<option value=""></option>
												<PF:OptionDictionary index="RESUME_TYPE" isTextValue="0" />
										</select></td>
										<td class="resum_title">企业性质<span class="alertMsgClass">*</span></td>
										<td><select class="form-control" name="eproperty"
											id="epropertyid" val="${work.eproperty}">
												<option value=""></option>
												<PF:OptionDictionary index="RESUME_EPROPERTY"
													isTextValue="0" />
										</select></td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">企业规模<span class="alertMsgClass">*</span></td>
										<td><select class="form-control" name="escale"
											id="escaleid" val="${work.escale}">
												<option value=""></option>
												<PF:OptionDictionary index="RESUME_ESCALE" isTextValue="0" />
										</select></td>
										<td class="resum_title">职位月薪(税前)<span
											class="alertMsgClass">*</span></td>
										<td><select class="form-control" name="salary"
											id="salaryid" val="${work.salary}">
												<option value=""></option>
												<PF:OptionDictionary index="RESUME_SALARY" isTextValue="0" />
										</select></td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">工作描述/备注<span
											class="alertMsgClass">*</span></td>
										<td colspan="5"><textarea id="worknoteid" name="worknote" class="form-control"
												style="height: 90px; width: 100%;">${work.worknote}</textarea></td>
									</tr>
									<tr>
										<td colspan="6" style="text-align: center;"><button
												type="button" id="SubmitButtonId" class="btn btn-info btn-lg">提交</button>
											<div style="margin-top: 4px;">
												<span class="alertMsgClass" id="errormessageShowboxId"></span>
											</div></td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>

<script type="text/javascript">
	var operatortype = "${OPERATE}";
	$(function() {
		$('select', '#SubmitFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		$(".form_day").datetimepicker({
			format : "yyyy-mm",
			showMeridian : true,
			autoclose : true,
			todayBtn : true,
			minView : 3,
			startView : 3
		});
		//删除按钮
		$(".delbutton").bind('click', function() {
			if (confirm("是否删除该数据?")) {
				window.location =basePath + "resume/workEdit.do?" + $(this).attr('para');
			}
		});
		//企业名称
		validateInput('nameid', function(id, val, obj) {
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
			return {
				valid : true,
				msg : '正确'
			};
		});
		//行业类别
		validateInput('typeid', function(id, val, obj) {
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
		//职位名称
		validateInput('postnameid', function(id, val, obj) {
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
			return {
				valid : true,
				msg : '正确'
			};
		});
		//工作时间起
		validateInput('datestartid', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 7)) {
				return {
					valid : false,
					msg : '长度不能大于' + 7
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		},'dateendMsgId');
		//工作时间止
		validateInput('dateendid', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 7)) {
				return {
					valid : false,
					msg : '长度不能大于' + 7
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		},'dateendMsgId');
		//职位月薪(税前)
		validateInput('salaryid', function(id, val, obj) {
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
		//工作描述
		validateInput('worknoteid', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
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
		//企业性质
		validateInput('epropertyid', function(id, val, obj) {
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
		//企业规模
		validateInput('escaleid', function(id, val, obj) {
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