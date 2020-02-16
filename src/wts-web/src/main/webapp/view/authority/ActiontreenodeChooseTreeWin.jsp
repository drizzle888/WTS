<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="margin: 4px;">
	<div style="text-align: center;">
		<select id="chooseMenuDomainId" style="width: 100px;">
			<option value="alone">
				标准权限
			</option>
			<PF:OptionDictionary index="ALONE_MENU_DOMAIN" isTextValue="0" />
		</select>
		<a id="ActiontreeChooseTreeOpenAll" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-sitemap">全部展开</a>
	</div>
	<hr />
	<ul id="ActionTreeNodeDomTree"></ul>
</div>
<script type="text/javascript">
	$(function() {
		$('#ActiontreeChooseTreeOpenAll').bind('click', function() {
			$('#ActionTreeNodeDomTree').tree('expandAll');
		});
		loadChooseTree($('#chooseMenuDomainId').val());
		$('#chooseMenuDomainId').bind('change', function() {
			loadChooseTree($('#chooseMenuDomainId').val());
		});
	});
	function loadChooseTree(domain) {
		$('#ActionTreeNodeDomTree').tree( {
			url : 'actiontree/loadtree.do?domain=' + domain,
			onSelect : ActionTreeNodetreeNodeClick
		});
	}
	function ActionTreeNodetreeNodeClick(node) {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否移动该节点到目标节点下？",
				function(flag) {
					if (flag) {
						$.post("actiontree/move.do", {
							oid : '${ids}',
							deid : node.id
						}, function(flag) {
							var jsonObject = JSON.parse(flag, null);
							if (jsonObject.STATE == 0) {
								$('#ActionTreeNodeWin').window('close');
								$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧权限树？', function(r){
									if (r){
										$('#ActiontreeTree').tree('reload');
									}
								});
								$.messager.alert(MESSAGE_PLAT.SUCCESS,MESSAGE_PLAT.SUCCESS, 'info');
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