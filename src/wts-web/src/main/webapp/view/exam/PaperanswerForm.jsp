<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--问卷答案表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formCardAnswer">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        狀態:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[1]']"
          id="entity_pstate" name="pstate" value="${entity.pstate}">
        </td>
      </tr>
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
        提交时间:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[8]']"
          id="entity_ctime" name="ctime" value="${entity.ctime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        答案值:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32,767.5]']"
          id="entity_valstr" name="valstr" value="${entity.valstr}">
        </td>
      </tr>
      <tr>
        <td class="title">
        提交人:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_cuser" name="cuser" value="${entity.cuser}">
        </td>
      </tr>
      <tr>
        <td class="title">
        题版本ID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_versionid" name="versionid" value="${entity.versionid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        答案ID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_answerid" name="answerid" value="${entity.answerid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        答题卡ID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_cardid" name="cardid" value="${entity.cardid}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityCardAnswer" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityCardAnswer" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formCardAnswer" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionCardAnswer = 'cardAnswer/add.do';
  var submitEditActionCardAnswer = 'cardAnswer/edit.do';
  var currentPageTypeCardAnswer = '${pageset.operateType}';
  var submitFormCardAnswer;
  $(function() {
    //表单组件对象
    submitFormCardAnswer = $('#dom_formCardAnswer').SubmitForm( {
      pageType : currentPageTypeCardAnswer,
      grid : gridCardAnswer,
      currentWindowId : 'winCardAnswer'
    });
    //关闭窗口
    $('#dom_cancle_formCardAnswer').bind('click', function() {
      $('#winCardAnswer').window('close');
    });
    //提交新增数据
    $('#dom_add_entityCardAnswer').bind('click', function() {
      submitFormCardAnswer.postSubmit(submitAddActionCardAnswer);
    });
    //提交修改数据
    $('#dom_edit_entityCardAnswer').bind('click', function() {
      submitFormCardAnswer.postSubmit(submitEditActionCardAnswer);
    });
  });
  //-->
</script>