<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>用户答卷数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchPaperuserownForm">
        <table class="editTable">
          <tr style="text-align: center;">
            <td colspan="4">
              <a id="a_search" href="javascript:void(0)"
                class="easyui-linkbutton" iconCls="icon-search">查询</a>
              <a id="a_reset" href="javascript:void(0)"
                class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
            </td>
          </tr>
        </table>
      </form>
    </div>
    <div data-options="region:'center',border:false">
      <table id="dataPaperuserownGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="ROOMNAME" data-options="sortable:true" width="80">ROOMNAME</th>
            <th field="PAPERNAME" data-options="sortable:true" width="90">PAPERNAME</th>
            <th field="ROOMID" data-options="sortable:true" width="60">ROOMID</th>
            <th field="PAPERID" data-options="sortable:true" width="70">PAPERID</th>
            <th field="MODELTYPE" data-options="sortable:true" width="90">MODELTYPE</th>
            <th field="PCONTENT" data-options="sortable:true" width="80">PCONTENT</th>
            <th field="PSTATE" data-options="sortable:true" width="60">PSTATE</th>
            <th field="CUSERNAME" data-options="sortable:true" width="90">CUSERNAME</th>
            <th field="CUSER" data-options="sortable:true" width="50">CUSER</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionPaperuserown = "paperuserown/del.do";//删除URL
    var url_formActionPaperuserown = "paperuserown/form.do";//增加、修改、查看URL
    var url_searchActionPaperuserown = "paperuserown/query.do";//查询URL
    var title_windowPaperuserown = "用户答卷管理";//功能名称
    var gridPaperuserown;//数据表格对象
    var searchPaperuserown;//条件查询组件对象
    var toolBarPaperuserown = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataPaperuserown
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataPaperuserown
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataPaperuserown
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataPaperuserown
    } ];
    $(function() {
      //初始化数据表格
      gridPaperuserown = $('#dataPaperuserownGrid').datagrid( {
        url : url_searchActionPaperuserown,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarPaperuserown,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchPaperuserown = $('#searchPaperuserownForm').searchForm( {
        gridObj : gridPaperuserown
      });
    });
    //查看
    function viewDataPaperuserown() {
      var selectedArray = $(gridPaperuserown).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionPaperuserown + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winPaperuserown',
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
    function addDataPaperuserown() {
      var url = url_formActionPaperuserown + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winPaperuserown',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataPaperuserown() {
      var selectedArray = $(gridPaperuserown).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionPaperuserown + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winPaperuserown',
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
    function delDataPaperuserown() {
      var selectedArray = $(gridPaperuserown).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridPaperuserown).datagrid('loading');
            $.post(url_delActionPaperuserown + '?ids=' + $.farm.getCheckedIds(gridPaperuserown,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridPaperuserown).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridPaperuserown).datagrid('reload');
              } else {
                var str = MESSAGE_PLAT.ERROR_SUBMIT
                    + jsonObject.MESSAGE;
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
</html>