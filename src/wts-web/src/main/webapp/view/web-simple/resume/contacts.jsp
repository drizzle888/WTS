<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>通讯录-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div>&nbsp;</div>
		<div class="container" style="background: #fff;">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-6">
							<h3>
								<span class="glyphicon glyphicon-list-alt"></span>&nbsp;通讯录
							</h3>
						</div>
						<div class="col-xs-6">
							<div class="input-group" style="margin-top: 8px; width: 300px;">
								<input type="text" class="form-control" id="contactsSearchKeyId"
									placeholder="输入查询条件..."><span class="input-group-btn">
									<button class="btn btn-default" type="button"
										id="contactsSearchId">
										<span class="glyphicon glyphicon-search"></span>
									</button>
									<button class="btn btn-default" type="button"
										id="contactsSearchClearId">
										<span class="glyphicon glyphicon-refresh"></span>
									</button>
								</span>
							</div>
							<!-- /input-group -->
						</div>
					</div>
					<!-- /.row -->
				</div>
			</div>
			<div class="row" style="margin-top: 4px;">
				<div class="col-md-12 table-responsive">
					<table class="table table-bordered" id="contactsId">
						<tr class="active">
							<th>组织机构</th>
							<th style="width: 100px;">姓名</th>
							<th>岗位</th>
							<th style="width: 45px;">性别</th>
							<th>电子邮箱</th>
							<th>手机</th>
							<th>电话</th>
							<th>qq</th>
							<th>微信</th>
							<th style="width: 50px;">操作</th>
						</tr>
						<c:forEach items="${result.resultList}" var="node">
							<tr class="userinfoClass">
								<td class="success" valign="middle"
									style="vertical-align: middle;" align="center">${node.ORGNAMES}</td>
								<td class="active" style="font-weight: bold;">${node.USERNAME}
								</td>
								<td class="warning">${node.POSTNAMES}</td>
								<td class="success">${node.SEX}</td>
								<td class="warning">${node.EMAILCODE}</td>
								<td class="success">${node.MOBILECODE}</td>
								<td class="warning">${node.PHONECODE}</td>
								<td class="success">${node.QQCODE}</td>
								<td class="warning">${node.WXCODE}</td>
								<td align="center"><a
									href="webuser/Home.do?type=comment&userid=${node.USERID}"><span
										class="glyphicon glyphicon-envelope"></span></a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<br /> <br />
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
<script type="text/javascript">
	function mc(tableId, startRow, endRow, col) {
		var tb = document.getElementById(tableId);
		if (col >= tb.rows[0].cells.length) {
			return;
		}
		if (col == 0) {
			endRow = tb.rows.length - 1;
		}
		for (var i = startRow; i < endRow; i++) {
			if (tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[0].innerHTML) {
				tb.rows[i + 1].removeChild(tb.rows[i + 1].cells[0]);
				tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan | 0) + 1;
				if (i == endRow - 1 && startRow != endRow) {
					mc(tableId, startRow, endRow, col + 1);
				}
			} else {
				mc(tableId, startRow, i + 0, col + 1);
				startRow = i + 1;
			}
		}
	}
	function innerSearch(txt) {
		if ($.trim(txt) != "") {
			$("table .userinfoClass:not('#theader')").filter(
					":contains('" + txt + "')").css("color", "#e74c3c");
			$("html,body").animate(
					{
						scrollTop : $("table .userinfoClass:not('#theader')")
								.filter(":contains('" + txt + "'):first")
								.offset().top - 150
					}, 500);//1000是ms,也可以用slow代替 
		} else {
			$("table .userinfoClass:not('#theader')").css("color", "#333333");
		}
	}
	$(function() {
		$("#contactsSearchId").click(function() {
			var txt = $("#contactsSearchKeyId").val();
			innerSearch("");
			innerSearch(myTrim(txt));
		});
		$('#contactsSearchKeyId').keydown(function(e) {
			if (e.keyCode == 13) {
				$("#contactsSearchId").click();
			}
		});
		$("#contactsSearchClearId").click(function() {
			innerSearch("");
		});
		mc('contactsId', 0, 0, 0);
	});
	function myTrim(x) {
		return x.replace(/^\s+|\s+$/gm, '');
	}
</script>
</html>