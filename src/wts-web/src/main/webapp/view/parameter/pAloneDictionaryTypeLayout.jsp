<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<table id="dataDiclisttypeGrid">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="NAME" data-options="sortable:true" width="80">
				名称
			</th>
			<th field="ENTITYTYPE" data-options="sortable:true" width="80">
				类型
			</th>
			<th field="SORT" data-options="sortable:true" width="80">
				排序号
			</th>
			<th field="STATE" data-options="sortable:true" width="80">
				状态
			</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
    var url_delActionDiclisttype = "dictionaryType/del.do";//删除URL
    var url_formActionDiclisttype = "dictionaryType/form.do";//增加、修改、查看URL
    var url_searchActionDiclisttype = "dictionaryType/query.do?ids=${ids}";//查询URL
    var title_windowDiclisttype = "列表字典数据类型";//功能名称
    var gridDiclisttype;//数据表格对象
    var toolBarDiclisttype = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataDiclisttype
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataDiclisttype
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataDiclisttype
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataDiclisttype
    } ];
    $(function() {
      //初始化数据表格
      gridDiclisttype = $('#dataDiclisttypeGrid').datagrid( {
        url : url_searchActionDiclisttype,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarDiclisttype,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
    });
    //查看
    function viewDataDiclisttype() {
      var selectedArray = $(gridDiclisttype).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionDiclisttype + '?operateType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winDiclisttype',
          width : 600,
          height : 300,
          modal : true,
          url : url,
          title : '浏览'
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
            'info');
      }
    }
    //新增
    function addDataDiclisttype() {
      var url = url_formActionDiclisttype + '?dicId=${ids}&operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winDiclisttype',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataDiclisttype() {
      var selectedArray = $(gridDiclisttype).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionDiclisttype + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winDiclisttype',
          width : 600,
          height : 300,
          modal : true,
          url : url,
          title : '修改'
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
            'info');
      }
    }
    //删除
    function delDataDiclisttype() {
      var selectedArray = $(gridDiclisttype).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridDiclisttype).datagrid('loading');
            $.post(url_delActionDiclisttype + '?ids=' + $.farm.getCheckedIds(gridDiclisttype,'ID'), {},
              function(flag) {
            	var jsonObject = JSON.parse(flag, null);
                $(gridDiclisttype).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridDiclisttype).datagrid('reload');
              } else {
                var str = MESSAGE_PLAT.ERROR_SUBMIT
                    + flag.pageset.message;
                $.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
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
