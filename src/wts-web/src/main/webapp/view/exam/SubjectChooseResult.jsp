<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" id="subjectChooseResultPage"
	data-options="fit:true" style="display: none;">
	<div data-options="region:'west',split:true,border:false"
		style="width: 250px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="subjectTypeTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="subjectTypeTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="subjectTypeTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchSubjectForm">
				<table class="editTable">
					<tr>
						<td class="title">业务分类:</td>
						<td><input id="PARENTTITLESUB_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8; width: 100px;">
							<input id="PARENTIDSUB_RULE" name="C.TREECODE:like" type="hidden"></td>
						<td class="title">题型:</td>
						<td><select name="TIPTYPE:like" style="width: 100px;">
								<option value=""></option>
								<option value="1">填空</option>
								<option value="2">单选</option>
								<option value="3">多选</option>
								<option value="4">判断</option>
								<option value="5">问答</option>
								<option value="6">附件</option>
						</select></td>
						<td class="title">题目:</td>
						<td><input name="TIPSTR:like" type="text"
							style="width: 100px;"></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6"><a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
							id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload">清除条件</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataSubjectGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="TIPTYPE" data-options="sortable:true" width="20">试题类型</th>
						<th field="TIPSTR" data-options="sortable:true" width="60">题目</th>
						<th field="TYPENAME" data-options="sortable:true" width="20">题库分类</th>
						<th field=TITLE data-options="sortable:true" width="30">引用材料</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="SubjectToolbar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-check',plain:true,onClick:chooseSubjects">选择
			</a> <a class="easyui-linkbutton"
				data-options="iconCls:'icon-showreel',plain:true,onClick:viewDataSubject">预览题目
			</a>
			<!-- 1.填空，2.单选，3.多选，4判断，5问答 -->
			<a href="javascript:void(0)" id="mb" class="easyui-menubutton"
				data-options="menu:'#cmm6',iconCls:'icon-add'">新增</a>
			<div id="cmm6" style="width: 150px;">
				<div data-options="iconCls:'icon-invoice'"
					onclick="addDataSubject(1)">填空</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-finished-work'"
					onclick="addDataSubject(2)">单选</div>
				<div data-options="iconCls:'icon-finished-work'"
					onclick="addDataSubject(3)">多选</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-issue'" onclick="addDataSubject(4)">判断</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-attibutes'"
					onclick="addDataSubject(5)">问答</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-documents_email'"
					onclick="addDataSubject(6)">附件</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-old-versions'"
					onclick="addSubjects()">批量创建</div>
			</div>
			<a href="javascript:void(0)" id="mb" class="easyui-menubutton"
				data-options="menu:'#cmm7',iconCls:'icon-edit'">编辑</a>
			<div id="cmm7" style="width: 150px;">
				<div data-options="iconCls:'icon-edit'" onclick="editDataSubject()">修改
				</div>
				<!-- <div data-options="iconCls:'icon-remove'" onclick="delDataSubject()">删除
				</div> -->
				<div data-options="iconCls:'icon-communication'"
					onclick="moveSubjectTypetree()">设置分类</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var funtype = '1';
	//查看
	function chooseSubjects() {
		chooseSubjectCallback(gridSubject);
	}
</script>
<script type="text/javascript" src="view/exam/subject/subject.js"></script>
</html>