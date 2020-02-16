var wcpGraphDialog;
var wcpGraphEditor;
KindEditor.plugin('wcprelation', function(K) {
	wcpGraphEditor = this, name = 'wcprelation';
	wcpGraphEditor.clickToolbar(name,function() {
		wcpGraphDialog = K
			.dialog( {
				width : 700,
				height: 350, 
				title : '选择知识图谱',
				body : getHtml(),
				closeBtn : {
					name : '关闭',
					click : function(e) {
						wcpGraphDialog.remove();
					}
				}
			});
		loadGraph_wcpGraph();
		$('#wcpGraph_button_id').bind('click',function() {
			loadGraph_wcpGraph($('#wcpGraph_input_id').val());
		});
		$('#wcpGraph_input_id').keydown(function(e){
			if(e.keyCode==13){
				loadGraph_wcpGraph($(
				'#wcpGraph_input_id')
				.val());
			}
		});
	});
});
function getHtml(){
	//图谱框（wcp-relation-graph-id 加载图谱的框）
	var graphHtml='<div id="wcp-relation-graph-id" class="wcp-relation-graph"><center><img style="margin-top:50px;" src="text/lib/kindeditor/themes/common/graphDemo.png"/></center></div>';  
	//知识点列表框(knowPointListBoxId 加载知识点列表的框)
	var listHtml='<table class="table table-condensed"><tr><td ><input id="wcpGraph_input_id" type="text"/>'+'<span class="ke-button-common ke-button-outer ke-dialog-yes" title="查找">'
	+'<input class="ke-button-common ke-button" id="wcpGraph_button_id" value="查找" type="button">'
	+'</span>'+'</td></tr><tr><td><div style="overflow: scroll;height: 330px; "><table class="table table-condensed"  id="knowPointListBoxId"></table><div></td></tr></table>';
	//窗口结构框 
	var html='<div><table class="table table-bordered"><tr> <td class="wcp-relation-td1">'+listHtml+'</td><td class="success wcp-relation-td2">'+graphHtml+'</td></tr></table></div>';
	return html;
} 
function loadGraph_wcpGraph(Graphtitle) {
	$('#knowPointListBoxId').html('loading...');
	$.post('gridkpoint/loadGraphPointList.do', {'key' : Graphtitle}, function(flag) {
		if (flag.points.length > 0) {
			$('#knowPointListBoxId').html('<tr><th >知识点名称</th><th width="120">操作</th></tr>');
			$(flag.points).each(
					function(i, obj) {
						$('#knowPointListBoxId').append('<tr><td>'+obj.name+'</td><td>'+'<a title="'+obj.name+'" onClick="clickLink_insertGraph(this)" id="'
								+ obj.id + '" >插入图谱</a>'+'&nbsp;&nbsp;<a title="'+obj.name+'" onClick="clickLink_showGraph(this)" id="'
								+ obj.id + '" >预览图谱</a>'+'</td></tr>'
								);
					});
		} else {
			alert('未匹配到相关知识点!');
		}
	}, 'json');
}
//插入图谱
function clickLink_insertGraph(flag) {
	var id = $(flag).attr('id'); 
	var title = $(flag).attr('title');
	var tag='<br/><div class="ke-wcp-graph"> <a href="'+id+'"><img src="text/img/graphDemo.png" width="128" height="128" /></a><br/>知识图谱:'+title+'<br/><span> 知识展示时将加载知识图谱</span></div><br/>';
	wcpGraphEditor.insertHtml(tag);
	wcpGraphDialog.remove();
}
//预览图谱
function clickLink_showGraph(flag) {
	var id = $(flag).attr('id');
	var title = $(flag).attr('title');
	$('#wcp-relation-graph-id').load("gridkpoint/PubloadGraph.do?pointId="+id);
}
KindEditor.lang({wcprelation : '插入知识图谱'});
