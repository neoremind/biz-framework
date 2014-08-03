package net.neoremind.bizframework.keyword.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 关键词存储实体
 * 
 * @author zhangxu04
 */
public class Keyword {
	
	private Integer keywordId;

	private Integer groupId;

	private Integer planId;

	private Integer userId;

	private String keyword;
	
	public Keyword() {
		super();
	}

	public Keyword(Integer groupId, String keyword) {
		super();
		this.groupId = groupId;
		this.keyword = keyword;
	}

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
	
	@Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Keyword)) {
            return false;
        }
        Keyword that = (Keyword) other;
        return new EqualsBuilder()
            .append(this.getGroupId(), that.getGroupId())
            .append(this.getKeyword(), that.getKeyword())
            .isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.getGroupId())
            .append(this.getKeyword())
            .toHashCode();
    }

}
