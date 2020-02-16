<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--触发器定义-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">
			<c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</div>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formfarmqztrigger">
			<input type="hidden" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						名称:
					</td>
					<td colspan="3">
						<input type="text" class="easyui-validatebox"
							style="width: 360px;"
							data-options="required:true,validType:',maxLength[64]'"
							id="entity_name" name="name" value="${entity.name}">
					</td>
				</tr>
				<tr>
					<td class="title">
						触发计划:
					</td>
					<td colspan="3">
						<jsp:include page="pFarmQzTriggerEntity-plan.jsp"></jsp:include>
						<input type="hidden" id="entity_descript"
							value="${entity.descript}" name="descript">
					</td>
				</tr>
				<tr>
					<td class="title">
						计划脚本:
					</td>
					<td colspan="3">
						<span id="scriptShowboxId"></span>
					</td>
				</tr>
				<tr>
					<td class="title">
						备注:
					</td>
					<td colspan="3">
						<textarea rows="3" style="width: 360px;"
							class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[64]'"
							id="entity_pcontent" name="pcontent">${entity.pcontent}</textarea>
					</td>
				</tr>
				<c:if test="${pageset.operateType==1}">
					<!--新增-->
				</c:if>
				<c:if test="${pageset.operateType==2}">
					<!--修改-->
				</c:if>
				<c:if test="${pageset.operateType==0}">
					<!--浏览-->
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityfarmqztrigger" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityfarmqztrigger" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfarmqztrigger" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfarmqztrigger = 'qzTrigger/add.do';
	var submitEditActionfarmqztrigger = 'qzTrigger/edit.do';
	var currentPageTypefarmqztrigger = '${pageset.operateType}';
	var submitFormfarmqztrigger;
	$(function() {
		submitFormfarmqztrigger = $('#dom_formfarmqztrigger').SubmitForm( {
			pageType : currentPageTypefarmqztrigger,
			grid : gridfarmqztrigger,
			currentWindowId : 'winfarmqztrigger'
		});
		decodePlanDescribe();
		$('#dom_cancle_formfarmqztrigger').bind('click', function() {
			$('#winfarmqztrigger').window('close');
		});
		$('#dom_add_entityfarmqztrigger').bind('click', function() {
			encodePlanDescribe();
			submitFormfarmqztrigger.postSubmit(submitAddActionfarmqztrigger);
		});
		$('#dom_edit_entityfarmqztrigger').bind('click', function() {
			encodePlanDescribe();
			submitFormfarmqztrigger.postSubmit(submitEditActionfarmqztrigger);
		});

	});
	function decodePlanDescribe() {
		var str = $('#entity_descript').val();
		$('#scriptShowboxId').text(str);
		var strarray = str.split(' ');
		$('#yearid').val(strarray[6]);
		$('#monthid').val(strarray[4]);
		$('#dayid').val(strarray[3]);
		$('#hourid').val(strarray[2]);
		$('#minuteid').val(strarray[1]);
		$('#secondid').val(strarray[0]);
	}
	function encodePlanDescribe() {
		//$('#yearid').val();
		//$('#monthid').val();
		//$('#dayid').val();
		//$('#hourid').val();
		//$('#minuteid').val();
		//$('#secondid').val();
		//[秒] [分] [小时] [日] [月] [周] [年] 
		var str = $('#secondid').val() + ' ' + $('#minuteid').val() + ' '
				+ $('#hourid').val() + ' ' + $('#dayid').val() + ' '
				+ $('#monthid').val() + ' ' + '?' + ' ' + $('#yearid').val();
		$('#scriptShowboxId').text(str);
		$('#entity_descript').val(str);
		//'0 15 10 * * ? 2005 ';
		var checkText = $("#select_id").find("option:selected").text()
		if ($.trim($('#entity_pcontent').val()) == null
				|| $.trim($('#entity_pcontent').val()) == '') {
			$('#entity_pcontent').text(
					clearBr($("#yearid").find("option:selected").text() + '年'
							+ $("#monthid").find("option:selected").text()
							+ '月' + $("#dayid").find("option:selected").text()
							+ '日' + $("#hourid").find("option:selected").text()
							+ '时'
							+ $("#minuteid").find("option:selected").text()
							+ '分'
							+ $("#secondid").find("option:selected").text()
							+ '秒'));
		}
	}
	//去除换行
	function clearBr(key) {
		key = key.replace(/<\/?.+?>/g, "");
		key = key.replace(/[\r\n]/g, "");
		key = key.replace(/\s+/g, "");
		return key;
	}
	//-->
</script>