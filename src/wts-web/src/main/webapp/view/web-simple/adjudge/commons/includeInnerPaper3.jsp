<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="col-md-6" style="padding-left: 8px; padding-right: 8px;">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="media" style="height: 153px; overflow: hidden;">
				<div
					style="text-align: center; border-bottom: 1px dashed #ccc; margin-bottom: 8px; padding-bottom: 0px;">
					<div class="doc_node_title_box"
						style="font-size: 16px; white-space: nowrap;">${paper.info.name}</div>
				</div>
				<div class="pull-right">
					<img alt="练习室" style="width: 64px; height: 64px;"
						src="text/img/random.png">
				</div>
				<div class="media-body">
					<div style="margin-left: 4px;" class="pull-left">
						<div class="side_unit_info">题量：共${paper.subjectNum}道题</div>
						<div class="side_unit_info">随机答题,不计得分</div>
						<div class="side_unit_info">错题记录可在用户空间中查看</div>
					</div>
				</div>
				<div style="padding-top: 20px;">
					<div class="btn-group btn-group-justified" role="group"
						aria-label="...">
						<div class="btn-group" role="group">
							<a type="button" class="btn btn-default" disabled="disabled">练习题无需阅卷
							</a>
						</div>
						<div class="btn-group" role="group">
							<button onclick="exportWordPaper('${paper.info.id}')"
								type="button" class="btn btn-info">
								<i class="glyphicon glyphicon-download-alt"></i>&nbsp;导出答卷
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>