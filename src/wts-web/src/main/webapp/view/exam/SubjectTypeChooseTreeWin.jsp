<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="margin: 4px;">
	<div style="text-align: center;">
		<a id="SubjectTreeChooseTreeOpenAll" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-sitemap">全部展开</a>
	</div>
	<hr />
	<ul id="SubjectTreeNodeDomTree"></ul>
</div>
<script type="text/javascript">
	$(function() {
		$('#SubjectTreeChooseTreeOpenAll').bind('click', function() {
			$('#SubjectTreeNodeDomTree').tree('expandAll');
		});
		loadChooseTree($('#chooseMenuDomainId').val());
		$('#chooseMenuDomainId').bind('change', function() {
			loadChooseTree($('#chooseMenuDomainId').val());
		});
	});
	function loadChooseTree(domain) {
		$('#SubjectTreeNodeDomTree').tree( {
			url : 'subjectTypeTree/subjecttypeTree.do?funtype=${funtype}',
			onSelect : SubjectTreeNodetreeNodeClick
		});
	}
	function SubjectTreeNodetreeNodeClick(node){
		chooseSubjectNodeBackFunc(node,'${ids}');
	}
	//-->
</script>