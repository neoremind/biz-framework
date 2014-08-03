package net.neoremind.bizframework.keyword.factory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.neoremind.bizframework.commons.exception.OperationNotSupportException;
import net.neoremind.bizframework.commons.factory.ModelFactory;
import net.neoremind.bizframework.commons.model.BizModel;
import net.neoremind.bizframework.group.entity.Group;
import net.neoremind.bizframework.group.mgr.GroupMgr;
import net.neoremind.bizframework.keyword.bo.KeywordBo;
import net.neoremind.bizframework.keyword.entity.Keyword;
import net.neoremind.bizframework.keyword.model.KeywordModel;
import net.neoremind.bizframework.keyword.util.KeywordCacheUtil;

/**
 * 经过业务校验后，经由该模型工厂输出业务领域模型
 * 
 * @author zhangxu04
 */
@Component
public class KeywordModelFactory implements ModelFactory<Integer, KeywordBo, KeywordModel> {

	private final static Logger LOG = LoggerFactory.getLogger(KeywordCacheUtil.class); 
	
	@Resource
	private GroupMgr groupMgr;

	@Resource
	private KeywordCacheUtil keywordCacheUtil;

	public BizModel<Integer, KeywordModel> createModel4Add(List<KeywordBo> bizObjects) {
		return createModel4Add(bizObjects, false);
	}

	public BizModel<Integer, KeywordModel> createModel4OverrideAdd(List<KeywordBo> bizObjects) {
		return createModel4Add(bizObjects, true);
	}

	private BizModel<Integer, KeywordModel> createModel4Add(List<KeywordBo> bizObjects, boolean isOverride) {
		if (bizObjects.isEmpty()) {
			return new BizModel<Integer, KeywordModel>();
		}
		List<KeywordModel> toAddList = new ArrayList<KeywordModel>(bizObjects.size());

		Map<Integer, Group> groupMap = groupMgr.getHashMappedGroups(new ArrayList<KeywordBo>(bizObjects));

		Map<Integer, Set<Keyword>> pendingDelKeywordMap = new HashMap<Integer, Set<Keyword>>();

		for (KeywordBo keywordBo : bizObjects) {
			KeywordModel model = new KeywordModel();

			Integer groupId = keywordBo.getGroupId();
			Group group = groupMap.get(groupId);
			model.setGroupId(keywordBo.getGroupId());
			model.setKeyword(filterKeyword(keywordBo.getKeyword()));

			// 填充其他冗余ID信息
			model.setPlanId(group.getPlanId());
			model.setUserId(group.getUserId());

			if (isOverride) {
				Set<Keyword> persistKeywords = keywordCacheUtil.getKeywords(groupId);

				if (!pendingDelKeywordMap.containsKey(groupId)) {
					pendingDelKeywordMap.put(groupId, new HashSet<Keyword>(persistKeywords));
				}

				// 如果关键词已经存在，则从待删除的列表中剔除掉
				Keyword keyword2Check = new Keyword(groupId, model.getKeyword());
				if (persistKeywords != null && persistKeywords.contains(keyword2Check)) {
					pendingDelKeywordMap.get(groupId).remove(keyword2Check);
					continue;
				}
			}

			toAddList.add(model);
		}

		Set<Integer> toDelKeywordIds = new HashSet<Integer>();
		if (isOverride) {
			for (Set<Keyword> keywordSet : pendingDelKeywordMap.values()) {
				if (keywordSet != null) {
					for (Keyword keyword : keywordSet) {
						toDelKeywordIds.add(keyword.getKeywordId());
					}
				}

			}
		}
		
		LOG.info("bizObjSize=" + bizObjects.size() + ", toAddNum=" + toAddList.size() + ", toDelSize=" + toDelKeywordIds.size() + ", isOverride=" + isOverride);

		return new BizModel<Integer, KeywordModel>().
				buildAdd(toAddList).
				buildDel(new ArrayList<Integer>(toDelKeywordIds));
	}

	public BizModel<Integer, KeywordModel> createModel4Update(List<KeywordBo> bizObjects) {
		throw new OperationNotSupportException("Keyword update not supported");
	}

	public BizModel<Integer, KeywordModel> createModel4Del(List<KeywordBo> bizObjects) {
		if (bizObjects.isEmpty()) {
			return new BizModel<Integer, KeywordModel>();
		}
		List<Integer> result = new ArrayList<Integer>(bizObjects.size());
		for (KeywordBo keywordBo : bizObjects) {
			result.add(keywordBo.getKeywordId());
		}
		return new BizModel<Integer, KeywordModel>().buildDel(result);
	}

	/**
	 * 保存关键词过滤
	 * 
	 * @param keyword
	 * @return
	 */
	private String filterKeyword(String keyword) {
		return keyword.trim().toLowerCase();
	}

}
