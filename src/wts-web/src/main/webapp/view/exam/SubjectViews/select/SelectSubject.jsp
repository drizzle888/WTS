<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.TableTitle .title {
	font-weight: 700;
	margin-left: 30px;
}

.TableTitle .vartext {
	font-weight: 200;
}

.editTable .titleshort {
	width: 60px;
}
</style>
<!--考题表单-->
<div class="easyui-layout" data-options="fit:true">
	<div
		data-options="region:'east',iconCls:'icon-comment',collapsible:false,title:'选择题选项'"
		style="width: 450px;">
		<table id="dataSubjectanswerGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="SORT" data-options="sortable:true" width="20">顺序</th>
					<th field="ANSWER" data-options="sortable:true" width="80">选项内容</th>
					<!-- <th field="RIGHTANSWER" data-options="sortable:true" width="40">正确</th> -->
					<th field="RIGHTANSWER" data-options="sortable:true" width="30">类型</th>
				</tr>
			</thead>
		</table>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formSubject">
			<input type="hidden" id="entity_id" name="id"
				value="${subjectu.version.id}">
			<table class="editTable">
				<tr>
					<td class="title titleshort"></td>
					<td style="color: #999;">试题类型：${subjectu.tipType.title}</td>
					<td class="title titleshort"></td>
					<td style="color: #999;">分类：${subjectu.subjectType.name}</td>
				</tr>
				<tr>
					<td class="title"></td>
					<td colspan="3" style="color: #999;">版本号：${subjectu.version.ctime}</td>
				</tr>
				<tr>
					<td class="title">题目:</td>
					<td colspan="3"><textarea rows="3" style="width: 355px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[128]']"
							id="entity_tipstr" name="tipstr">${subjectu.version.tipstr}</textarea></td>
				</tr>
				<tr>
					<td class="title"></td>
					<td colspan="3"><textarea id="entity_tipnote"
							style="display: none;" name="tipnote">${subjectu.version.tipnote}
						</textarea>
						<div style="width: 355px;"><jsp:include
								page="../../comment/IncludeEditor.jsp">
								<jsp:param value="entity_tipnote" name="fieldId" />
								<jsp:param value="${operateType}" name="type" />
								<jsp:param value="附加描述" name="fieldTitle" />
							</jsp:include></div></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_edit_entitySubject" href="javascript:void(0)"
				iconCls="icon-save" class="easyui-linkbutton">保存题目</a>&nbsp;<a
				id="dom_view_entitySubject" href="javascript:void(0)"
				iconCls="icon-showreel" class="easyui-linkbutton">预览</a>&nbsp;<a
				id="dom_cancle_formSubject" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var url_delActionSubjectanswer = "subjectanswer/del.do";//删除URL
	var url_formActionSubjectanswer = "subjectanswer/form.do";//增加、修改、查看URL
	var url_searchActionSubjectanswer = "subjectanswer/query.do?versionid=${subjectu.version.id}";//查询URL
	var title_windowSubjectanswer = "考题答案管理";//功能名称
	var submitAddActionSubject = 'subject/add.do';
	var submitEditActionSubject = 'subject/edit.do';
	var currentPageTypeSubject = '${operateType}';
	var submitFormSubject;
	var toolBarSubjectanswer = [ {
		id : 'add',
		text : '新增选项',
		iconCls : 'icon-add',
		handler : addDataSubjectanswer
	}, {
		id : 'edit',
		text : '修改选项',
		iconCls : 'icon-edit',
		handler : editDataSubjectanswer
	}, {
		id : 'del',
		text : '删除选项',
		iconCls : 'icon-remove',
		handler : delDataSubjectanswer
	}, {
		id : 'onlyRight',
		text : '唯一正确',
		iconCls : 'icon-ok',
		handler : onlyRightAnswer
	} ];
	$(function() {
		//初始化数据表格
		gridSubjectanswer = $('#dataSubjectanswerGrid').datagrid({
			url : url_searchActionSubjectanswer,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarSubjectanswer,
			pagination : false,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//表单组件对象
		submitFormSubject = $('#dom_formSubject').SubmitForm({
			pageType : currentPageTypeSubject,
			grid : gridSubject,
			currentWindowId : 'winSubject'
		});
		//关闭窗口
		$('#dom_cancle_formSubject').bind('click', function() {
			$('#winSubject').window('close');
		});
		//提交修改数据
		$('#dom_edit_entitySubject').bind('click', function() {
			submitFormSubject.postSubmit(submitEditActionSubject);
		});
		//预览试题
		$('#dom_view_entitySubject').bind('click', function() {
			submitFormSubject.postSubmit(submitEditActionSubject, function() {
				viewDataSubject('${subjectu.subject.id}');
			});
		});
	});

	//新增
	function addDataSubjectanswer() {
		var url = url_formActionSubjectanswer
				+ '?versionid=${subjectu.version.id}&operateType='
				+ PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winSubjectanswer',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增答案'
		});
	}
	//修改
	function editDataSubjectanswer() {
		var selectedArray = $(gridSubjectanswer).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionSubjectanswer + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winSubjectanswer',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '修改答案'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}

	//选择唯一正确答案
	function onlyRightAnswer() {
		var selectedArray = $(gridSubjectanswer).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = "该选项将被设置为唯一正确答案！";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridSubjectanswer).datagrid('loading');
					$.post(  'subjectanswer/onlyRight.do?ids='
							+ $.farm.getCheckedIds(gridSubjectanswer, 'ID'),
							{}, function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridSubjectanswer).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridSubjectanswer).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//删除
	function delDataSubjectanswer() {
		var selectedArray = $(gridSubjectanswer).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridSubjectanswer).datagrid('loading');
					$.post(url_delActionSubjectanswer + '?ids='
							+ $.farm.getCheckedIds(gridSubjectanswer, 'ID'),
							{}, function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridSubjectanswer).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridSubjectanswer).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>