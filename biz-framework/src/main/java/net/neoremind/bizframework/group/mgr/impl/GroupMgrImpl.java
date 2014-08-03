package net.neoremind.bizframework.group.mgr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import net.neoremind.bizframework.commons.factory.ModelFactory;
import net.neoremind.bizframework.commons.mgr.AbstractMgr;
import net.neoremind.bizframework.commons.repository.ModelRepository;
import net.neoremind.bizframework.commons.validator.Validator;
import net.neoremind.bizframework.group.bo.GroupBo;
import net.neoremind.bizframework.group.bo.GroupIdAware;
import net.neoremind.bizframework.group.dao.GroupDao;
import net.neoremind.bizframework.group.entity.Group;
import net.neoremind.bizframework.group.mgr.GroupMgr;
import net.neoremind.bizframework.group.model.GroupModel;

/**
 * 推广组业务逻辑service实现
 * 
 * @author zhangxu04
 *
 */
@Service
public class GroupMgrImpl extends AbstractMgr<GroupBo, GroupModel, Integer> implements GroupMgr {

	/**
	 * 验证器
	 */
	@Resource
	protected Validator<GroupBo> groupValidator;
	
	/**
	 * 组装model工厂
	 */
	@Resource
	protected ModelFactory<Integer, GroupBo, GroupModel> groupModelFactory;
	
	/**
	 * model存储repository
	 */
	@Resource
	protected ModelRepository<Integer, GroupModel> groupModelRepository;

	@PostConstruct
	public void init() {
		this.validator = groupValidator;
		this.modelFactory = groupModelFactory;
		this.modelRepository = groupModelRepository;
	}
	
	@Resource
	private GroupDao groupDao;
	
	@Override
	public Group findById(Integer groupId) {
		return groupDao.get(groupId);
	}
	
	@Override
	public List<Group> findByIds(List<Integer> groupIds) {
		return groupDao.getByIds(groupIds);
	}
	
	/**
	 * Extract group id -> group hash map
	 * @param bizObjects
	 * @return 
	 */
	public Map<Integer, Group> getHashMappedGroups(List<? extends GroupIdAware> bizObjects) {
		if (CollectionUtils.isEmpty(bizObjects)) {
			return new HashMap<Integer, Group>(0);
		}
		Set<Integer> groupIdSet = new HashSet<Integer>();
		for (int i = 0; i < bizObjects.size(); i++) {
			GroupIdAware bizObject = bizObjects.get(i);
			Integer groupId = bizObject.fetchGroupId();
			groupIdSet.add(groupId);
		}
		
		List<Group> groups = this.findByIds(new ArrayList<Integer>(groupIdSet));
		Map<Integer, Group> result = new HashMap<Integer, Group>(bizObjects.size());
		for (Group group : groups) {
			result.put(group.getGroupId(), group);
		}
		
		return result;
	}
	
}
