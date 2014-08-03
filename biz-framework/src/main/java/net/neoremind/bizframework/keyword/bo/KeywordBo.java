package net.neoremind.bizframework.keyword.bo;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

import net.neoremind.bizframework.commons.validator.custom.ValidText;
import net.neoremind.bizframework.commons.validator.group.CheckAdd;
import net.neoremind.bizframework.group.bo.GroupIdAware;
import net.neoremind.bizframework.group.commons.GroupConstants;

/**
 * 面向业务Buniess Object
 * 
 * @author zhangxu04
 */
public class KeywordBo implements GroupIdAware {

	private Integer keywordId;
	
	@NotNull(groups = CheckAdd.class)
	private Integer groupId;

	@ValidText(regex = GroupConstants.KEYWORD_PATTERN, min = 1, max = 20,  message = "{com.baidu.beidou.keyword.invalid}", groups = CheckAdd.class)
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
				append("keyword", keyword)
				.toString();
	}
	
	@Override
	public Integer fetchGroupId() {
		return groupId;
	}

}
