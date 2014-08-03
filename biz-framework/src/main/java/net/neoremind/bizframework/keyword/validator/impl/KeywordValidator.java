package net.neoremind.bizframework.keyword.validator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.neoremind.bizframework.commons.exception.OperationNotSupportException;
import net.neoremind.bizframework.commons.validator.AbstractValidator;
import net.neoremind.bizframework.commons.validator.Validator;
import net.neoremind.bizframework.commons.validator.error.BizError;
import net.neoremind.bizframework.commons.validator.group.CheckAdd;
import net.neoremind.bizframework.group.entity.Group;
import net.neoremind.bizframework.group.error.GroupError;
import net.neoremind.bizframework.group.mgr.GroupMgr;
import net.neoremind.bizframework.keyword.bo.KeywordBo;
import net.neoremind.bizframework.keyword.commons.KeywordConstant;
import net.neoremind.bizframework.keyword.error.KeywordError;
import net.neoremind.bizframework.keyword.util.KeywordCacheUtil;

@Component
public class KeywordValidator extends AbstractValidator implements Validator<KeywordBo> {
	
	@Resource
	private GroupMgr groupMgr;
	
	@Resource
	private KeywordCacheUtil keywordCacheUtil;

	public Map<Integer, BizError> validateAdd(List<KeywordBo> bizObjects) {
		Map<Integer, BizError> result = buildValidationResult();
		
		// 批量查询推广组信息，避免loop性能问题
		Map<Integer, Group> groupMap = groupMgr.getHashMappedGroups(bizObjects);
		
		// 遍历业务对象进行验证
		for (int i = 0; i < bizObjects.size(); i++) {
			KeywordBo keywordBo = bizObjects.get(i);
			
			List<BizError> errors = new ArrayList<BizError>();
			
			// 验证推广组是否存在
			Integer groupId = keywordBo.getGroupId();
			Group group = groupMap.get(groupId);
			if (group == null) {
				addBizError(i, errors, GroupError.GROUP_NOT_FOUND, keywordBo.getGroupId());
			}
			
			// 验证关键词数量是否超过限制，启用JVM缓存查询
			if (checkTotalNumExceed(groupId)) {
				addBizError(i, errors, KeywordError.KEYWORD_NUM_EXCEED, keywordBo.getKeyword());
			}

			// 利用Hibernate Validation框架进行基本验证
			validateBizObj(keywordBo, i, errors, CheckAdd.class);
			
			// 最后，将错误信息放入返回对象
			handleResultError(result, i, errors);
		}
		
		return result;
	}
	
	public Map<Integer, BizError> validateUpdate(List<KeywordBo> bizObjects) {
		throw new OperationNotSupportException("Keyword update not support");
	}
	
	public Map<Integer, BizError> validateDel(List<KeywordBo> bizObjects) {
		Map<Integer, BizError> result = buildValidationResult();
		
		// 批量查询推广组信息，避免loop性能问题
		Map<Integer, Group> groupMap = groupMgr.getHashMappedGroups(bizObjects);
				
		// 遍历业务对象进行验证
		for (int i = 0; i < bizObjects.size(); i++) {
			KeywordBo keywordBo = bizObjects.get(i);
			
			List<BizError> errors = new ArrayList<BizError>();
			
			// 验证推广组是否存在
			Integer groupId = keywordBo.getGroupId();
			Group group = groupMap.get(groupId);
			if (group == null) {
				addBizError(i, errors, GroupError.GROUP_NOT_FOUND, keywordBo.getGroupId());
			}

			// 验证关键词是否重复，启用JVM缓存查询
			Set<Integer> keywordSet = keywordCacheUtil.getKeywordIds(groupId);
			if (!keywordSet.contains(keywordBo.getKeywordId())) {
				addBizError(i, errors, KeywordError.KEYWORD_NOT_EXIST, keywordBo.getGroupId());
			}
						
			// 最后，将错误信息放入返回对象
			handleResultError(result, i, errors);
		}
		
		return result;
	}

	// 验证关键词数量是否超过单个推广组
	private Map<Integer, AtomicInteger> groupId2KeywordCountMap = new HashMap<Integer, AtomicInteger>();
	private boolean checkTotalNumExceed(Integer groupId) {
		int count = keywordCacheUtil.getCachedKeywordCountByGroupId(groupId);
		if (!groupId2KeywordCountMap.containsKey(groupId)) {
			groupId2KeywordCountMap.put(groupId, new AtomicInteger(count));
		}
		return groupId2KeywordCountMap.get(groupId).addAndGet(1) > KeywordConstant.MAX_KEYWORD_PER_GROUP;
	}
		
}
