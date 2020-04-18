<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--wtsp文件导入表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">wtsp文件导入</div>
	</div>
	<div data-options="region:'center'">
		<div style="margin-bottom: 20px; padding: 8px;">
			<form id="doWtspImportForm">
				<table style="width: 100%">
					<tr>
						<td>wtsp文件:</td>
						<td><input class="easyui-filebox" name="file"
							data-options="prompt:'请选择',buttonText:'请选择'"></td>
					</tr>
					<tr>
						<td style="padding-top: 8px;">业务分类:</td>
						<td><input id="entity_examTypeId" name="examType"></td>
					</tr>
					<tr>
						<td style="padding-top: 8px;">题库分类:</td>
						<td><input id="entity_subjectTypeId" name="subjectType"></td>
					</tr>
					<tr>
						<td colspan="2" style="padding-top: 8px;"><a
							class="easyui-linkbutton" id="importButtonId"
							data-options="iconCls:'icon-move_to_folder',onClick:doWtspImport">导入</a>
						</td>
					</tr>
				</table>
				<span id="doWtspImportMessage" style="color: red;"></span>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$('#entity_examTypeId').combotree({
			url : 'examTypeTree/examtypeTree.do?funtype=0',
			required : true,
			textFiled : 'name',
			parentField : 'parentid',
			onSelect : function(node) {

			},
			onLoadSuccess : function(node, data) {

			}
		});
		$('#entity_subjectTypeId').combotree({
			url : 'subjectTypeTree/subjecttypeTree.do?funtype=0',
			required : true,
			textFiled : 'name',
			parentField : 'parentid',
			onSelect : function(node) {

			},
			onLoadSuccess : function(node, data) {

			}
		});
	});

	function doWtspImport() {
		if ($('#doWtspImportForm').form('validate')) {
			var formData = new FormData($("#doWtspImportForm")[0]);
			var doWtspImportMessage = $("#doWtspImportMessage");
			$('#importButtonId').linkbutton('disable');
			doWtspImportMessage.html("导入中...");
			$.ajax({
				url : 'paper/doWtspImport.do',
				type : 'POST',
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					$('#importButtonId').linkbutton('enable');
					var $data = $.parseJSON(data)
					if ($data.STATE == 1) {
						doWtspImportMessage.html($data.MESSAGE);
						return;
					}
					$(gridPaper).datagrid('reload');
					doWtspImportMessage.html("导入成功！");
					$(gridUser).datagrid('reload');
					setTimeout("$('#toUserImport').window('close')", 1500);
				},
				error : function(data) {
					$('#importButtonId').linkbutton('enable');
					var $data = $.parseJSON(data)
					if ($data.STATE == 1) {
						doWtspImportMessage.html($data.MESSAGE);
						return;
					}
					doWtspImportMessage.html("导入成功！");
					$(gridUser).datagrid('reload');
					setTimeout("$('#toUserImport').window('close')", 1500);
				}
			});
		}
	}
</script>