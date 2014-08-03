package net.neoremind.bizframework.commons.sterotype;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.neoremind.bizframework.commons.validator.error.BizError;

/**
 * 业务处理完毕结果，包含错误和数据
 * 
 * @author zhangxu04
 */
public class BizResult<V> {

	private List<BizError> errors = new ArrayList<BizError>();
	
	private List<V> dataList = new ArrayList<V>();
	
	public static <V> BizResult<V> build() {
		return new BizResult<V>();
	}
	
	public BizResult<V> addData(V data) {
		this.dataList.add(data);
		return this;
	}
	
	public void addAllData(List<V> dataList) {
		this.dataList = dataList;
	}
	
	public List<V> getDataList() {
		return dataList;
	}
	
	public List<BizError> getErrors() {
		return errors;
	}
	
	public void addError(BizError error) {
		this.errors.add(error);
	}

	public boolean isSuccess(){
		return errors.size() == 0 ? true : false;
	}
	
	public int getErrorNum(){
		return errors.size();
	}

	public String toString(){
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("dataList", dataList)
		.append("errors", errors)
        .toString();
	}
	
}
