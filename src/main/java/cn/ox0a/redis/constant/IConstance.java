//******************************************************************
//系统名称：Redis
//模块名称：TODO
//版本信息
//版本:1.0    日期:2019年3月4日    作者:Leon     备注:新建
//******************************************************************
package cn.ox0a.redis.constant;

/**
 * <b>概述</b>：
 * <blockquote>统一配置清单</blockquote>
 * @author  <a href="mailto:1284676837@qq.com">Leon</a>
 **/
public interface IConstance {
    /**
     * 集群模式
     */
    public final static String CLUSTER = "cluster";
    /**
     * 哨兵模式
     */
    public final static String SENTINEL = "sentinel";
    /**
     * 单节点模式
     */
    public final static String NORMAL = "normal";
}
