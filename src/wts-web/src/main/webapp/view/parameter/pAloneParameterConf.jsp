<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>系统参数用户界面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<style>
<!--
.paraUnit {
	float: left;
	width: 100%;
}

.tabs-header {
	border-top: 0px solid white;
	padding-top: 0px;
}

.tabs {
	padding-top: 18px;
	background-color: white;
}

.demo-info {
	margin: 8px;
}

.parameterTip {
	font-size: 10px;
	color: #888;
}
-->
</style>
<body>
	<div id="tt" class="easyui-tabs" data-options="fit:true,border:false">
		<div title="系统运行参数配置" data-options="iconCls:'icon-sprocket_dark'">
			<div class="paraUnit">
				<div class="demo-info">
					<div class="demo-tip icon-tip"></div>
					<div>
						系统运行参数配置，修改后请保存。 <a href="javascript:void(0)"
							class="easyui-linkbutton a_cache_flash"
							data-options="plain:false"
							style="float: right; margin-top: -4px; margin-right: 4px; margin-left: 4px;"
							iconCls="icon-arrow_refresh">更新缓存和参数</a> <a id="a_tree_save"
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:false"
							style="float: right; margin-top: -4px; margin-right: 4px; margin-left: 4px;"
							iconCls="icon-save">保存</a> <a id="a_parameter_find"
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:false"
							style="float: right; margin-top: -4px; margin-right: 4px; margin-left: 4px;"
							iconCls="icon-zoom">读取参数</a> <a id="a_parameter_open"
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:false"
							style="float: right; margin-top: -4px; margin-right: 4px; margin-left: 4px;"
							iconCls="icon-address-book">折叠</a> <a id="a_parameter_close"
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:false"
							style="float: right; margin-top: -4px; margin-right: 4px; margin-left: 4px;"
							iconCls="icon-address-book-open">展开</a>
					</div>
				</div>
				<div style="margin: 8px;">
					<table id="propertyTable_ID" class="easyui-propertygrid"
						style="width: 100%; overflow: auto;"
						data-options="url: 'parameter/queryForEU.do',showGroup: true,scrollbarSize: 0,columns: mycolumns">
					</table>
				</div>
			</div>
			<div style="display: none;">
				<div id="win" title="查看值" style="text-align: center;"
					data-options="iconCls:'icon-save',modal:false,minimizable:false,maximizable:false,resizable:false">
					<textarea id="winTextBox"
						style="width: 90%; height: 100px; border: 0px; margin: auto;"></textarea>
				</div>
			</div>
		</div>
		<div title="XML文件参数" data-options="iconCls:'icon-cv'">
			<div class="paraUnit">
				<div class="demo-info">
					<div class="demo-tip icon-tip"></div>
					<div>
						XML文件参数，只读模式。配置文件在：(部署路径\WEB-INF\classes\)目录下 <br />
						双击值表格，可以再弹出框中查看值
					</div>
				</div>
				<div style="margin: 8px;">
					<table class="easyui-datagrid" style="width: 100%; overflow: auto;"
						data-options="fitColumns:true,singleSelect:true,onDblClickCell:showValue">
						<thead>
							<tr>
								<th data-options="field:'code'" width="50">key</th>
								<th data-options="field:'name'" width="50">值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="node" items="${filePropertys}">
								<tr>
									<td>${node.key}</td>
									<td>${node.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div title="常量参数" data-options="iconCls:'icon-lightbulb'">
			<div class="paraUnit">
				<div class="demo-info">
					<div class="demo-tip icon-tip"></div>
					<div>
						常量参数，只读模式。(com.farm.parameter.FarmParameterService中定义) <br />
						双击值表格，可以再弹出框中查看值
					</div>
				</div>
				<div style="margin: 8px;">
					<table class="easyui-datagrid" style="width: 100%; overflow: auto;"
						data-options="fitColumns:true,singleSelect:true,onDblClickCell:showValue">
						<thead>
							<tr>
								<th data-options="field:'code'" width="50">key</th>
								<th data-options="field:'name'" width="50">值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="node" items="${constantPropertys}">
								<tr>
									<td>${node.key}</td>
									<td>${node.value}</td>
								</tr>
							</c:forEach>
							<tr>
								<td>farm.sys.tomcat</td>
								<td><%=application.getServerInfo()%></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div title="servletContext参数" data-options="iconCls:'icon-world'">
			<div class="paraUnit">
				<div class="demo-info">
					<div class="demo-tip icon-tip"></div>
					<div>
						servletContext参数，只读模式。大部分来自XML配置文件 <br /> 双击值表格，可以再弹出框中查看值
					</div>
				</div>
				<div style="margin: 8px;">
					<table class="easyui-datagrid" style="width: 100%; overflow: auto;"
						data-options="fitColumns:true,singleSelect:true,onDblClickCell:showValue">
						<thead>
							<tr>
								<th data-options="field:'code'" width="50">key</th>
								<th data-options="field:'name'" width="50">值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="node" items="${servletContextPropertys}">
								<tr>
									<td>${node.key}</td>
									<td>${node.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div title="缓存状态" data-options="iconCls:'icon-database'">
			<div class="paraUnit">
				<div class="demo-info">
					<div class="demo-tip icon-tip"></div>
					<div>
						缓存状态 <a href="javascript:void(0)"
							class="easyui-linkbutton a_cache_flash"
							data-options="plain:false"
							style="float: right; margin-top: -4px;" iconCls="icon-save">更新缓存和参数</a>
							<a href="javascript:void(0)"
							class="easyui-linkbutton file_cache_flash"
							data-options="plain:false"
							style="float: right; margin-top: -4px;margin-right: 4px;" iconCls="icon-archives">更新附件缓存</a>
					</div>
				</div>
				<div style="margin: 8px;">
					<table class="easyui-datagrid" style="width: 100%; overflow: auto;"
						data-options="fitColumns:true,singleSelect:true,onDblClickCell:showValue">
						<thead>
							<tr>
								<th data-options="field:'code'" width="50">key</th>
								<th data-options="field:'name'" width="50">值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="node" items="${cachePropertys}">
								<tr>
									<td>${node.key}</td>
									<td>${node.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	var mycolumns = [ [ {
		field : 'name',
		title : '系统选项',
		width : 100,
		sortable : true
	}, {
		field : 'value',
		title : '值',
		width : 100,
		resizable : false
	} ] ];
	$('.a_cache_flash').bind(
			'click',
			function() {
				$('.a_cache_flash').linkbutton('disable');
				$.post(
						'parameter/clearCache.do',
						{},
						function(flag) {
							var jsonObject = JSON.parse(flag, null);
							if (jsonObject.STATE == '0') {
								$.messager
										.alert(MESSAGE_PLAT.PROMPT,
												MESSAGE_PLAT.SUCCESS
														+ ",刷新本页面后显示最新信息!",
												'info');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ jsonObject.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
							$('.a_cache_flash').linkbutton('enable');
						}).error(
						function() {
							$.messager.alert(MESSAGE_PLAT.ERROR,
									MESSAGE_PLAT.ERROR_SUBMIT_NO_MESSAGE,
									'error');
							$('.a_cache_flash').linkbutton('enable');
						});
			});
	$('.file_cache_flash').bind(
			'click',
			function() {
				$('.file_cache_flash').linkbutton('disable');
				$.post(
						'docfile/clearCache.do',
						{},
						function(flag) {
							var jsonObject = JSON.parse(flag, null);
							if (jsonObject.STATE == '0') {
								$.messager
										.alert(MESSAGE_PLAT.PROMPT,
												MESSAGE_PLAT.SUCCESS
														+ ",附件刷新完毕!",
												'info');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ jsonObject.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
							$('.file_cache_flash').linkbutton('enable');
						}).error(
						function() {
							$.messager.alert(MESSAGE_PLAT.ERROR,
									MESSAGE_PLAT.ERROR_SUBMIT_NO_MESSAGE,
									'error');
							$('.file_cache_flash').linkbutton('enable');
						});
			});
	$('#a_parameter_find').bind('click', function() {
		$.messager.prompt('提示信息', '请输入参数名称：', function(r) {
			if (r) {
				$.post('parameter/findPara.do', {
					key : r
				}, function(flag) {
					if (flag.STATE == 0) {
						alert(flag.val);
					} else {
						var str = MESSAGE_PLAT.ERROR_SUBMIT + flag.MESSAGE;
						$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
					}
				}, 'json');
			}
		});
	});
	$('#a_parameter_open').bind('click', function() {
		$('#propertyTable_ID').propertygrid('collapseGroup');
	});
	$('#a_parameter_close').bind('click', function() {
		$('#propertyTable_ID').propertygrid('expandGroup');
	});
	$('#a_tree_save').bind(
			'click',
			function() {
				var paraArray = $('#propertyTable_ID').propertygrid('getRows');
				var paraSubmitVar;
				$(paraArray).each(
						function(index, obj) {
							if (index == 0) {
								paraSubmitVar = paraArray[index].id + 'S2581E'
										+ paraArray[index].value;
							} else {
								paraSubmitVar = paraSubmitVar + 'S2582E'
										+ paraArray[index].id + 'S2581E'
										+ paraArray[index].value;
							}
						});
				$('#a_tree_save').linkbutton('disable');
				$.post(
						'parameter/editEU.do',
						{
							ids : paraSubmitVar
						},
						function(flag) {
							var jsonObject = JSON.parse(flag, null);
							if (jsonObject.STATE == '0') {
								$.messager.alert(MESSAGE_PLAT.PROMPT,
										MESSAGE_PLAT.SUCCESS + ",刷新缓存后启用参数!",
										'warning');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ jsonObject.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
							$('#a_tree_save').linkbutton('enable');
						}).error(
						function() {
							$.messager.alert(MESSAGE_PLAT.ERROR,
									MESSAGE_PLAT.ERROR_SUBMIT_NO_MESSAGE,
									'error');
							$('#a_tree_save').linkbutton('enable');
						});
			});
	function showValue(rowIndex, field, value) {
		$('#win').window({
			width : 400,
			height : 200,
			top : $(this).offset().top + 100
		});
		$('#winTextBox').val(value);
	}
</script>
</html>




