<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答题卡表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formCard">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        備注:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[128]']"
          id="entity_pcontent" name="pcontent" value="${entity.pcontent}">
        </td>
      </tr>
      <tr>
        <td class="title">
        答题交卷时间:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[8]']"
          id="entity_endtime" name="endtime" value="${entity.endtime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        状态:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[1]']"
          id="entity_pstate" name="pstate" value="${entity.pstate}">
        </td>
      </tr>
      <tr>
        <td class="title">
        答题开始时间:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[8]']"
          id="entity_starttime" name="starttime" value="${entity.starttime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        阅卷时间:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[8]']"
          id="entity_adjudgetime" name="adjudgetime" value="${entity.adjudgetime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        阅卷人:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[16]']"
          id="entity_adjudgeuser" name="adjudgeuser" value="${entity.adjudgeuser}">
        </td>
      </tr>
      <tr>
        <td class="title">
        得分:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[6]']"
          id="entity_point" name="point" value="${entity.point}">
        </td>
      </tr>
      <tr>
        <td class="title">
        人员ID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_userid" name="userid" value="${entity.userid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        考场ID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_roomid" name="roomid" value="${entity.roomid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        考卷ID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_paperid" name="paperid" value="${entity.paperid}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityCard" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityCard" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formCard" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionCard = 'card/add.do';
  var submitEditActionCard = 'card/edit.do';
  var currentPageTypeCard = '${pageset.operateType}';
  var submitFormCard;
  $(function() {
    //表单组件对象
    submitFormCard = $('#dom_formCard').SubmitForm( {
      pageType : currentPageTypeCard,
      grid : gridCard,
      currentWindowId : 'winCard'
    });
    //关闭窗口
    $('#dom_cancle_formCard').bind('click', function() {
      $('#winCard').window('close');
    });
    //提交新增数据
    $('#dom_add_entityCard').bind('click', function() {
      submitFormCard.postSubmit(submitAddActionCard);
    });
    //提交修改数据
    $('#dom_edit_entityCard').bind('click', function() {
      submitFormCard.postSubmit(submitEditActionCard);
    });
  });
  //-->
</script>