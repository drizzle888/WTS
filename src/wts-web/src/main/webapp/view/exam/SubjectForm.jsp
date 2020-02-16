<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--考题表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formSubject">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        当前版本ID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[16]']"
          id="entity_versionid" name="versionid" value="${entity.versionid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        题库分类:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[16]']"
          id="entity_typeid" name="typeid" value="${entity.typeid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        试题解析:
        </td>
        <td colspan="3">
          <textarea rows="2" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[32,767.5]']"
          id="entity_tipanalysis" name="tipanalysis">${entity.tipanalysis}</textarea>
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entitySubject" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entitySubject" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formSubject" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionSubject = 'subject/add.do';
  var submitEditActionSubject = 'subject/edit.do';
  var currentPageTypeSubject = '${pageset.operateType}';
  var submitFormSubject;
  $(function() {
    //表单组件对象
    submitFormSubject = $('#dom_formSubject').SubmitForm( {
      pageType : currentPageTypeSubject,
      grid : gridSubject,
      currentWindowId : 'winSubject'
    });
    //关闭窗口
    $('#dom_cancle_formSubject').bind('click', function() {
      $('#winSubject').window('close');
    });
    //提交新增数据
    $('#dom_add_entitySubject').bind('click', function() {
      submitFormSubject.postSubmit(submitAddActionSubject);
    });
    //提交修改数据
    $('#dom_edit_entitySubject').bind('click', function() {
      submitFormSubject.postSubmit(submitEditActionSubject);
    });
  });
  //-->
</script>