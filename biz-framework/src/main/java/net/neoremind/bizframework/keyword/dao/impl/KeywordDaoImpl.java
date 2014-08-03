package net.neoremind.bizframework.keyword.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import net.neoremind.bizframework.commons.dao.impl.AbstractDao;
import net.neoremind.bizframework.keyword.dao.KeywordDao;
import net.neoremind.bizframework.keyword.entity.Keyword;

@Repository
public class KeywordDaoImpl extends AbstractDao<Integer, Keyword> implements KeywordDao {

	private final static Logger LOG = LoggerFactory.getLogger(KeywordDaoImpl.class); 

	private static final AtomicInteger atomicIncrKey = new AtomicInteger(60000);
	private int startGroupId = 100;
	private int endGroupId = 110;
	
	@PostConstruct
	public void init() {
		try {
			for (int i = startGroupId; i < endGroupId; i++) {
				if (i % 2 == 0) {
					continue;
				}
				for (int j = 0; j < 5; j++) {
					Keyword keyword = new Keyword();
					Integer keywordId = atomicIncrKey.addAndGet(1);
					keyword.setKeywordId(keywordId);
					keyword.setGroupId(i);
					keyword.setPlanId(i % 2);
					keyword.setUserId(499);
					keyword.setKeyword("词-" + i + "-" + j);
					entityList.add(keyword);
					entityMap.put(keywordId, keyword);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按组查询
	 */
	public List<Keyword> findByGroupId(Integer groupId) {
		List<Keyword> result = new ArrayList<Keyword>();
		for (Keyword keyword : entityList) {
			if (keyword.getGroupId().equals(groupId)) {
				result.add(keyword);
			}
		}
		return result;
	}
	
	public Keyword create(Keyword keyword) {
		keyword.setKeywordId(atomicIncrKey.addAndGet(1));
		entityMap.put(keyword.getGroupId(), keyword);
		entityList.add(keyword);
		LOG.info("create Keyword " + keyword);
		return keyword;
	}
	
	public List<Keyword> create(List<Keyword> keywords) {
		for (Keyword keyword : keywords) {
			create(keyword);
		}
		return keywords;
	}
	
	public boolean update(Keyword keyword) {
		if (!entityMap.containsKey(keyword.getGroupId())) {
			throw new RuntimeException("No found groupid " + keyword.getGroupId());
		}
		entityMap.put(keyword.getGroupId(), keyword);
		LOG.info("update Keyword " + keyword);
		return true;
	}
	
	public int update(List<Keyword> keywords) {
		for (Keyword keyword : keywords) {
			update(keyword);
		}
		return keywords.size();
	}
	
}
