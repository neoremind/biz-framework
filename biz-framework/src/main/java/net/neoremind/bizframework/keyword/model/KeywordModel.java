package net.neoremind.bizframework.keyword.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class KeywordModel {

	private Integer keywordId;

	private Integer groupId;

	private Integer planId;

	private Integer userId;

	private String keyword;
	
	public Integer getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).
				append("keywordId", keywordId).
				append("groupId", groupId).
				append("planId", planId).
				append("userId", userId).
				append("keyword", keyword)
				.toString();
	}
	
}
