<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--题库权限表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formSubjectpop">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        分类id:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_typeid" name="typeid" value="${entity.typeid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        用户名称:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_username" name="username" value="${entity.username}">
        </td>
      </tr>
      <tr>
        <td class="title">
        用户id:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_userid" name="userid" value="${entity.userid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        权限类型:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[0.5]']"
          id="entity_funtype" name="funtype" value="${entity.funtype}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entitySubjectpop" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entitySubjectpop" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formSubjectpop" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionSubjectpop = 'subjectpop/add.do';
  var submitEditActionSubjectpop = 'subjectpop/edit.do';
  var currentPageTypeSubjectpop = '${pageset.operateType}';
  var submitFormSubjectpop;
  $(function() {
    //表单组件对象
    submitFormSubjectpop = $('#dom_formSubjectpop').SubmitForm( {
      pageType : currentPageTypeSubjectpop,
      grid : gridSubjectpop,
      currentWindowId : 'winSubjectpop'
    });
    //关闭窗口
    $('#dom_cancle_formSubjectpop').bind('click', function() {
      $('#winSubjectpop').window('close');
    });
    //提交新增数据
    $('#dom_add_entitySubjectpop').bind('click', function() {
      submitFormSubjectpop.postSubmit(submitAddActionSubjectpop);
    });
    //提交修改数据
    $('#dom_edit_entitySubjectpop').bind('click', function() {
      submitFormSubjectpop.postSubmit(submitEditActionSubjectpop);
    });
  });
  //-->
</script>