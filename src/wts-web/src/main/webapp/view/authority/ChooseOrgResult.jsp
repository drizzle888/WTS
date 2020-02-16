<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="OrganizationTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新菜单</a> <a id="OrganizationTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">全部展开</a>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<ul id="OrganizationTree"></ul>
	</div>
</div>
<script type="text/javascript">
	var currentType, currentTypeName;
	$(function() {
		$('#OrganizationTree').tree({
			url : 'organization/organizationTree.do',
			onSelect : function(node) {
				//$('#PARENTID_RULE').val(node.id);
				//$('#PARENTTITLE_RULE').val(node.text);
				chooseOrgWindowCallBackHandle(node);
			}
		});
		$('#OrganizationTreeReload').bind('click', function() {
			$('#OrganizationTree').tree('reload');
		});
		$('#OrganizationTreeOpenAll').bind('click', function() {
			$('#OrganizationTree').tree('expandAll');
		});
	});
</script>
