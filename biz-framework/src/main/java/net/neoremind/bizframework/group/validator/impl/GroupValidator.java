package net.neoremind.bizframework.group.validator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.neoremind.bizframework.commons.exception.OperationNotSupportException;
import net.neoremind.bizframework.commons.validator.AbstractValidator;
import net.neoremind.bizframework.commons.validator.Validator;
import net.neoremind.bizframework.commons.validator.error.BizError;
import net.neoremind.bizframework.group.bo.GroupBo;
import net.neoremind.bizframework.group.commons.GroupConstants;
import net.neoremind.bizframework.group.dao.GroupDao;
import net.neoremind.bizframework.group.entity.Group;
import net.neoremind.bizframework.group.error.GroupError;
import net.neoremind.bizframework.group.util.TargettypeUtil;

@Component
public class GroupValidator extends AbstractValidator implements Validator<GroupBo> {
	
	@Resource
	private GroupDao groupDao;

	public Map<Integer, BizError> validateAdd(List<GroupBo> bizObjects) {
		Map<Integer, BizError> result = buildValidationResult();
		
		// 遍历业务对象进行验证
		for (int i = 0; i < bizObjects.size(); i++) {
			GroupBo groupBo = bizObjects.get(i);
			
			List<BizError> errors = new ArrayList<BizError>();
			
			// 利用Hibernate Validation框架进行基本验证
			validateBizObj(groupBo, i, errors);
			
			// 验证推广组状态
			if (!GroupConstants.GROUP_STATE_TYPE.contains(groupBo.getGroupState())) {
				addBizError(i, errors, GroupError.GROUP_STATE_INVALID, groupBo.getGroupState());
			}
			
			// 验证定向方式
			if (!TargettypeUtil.isValid(groupBo.getTargetType())) {
				addBizError(i, errors, GroupError.GROUP_TARGET_TYPE_INVALID, groupBo.getTargetType());
			}
			
			// 如果启用关键词定向，则验证关键词
			if (TargettypeUtil.hasKT(groupBo.getTargetType())) {
				validateBizObj(groupBo, i, errors, CheckKT.class);
			}
			
			// 最后，将错误信息放入返回对象
			handleResultError(result, i, errors);
		}
		return result;
	}
	
	public Map<Integer, BizError> validateUpdate(List<GroupBo> bizObjects) {
		throw new OperationNotSupportException("Group update not support");
	}
	
	public Map<Integer, BizError> validateDel(List<GroupBo> bizObjects) {
		Map<Integer, BizError> result = buildValidationResult();
		
		// 遍历业务对象进行验证
		for (int i = 0; i < bizObjects.size(); i++) {
			GroupBo groupBo = bizObjects.get(i);
			
			List<BizError> errors = new ArrayList<BizError>();
			
			Integer groupId = groupBo.getGroupId();
			Group group = groupDao.get(groupId);
			if (group == null) {
				addBizError(i, errors, GroupError.GROUP_NOT_FOUND, groupBo.getGroupId());
			}

			// 最后，将错误信息放入返回对象
			handleResultError(result, i, errors);
		}
		return result;
	}

}
