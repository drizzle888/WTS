package com.farm.util.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 知识库缓存数据
 * 
 * @author wangdong
 *
 */
public class FarmCaches {
	private static FarmCaches OBJ;
	private CacheManager cacheManager;
	static final Logger log = Logger.getLogger(FarmCaches.class);

	synchronized public static FarmCaches getInstance() {
		if (OBJ == null) {
			OBJ = new FarmCaches();
			OBJ.cacheManager = CacheManager.create(FarmCaches.class.getResource("/config/WcpCacheConfig.xml"));
		}
		return OBJ;
	}

	/**
	 * 获得缓存状态信息
	 * 
	 * @return
	 */
	public Map<String, Object> getCacheInfo() {
		Map<String, Object> map = new HashMap<>();
		String[] names = cacheManager.getCacheNames();
		if (names != null) {
			for (String name : names) {
				Cache cache = cacheManager.getCache(name);
				if (cache != null) {
					map.put(name, cache.getSize());
				}
			}
		}
		return map;
	}

	/**
	 * 清空所有缓存
	 */
	public void clearAllCache() {
		String[] names = cacheManager.getCacheNames();
		if (names != null) {
			for (String name : names) {
				clearCache(name);
			}
		}
	}

	private void clearCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.removeAll();
		}
	}

	/**
	 * 清空指定缓存
	 * 
	 * @param cacheName
	 */
	public void clearCache(FarmCacheName farmCacheName) {
		clearCache(farmCacheName.getPermanentCacheName());
	}

	/**
	 * 獲得cache的數量
	 * 
	 * @param cacheName
	 * @return
	 */
	public int getCacheSize(FarmCacheName farmCacheName) {
		Cache cache = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		if (cache == null) {
			throw new RuntimeException("the cache " + farmCacheName.getPermanentCacheName() + " is not exist!");
		}
		return cache.getSize();
	}

	/**
	 * 清空缓存
	 * 
	 * @param permanentCacheName
	 * @param liveCacheName
	 */
	public void clearCache(FarmCacheNames farmCacheName) {
		clearCache(farmCacheName.getLiveCacheName());
		clearCache(farmCacheName.getPermanentCacheName());
	}

	/**
	 * 缓存是否启用（live时间为0时为禁用）
	 * 
	 * @param farmCacheName
	 * @return
	 */
	public boolean isAble(FarmCacheName farmCacheName) {
		final Cache live = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		// 如果任意缓存的存活时间时0则缓存不生效
		long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
		if (l_live_time == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 缓存是否启用（live时间为0时为禁用）
	 * 
	 * @param farmCacheName
	 * @return
	 */
	public boolean isAble(FarmCacheNames farmCacheName) {
		final Cache permanent = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		final Cache live = cacheManager.getCache(farmCacheName.getLiveCacheName());
		// 如果任意缓存的存活时间时0则缓存不生效
		long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
		long p_live_time = permanent.getCacheConfiguration().getTimeToLiveSeconds();
		if (l_live_time == 0 || p_live_time == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取缓存数据(异步)
	 * 
	 * @param key
	 *            缓存key
	 * @param generater
	 *            数据生产者
	 * @param permanentCacheName
	 *            永久缓存名称
	 * @param liveCacheName
	 *            活动缓存名称
	 * @return
	 */
	public Object getCacheData(final String key, final FarmCacheGenerater generater,
			final FarmCacheNames farmCacheName) {
		synchronized (key + farmCacheName.getLiveCacheName()) {
			final Cache permanent = cacheManager.getCache(farmCacheName.getPermanentCacheName());
			final Cache live = cacheManager.getCache(farmCacheName.getLiveCacheName());
			{
				// 如果任意缓存的存活时间时0则缓存不生效
				long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
				long p_live_time = permanent.getCacheConfiguration().getTimeToLiveSeconds();
				if (l_live_time == 0 || p_live_time == 0) {
					log.info("--------------------FarmCache--禁用缓存" + farmCacheName.getPermanentCacheName()
							+ " -------------------");
					return generater.generateData();
				}
			}
			// 是否是异步加载缓存
			final boolean isAsynchronous = true;
			// 先从二级里面取，
			Element result = null;
			result = live.get(key);
			// 取到就返回，取不到就从一级里面取
			if (result != null) {
				log.info("--------------------FarmCache--:返回(" + key + ")生存数据--------"
						+ farmCacheName.getPermanentCacheName() + "/" + farmCacheName.getLiveCacheName()
						+ "-------------------");
				return result.getObjectValue();
			} else {
				result = permanent.get(key);
			}
			if (result != null) {
				// ，取到就返回
				{// 并且启动一个线程更新二级缓存和一级缓存，
					Thread thread = new Thread(new Runnable() {
						public void run() {
							Object data = generater.generateData();
							Element element = new Element(key, data);
							permanent.put(element);
							live.put(element);
							if (isAsynchronous) {
								log.info("--------------------FarmCache--:异步填充(" + key + ")数据--------"
										+ farmCacheName.getPermanentCacheName() + "/" + farmCacheName.getLiveCacheName()
										+ "-------------------");
							} else {
								log.info("--------------------FarmCache--:同步填充(" + key + ")数据--------"
										+ farmCacheName.getPermanentCacheName() + "/" + farmCacheName.getLiveCacheName()
										+ "-------------------");
							}
						}
					});
					if (isAsynchronous) {
						thread.start();
					} else {
						thread.run();
					}
				}
				log.info("--------------------FarmCache--:返回(" + key + ")过期数据--------"
						+ farmCacheName.getPermanentCacheName() + "/" + farmCacheName.getLiveCacheName()
						+ "-------------------");
				return result.getObjectValue();
			} else {
				// 取不到就直接更新二级缓存和一级缓存后再返回
				Object data = generater.generateData();
				Element element = new Element(key, data);
				permanent.put(element);
				live.put(element);
				log.info("--------------------FarmCache--:返回(" + key + ")实时数据--------"
						+ farmCacheName.getPermanentCacheName() + "/" + farmCacheName.getLiveCacheName()
						+ "-------------------");
				return data;
			}
		}
	}

	/**
	 * 获取缓存数据（同步）
	 * 
	 * @param key
	 *            缓存key
	 * @param generater
	 *            数据生产者
	 * @param CacheName
	 *            活动缓存名称
	 * @return
	 */
	public Object getCacheData(String key, FarmCacheGenerater generater, FarmCacheName farmCacheName) {
		synchronized (key + farmCacheName.getPermanentCacheName()) {
			final Cache live = cacheManager.getCache(farmCacheName.getPermanentCacheName());
			{
				// 如果任意缓存的存活时间时0则缓存不生效
				long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
				if (l_live_time == 0) {
					log.info("--------------------FarmCache--禁用缓存" + farmCacheName.getPermanentCacheName()
							+ " -------------------");
					return generater.generateData();
				}
			}
			// 先从二级里面取，
			Element result = null;
			result = live.get(key);
			// 取到就返回，取不到就从一级里面取
			if (result != null) {
				log.info("--------------------FarmCache--:返回(" + key + ")生存数据--------"
						+ farmCacheName.getPermanentCacheName() + "-------------------");
				return result.getObjectValue();
			} else {
				// 取不到就直接更新二级缓存和一级缓存后再返回
				Object data = generater.generateData();
				Element element = new Element(key, data);
				live.put(element);
				log.info("--------------------FarmCache--:返回(" + key + ")实时数据--------"
						+ farmCacheName.getPermanentCacheName() + "-------------------");
				return data;
			}
		}
	}

	/**
	 * 记录数据到缓存中
	 * 
	 * @param key
	 * @param val
	 * @param CacheName
	 * @return
	 */
	public void putCacheData(String key, Object val, FarmCacheName farmCacheName) {
		final Cache live = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		{
			// 如果任意缓存的存活时间时0则缓存不生效
			long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
			if (l_live_time == 0) {
				log.info("--------------------FarmCache--禁用缓存" + farmCacheName.getPermanentCacheName()
						+ " -------------------");
				return;
			}
		}
		Element element = new Element(key, val);
		live.put(element);
	}

	/**
	 * 获得缓存数据
	 * 
	 * @param key
	 * @param CacheName
	 * @return
	 */
	public Object getCacheData(String key, FarmCacheName farmCacheName) {
		final Cache live = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		{
			// 如果任意缓存的存活时间时0则缓存不生效
			long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
			if (l_live_time == 0) {
				log.info("--------------------FarmCache--禁用缓存" + farmCacheName.getPermanentCacheName()
						+ " -------------------");
				return null;
			}
		}
		log.info("--------------------FarmCache--:返回(" + key + ")生存数据--------" + farmCacheName.getPermanentCacheName()
				+ "-------------------");
		Element returnVal = live.get(key);
		if (returnVal == null) {
			return null;
		} else {
			return returnVal.getObjectValue();
		}
	}

	/**
	 * 删除一个缓存数据
	 * 
	 * @param key
	 * @param CacheName
	 */
	public void removeCacheData(String key, FarmCacheName farmCacheName) {
		final Cache live = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		live.remove(key);
	}

	/**
	 * 获得一个缓存的有效时间
	 * 
	 * @param sharelevel1cache
	 * @return
	 */
	public long getliveTime(FarmCacheName farmCacheName) {
		final Cache live = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
		return l_live_time;
	}

	/**
	 * 缓存对象是否存在
	 * 
	 * @param docId
	 * @param doctypecache
	 * @return
	 */
	public boolean isHaveVal(String key, FarmCacheName farmCacheName) {
		final Cache live = cacheManager.getCache(farmCacheName.getPermanentCacheName());
		{
			// 如果任意缓存的存活时间时0则缓存不生效
			long l_live_time = live.getCacheConfiguration().getTimeToLiveSeconds();
			if (l_live_time == 0) {
				log.info("--------------------FarmCache--禁用缓存" + farmCacheName.getPermanentCacheName()
						+ " -------------------");
				return false;
			}
		}
		return live.get(key) != null;
	}
}
