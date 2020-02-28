<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--用户答题统计表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formExamstat">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        PAPERNUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_papernum" name="papernum" value="${entity.papernum}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ERRORSUBNUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_errorsubnum" name="errorsubnum" value="${entity.errorsubnum}">
        </td>
      </tr>
      <tr>
        <td class="title">
        SUBJECTNUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_subjectnum" name="subjectnum" value="${entity.subjectnum}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PCONTENT:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[64]']"
          id="entity_pcontent" name="pcontent" value="${entity.pcontent}">
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
        EUSER:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_euser" name="euser" value="${entity.euser}">
        </td>
      </tr>
      <tr>
        <td class="title">
        EUSERNAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_eusername" name="eusername" value="${entity.eusername}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CUSER:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_cuser" name="cuser" value="${entity.cuser}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CUSERNAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_cusername" name="cusername" value="${entity.cusername}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ETIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[8]']"
          id="entity_etime" name="etime" value="${entity.etime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CTIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[8]']"
          id="entity_ctime" name="ctime" value="${entity.ctime}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityExamstat" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityExamstat" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formExamstat" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionExamstat = 'examstat/add.do';
  var submitEditActionExamstat = 'examstat/edit.do';
  var currentPageTypeExamstat = '${pageset.operateType}';
  var submitFormExamstat;
  $(function() {
    //表单组件对象
    submitFormExamstat = $('#dom_formExamstat').SubmitForm( {
      pageType : currentPageTypeExamstat,
      grid : gridExamstat,
      currentWindowId : 'winExamstat'
    });
    //关闭窗口
    $('#dom_cancle_formExamstat').bind('click', function() {
      $('#winExamstat').window('close');
    });
    //提交新增数据
    $('#dom_add_entityExamstat').bind('click', function() {
      submitFormExamstat.postSubmit(submitAddActionExamstat);
    });
    //提交修改数据
    $('#dom_edit_entityExamstat').bind('click', function() {
      submitFormExamstat.postSubmit(submitEditActionExamstat);
    });
  });
  //-->
</script>