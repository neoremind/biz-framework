package net.neoremind.bizframework.group.factory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.neoremind.bizframework.commons.exception.OperationNotSupportException;
import net.neoremind.bizframework.commons.factory.ModelFactory;
import net.neoremind.bizframework.commons.model.BizModel;
import net.neoremind.bizframework.group.bo.GroupBo;
import net.neoremind.bizframework.group.mgr.GroupMgr;
import net.neoremind.bizframework.group.model.GroupModel;

@Component
public class GroupModelFactory implements ModelFactory<Integer, GroupBo, GroupModel> {

	@Resource
	private GroupMgr groupMgr;
	
	public BizModel<Integer, GroupModel> createModel4Add(List<GroupBo> bizObjects) {
		if (bizObjects.isEmpty()) {
			return new BizModel<Integer, GroupModel>();
		}
		List<GroupModel> result = new ArrayList<GroupModel>(bizObjects.size());
		for (GroupBo groupBo : bizObjects) {
			GroupModel model = new GroupModel();
			model.setGroupId(groupBo.getGroupId());
			model.setGroupName(groupBo.getGroupName());
			model.setGroupState(groupBo.getGroupState());
			model.setTargetType(groupBo.getTargetType());
			model.setKeywordList(groupBo.getKeywordList());
			model.setUserId(groupBo.getUserId());
			model.setPlanId(groupBo.getPlanId());
			model.setAddTime(new Date());
			model.setModTime(new Date());
			result.add(model);
		}
		return new BizModel<Integer, GroupModel>().buildAdd(result);
	}
	
	public BizModel<Integer, GroupModel> createModel4OverrideAdd(List<GroupBo> bizObjects) {
		throw new OperationNotSupportException("Group override add not supported");
	}

	public BizModel<Integer, GroupModel> createModel4Update(List<GroupBo> bizObjects) {
		throw new OperationNotSupportException("Group update not supported");
	}

	public BizModel<Integer, GroupModel> createModel4Del(List<GroupBo> bizObjects) {
		if (bizObjects.isEmpty()) {
			return new BizModel<Integer, GroupModel>();
		}
		List<Integer> result = new ArrayList<Integer>(bizObjects.size());
		for (GroupBo groupBo : bizObjects) {
			result.add(groupBo.getGroupId());
		}
		return new BizModel<Integer, GroupModel>().buildDel(result);
	}
	
}
