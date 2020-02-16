<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>用户消息数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchUsermessageForm">
        <table class="editTable">
          <tr>
            <td class="title">
              阅读人:
            </td>
            <td>
        <input name="USER.NAME:like" type="text">
            </td>
            <td class="title"></td>
            <td></td>
          </tr>
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
      <table id="dataUsermessageGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="READUSERNAME" data-options="sortable:true" width="40">阅读人</th>
            <th field="TITLE" data-options="sortable:true" width="40">消息主题</th>
            <th field="READSTATE" data-options="sortable:true" width="40">阅读状态</th>
            <th field="CONTENT" data-options="sortable:true" width="120">消息内容</th>
            <th field="SENDUSERNAME" data-options="sortable:true" width="40">发送人</th>
            <th field="USERMESSAGECTIME" data-options="sortable:true" width="40">发送时间</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionUsermessage = "usermessage/del.do";//删除URL
    var url_formActionUsermessage = "usermessage/form.do";//增加、修改、查看URL
    var url_searchActionUsermessage = "usermessage/query.do";//查询URL
    var title_windowUsermessage = "用户消息管理";//功能名称
    var gridUsermessage;//数据表格对象
    var searchUsermessage;//条件查询组件对象
    var toolBarUsermessage = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataUsermessage
    }, {
      id : 'add',
      text : '发送消息',
      iconCls : 'icon-email',
      handler : addDataUsermessage
    }, {
      //id : 'edit',
     // text : '修改',
     // iconCls : 'icon-edit',
     // handler : editDataUsermessage
    //}, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataUsermessage
    } ];
    $(function() {
      //初始化数据表格
      gridUsermessage = $('#dataUsermessageGrid').datagrid( {
        url : url_searchActionUsermessage,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarUsermessage,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchUsermessage = $('#searchUsermessageForm').searchForm( {
        gridObj : gridUsermessage
      });
    });
    //查看
    function viewDataUsermessage() {
      var selectedArray = $(gridUsermessage).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionUsermessage + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winUsermessage',
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
    function addDataUsermessage() {
      var url = url_formActionUsermessage + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winUsermessage',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataUsermessage() {
      var selectedArray = $(gridUsermessage).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionUsermessage + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winUsermessage',
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
    function delDataUsermessage() {
      var selectedArray = $(gridUsermessage).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridUsermessage).datagrid('loading');
            $.post(url_delActionUsermessage + '?ids=' + $.farm.getCheckedIds(gridUsermessage,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridUsermessage).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridUsermessage).datagrid('reload');
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