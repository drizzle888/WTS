<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>系统日志</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/view/conf/include.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="searchLogForm">
				<table class="editTable">
					<tr>
						<td class="title">
							日志:
						</td>
						<td>
							<input name="a.DESCRIBES:like" type="text">
						</td>
						<td class="title">
							日志级别:
						</td>
						<td>
							<select name="a.LEVELS:=">
								<option value="">
									全部
								</option>
								<option value="INFO">
									INFO
								</option>
								<option value="DEBUG">
									DEBUG
								</option>
								<option value="WARN">
									WARN
								</option>
								<option value="ERROR">
									ERROR
								</option>
							</select>
						</td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6">
							<a id="a_search" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-search">查询</a>
							<a id="a_reset" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataLogGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="DESCRIBES" data-options="sortable:true" width="200">
							日志
						</th>
						<th field="LEVELS" data-options="sortable:true" width="80">
							日志级别
						</th>
						<th field="IP" data-options="sortable:true" width="80">
							IP
						</th>
						<th field="CTIME" data-options="sortable:true" width="80">
							生成时间
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
<script type="text/javascript">
	var url_delActionLog = "log/del.do";
	var url_formActionLog = "log/form.do";
	var url_searchActionLog =  "log/query.do";
	var title_windowLog = "系统日志";
	var gridLog;//数据表格对象
	var searchLog;//条件查询组件对象
	var toolBarLog = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataLog
	}];
	$(function() {
		//初始化数据表格
		gridLog = $('#dataLogGrid').datagrid( {
			url : url_searchActionLog,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarLog,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchLog = $('#searchLogForm').searchForm( {
			gridObj : gridLog
		});
	});
	//格式化结果
	function ImgClassformatter(value, row, index) {
		return "<span class='" + row.ICONCLASS
				+ "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>" + value;
	}
	//查看
	function viewDataLog() {
		var selectedArray = $(gridLog).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionLog + '?operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winLog',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
</script>
</html>




