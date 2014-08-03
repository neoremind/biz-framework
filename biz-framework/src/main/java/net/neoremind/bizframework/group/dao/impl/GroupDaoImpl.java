package net.neoremind.bizframework.group.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import net.neoremind.bizframework.commons.dao.impl.AbstractDao;
import net.neoremind.bizframework.group.dao.GroupDao;
import net.neoremind.bizframework.group.entity.Group;

@Repository
public class GroupDaoImpl extends AbstractDao<Integer, Group> implements GroupDao {

	private final static Logger LOG = LoggerFactory.getLogger(GroupDaoImpl.class); 
	
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final AtomicInteger atomicIncrKey = new AtomicInteger(110);
	private int startGroupId = 100;
	private int endGroupId = 110;
	
	@PostConstruct
	public void init() {
		try {
			for (int i = startGroupId; i < endGroupId; i++) {
				Group group = new Group();
				group.setGroupId(i);
				group.setPlanId(i % 2);
				group.setUserId(499);
				group.setGroupName("推广组-" + i);
				group.setGroupState(1);
				group.setTargetType(7);
				group.setAddTime(dateFormat.parse("2013-11-11"));
				group.setModTime(dateFormat.parse("2013-12-11"));
				entityList.add(group);
				entityMap.put(i, group);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Group create(Group group) {
		group.setGroupId(atomicIncrKey.incrementAndGet());
		entityMap.put(group.getGroupId(), group);
		entityList.add(group);
		LOG.info("create groupinfo " + group);
		return group;
	}
	
	public List<Group> create(List<Group> groups) {
		for (Group group : groups) {
			create(group);
		}
		return groups;
	}
	
	public boolean update(Group group) {
		if (!entityMap.containsKey(group.getGroupId())) {
			throw new RuntimeException("No found groupid " + group.getGroupId());
		}
		entityMap.put(group.getGroupId(), group);
		LOG.info("update group " + group);
		return true;
	}
	
	public int update(List<Group> groups) {
		for (Group group : groups) {
			update(group);
		}
		return groups.size();
	}
	
}
