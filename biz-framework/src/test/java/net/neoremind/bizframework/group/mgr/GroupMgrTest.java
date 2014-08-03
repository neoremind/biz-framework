package net.neoremind.bizframework.group.mgr;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import net.neoremind.bizframework.commons.sterotype.BizResult;
import net.neoremind.bizframework.commons.validator.constant.ErrorConstant;
import net.neoremind.bizframework.group.bo.GroupBo;
import net.neoremind.bizframework.group.commons.GroupConstants;
import net.neoremind.bizframework.group.entity.Group;
import net.neoremind.bizframework.group.model.GroupModel;
import net.neoremind.bizframework.keyword.entity.Keyword;
import net.neoremind.bizframework.keyword.mgr.KeywordMgr;
import com.google.common.collect.Lists;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GroupMgrTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private GroupMgr groupMgr;
	
	@Autowired
	private KeywordMgr keywordMgr;
	
	@Test
	public void testAdd() {
		GroupBo group = new GroupBo();
		group.setUserId(499);
		group.setPlanId(1111);
		group.setGroupName("我的第一个推广组");
		group.setGroupState(1);
		group.setTargetType(GroupConstants.GROUP_TARGET_TYPE_CT_QT_HCT);
		group.setKeywordList(Arrays.asList(new String[]{"关键词1", "关键词2"}));
		List<GroupBo> groupList = Lists.newArrayList(group);
		BizResult<GroupModel> result = groupMgr.addStrictValid(groupList);
		System.out.println(result);
		assertThat(result.isSuccess(), is(true));
		assertThat(result.getErrorNum(), is(0));
		
		Integer groupId = result.getDataList().get(0).getGroupId();
		Group group2check = groupMgr.findById(groupId);
		assertThat(group2check.getGroupName(), is("我的第一个推广组"));
	
		List<Keyword> keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(2));
	}
	
	@Test
	public void testAddNegativeByKeywordListEmpty() {
		GroupBo group = new GroupBo();
		group.setUserId(499);
		group.setPlanId(1111);
		group.setGroupName("我的第一个推广组");
		group.setGroupState(1);
		group.setTargetType(GroupConstants.GROUP_TARGET_TYPE_CT_QT_HCT);
		group.setKeywordList(Arrays.asList(new String[]{}));
		List<GroupBo> groupList = Lists.newArrayList(group);
		BizResult<GroupModel> result = groupMgr.addStrictValid(groupList);
		System.out.println(result);
		assertThat(result.isSuccess(), is(false));
		assertThat(result.getErrorNum(), is(1));
		assertThat(result.getErrors().get(0).getCode(), is(ErrorConstant.DEAULT_ERROR_CODE));
	}
	
	@Test
	public void testAddNegativeByKeywordPatternInvalid() {
		GroupBo group = new GroupBo();
		group.setUserId(499);
		group.setPlanId(1111);
		group.setGroupName("我的第一个推广组");
		group.setGroupState(1);
		group.setTargetType(GroupConstants.GROUP_TARGET_TYPE_CT_QT_HCT);
		group.setKeywordList(Arrays.asList(new String[]{"sdfsdf,,,**&%$#@"}));
		List<GroupBo> groupList = Lists.newArrayList(group);
		BizResult<GroupModel> result = groupMgr.addStrictValid(groupList);
		System.out.println(result);
		assertThat(result.isSuccess(), is(false));
		assertThat(result.getErrorNum(), is(1));
	}
	
	@Test
	public void testUpdateNegative() {
		GroupBo group = new GroupBo();
		group.setGroupId(-1);
		group.setUserId(499);
		group.setPlanId(1111);
		group.setGroupName("abc-");
		group.setGroupState(1);
		group.setTargetType(GroupConstants.GROUP_TARGET_TYPE_CT_QT_HCT);
		group.setKeywordList(Arrays.asList(new String[]{"关键词1", "关键词2"}));
		List<GroupBo> groupList = Lists.newArrayList(group);
		BizResult<GroupModel> result = groupMgr.updateStrictValid(groupList);
		System.out.println(result);
		assertThat(result.isSuccess(), is(false));
		assertThat(result.getErrorNum(), is(1));
	}
	
	@Test
	public void testDelete() {
		int groupId = 100;
		GroupBo group = new GroupBo();
		group.setGroupId(groupId);
		List<GroupBo> groupList = Lists.newArrayList(group);
		BizResult<GroupModel> result = groupMgr.deleteStrictValid(groupList);
		System.out.println(result);
		
		Group group2check = groupMgr.findById(groupId);
		assertThat(group2check, nullValue());
	}

}
