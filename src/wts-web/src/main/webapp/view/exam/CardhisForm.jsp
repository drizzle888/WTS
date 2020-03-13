<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答题卡历史记录表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formCardhis">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        ALLNUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[5]']"
          id="entity_allnum" name="allnum" value="${entity.allnum}">
        </td>
      </tr>
      <tr>
        <td class="title">
        COMPLETENUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[5]']"
          id="entity_completenum" name="completenum" value="${entity.completenum}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ENDTIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[8]']"
          id="entity_endtime" name="endtime" value="${entity.endtime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PSTATE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[1]']"
          id="entity_pstate" name="pstate" value="${entity.pstate}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PCONTENT:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[128]']"
          id="entity_pcontent" name="pcontent" value="${entity.pcontent}">
        </td>
      </tr>
      <tr>
        <td class="title">
        STARTTIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[8]']"
          id="entity_starttime" name="starttime" value="${entity.starttime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ADJUDGETIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[8]']"
          id="entity_adjudgetime" name="adjudgetime" value="${entity.adjudgetime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ADJUDGEUSERNAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[32]']"
          id="entity_adjudgeusername" name="adjudgeusername" value="${entity.adjudgeusername}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ADJUDGEUSER:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[16]']"
          id="entity_adjudgeuser" name="adjudgeuser" value="${entity.adjudgeuser}">
        </td>
      </tr>
      <tr>
        <td class="title">
        POINT:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[6]']"
          id="entity_point" name="point" value="${entity.point}">
        </td>
      </tr>
      <tr>
        <td class="title">
        USERID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_userid" name="userid" value="${entity.userid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ROOMID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_roomid" name="roomid" value="${entity.roomid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ROOMNAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_roomname" name="roomname" value="${entity.roomname}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PAPERID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_paperid" name="paperid" value="${entity.paperid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PAPERNAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[64]']"
          id="entity_papername" name="papername" value="${entity.papername}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityCardhis" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityCardhis" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formCardhis" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionCardhis = 'cardhis/add.do';
  var submitEditActionCardhis = 'cardhis/edit.do';
  var currentPageTypeCardhis = '${pageset.operateType}';
  var submitFormCardhis;
  $(function() {
    //表单组件对象
    submitFormCardhis = $('#dom_formCardhis').SubmitForm( {
      pageType : currentPageTypeCardhis,
      grid : gridCardhis,
      currentWindowId : 'winCardhis'
    });
    //关闭窗口
    $('#dom_cancle_formCardhis').bind('click', function() {
      $('#winCardhis').window('close');
    });
    //提交新增数据
    $('#dom_add_entityCardhis').bind('click', function() {
      submitFormCardhis.postSubmit(submitAddActionCardhis);
    });
    //提交修改数据
    $('#dom_edit_entityCardhis').bind('click', function() {
      submitFormCardhis.postSubmit(submitEditActionCardhis);
    });
  });
  //-->
</script>