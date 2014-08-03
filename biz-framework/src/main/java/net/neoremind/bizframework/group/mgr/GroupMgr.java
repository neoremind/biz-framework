package net.neoremind.bizframework.group.mgr;

import java.util.List;
import java.util.Map;

import net.neoremind.bizframework.commons.mgr.BizMgr;
import net.neoremind.bizframework.group.bo.GroupBo;
import net.neoremind.bizframework.group.bo.GroupIdAware;
import net.neoremind.bizframework.group.entity.Group;
import net.neoremind.bizframework.group.model.GroupModel;

/**
 * 推广组业务逻辑service
 * 
 * @author zhangxu04
 */
public interface GroupMgr extends BizMgr<GroupBo, GroupModel, Integer> {

	Group findById(Integer groupId);
	
	List<Group> findByIds(List<Integer> groupIds);
	
	Map<Integer, Group> getHashMappedGroups(List<? extends GroupIdAware> bizObjects);
	
}
