package net.neoremind.bizframework.commons.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务上的模型封装，包含准备添加、更新、删除对象
 * 
 * @author zhangxu04
 *
 * @param <KEY>
 * @param <V>
 */
public class BizModel<KEY, V> {

	/**
	 * 准备添加的模型
	 */
	private List<V> toAddModel = new ArrayList<V>(0);
	
	/**
	 * 准备更新的模型
	 */
	private List<V> toUpdateModel = new ArrayList<V>(0);;
	
	/**
	 * 准备删除的模型，为待删除数据的主键
	 */
	private List<KEY> toDelModel = new ArrayList<KEY>(0);;

	public BizModel<KEY, V> buildAdd(List<V> toAddModel) {
		this.toAddModel = toAddModel;
		return this;
	}

	public BizModel<KEY, V> buildUpdate(List<V> toUpdateModel) {
		this.toUpdateModel = toUpdateModel;
		return this;
	}
	
	public BizModel<KEY, V> buildDel(List<KEY> toDelModel) {
		this.toDelModel = toDelModel;
		return this;
	}

	public List<V> getToAddModel() {
		return toAddModel;
	}

	public List<V> getToUpdateModel() {
		return toUpdateModel;
	}

	public List<KEY> getToDelModel() {
		return toDelModel;
	}
	
}
