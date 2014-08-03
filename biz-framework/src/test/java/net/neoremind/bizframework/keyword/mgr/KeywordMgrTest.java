package net.neoremind.bizframework.keyword.mgr;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import net.neoremind.bizframework.commons.sterotype.BizResult;
import net.neoremind.bizframework.group.mgr.GroupMgr;
import net.neoremind.bizframework.keyword.bo.KeywordBo;
import net.neoremind.bizframework.keyword.entity.Keyword;
import net.neoremind.bizframework.keyword.model.KeywordModel;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class KeywordMgrTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private GroupMgr groupMgr;
	
	@Autowired
	private KeywordMgr keywordMgr;
	
	@Test
	public void testAdd() {
		Integer groupId = 107;
		BizResult<KeywordModel> result = keywordMgr.addStrictValid(getPreparedAddKeywords(groupId));
		System.out.println(result);
		System.out.println(result.getDataList().get(0));
		assertThat(result.isSuccess(), is(true));
		
		List<Keyword> keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(10));
	}
	
	@Test
	public void testAddNegativePattern() {
		Integer groupId = 103;
		BizResult<KeywordModel> result = keywordMgr.addStrictValid(getPreparedNegativeKeywords(groupId, 1));
		System.out.println(result);
		assertThat(result.isSuccess(), is(false));
		assertThat(result.getErrorNum(), is(1));
		
		List<Keyword> keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(5));
	}
	
	@Test
	public void testAddNegativeNum() {
		Integer groupId = 103;
		BizResult<KeywordModel> result = keywordMgr.addStrictValid(getPreparedNegativeKeywords(groupId, 10));
		System.out.println(result);
		assertThat(result.isSuccess(), is(false));
		assertThat(result.getErrorNum(), is(10));
		
		List<Keyword> keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(5));
	}
	
	@Test
	public void testOverrideAdd() {
		Integer groupId = 105;
		List<Keyword> keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(5));
		
		BizResult<KeywordModel> result = keywordMgr.overrideAddStrictValid(getPreparedOverrideAddKeywords(groupId));
		System.out.println(result);
		assertThat(result.isSuccess(), is(true));
		assertThat(result.getErrorNum(), is(0));
		
		keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(5));
	}
	
	@Test
	public void testDel() {
		Integer groupId = 101;
		List<Keyword> keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(5));
		
		BizResult<KeywordModel> result = keywordMgr.deleteStrictValid(getPreparedDelKeywordIds(groupId));
		System.out.println(result);
		assertThat(result.isSuccess(), is(true));
		assertThat(result.getErrorNum(), is(0));
		
		keywords = keywordMgr.findByGroupId(groupId);
		System.out.println(keywords);
		assertThat(keywords.size(), is(3));
	}
	
	private List<KeywordBo> getPreparedAddKeywords(Integer groupId) {
		List<KeywordBo> keywords = new ArrayList<KeywordBo>();
		for (int i = 0; i < 5; i++) {
			KeywordBo keyword = new KeywordBo();
			keyword.setGroupId(groupId);
			keyword.setKeyword("添加关键词-" + i);
			keywords.add(keyword);
		}
		return keywords;
	}
	
	private List<KeywordBo> getPreparedNegativeKeywords(Integer groupId, int num) {
		List<KeywordBo> keywords = new ArrayList<KeywordBo>();
		for (int i = 0; i < num; i++) {
			KeywordBo keyword = new KeywordBo();
			keyword.setGroupId(groupId);
			keyword.setKeyword("非法词%……&*-" + i);
			keywords.add(keyword);
		}
		return keywords;
	}
	
	private List<KeywordBo> getPreparedOverrideAddKeywords(Integer groupId) {
		List<KeywordBo> keywords = new ArrayList<KeywordBo>();
		KeywordBo keyword = new KeywordBo();
		keyword.setGroupId(groupId);
		keyword.setKeyword("词-" + 105 + "-" + 0);
		keywords.add(keyword);
		for (int i = 0; i < 4; i++) {
			keyword = new KeywordBo();
			keyword.setGroupId(groupId);
			keyword.setKeyword("添加关键词-" + i);
			keywords.add(keyword);
		}
		return keywords;
	}
	
	private List<KeywordBo> getPreparedDelKeywordIds(Integer groupId) {
		List<KeywordBo> keywords = new ArrayList<KeywordBo>();
		for (int i = 1; i < 3; i++) {
			KeywordBo keyword = new KeywordBo();
			keyword.setKeywordId(60000 + i);
			keyword.setGroupId(groupId);
			keywords.add(keyword);
		}
		return keywords;
	}
	
}