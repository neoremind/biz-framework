package net.neoremind.bizframework.keyword.repository;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.neoremind.bizframework.commons.exception.OperationNotSupportException;
import net.neoremind.bizframework.commons.repository.ModelRepository;
import net.neoremind.bizframework.keyword.dao.KeywordDao;
import net.neoremind.bizframework.keyword.entity.Keyword;
import net.neoremind.bizframework.keyword.model.KeywordModel;

@Repository
public class KeywordModelRepository implements ModelRepository<Integer, KeywordModel> {
	
	@Autowired
	private KeywordDao keywordDao;
	
	@Transactional
	public List<KeywordModel> add(List<KeywordModel> keywordModels) {
		for (KeywordModel model : keywordModels) {
			Keyword keyword = new Keyword();
			keyword.setGroupId(model.getGroupId());
			keyword.setPlanId(model.getPlanId());
			keyword.setUserId(model.getUserId());
			keyword.setKeyword(model.getKeyword());
			keyword = keywordDao.create(keyword);
			
			// refill key
			model.setKeywordId(keyword.getKeywordId());
		}
		
		return keywordModels;
	}
	
	@Transactional
	public List<KeywordModel> overrideAdd(List<KeywordModel> toAddBizModel, List<Integer> toDelIds) {
		del(toDelIds);
		return add(toAddBizModel);
	}

	@Transactional
	public List<KeywordModel> update(List<KeywordModel> groupModels) {
		throw new OperationNotSupportException("Keyword update not support");
	}

	@Transactional
	public List<KeywordModel> del(List<Integer> keywordIds) {
		if (!CollectionUtils.isEmpty(keywordIds)) {
			for (Integer keywordId : keywordIds) {
				keywordDao.delete(keywordId);
			}
		}
		return Collections.emptyList();
	}

}
