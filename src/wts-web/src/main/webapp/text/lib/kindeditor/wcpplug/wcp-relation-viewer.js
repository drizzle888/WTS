//用来展示知识时初始化知识图谱
$(function() {
	initWcpGraph();
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		initWcpGraph();
	})
});
//打开一个知识点URL
function openPointKnow(urlid) {
	window.open(baseUrl+"/gpoint/view/Pub" + urlid + ".html");
}
//初始化text中的知识图谱
function initWcpGraph(){
	$('.ke-wcp-graph').each(function(i, obj) {
		$(obj).height(400);
		var id = $(obj).find("a").attr('href');
		$(obj).load("gridkpoint/PubloadGraph.do?pointId=" + id);
	});
}