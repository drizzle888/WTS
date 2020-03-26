<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript"
	src="<PF:basePath/>text/lib/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="<PF:basePath/>text/lib/kindeditor/zh-CN.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-multiimage-kindeditor.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-latex-kindeditor.js"></script>
<link href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-multiimage-kindeditor.css" rel="stylesheet"> 
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-kindeditor.css" />
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/htmltags.js"></script>
<!-- 附件批量上传组件 -->
<script src="<PF:basePath/>text/lib/fileUpload/jquery.ui.widget.js"></script>
<script src="<PF:basePath/>text/lib/fileUpload/jquery.iframe-transport.js"></script>
<script src="<PF:basePath/>text/lib/fileUpload/jquery.fileupload.js"></script>
<link href="<PF:basePath/>text/lib/fileUpload/jquery.fileupload.css" rel="stylesheet"> 
<textarea name="text" id="contentId" style="height: 400px; width: 100%;"></textarea>
<div style="text-align: center; padding-top: 12px;">
	<a id="keditor_btn_save" class="easyui-linkbutton"
		data-options="iconCls:'icon-save'">完成</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
		id="keditor_btn_cancel" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'">取消</a>
</div>
<script type="text/javascript">
	$(function() {
		var editor = KindEditor.create('textarea[id="contentId"]', {
			resizeType : 1,
			cssPath : '<PF:basePath/>text/lib/kindeditor/editInner.css',
			uploadJson : basePath + 'actionImg/PubFPuploadMedia.do',
			formatUploadUrl : false,
			allowPreviewEmoticons : false,
			allowImageRemote : true,
			allowImageUpload : true,
			items : [ 'fontsize', 'forecolor', 'bold', 'italic', 'underline',
					'|', 'removeformat', 'justifyleft', 'justifycenter',
					'insertorderedlist', 'insertunorderedlist', 'formatblock',
					'quickformat', 'table', 'hr', 'link',
					'media',
					'code', 'wcpimgs','wcplatex', 'source' ],
			afterCreate : function() {
				//粘贴的文件直接上传到后台
				pasteImgHandle(this,basePath + 'actionImg/PubUpBase64File.do');
				//禁止粘贴图片等文件:pasteNotAble(this.edit);
			}
		});
		editor.html(getEditorHtmlFunction());
		$('#keditor_btn_save').bind('click', function() {
			var html = editor.html();
			setEditorHtmlFunction(html);
			showEditorHtmlFunction(html);
			$('#mediaEditorWin').window('close');
		});
		$('#keditor_btn_cancel').bind('click', function() {
			$('#mediaEditorWin').window('close');
		});
	});
	//kindeditor中粘贴图片直接上传到后台
	//edit 在 kindeditor的事件回调中可以通过 this.edit来获取pasteImgHandle(this.edit);
	function pasteImgHandle(editor, uploadUrl) {
		var doc = editor.edit.doc;
		var cmd = editor.edit.cmd;
		$(doc.body).bind('paste',function(ev) {
			var $this = $(this);
			var dataItem = ev.originalEvent.clipboardData.items[0];
			if (dataItem) {
				var file = dataItem.getAsFile();
				if (file) {
					var reader = new FileReader();
					reader.onload = function(evt) {
						var imageDataBase64 = evt.target.result;
						$.post(uploadUrl,{'fileDataBase64' : imageDataBase64},
							function(json) {
								if (json.error == 0) {
									//var html = '<img src="' + json.url + '" alt="'+json.fileName+'" />';
									//cmd.inserthtml(html);
									editor.exec('insertimage', json.url, "粘贴图片", undefined, undefined, undefined, undefined);
								} else {
									cmd.inserthtml(json.message);
								}
							}, 'json');
					};
					var event = ev || window.event;//兼容IE
					//取消事件相关的默认行为
					if (event.preventDefault) //标准技术
					{
						event.preventDefault();
					}
					if (event.returnValue) //兼容IE9之前的IE
					{
						event.returnValue = false;
					}
					reader.readAsDataURL(file);
				}
			}
		});
	}
</script>