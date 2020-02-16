<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>

<div style="margin: 4px;">
	<div style="text-align: center;">
		<a id="OrganizationtreeChooseTreeOpenAll" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-sitemap">全部展开</a>
	</div>
	<hr />
	<ul id="UserORGDomTree"></ul>
</div>
<script type="text/javascript">
	$(function() {
		$('#UserORGDomTree').tree( {
			url : 'organization/postTree.do',//url : 'organization/organizationTree.do',
			lines : true,
			onSelect : UserORGtreeNodeClick
		});
		$('#OrganizationtreeChooseTreeOpenAll').bind('click', function() {
			$('#UserORGDomTree').tree('expandAll');
		});
	});
	function UserORGtreeNodeClick(node) {
		$.messager.confirm(MESSAGE_PLAT.PROMPT,
				"设置标准岗位时会删除已有标准岗位，设置临时岗位则为用户添加临时岗位。确定继续？", function(flag) {
					if (flag) {
						$.post("organization/userOrg.do", {
							ids : '${ids}',
							id : node.id
						}, function(flag) {
							var jsonObject = JSON.parse(flag, null);
							  if (jsonObject.STATE ==0) {
								$('#UserORGWin').window('close');
								$(gridUser).datagrid('reload');
							} else {
								$.messager.alert(MESSAGE_PLAT.ERROR,
										jsonObject.MESSAGE, 'error');
							}   
						});
					}
				});
	}
	//-->
</script>