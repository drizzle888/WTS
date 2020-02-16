<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>考题数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<script type="text/javascript"
	src="view/web-simple/paper/text/card.js"></script>
</head>
<body class="easyui-layout">
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
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTIDSUB_RULE" name="C.TREECODE:like" type="hidden"></td>
						<td class="title">题目:</td>
						<td><input name="TIPSTR:like" type="text"></td>
						<td class="title">材料标题:</td>
						<td><input name="d.TITLE:like" type="text"></td>
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
						<th field="TIPTYPE" data-options="sortable:true" width="20">题类型</th>
						<th field="TIPSTR" data-options="sortable:true" width="60">题目</th>
						<th field="TYPENAME" data-options="sortable:true" width="20">题库分类</th>
						<th field="ANSWERED" data-options="sortable:true" width="20">答案状态</th>
						<th field=TITLE data-options="sortable:true" width="30">引用材料</th>
						<th field=ANALYSISNUM data-options="sortable:true" width="30">题解析</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="SubjectToolbar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-showreel',plain:true,onClick:viewDataSubject">预览题目
			</a>
			<!-- 1.填空，2.单选，3.多选，4判断，5问答 -->
			<a href="javascript:void(0)" id="mb" class="easyui-menubutton"
				data-options="menu:'#mm6',iconCls:'icon-add'">创建题</a>
			<div id="mm6" style="width: 150px;">
				<div data-options="iconCls:'icon-invoice'"
					onclick="addDataSubject(1)">填空题</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-finished-work'"
					onclick="addDataSubject(2)">单选题</div>
				<div data-options="iconCls:'icon-finished-work'"
					onclick="addDataSubject(3)">多选题</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-issue'" onclick="addDataSubject(4)">判断题</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-attibutes'"
					onclick="addDataSubject(5)">问答题</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-documents_email'"
					onclick="addDataSubject(6)">附件题</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-old-versions'"
					onclick="addSubjects()">批量创建</div>
			</div>
			<a href="javascript:void(0)" id="bj" class="easyui-menubutton"
				data-options="menu:'#mm7',iconCls:'icon-edit'">编辑题</a>
			<div id="mm7" style="width: 150px;">
				<div onclick="editDataSubject()"
					data-options="iconCls:'icon-edit',plain:true">修改</div>
				<div onclick="moveSubjectTypetree()"
					data-options="iconCls:'icon-communication',plain:true">设置分类</div>
				<div class="menu-sep"></div>
				<div onclick="delDataSubject()"
					data-options="iconCls:'icon-remove',plain:true">删除</div>
				<div class="menu-sep"></div>
				<div onclick="clearCancelSubjects()"
					data-options="iconCls:'icon-refresh',plain:true">清理废题</div>
				<div onclick="checkAnswered()"
					data-options="iconCls:'icon-eye',plain:true">更新答案状态</div>
				<div class="menu-sep"></div>
				<div onclick="bindMaterial()"
					data-options="iconCls:'icon-address-book-open',plain:true">綁定材料
				</div>
				<div onclick="clearMaterial()"
					data-options="iconCls:'icon-address-book',plain:true">取消材料绑定
				</div>
			</div>
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-library',plain:true,onClick:analysisMng">设置题解析
			</a>
		</div>
	</div>
</body>
<script type="text/javascript">
	var funtype = '2';
</script>
<script type="text/javascript" src="view/exam/subject/subject.js"></script>
</html>