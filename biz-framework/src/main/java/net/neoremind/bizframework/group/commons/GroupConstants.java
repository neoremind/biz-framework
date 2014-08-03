package net.neoremind.bizframework.group.commons;

import java.util.List;

import net.neoremind.bizframework.commons.utils.EnumUtil;


public class GroupConstants {

	public static final String GROUP_NAME_PATTERN = "[\\w\\-\u4e00-\u9fa5]+";	//中文，英文，数字，_，-
	
	public static final String KEYWORD_PATTERN = "[a-zA-Z0-9\\.\\+\\#\\/\\ \\-\u4e00-\u9fa5]+";	
	
	/**
     * 推广组状态
     */
    public static enum GroupStateType {
    	
        NORMAL(0, "有效"), 
        PAUSE(1, "暂停"),
        DELETE(2, "删除"),
        PLAN_NOTBEGIN(3, "推广计划未开始"),
        PLAN_END(4, "推广计划已结束"),
        PLAN_PAUSE(5, "推广计划已暂停"),
        PALN_DELETE(6, "推广计划已删除"),
        PALN_OFFLINE(7, "推广计划已下线");
        
        public final int value;
        public final String name;

        GroupStateType(int value, String name) {
            this.name = name;
            this.value = value;
        }
    }
    
    public static final List<Integer> GROUP_STATE_TYPE = EnumUtil.listValue(GroupStateType.class);
    
    /**
     * 定向方式定义
     */
    public static final int GROUP_TARGET_TYPE_NONE = 0;//不定向（其他定向方式都没选）
	public static final int GROUP_TARGET_TYPE_CT = 1;//主题词定向（关键词定向的一种）
	public static final int GROUP_TARGET_TYPE_QT = 2;//搜索定向（关键词定向的一种）
	public static final int GROUP_TARGET_TYPE_HCT = 4;//历史主题词定向（关键词定向的一种）
	public static final int GROUP_TARGET_TYPE_RT = 8;//搜索点击定向（人群定向的一种）
	public static final int GROUP_TARGET_TYPE_VT = 16;//到访定向策略（人群定向的一种）
	public static final int GROUP_TARGET_TYPE_IT = 32;//兴趣定向
	public static final int GROUP_TARGET_TYPE_PACK = 64;//受众定向

	public static final int GROUP_TARGET_TYPE_CT_QT_HCT = 7;//CT&QT&HCT定向(关键词定向的一种)
	public static final int GROUP_TARGET_TYPE_CT_QT = 3; //CT&QT定向(关键词定向的一种)
	public static final int GROUP_TARGET_TYPE_CT_HCT = 5; //CT&HCT定向(关键词定向的一种)
	public static final int GROUP_TARGET_TYPE_QT_HCT = 6; //QT&HCT定向(关键词定向的一种)
	
	public static final int GROUP_TARGET_TYPE_AT_RIGHT = 128; //AT右定向
	public static final int GROUP_TARGET_TYPE_AT_LEFT = 256;  //AT左定向
    
	//TargetTypeUtil用的literal文字
	public static String KT_TARGETTYPE_QT_NAME = "搜索";
	public static String KT_TARGETTYPE_CT_NAME = "当前浏览";
	public static String KT_TARGETTYPE_HCT_NAME = "历史浏览";
	public static String TARGETTYPE_VT_NAME = "到访过特定网页";
	public static String TARGETTYPE_KT_NAME = "关注指定关键词";
	public static String TARGETTYPE_RT_NAME = "点击过搜索推广";
	public static String TARGETTYPE_PACK_NAME = "受众组合";
	public static String TARGETTYPE_NOT_SPECIFIED_NAME = "不使用";
	public static String ENABLE_NAME = "启用";
	public static String DISABLE_NAME = "不启用";
	
    
}
