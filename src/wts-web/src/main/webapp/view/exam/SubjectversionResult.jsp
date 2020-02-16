<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>考题版本数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchSubjectversionForm">
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
      <table id="dataSubjectversionGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="TIPTYPE" data-options="sortable:true" width="40">题目类型</th>
            <th field="SUBJECTID" data-options="sortable:true" width="40">试题id</th>
            <th field="TIPNOTE" data-options="sortable:true" width="40">题目描述</th>
            <th field="TIPSTR" data-options="sortable:true" width="20">题目</th>
            <th field="PCONTENT" data-options="sortable:true" width="20">备注</th>
            <th field="PSTATE" data-options="sortable:true" width="20">状态</th>
            <th field="CUSER" data-options="sortable:true" width="50">CUSER</th>
            <th field="CUSERNAME" data-options="sortable:true" width="90">CUSERNAME</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionSubjectversion = "subjectversion/del.do";//删除URL
    var url_formActionSubjectversion = "subjectversion/form.do";//增加、修改、查看URL
    var url_searchActionSubjectversion = "subjectversion/query.do";//查询URL
    var title_windowSubjectversion = "考题版本管理";//功能名称
    var gridSubjectversion;//数据表格对象
    var searchSubjectversion;//条件查询组件对象
    var toolBarSubjectversion = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataSubjectversion
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataSubjectversion
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataSubjectversion
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataSubjectversion
    } ];
    $(function() {
      //初始化数据表格
      gridSubjectversion = $('#dataSubjectversionGrid').datagrid( {
        url : url_searchActionSubjectversion,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarSubjectversion,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchSubjectversion = $('#searchSubjectversionForm').searchForm( {
        gridObj : gridSubjectversion
      });
    });
    //查看
    function viewDataSubjectversion() {
      var selectedArray = $(gridSubjectversion).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionSubjectversion + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winSubjectversion',
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
    function addDataSubjectversion() {
      var url = url_formActionSubjectversion + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winSubjectversion',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataSubjectversion() {
      var selectedArray = $(gridSubjectversion).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionSubjectversion + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winSubjectversion',
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
    function delDataSubjectversion() {
      var selectedArray = $(gridSubjectversion).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridSubjectversion).datagrid('loading');
            $.post(url_delActionSubjectversion + '?ids=' + $.farm.getCheckedIds(gridSubjectversion,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridSubjectversion).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridSubjectversion).datagrid('reload');
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