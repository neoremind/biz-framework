package net.neoremind.bizframework.group.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GroupModel {

	private Integer userId;

	private Integer groupId;
	
	private Integer planId;

	private String groupName;

	private Integer groupState;

	private Integer targetType;

	private Integer price;

	private List<String> keywordList;

	private List<Integer> siteList;

	private Date addTime;

	private Date modTime;

	public GroupModel() {
		
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<String> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<String> keywordList) {
		this.keywordList = keywordList;
	}

	public List<Integer> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<Integer> siteList) {
		this.siteList = siteList;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getModTime() {
		return modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("userId", userId)
		.append("planId", planId)
		.append("groupId", groupId)
		.append("groupName", groupName)
		.append("groupState", groupState)
		.append("targetType", targetType)
		.append("price", price)
		.append("keywordList", keywordList)
		.append("siteList", siteList)
		.append("addTime", addTime)
		.append("modTime", modTime)
		.toString();
	}
}
