<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>问卷答案数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchCardAnswerForm">
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
      <table id="dataCardAnswerGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="PSTATE" data-options="sortable:true" width="20">狀態</th>
            <th field="PCONTENT" data-options="sortable:true" width="20">備注</th>
            <th field="CTIME" data-options="sortable:true" width="40">提交时间</th>
            <th field="VALSTR" data-options="sortable:true" width="30">答案值</th>
            <th field="CUSER" data-options="sortable:true" width="30">提交人</th>
            <th field="VERSIONID" data-options="sortable:true" width="50">题版本ID</th>
            <th field="ANSWERID" data-options="sortable:true" width="40">答案ID</th>
            <th field="CARDID" data-options="sortable:true" width="50">答题卡ID</th>
            <th field="ID" data-options="sortable:true" width="20">主键</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionCardAnswer = "cardAnswer/del.do";//删除URL
    var url_formActionCardAnswer = "cardAnswer/form.do";//增加、修改、查看URL
    var url_searchActionCardAnswer = "cardAnswer/query.do";//查询URL
    var title_windowCardAnswer = "问卷答案管理";//功能名称
    var gridCardAnswer;//数据表格对象
    var searchCardAnswer;//条件查询组件对象
    var toolBarCardAnswer = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataCardAnswer
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataCardAnswer
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataCardAnswer
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataCardAnswer
    } ];
    $(function() {
      //初始化数据表格
      gridCardAnswer = $('#dataCardAnswerGrid').datagrid( {
        url : url_searchActionCardAnswer,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarCardAnswer,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchCardAnswer = $('#searchCardAnswerForm').searchForm( {
        gridObj : gridCardAnswer
      });
    });
    //查看
    function viewDataCardAnswer() {
      var selectedArray = $(gridCardAnswer).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionCardAnswer + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winCardAnswer',
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
    function addDataCardAnswer() {
      var url = url_formActionCardAnswer + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winCardAnswer',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataCardAnswer() {
      var selectedArray = $(gridCardAnswer).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionCardAnswer + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winCardAnswer',
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
    function delDataCardAnswer() {
      var selectedArray = $(gridCardAnswer).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridCardAnswer).datagrid('loading');
            $.post(url_delActionCardAnswer + '?ids=' + $.farm.getCheckedIds(gridCardAnswer,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridCardAnswer).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridCardAnswer).datagrid('reload');
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