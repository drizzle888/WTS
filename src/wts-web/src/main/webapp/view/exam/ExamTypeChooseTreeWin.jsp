<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="margin: 4px;">
	<div style="text-align: center;">
		<a id="ExamtreeChooseTreeOpenAll" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-sitemap">全部展开</a>
	</div>
	<hr />
	<ul id="ExamTreeNodeDomTree"></ul>
</div>
<script type="text/javascript">
	$(function() {
		$('#ExamtreeChooseTreeOpenAll').bind('click', function() {
			$('#ExamTreeNodeDomTree').tree('expandAll');
		});
		loadChooseTree($('#chooseMenuDomainId').val());
		$('#chooseMenuDomainId').bind('change', function() {
			loadChooseTree($('#chooseMenuDomainId').val());
		});
	});
	function loadChooseTree(domain) {
		$('#ExamTreeNodeDomTree').tree( {
			url : 'examTypeTree/examtypeTree.do?funtype=${funtype}',
			onSelect : ExamTreeNodetreeNodeClick
		});
	}
	function ExamTreeNodetreeNodeClick(node){
		chooseExamNodeBackFunc(node,'${ids}');
	}
	//-->
</script>