package com.farm.util.cache;

/**
 * 缓存名称
 * 
 * @author macpl
 *
 */
public enum FarmCacheName {
	// 知识缓存
	DocCache("wcp-cache-docs"),
	// 知识分类缓存（用于sevice中获得知识的分类一般缓存设置于1秒之内）
	DocTypeCache("wcp-cache-doctype"),
	//获得专家的userids
	ExperUserIds("wcp-expert-userids"),
	// 附件緩存
	FileCache("wcp-cache-files"),
	// 分享鏈接緩存1
	ShareLevel1Cache("wcp-share-level1"),
	// 分享鏈接緩存2
	ShareLevel2Cache("wcp-share-level2"),
	// 分享鏈接緩存3
	ShareLevel3Cache("wcp-share-level3");
	/**
	 * 持久缓存
	 */
	private String permanentCacheName;

	FarmCacheName(String permanentCacheName) {
		this.permanentCacheName = permanentCacheName;
	}

	/**
	 * 如果只有一个缓存就是这个持久缓缓存
	 * 
	 * @return
	 */
	public String getPermanentCacheName() {
		return permanentCacheName;
	}
}
