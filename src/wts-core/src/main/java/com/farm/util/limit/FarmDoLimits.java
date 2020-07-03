package com.farm.util.limit;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.util.Urls;
import com.farm.core.config.AppConfig;
import com.farm.web.constant.FarmConstant;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

public class FarmDoLimits {

	// 根据IP分不同的令牌桶, 每天自动清理缓存（动态资源）
	private static LoadingCache<String, RateLimiter> DO_IP_CACHES = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, RateLimiter>() {
				@Override
				public RateLimiter load(String key) throws Exception {
					// 新的IP初始化 (限流每秒两个令牌响应)
					return RateLimiter.create(new Integer(AppConfig.getString("config.client.limit.do.ip.second.num")));
				}
			});
	// 根据IP和session分不同的令牌桶, 每天自动清理缓存（动态资源）
	private static LoadingCache<String, RateLimiter> DO_IP_AND_SESSION_CACHES = CacheBuilder.newBuilder()
			.maximumSize(10000).expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, RateLimiter>() {
				@Override
				public RateLimiter load(String key) throws Exception {
					// 新的IP初始化 (限流每秒两个令牌响应)
					return RateLimiter
							.create(new Integer(AppConfig.getString("config.client.limit.do.ipsession.second.num")));
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
	 * 是否允许访问（超过ip浏览限制则不允许访问,或者超过session限流不许访问）
	 * 
	 * @param ip
	 * @param isStatic
	 *            true的时候表示所有url，包含静态和动态，当false时为动态资源
	 * @return
	 */
	public static boolean isVisiteAble(String ip, HttpSession session, String url) {
		try {
			docount.get(ip + getCLoginName(session)).getAndIncrement();
		} catch (ExecutionException e) {
			e.printStackTrace();
			return true;
		}
		{
			// 非图片的后台ip请求限流
			if (AppConfig.getBoolean("config.client.limit.ip.able")) {
				if (isLimitDoUrl(url) && !isVisiteAbleByIpDo(ip)) {
					return false;
				}
			}
		}
		{
			// 非图片的后台ipAndSession请求限流
			if (AppConfig.getBoolean("config.client.limit.ipsession.able")) {
				if (isLimitDoUrl(url) && !isVisiteAbleByIpAndSessionDo(ip, session.getId())) {
					return false;
				}
			}
		}
		return true;
	}

	private static String getCLoginName(HttpSession session) {
		LoginUser user = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
		if (user == null) {
			return "";
		}
		if (StringUtils.isBlank(user.getLoginname())) {
			return "";
		}
		return user.getLoginname();
	}

	private static boolean isVisiteAbleByIpDo(String ip) {
		RateLimiter limiter;
		try {
			limiter = DO_IP_CACHES.get(ip);
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

	private static boolean isVisiteAbleByIpAndSessionDo(String ip, String sessionid) {
		RateLimiter limiter;
		try {
			limiter = DO_IP_AND_SESSION_CACHES.get(ip + sessionid);
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
	public static int getVisiteCountBylast3Hours(String ip, String loginname) {
		try {
			if (loginname == null) {
				loginname = "";
			}
			return docount.get(ip + loginname).get();
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
