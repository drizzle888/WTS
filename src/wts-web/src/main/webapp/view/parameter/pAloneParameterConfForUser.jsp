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
<body>

	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>
			用户个性化参数配置，修改后请保存。
			<PF:ParameterValue key="jdbc.url" />
		</div>
	</div>
	<div style="padding: 4px;">
		<div style="border-bottom: 1px solid #ddd;">
			<a id="a_tree_save" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-save">保存</a>
		</div>
		<div style="margin: 8px;">
			<table id="propertyTable_ID" class="easyui-propertygrid"
				style="width: 600px"
				data-options="url: 'parameter/userqueryForEU.do',showGroup: true,scrollbarSize: 0,columns: mycolumns">
			</table>
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
	$('#a_tree_save').bind(
			'click',
			function() {
				var paraArray = $('#propertyTable_ID').propertygrid('getRows');
				var paraSubmitVar;
				$(paraArray).each(
						function(index, obj) {
							if (index == 0) {
								paraSubmitVar = paraArray[index].id + '&2581&'
										+ paraArray[index].value;
							} else {
								paraSubmitVar = paraSubmitVar + '&2582&'
										+ paraArray[index].id + '&2581&'
										+ paraArray[index].value;
							}
						});
				$('#a_tree_save').linkbutton('disable');
				$.post(
						'parameter/editUserEU.do',
						{
							ids : paraSubmitVar
						},
						function(flag) {
							var jsonObject = JSON.parse(flag, null);
							if (jsonObject.STATE == '0') {
								$.messager.alert(MESSAGE_PLAT.PROMPT,
										MESSAGE_PLAT.SUCCESS, 'info');
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
</script>
</html>




