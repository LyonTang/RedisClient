//******************************************************************
//系统名称：Redis
//模块名称：TODO
//版本信息
//版本:1.0    日期:2019年3月4日    作者:Leon     备注:新建
//******************************************************************

package cn.ox0a.redis.beans;

import cn.ox0a.redis.RedisClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;

/**
 * <b>概述</b>： <blockquote>提供Redis通用Bean</blockquote>
 * <p/>
 * <b>功能</b>： <blockquote>提供Redis通用Bean</blockquote>
 *
 * @author <a href="mailto:1284676837@qq.com">Leon</a>
 **/
@Component
public class RedisBeans {

    /**
     * 最大空闲数
     */
    private int maxIdle = 300;
    /**
     * 连接池最大连接数
     */
    private int maxTotal = 600;
    /**
     * 最大建立连接等待时间
     */
    private long maxWaitMillis = 100000L;
    /**
     * 逐出连接的的最小空闲时间，默认1800000（30min）
     */
    private long minEvictableIdleTimeMillis = 10000L;
    /**
     * 每次逐出检查的最大数目，如果为负数就是1/abs(n)，默认3
     */
    private int numTestsPerEvictionRun = 600;
    /**
     * 逐出扫描的时间间隔(毫秒)，如果为负数，则不运行逐出线程，默认-1
     */
    private int timeBetweenEvictionRunsMillis = 300;
    /**
     * 是否从池中取出连接前进行检验，如果失败，去连接池中尝试取出另一个
     */
    private boolean testOnBorrow = true;
    /**
     * 在空闲时检查有效性，默认false
     */
    private boolean testWhileIdle = false;

    /**
     *
     * <b>功能</b>：<br/>
     * 连接池初始化配置
     *
     * @return JedisPoolConfig
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(
                minEvictableIdleTimeMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(
                timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 获取公共组件-redis实例
     *
     * @return RedisClient
     */
    @Bean
    public RedisClient redisClient() {
        return new RedisClient();
    }

}
