<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--选择材料-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchMaterial">
			<table class="editTable">
				<tr>
					<td class="title">材料标题</td>
					<td><input name="TITLE:like" type="text"></td>
					<td class="title"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
					<td><a id="a_reset" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_chooseGridMaterial">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="50">标题</th>
					<th field="RFNUM" data-options="sortable:true" width="50">引用次数</th>
					<th field="USERNAME" data-options="sortable:true" width="50">创建人</th>
					<th field="CTIME" data-options="sortable:true" width="50">创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var chooseGridMaterial;
	var chooseSearchfarmMaterial;
	var toolbar_chooseMaterial = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_chooseGridMaterial').datagrid(
					'getSelections');
			if (selectedArray.length == 1) {
				chooseWindowCallBackHandle(selectedArray[0]);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
	} ];
	$(function() {
		chooseGridMaterial = $('#dom_chooseGridMaterial').datagrid({
			url : 'material/query.do',
			fit : true,
			'toolbar' : toolbar_chooseMaterial,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmMaterial = $('#dom_chooseSearchMaterial').searchForm({
			gridObj : chooseGridMaterial
		});
	});
//-->
</script>







<!--1.在调用JSP页面，中粘贴下面js中的一段（绑定到按钮事件，或通过方法打开窗口） 
//---------------------------使用下面的（绑定到按钮事件）----------------------------------------------------- 
<a id="buttonMaterialChoose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
  $(function() {
    $('#buttonMaterialChoose').bindChooseWindow('chooseMaterialWin', {
      width : 600,
      height : 300,
      modal : true,
      url : 'admin/MaterialChooseGridPage.do',
      title : '选择材料',
      callback : function(rows) {
        //$('#NAME_like').val(rows[0].NAME);
      }
    });
  });
</script>
//----------------------或----使用下面的（窗口中打开）----------------------------------------------------- 
chooseWindowCallBackHandle = function(row) {
    $("#chooseMaterialWin").window('close');  
};
$.farm.openWindow( {
  id : 'chooseMaterialWin',
  width : 600,
  height : 300,
  modal : true,
  url : 'admin/MaterialChooseGridPage.do',
  title : '选择材料'
});
 -->





<!--2.粘贴到Action中的java方法
@RequestMapping("/MaterialChooseQuery")
@ResponseBody
public Map<String, Object> MaterialChooseQuery(DataQuery query,
		HttpServletRequest request) {
	try {
		query = EasyUiUtils.formatGridQuery(request, query);
		DataResult result = //////;
		return ViewMode.getInstance()
				.putAttrs(EasyUiUtils.formatGridData(result))
				.returnObjMode();
	} catch (Exception e) {
		log.error(e.getMessage());
		return ViewMode.getInstance().setError(e.getMessage())
				.returnObjMode();
	}
}
@RequestMapping("/MaterialChooseGridPage")
	public ModelAndView MaterialChooseGridPage(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("device/ChoosedeviceChooseGridWin");
	}
-->