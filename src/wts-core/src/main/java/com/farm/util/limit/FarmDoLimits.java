package com.farm.util.limit;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.farm.core.auth.util.Urls;
import com.farm.core.config.AppConfig;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

public class FarmDoLimits {
	// 根据IP分不同的令牌桶, 每天自动清理缓存(全部资源)
	private static LoadingCache<String, RateLimiter> ALL_CACHES = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, RateLimiter>() {
				@Override
				public RateLimiter load(String key) throws Exception {
					// 新的IP初始化 (限流每秒两个令牌响应)
					return RateLimiter.create(new Integer(AppConfig.getString("config.client.limit.all.second.num")));
				}
			});
	// 根据IP分不同的令牌桶, 每天自动清理缓存（动态资源）
	private static LoadingCache<String, RateLimiter> DO_CACHES = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, RateLimiter>() {
				@Override
				public RateLimiter load(String key) throws Exception {
					// 新的IP初始化 (限流每秒两个令牌响应)
					return RateLimiter.create(new Integer(AppConfig.getString("config.client.limit.do.second.num")));
				}
			});
	// 访问计数器, 每天自动清理缓存
	private static LoadingCache<String, AtomicInteger> docount = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterWrite(3, TimeUnit.HOURS).build(new CacheLoader<String, AtomicInteger>() {
				@Override
				public AtomicInteger load(String key) throws Exception {
					// 新的IP初始化 (限流每秒两个令牌响应)
					return new AtomicInteger(0);
				}
			});

	/**
	 * 是否允许访问（超过ip浏览限制则不允许访问）
	 * 
	 * @param ip
	 * @param isStatic
	 *            true的时候表示所有url，包含静态和动态，当false时为动态资源
	 * @return
	 */
	public static boolean isVisiteAble(String ip, String url) {
		try {
			docount.get(ip).getAndIncrement();
			if (!AppConfig.getBoolean("config.client.limit.able")) {
				// 不啓用浏览控制
				return true;
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
			return true;
		}
		// 所有资源限流
		if (!isVisiteAbleByall(ip)) {
			return false;
		}
		// 非图片的后台请求限流
		if (isLimitDoUrl(url) && !isVisiteAbleByDo(ip)) {
			return false;
		}
		return true;
	}

	private static boolean isVisiteAbleByall(String ip) {
		RateLimiter limiter;
		try {
			limiter = ALL_CACHES.get(ip);
			if (limiter.tryAcquire()) {
				return true;
			} else {
				System.err.println(ip + ":请求频繁被限制");
				return false;
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
			return true;
		}
	}

	private static boolean isVisiteAbleByDo(String ip) {
		RateLimiter limiter;
		try {
			limiter = DO_CACHES.get(ip);
			if (limiter.tryAcquire()) {
				return true;
			} else {
				System.err.println(ip + ":请求频繁被限制");
				return false;
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * 获得访问指标
	 * 
	 * @param ip
	 * @return
	 */
	public static int getVisiteCountBylast3Hours(String ip) {
		try {
			return docount.get(ip).get();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 是否进行动态资源检查
	 * 
	 * @param formatUrl
	 * @return
	 */
	private static boolean isLimitDoUrl(String formatUrl) {
		// 1.必须后缀为html/do
		if (!(Urls.isActionByUrl(formatUrl, "do") || Urls.isActionByUrl(formatUrl, "html"))) {
			return false;
		}
		// 不是图片处理的url
		if (formatUrl.indexOf("actionImg/") >= 0) {
			return false;
		}
		return true;
	}
}
