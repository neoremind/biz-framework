package net.neoremind.bizframework.group.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.neoremind.bizframework.group.commons.GroupConstants;
import net.neoremind.bizframework.group.exception.TargetTypeException;

/**
 * @author hanxu03
 *
 */
public class TargettypeUtil {
	
	private static final int KT_TARGET_MASK = 
			GroupConstants.GROUP_TARGET_TYPE_CT + GroupConstants.GROUP_TARGET_TYPE_QT + GroupConstants.GROUP_TARGET_TYPE_HCT;
	
	private static final int ALL_TARGET_MASK = 
			GroupConstants.GROUP_TARGET_TYPE_CT + GroupConstants.GROUP_TARGET_TYPE_QT + GroupConstants.GROUP_TARGET_TYPE_HCT + 
			GroupConstants.GROUP_TARGET_TYPE_RT + GroupConstants.GROUP_TARGET_TYPE_VT + GroupConstants.GROUP_TARGET_TYPE_IT +
			GroupConstants.GROUP_TARGET_TYPE_PACK + GroupConstants.GROUP_TARGET_TYPE_AT_RIGHT;
	
	private static int getTargetByMask(int targetType, int mask){
		if(!isValid(targetType)){
			throw new TargetTypeException("targettype " + targetType + " is not valid!");
		}
		
		return targetType & mask;
	}
	
	private static boolean isValidKtRtVt(int kt, int rt, int vt){
		return !((kt>0 && rt>0) || (kt>0 && vt>0) || (rt>0 && vt>0));
	}
	
	/**
	 * 此方法不能调用getTargetByMask方法，否则会死循环
	 * @param targetType
	 * @return
	 */
	public static boolean isValid(int targetType){
		// 没有选定向方式时，最小值为0；所有定向方式都选中时，最大值为ALL_TARGET_MASK
		if(targetType<0 || targetType>ALL_TARGET_MASK){
			return false;
		}
		
		// 推广组的标准设置和受众定向设置不能共存
		int packTarget = targetType & GroupConstants.GROUP_TARGET_TYPE_PACK;
		if( packTarget>0 && packTarget != targetType){
			return false;
		}
		
		// KT、RT、VT不能两两共存
		if(!isValidKtRtVt(
				targetType & KT_TARGET_MASK, 
				targetType & GroupConstants.GROUP_TARGET_TYPE_RT, 
				targetType & GroupConstants.GROUP_TARGET_TYPE_VT)){
			return false;
		}
		
		return true;
	}
	
	public static boolean hasKT(int targetType){
		return getTargetByMask(targetType, KT_TARGET_MASK) > 0;
	}
	
	public static boolean hasIT(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_IT) > 0;
	}
	
	public static boolean hasRT(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_RT) > 0;
	}
	
	public static boolean hasVT(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_VT) > 0;
	}
	
	public static boolean hasCT(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_CT) > 0;
	}
	
	public static boolean hasQT(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_QT) > 0;
	}
	
	public static boolean hasHCT(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_HCT) > 0;
	}
	
	public static boolean isOnlyCT(int targetType){
		return getTargetByMask(targetType, KT_TARGET_MASK) == GroupConstants.GROUP_TARGET_TYPE_CT;
	}
	
	public static boolean isOnlyIT(int targetType){
		return getTargetByMask(targetType,ALL_TARGET_MASK) == GroupConstants.GROUP_TARGET_TYPE_IT;
	}
	
	public static boolean isPT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK) == 0;
	}
	
	public static boolean isPack(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_PACK) > 0;
	}
	
	public static boolean isAtRight(int targetType){
		return getTargetByMask(targetType, GroupConstants.GROUP_TARGET_TYPE_AT_RIGHT) > 0;
	}

	public static int getKtMask(){
		return KT_TARGET_MASK;
	}
	
	public static int getTargettype(int ct, int qt, int hct, int rt, int vt, int it, int pack){
		
		if(ct!=0 && ct!=GroupConstants.GROUP_TARGET_TYPE_CT){
			throw new TargetTypeException("ct targettype " + ct + " is not valid!");
		}
		
		if(qt!=0 && qt!=GroupConstants.GROUP_TARGET_TYPE_QT){
			throw new TargetTypeException("qt targettype " + qt + " is not valid!");
		}
		
		if(hct!=0 && hct!=GroupConstants.GROUP_TARGET_TYPE_HCT){
			throw new TargetTypeException("hct targettype " + hct + " is not valid!");
		}
		
		if(rt!=0 && rt!=GroupConstants.GROUP_TARGET_TYPE_RT){
			throw new TargetTypeException("rt targettype " + rt + " is not valid!");
		}
		
		if(vt!=0 && vt!=GroupConstants.GROUP_TARGET_TYPE_VT){
			throw new TargetTypeException("vt targettype " + vt + " is not valid!");
		}
		
		if(it!=0 && it!=GroupConstants.GROUP_TARGET_TYPE_IT){
			throw new TargetTypeException("it targettype " + it + " is not valid!");
		}
		
		if(pack!=0 && pack!=GroupConstants.GROUP_TARGET_TYPE_PACK){
			throw new TargetTypeException("pack targettype " + it + " is not valid!");
		}
		
		int target = ct | qt | hct | rt | vt | it | pack;
		if(!isValid(target)){
			throw new TargetTypeException("the merged targettype " + it + " is not valid!");
		}
		
		return target;
	}
	
	public static int clearKT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - KT_TARGET_MASK);
	}
	
	public static int clearIT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - GroupConstants.GROUP_TARGET_TYPE_IT);
	}
	
	public static int clearRT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - GroupConstants.GROUP_TARGET_TYPE_RT);
	}

	public static int clearVT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - GroupConstants.GROUP_TARGET_TYPE_VT);
	}
	
	public static int clearCT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - GroupConstants.GROUP_TARGET_TYPE_CT);
	}
	
	public static int clearQT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - GroupConstants.GROUP_TARGET_TYPE_QT);
	}
	
	public static int clearHCT(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - GroupConstants.GROUP_TARGET_TYPE_HCT);
	}
	
	public static int clearPack(int targetType){
		return getTargetByMask(targetType, ALL_TARGET_MASK - GroupConstants.GROUP_TARGET_TYPE_PACK);
	}
	
	/**
	 * 获取关键词定向方式文字
	 * @param targetType
	 * @return 获取CT/QT/HCT组合而成的字符串
	 */
	public static String getKTTargetTypeString(int targetType){
		return StringUtils.join(getKTTargetTypeList(targetType), ',');
	}
	
	public static List<String> getKTTargetTypeList(int targetType){
		List<String> list = new ArrayList<String>();
		if (hasQT(targetType)) {
			list.add(GroupConstants.KT_TARGETTYPE_QT_NAME);
		}
		if (hasCT(targetType)) {
			list.add(GroupConstants.KT_TARGETTYPE_CT_NAME);
		}
		if (hasHCT(targetType)) {
			list.add(GroupConstants.KT_TARGETTYPE_HCT_NAME);
		}
		return list;
	}
	
	/**
	 * 获取受众行为文字
	 * @param targetType
	 * @return KT/VT/RT/不指定受众行为字符串
	 */
	public static String getTargetTypeString(int targetType){
		if (hasVT(targetType)) {
			return GroupConstants.TARGETTYPE_VT_NAME;
		}
		if (hasKT(targetType)) {
			return GroupConstants.TARGETTYPE_KT_NAME;
		}
		if (hasRT(targetType)) {
			return GroupConstants.TARGETTYPE_RT_NAME;
		}
		if (isPack(targetType)) {
			return GroupConstants.TARGETTYPE_PACK_NAME;
		}
		return GroupConstants.TARGETTYPE_NOT_SPECIFIED_NAME;
	}
	
	/**
	 * 获取受众兴趣启用/不启用字面
	 * @param targetType
	 * @return 启用/不启用字面
	 */
	public static String getITEnableString(int targetType){
		if (hasIT(targetType)) {
			return GroupConstants.ENABLE_NAME;
		}
		return GroupConstants.DISABLE_NAME;
	}
	
	/**
	 * 获取受众组合启用/不启用字面
	 * @param targetType
	 * @return 启用/不启用字面
	 */
	public static String getPackEnableString(int targetType){
		if (isPack(targetType)) {
			return GroupConstants.ENABLE_NAME;
		}
		return GroupConstants.DISABLE_NAME;
	}
	
	/**
	 * 获取KT_TARGET_MASK后的KT定向方式值
	 * @param targetType
	 * @return
	 */
	public static int getKTType(int targetType){
		return getTargetByMask(targetType, KT_TARGET_MASK);
	}
	
	/**
	 * 根据当前的targettyp，返回当前起作用的定向方式的列表
	 * 如：targettype为35，则返回{1, 2, 32}；如果是PT，则返回{0}
	 * @param targetType
	 * @return
	 */
	public static List<Integer> getTargetList(int targetType){
		List<Integer> targetList = new ArrayList<Integer>();
		
		// 如果是受众定向，则直接返回
		if(isPack(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_PACK);
			return targetList;
		}
		
		// 如果是PT，则直接返回
		if(isPT(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_NONE);
			return targetList;
		}
		
		if(hasCT(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_CT);
		}
		if(hasQT(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_QT);
		}
		if(hasHCT(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_HCT);
		}
		if(hasRT(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_RT);
		}
		if(hasVT(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_VT);
		}
		if(hasIT(targetType)){
			targetList.add(GroupConstants.GROUP_TARGET_TYPE_IT);
		}
		
		return targetList;
	}
	
	public static void main(String[] args){
		int targetType = 64;
		System.out.println(TargettypeUtil.getTargetTypeString(targetType));
		System.out.println(TargettypeUtil.hasCT(targetType));
		System.out.println(TargettypeUtil.hasQT(targetType));
		System.out.println(TargettypeUtil.hasHCT(targetType));
		System.out.println(TargettypeUtil.hasRT(targetType));
		System.out.println(TargettypeUtil.hasVT(targetType));
		System.out.println(TargettypeUtil.hasKT(targetType));
		System.out.println(TargettypeUtil.isPT(targetType));
		System.out.println(TargettypeUtil.isOnlyCT(targetType));
		System.out.println(TargettypeUtil.isPack(targetType));
		
		targetType = 39;
		System.out.println(TargettypeUtil.clearKT(targetType));
		System.out.println(TargettypeUtil.clearIT(targetType));
		System.out.println(TargettypeUtil.clearCT(targetType));
		System.out.println(TargettypeUtil.clearQT(targetType));
		System.out.println(TargettypeUtil.clearHCT(targetType));
		System.out.println(TargettypeUtil.clearPack(targetType));
		
		targetType = 40;
		System.out.println(TargettypeUtil.clearRT(targetType));
		System.out.println(TargettypeUtil.clearVT(targetType));
		
		targetType = 35;
		System.out.println(Arrays.deepToString(TargettypeUtil.getTargetList(targetType).toArray()));
	}
}
