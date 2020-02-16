
var wcp_files_editor;
var wcpfilesKnowdialog;
KindEditor.plugin('wcpfile', function(K) {
	wcp_files_editor = this, name = 'wcpfile';
	// 点击图标时执行
	wcp_files_editor.clickToolbar(name, function() {
		wcpfilesKnowdialog = K
		.dialog( {
			width : 400,
			title : '批量上传附件',
			body : '<div style="margin:10px;height:400px;text-align: center;overflow:auto;">'+
			       '<div class="wcpMultiUploadButtonBox">'+
			       ' <span class="btn btn-info fileinput-button"> '+
			       '  <span>批量上传附件</span><span id="html5uploadProsess"></span>'+
			       '  <input id="fileupload" type="file" name="imgFile" multiple>'+
			       ' </span>'+
			       ' <span class="btn btn-info insertEditor" style="height:30px;padding-top:6px;font-size:12px;"> '+ 
			       '  <span>全部插入编辑器中</span>'+  
			       ' </span>'+
			       '<br/>第1步.点击"批量上传附件"按钮，上传附件。<br/>第2步.点击"全部插入编辑器"按钮，将附件插入编辑器中。</div>'+ 
				   '<div class="panel-body" style="height:300px;overflow:auto;margin-top:12px;" id="wcpimgFileListId"><div id="fileQueue"></div></div>'+
				   '</div>',
			closeBtn : {
				name : '关闭',
				click : function(e) {
					wcpfilesKnowdialog.remove();
				}
			}
		});
		$('.wcpMultiUploadButtonBox .insertEditor').click(function(){
			$('.uploadedFileUnit').each(function(i,obj){
				var fileUrl=$(obj).children("input").val();
				var fileId=$(obj).children("input").attr("name");
				var imgUrl=$(obj).children(".stream-item").children(".media").children(".pull-left").children("img").attr("src");
				var fileName=$(obj).children(".stream-item").children(".media").children(".pull-left").children("img").attr("alt");
				var tag='<br/><div class="ke-wcp-file"> <a href="webdoc/view/PubFile'+fileId+'.html"><img src="'+imgUrl+'" width="64" height="64" /><br/><span>'+fileName+'</span></a><a href="'+fileUrl+'"></a></div><br/>';
				wcp_files_editor.insertHtml(tag);
			});
			wcpfilesKnowdialog.remove();
		});
		$('.wcpMultiUploadButtonBox #fileupload').fileupload({
			url : "actionImg/PubFPupload.do",
			dataType : 'json',
			done : function(e, data) {
				var url=data.result.url;
				var error=data.result.error;
				var message=data.result.message;
				var id=data.result.id;
				var fileName=data.result.fileName;
				//alert("url:"+url);
				if(error!=0){
					wcpfilesKnowdialog.remove();
					Modal.alert({msg: message,title: '错误',btnok: '确定',btncl:'取消'});
					return;
				}
				addFileNodeByWcpFile('actionImg/PubIcon.do?id='+id, decodeURIComponent(fileName), id,url);
			},
			progressall : function(e, data) {
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$('#html5uploadProsess').text(progress + '%');
			}
		});
	});
});
function removeFile(fileId) {
	$("#file_" + fileId).remove();
} 
function addFileNodeByWcpFile(imgUrl, fileName, fileId,url) {
	var html = '<div class="col-md-12 file-block-box uploadedFileUnit" id="file_'+fileId+'">';
	html = html + '		<div class="stream-item" >';
	html = html + '				<div class="media">';
	html = html + '					<div class="pull-left">';
	html = html + '						<img  alt="'+fileName+'" src="'+imgUrl+'">';
	html = html + '					</div>';
	html = html + '					<div class="media-body" >';
	html = html + '							<i onclick="removeFile(\'' + fileId
			+ '\');" class="glyphicon glyphicon-remove pull-right" ></i>';
	html = html + '						<div class="file-title" >' + fileName + '</div>';
	html = html + '					</div>';
	html = html + '				</div>';
	html = html + '		</div>';
	html = html
			+ '<input type="hidden" name="'+fileId+'" value="'+url+'" /></div>';
	$('#wcpimgFileListId').append(html);
}
KindEditor.lang({
	wcpfile : '批量上传附件'
});
