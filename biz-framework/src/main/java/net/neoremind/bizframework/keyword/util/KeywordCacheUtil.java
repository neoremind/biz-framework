package net.neoremind.bizframework.keyword.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.neoremind.bizframework.commons.localcache.ContextCache;
import net.neoremind.bizframework.commons.localcache.ContextCacheBuilder;
import net.neoremind.bizframework.keyword.entity.Keyword;
import net.neoremind.bizframework.keyword.mgr.KeywordMgr;
import com.google.common.collect.ImmutableSet;

/**
 * 线程级别缓存业务逻辑处理中间的结果对象
 * 
 * @author zhangxu04
 */
@Component
public class KeywordCacheUtil {

	private final static Logger LOG = LoggerFactory.getLogger(KeywordCacheUtil.class); 
	
	@Resource
	private KeywordMgr keywordMgr;
	
	/**
	 * Get keywords set by group id
	 * <p>
	 * Note: Cache enabled
	 * @param groupId
	 * @return
	 */
	public Set<Keyword> getKeywords(Integer groupId) {
		return ImmutableSet.copyOf(getCachedKeywordsByGroupId(groupId));
	}
	
	/**
	 * Get keywords literally set by group id
	 * <p>
	 * Note: Cache immutabled object enabled
	 * @param groupId
	 * @return
	 */
	public Set<String> getLiteralKeywords(Integer groupId) {
		if (groupId == null) {
			return new HashSet<String>(0);
		}
		ImmutableSet.Builder<String> resultBuilder = ImmutableSet.<String>builder();
		for (Keyword keyword : getCachedKeywordsByGroupId(groupId)) {
			resultBuilder.add(keyword.getKeyword());
		}
		
		return resultBuilder.build();
	}
	
	/**
	 * Get keyword id set by group id
	 * <p>
	 * Note: Cache immutabled object enabled
	 * @param groupId
	 * @return
	 */
	public Set<Integer> getKeywordIds(final Integer groupId) {
		if (groupId == null) {
			return new HashSet<Integer>(0);
		}
		ImmutableSet.Builder<Integer> resultBuilder = ImmutableSet.<Integer>builder();
		for (Keyword keyword : getCachedKeywordsByGroupId(groupId)) {
			resultBuilder.add(keyword.getKeywordId());
		}
		
		return resultBuilder.build();
	}
	
	/**
	 * Cached query keywords by groupid
	 * @param groupId
	 * @return
	 */
	private List<Keyword> getCachedKeywordsByGroupId(final Integer groupId) {
		ContextCache<String, List<Keyword>> contextCache = ContextCacheBuilder.build();
		try {
			Callable<List<Keyword>> callable = new Callable<List<Keyword>>() {  
	            @Override  
	            public List<Keyword> call() {  
	            	return keywordMgr.findByGroupId(groupId);
	            }  
	        };
			List<Keyword> keywords = contextCache.get("getCachedKeywordsByGroupId-" + groupId, callable);
			return keywords;
		} catch (Exception e) {
			LOG.error("Error to cache", e);
			throw new RuntimeException("Error occurred when getCachedKeywordsByGroupId");
		}
	}
	
	/**
	 * Cached query keywords count by groupid
	 * @param groupId
	 * @return
	 */
	public int getCachedKeywordCountByGroupId(final Integer groupId) {
		ContextCache<String, Integer> contextCache = ContextCacheBuilder.build();
		try {
			Integer count = contextCache.get("getCachedKeywordCountByGroupId-" + groupId,  new Callable<Integer>() {  
	            @Override  
	            public Integer call() {  
	            	return keywordMgr.countByGroupId(groupId);
	            }  
	        });
			return count;
		} catch (Exception e) {
			LOG.error("Error to cache", e);
			throw new RuntimeException("Error occurred when getCachedKeywordCountByGroupId");
		}
	}
	
}
