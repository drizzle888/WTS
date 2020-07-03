<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<link rel="stylesheet" type="text/css"
	href="text/lib/kindeditor/themes/default/default.css">
<script type="text/javascript"
	src="text/lib/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="text/lib/kindeditor/zh-CN.js"></script>
<!--答题表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary">修改${fn:length(idlist)}个答题室的有效答题时间 </span>
	</div>
	<div data-options="region:'center'" style="overflow-x: hidden;">
		<form id="dom_formDotimeRoom">
			<input type="hidden" id="entity_id" name="ids" value="${ids}">
			<table class="editTable">
				<tr>
					<td class="title">有效时间:</td>
					<td colspan="3"><select name="timetype" id="entity_dotimetype"
						style="width: 120px;" val="${entity.timetype}"><option
								value="1">永久</option>
							<option value="2">限时</option>
					</select></td>
				</tr>
				<tr id="tr_dotime" style="display: none;">
					<td class="title">开始时间:</td>
					<td><input id="entity_dostarttime" name="starttime"
						style="width: 140px;" value="${entity.starttime}" type="text"
						class="easyui-datetimebox"></input></td>
					<td class="title">结束时间:</td>
					<td><input id="entity_doendtime" name="endtime"
						style="width: 140px;" value="${entity.endtime}" type="text"
						class="easyui-datetimebox"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_edit_dotimeRoom" href="javascript:void(0)"
				iconCls="icon-save" class="easyui-linkbutton">修改</a> <a
				id="dom_cancle_dotimeRoom" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitDoTimeFormRoom;
	$(function() {
		//表单组件对象
		submitDoTimeFormRoom = $('#dom_formDotimeRoom').SubmitForm({
			pageType : '2',
			grid : gridRoom,
			currentWindowId : 'winDotimeRoom'
		});
		//关闭窗口
		$('#dom_cancle_dotimeRoom').bind('click', function() {
			$('#winDotimeRoom').window('close');
		});
		//提交修改数据
		$('#dom_edit_dotimeRoom').bind('click', function() {
			submitDoTimeFormRoom.postSubmit('room/doTimeSubmit.do');
		});
		$('#entity_dotimetype').change(function(e) {
			var type = $('#entity_dotimetype').val();
			//1永久/2限时
			if (type == '1') {
				//隐藏时间表单
				$('#tr_dotime').hide();
				$('#entity_dostarttime,#entity_doendtime').datetimebox({
					required : false,
					showSeconds : false
				});
			}
			if (type == '2') {
				//显示时间表单
				$('#tr_dotime').show();
				$('#entity_dostarttime,#entity_doendtime').datetimebox({
					required : true,
					showSeconds : false
				});
			}
		});
	});
//-->
</script>