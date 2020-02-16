<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-家庭/联系人-<PF:ParameterValue
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
					<h3>${USEROBJ.name}-家庭成员/联系人<a
							class="resum_button btn btn-info btn-sm"
							href="resume/view.do?userid=${USEROBJ.id}">返回</a><a
							class="resum_button btn btn-info btn-sm"
							href="resume/familyEdit.do?userid=${USEROBJ.id}&pagetype=1">新增</a>
					</h3>
				</div>
			</div>
			<div class="row " style="margin-top: 4px;">
				<div class="widget-box shadow-box" style="min-width: 800px;">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>家庭成员/联系人
						</h3>
					</div>
					<div class="stream-list p-stream">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th>关系</th>
									<th>姓名</th>
									<th>出生日期</th>
									<th>联系电话</th>
									<th>住址</th>
									<!-- <th>备注</th> -->
									<th style="width: 200px; text-align: right;">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${familys}" var="none">
									<tr>
										<td></td>
										<td>${none.relationtype}</td>
										<td>${none.name}</td>
										<td>${none.birth}</td>
										<td>${none.phone}</td>
										<td>${none.addr}</td>
										<!-- <td>${none.pcontent}</td> -->
										<td><a class="resum_button btn btn-info btn-xs"
											href="resume/familyEdit.do?userid=${USEROBJ.id }&familyid=${none.id}&pagetype=2">编辑</a><a
											class="resum_button btn btn-info btn-xs delbutton"
											para="userid=${USEROBJ.id }&familyid=${none.id}&pagetype=3">删除</a></td>
									</tr>
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
							<form role="form" action="resume/familyCommit.do"
								id="SubmitFormId" method="post">
								<input type="hidden" name="id" value="${family.id}">
								<table class="table table-striped resum_unit" style="font-size: 12px;">
									<tr>
										<td class="resum_title">关系<span class="alertMsgClass">*</span></td>
										<td style="width: 25%"><select class="form-control"
											name="relationtype" id="relationtypeid"
											val="${family.relationtype}">
												<option value=""></option>
												<PF:OptionDictionary index="RESUME_RELATIONTYPE"
													isTextValue="0" />
										</select></td>
										<td class="resum_title"></td>
										<td style="width: 25%"></td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">姓名<span class="alertMsgClass">*</span></td>
										<td><input type="text" class="form-control" id="nameid"
											name="name" value="${family.name}"></td>
										<td class="resum_title">出生日期<span class="alertMsgClass">*</span></td>
										<td><div class="input-group ">
												<div class="input-group-addon">
													<span class="glyphicon glyphicon-calendar"
														aria-hidden="true"></span>
												</div>
												<input type="text" class="form-control form_year"
													name="birth" id="birthid" value="${family.birth}">
											</div>
											<div id="birthMsgId"></div>
										</td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">政治面貌<span class="alertMsgClass">*</span></td>
										<td><select class="form-control" name="zzmm" id="zzmmid"
											val="${family.zzmm}">
												<option value=""></option>
												<PF:OptionDictionary index="RESUME_ZZMM" isTextValue="0" />
										</select></td>
										<td class="resum_title">工作单位<span class="alertMsgClass">*</span></td>
										<td><input type="text" class="form-control" id="wordaddr"
											name="wordaddr" value="${family.wordaddr}"></td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">联系电话<span class="alertMsgClass">*</span></td>
										<td><input type="text" class="form-control" id="phoneid"
											name="phone" value="${family.phone}"></td>
										<td class="resum_title">住址<span class="alertMsgClass">*</span></td>
										<td><input type="text" class="form-control" id="addrid"
											name="addr" value="${family.addr}"></td>
										<td class="resum_title"></td>
										<td></td>
									</tr>
									<tr>
										<td class="resum_title">备注</td>
										<td colspan="5"><textarea id="pcontentid" name="pcontent" class="form-control"
												style="height: 90px; width: 100%;">${family.pcontent}</textarea></td>
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
		$(".form_year").datetimepicker({
			format : "yyyy",
			showMeridian : true,
			autoclose : true,
			todayBtn : true,
			minView : 4,
			startView : 4
		});
		//删除按钮
		$(".delbutton").bind(
				'click',
				function() {
					if (confirm("是否删除该数据?")) {
						window.location = basePath +"resume/familyEdit.do?"
								+ $(this).attr('para');
					}
				});

		//关系
		validateInput('relationtypeid', function(id, val, obj) {
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
		//政治面貌
		validateInput('zzmmid', function(id, val, obj) {
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
		//姓名
		validateInput('nameid', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 12)) {
				return {
					valid : false,
					msg : '长度不能大于' + 12
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		});
		//出生日期
		validateInput('birthid', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
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
		},'birthMsgId');
		//联系电话
		validateInput('phoneid', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
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
		//工作单位
		validateInput('wordaddrid', function(id, val, obj) {
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
		//住址
		validateInput('addrid', function(id, val, obj) {
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
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
		//备注
		validateInput('pcontentid', function(id, val, obj) {
			if (valid_maxLength(val, 128)) {
				return {
					valid : false,
					msg : '长度不能大于' + 128
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