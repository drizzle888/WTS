<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">

	<div data-options="region:'center'">
		<ul id="menuTree_tt" class="easyui-tree" style="margin: 4px;"
			data-options="url:'post/ALONEMENU_RULEGROUP_TREENODE.do?ids=${ids}',animate:true,checkbox:true,lines:true"></ul>
	</div>
	<div data-options="region:'south'"
		style="height: 40px; text-align: center;">
		<a id="a_tree_save" href="javascript:void(0)"
			class="easyui-linkbutton" style="margin: 4px;" iconCls="icon-save">保存</a>
		<a id="a_tree_close" href="javascript:void(0)" iconCls="icon-cancel"
			class="easyui-linkbutton" style="color: #000000;">取消</a>
	</div>
</div>
<script type="text/javascript">
	$('#a_tree_close').bind('click', function() {
		$('#winPostActionsChoose').window('close');
	});
	$('#a_tree_save').bind(
			'click',
			function() {
				var nodes = $('#menuTree_tt').tree('getChecked',
						[ 'checked', 'indeterminate' ]);
				var s = '';
				for ( var i = 0; i < nodes.length; i++) {
					if (s != '')
						s += ',';
					s += nodes[i].id;
				}
				$.messager.confirm(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.COMMIT_IS
						+ '选中' + nodes.length + '项', function(r) {
					if (r) {
						$('#a_tree_save').linkbutton('disable');
						$.post('post/ALONEROLEGROUP_SYSBASE_SETTREE.do', {
							ids : s,
							id : '${ids}'
						}, function(flag) {
							var jsonObject = JSON.parse(flag, null);
							if (jsonObject.model.STATE == 0) {
								$.messager.alert(MESSAGE_PLAT.PROMPT,
										MESSAGE_PLAT.SUCCESS, 'info');
								$('#winPostActionsChoose').window('close');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ flag.pageset.message;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
							$('#a_tree_save').linkbutton('enable');
						});
					}
				});
			});
	//-->
</script>