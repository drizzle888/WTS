package com.farm.util.cache;

/**
 * 缓存名称
 * 
 * @author macpl
 *
 */
public enum FarmCacheNames {
	/**
	 * 获得分类知识（用于知识访问页面的知识推送）
	 */
	TypeDocsForDocpage("wcp-typedocsfordocpage"),
	/**
	 * 获取未读的消息
	 */
	UnReadMessagesByUser("wcp-unreadmessagesbyuser"),
	/**
	 * 获得所有专题,可用根据状态和id进行筛选（用于前台展示）
	 */
	AllSpecial("wcp-allspecial"),
	
	/**
	 * 获得所有专题,可用根据状态和id进行筛选（用于前台展示）
	 */
	TypeDocs("wcp-typedocs"),
	/**
	 * 获得用户所有（被授权阅读的受限分类的分类的ID）（阅读） ///////受限分类是相对于未分配权限的分类（未分配权限的分类，所有人均可访问）
	 */
	UserReadTypeids("wcp-userreadtypeids"),
	/**
	 * 获取知识分类信息（带分类下知识数量）用于创建知识时候的
	 */
	TypesForWriteDoc("wcp-typesforwritedoc"),
	/**
	 * 获取问题分类信息（带分类下问题数量）用于创建问题时候的
	 */
	FqaTypesForWriteDoc("wcp-fqatypesforwritedoc"),
	/**
	 * 获取知识分类信息（带分类下知识数量）
	 */
	PopTypesForReadDoc("wcp-poptypesforreaddoc"),
	/**
	 *  获取问答分类信息（带分类下问答数量）
	 */
	PopFqaTypesForReadDoc("wcp-popfqatypesforreaddoc"),
	/**
	 * 获取公开的知识分类信息
	 */
	pubTypeids("wcp-pubtypeids"),
	/**
	 * 获得分类所有子节点（包含它自己）
	 */
	AllSubType("wcp-allsubnode"),
	/**
	 * 获取知识分类详细信息（带分类下知识数量）（下级分类和下下级别分类）
	 */
	TypeInfos("wcp-typeinfos"),
	/**
	 *  获取友情链接集合,所有的
	 */
	WebUrlList("wcp-weburllist"),
	/**
	 * 获取友情链接集合,不登录显示的
	 */
	WebUrlListNoLogin("wcp-weburllistnologin"),
	/**
	 * 获得某分类下的问答
	 */
	TypeFqas("wcp-typefqas"),
	/**
	 * 最新的公开知识的评论
	 */
	NewPublicDocMessages("wcp-newpublicdocmessages"),
	/**
	 * 最热已完成问答
	 */
	HotQuestionByFinish("wcp-hotquestionbyfinish"),
	/**
	 * 最热待完成问答
	 */
	HotQuestionByWaiting("wcp-hotquestionbywaiting"),
	/**
	 * 最热问答
	 */
	HotQuestion("wcp-hotquestion"),
	/**
	 *展示最新知识
	 */
	NewKnowList("wcp-newknowlist"),
	/**
	 * 获得最热知识
	 */
	PubHotDoc("wcp-pubhotdoc"),
	/**
	 *获得置顶知识
	 */
	PubTopdoc("wcp-pubtopdoc"),
	/**
	 *获得好评用户
	 */
	StatGoodUsers("wcp-statgoodusers"),
	/**
	 *获得好评小组
	 */
	StatGoodGroups("wcp-statgoodgroups"),
	/**
	 *最多知识用户
	 */
	StatMostUsers("wcp-statmostusers"),
	/**
	 *好评文档
	 */
	StatGoodDocs("wcp-statgooddocs"),
	/**
	 *差评文档
	 */
	StatBadDocs("wcp-statbaddocs"),
	/**
	 *整体用量(获得每天的知识总数、好评数、差评数)
	 */
	StatNumForDay("wcp-statnumforday"),
	/**
	 * 用户知识统计
	 */
	StatUser("wcp-statuser"),
	/**
	 * 获得最热门小组
	 */
	HotDocGroups("wcp-hotdocgroups");
	
	/**
	 * 持久缓存
	 */
	private String permanentCacheName;
	/**
	 * 动态缓存
	 */
	private String liveCacheName;

	FarmCacheNames(String permanentCacheName) {
		this.permanentCacheName = permanentCacheName;
		this.liveCacheName = permanentCacheName + "-live";
	}

	/**
	 * 如果只有一个缓存就是这个持久缓缓存
	 * 
	 * @return
	 */
	public String getPermanentCacheName() {
		return permanentCacheName;
	}

	/**
	 * 动态缓存，短时间的缓存
	 * 
	 * @return
	 */
	public String getLiveCacheName() {
		return liveCacheName;
	}

}
