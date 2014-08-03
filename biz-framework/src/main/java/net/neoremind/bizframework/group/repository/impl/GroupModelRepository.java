package net.neoremind.bizframework.group.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.neoremind.bizframework.commons.exception.OperationNotSupportException;
import net.neoremind.bizframework.commons.repository.ModelRepository;
import net.neoremind.bizframework.group.dao.GroupDao;
import net.neoremind.bizframework.group.entity.Group;
import net.neoremind.bizframework.group.model.GroupModel;
import net.neoremind.bizframework.keyword.dao.KeywordDao;
import net.neoremind.bizframework.keyword.entity.Keyword;

@Repository
public class GroupModelRepository implements ModelRepository<Integer, GroupModel> {

	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private KeywordDao keywordDao;
	
	@Transactional
	public List<GroupModel> add(List<GroupModel> groupModels) {
		for (GroupModel model : groupModels) {
			Group group = new Group();
			group.setGroupId(model.getGroupId());
			group.setPlanId(model.getPlanId());
			group.setUserId(model.getUserId());
			group.setTargetType(model.getTargetType());
			group.setGroupName(model.getGroupName());
			group.setGroupState(model.getGroupState());
			group.setAddTime(model.getAddTime());
			group.setModTime(model.getModTime());
			
			group = groupDao.create(group);
			model.setGroupId(group.getGroupId());
			
			List<Keyword> keywords = new ArrayList<Keyword>(); 
			for (String keywordStr : model.getKeywordList()) {
				Keyword keyword = new Keyword();
				keyword.setGroupId(model.getGroupId());
				keyword.setPlanId(model.getPlanId());
				keyword.setUserId(model.getUserId());
				keyword.setKeyword(keywordStr);
				keywords.add(keyword);
			}
			
			keywordDao.create(keywords);
		}
		
		return groupModels;
	}
	
	@Transactional
	public List<GroupModel> overrideAdd(List<GroupModel> toAddBizModel, List<Integer> toDelIds) {
		throw new OperationNotSupportException("Group update not support");
	}

	@Transactional
	public List<GroupModel> update(List<GroupModel> groupModels) {
		throw new OperationNotSupportException("Group update not support");
	}

	@Transactional
	public List<GroupModel> del(List<Integer> groupIds) {
		if (!CollectionUtils.isEmpty(groupIds)) {
			for (Integer groupId : groupIds) {
				groupDao.delete(groupId);
			}
		}
		return Collections.emptyList();
	}
	
}
