package net.neoremind.bizframework.group.bo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Range;

import net.neoremind.bizframework.commons.validator.custom.ValidText;
import net.neoremind.bizframework.group.commons.GroupConstants;
import net.neoremind.bizframework.group.validator.impl.CheckKT;

public class GroupBo implements GroupIdAware {

	@NotNull
	private Integer userId;
	
	@NotNull
	private Integer planId;
	
	private Integer groupId;

	@ValidText(regex = GroupConstants.GROUP_NAME_PATTERN, min = 1, max = 20,  message = "{com.baidu.beidou.group.name}")
	private String groupName;

	@Range(min=1, max=5)
	private Integer groupState;

	private Integer targetType;

	@ValidText(regex = GroupConstants.KEYWORD_PATTERN, min = 1, max = 20,  message = "{com.baidu.beidou.group.keyword}")
	@Size(min=1, max=5000, groups = CheckKT.class)
	private List<String> keywordList;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<String> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<String> keywordList) {
		this.keywordList = keywordList;
	}

	public Integer getGroupState() {
		return groupState;
	}

	public void setGroupState(Integer groupState) {
		this.groupState = groupState;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	
	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
		.append("userId", userId)
		.append("planId", planId)
		.append("groupId", groupId)
		.append("groupName", groupName)
		.append("groupState", groupState)
		.append("targetType", targetType)
		.append("keywordList", keywordList)
		.toString();
	}
	
	@Override
	public Integer fetchGroupId() {
		return groupId;
	}

}
