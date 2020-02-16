


/**
 * //如果要启用草稿箱，必须在jsp页面中加入下面函数，参数是定时保存草稿的秒数,当然也得添加本js文件到对应的页面中，同时在kindeditor编辑器中启用控件wcpdraft
 *runAotoSubmitDraft(600);
 */


var wcp_draft_editor;
var wcpdraftKnowdialog;
KindEditor.plugin('wcpdraft', function(K) {
	wcp_draft_editor = this, name = 'wcpdraft';
	// 点击图标时执行
	wcp_draft_editor.clickToolbar(name, function() {
		wcpdraftKnowdialog = K
		.dialog( {
			width : 600,
			title : '草稿',
			body : '<div style="margin:10px;height:600;text-align: center;height:100%;overflow:auto;">'+
			       '<p style="height: 25px; width: 100%;color:green;overflow:auto;" id="draftTimeBoxId"></p>'+
				   '<p style="height: 500px; width: 100%;overflow:auto;text-align: left;" id="draftBoxId"></p>'+
				   '<span class="ke-button-common ke-button-outer ke-dialog-yes" title="保存草稿"><input class="ke-button-common ke-button" id="draft_save_button_id" value="保存草稿" type="button"></span>'+
			       '<span class="ke-button-common ke-button-outer ke-dialog-yes" title="恢复草稿"><input class="ke-button-common ke-button" id="draft_button_id" value="恢复草稿" type="button"></span></div>',
			closeBtn : {
				name : '关闭',
				click : function(e) {
					wcpdraftKnowdialog.remove();
				}
			}
		});
		 loadDraftToBox();
		 RUN_AOTOTASK=true;
		 $('#draft_button_id').click(function(){
			 reoverDraftToEditor();
		 });
		 $('#draft_save_button_id').click(function(){
			 RUN_AOTOTASK=true;
			 myAotoSubmitInterval();
		 });
		 
	});
});
KindEditor.lang({
	wcpdraft : '草稿'
});
/**
 * 将草稿箱中的草稿覆盖到文本编辑器中
 */
function reoverDraftToEditor(){
	if(confirm("是否将草稿箱中的草稿覆盖到文本编辑器中？")){
		 wcp_draft_editor.html($('#draftBoxId').html());
		 wcpdraftKnowdialog.remove();
	}
}
/**定时任务，提交草稿
 * @param time_s 多久自動保存一遍，
 */
function runAotoSubmitDraft(time_s){
	if(!wcp_draft_editor.html()){
		$.post('docdraft/findKnow.do',{},function(flag){
			if(flag.entity){
				if(flag.entity.content){
					if(confirm("是否将草稿箱中的草稿覆盖到文本编辑器中？")){
						 wcp_draft_editor.html(flag.entity.content);
					}
				}	
			}
		},"json");
	}
	setInterval("myAotoSubmitInterval()",time_s*1000);//1000为1秒钟
}

var RUN_AOTOTASK = true; //启动及关闭定时任务
/**
 * 提交草稿
 */
function myAotoSubmitInterval()
{
	if(!RUN_AOTOTASK){
		return;
	}
	//判断是否编辑器中有内容
	if(!wcp_draft_editor.html()){
		return;
	}
	var contentText=wcp_draft_editor.html();
	
	try {
		if (wcpdraftKnowdialog) {
			wcpdraftKnowdialog.remove();
		}
	} catch (e) {
		//因为该变量没有初始化
	}
	
	Modal.confirm({
		msg : "是否将当前内容保存到草稿箱中，该操作将覆盖原有草稿？"
	}).on(function(e) {
		if(e){
			if(contentText.length>1000000){
				alert("正文超长"+contentText.length+"，无法保存为草稿！");
				return;
			}
			$('#draftBoxId').html(wcp_draft_editor.html());
			$.post('docdraft/addKnow.do',{text:wcp_draft_editor.html()},function(flag){
				//{MESSAGE=文本为空，可能是因为文本太大无法保存到草稿箱，请及时提交保存！, STATE=1, OPERATE=1}
				if(flag.STATE==0){
					loadDraftToBox();
				}else{
					alert(flag.MESSAGE);
				}
			},"json");
		}else{
			RUN_AOTOTASK=false;
		}
	});
}
//加载草稿到草稿箱控件中
function loadDraftToBox(){
	$('#draftTimeBoxId').html("loading...");
	$.post('docdraft/findKnow.do',{},function(flag){
		if(flag.entity){
			$('#draftBoxId').html(flag.entity.content);
			$('#draftTimeBoxId').html("当前草稿保存于"+flag.entity.etime);
		}else{
			$('#draftTimeBoxId').html("无草稿");
		}
	},"json");
}