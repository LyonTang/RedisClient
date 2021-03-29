//******************************************************************
//系统名称：Redis
//模块名称：TODO
//版本信息
//版本:1.0    日期:2019年2月28日    作者:Leon     备注:新建
//******************************************************************

package cn.ox0a.redis.beans;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import cn.ox0a.redis.constant.GenerateRedisHelper;
import cn.ox0a.redis.constant.IConstance;

import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * <b>概述</b>： <blockquote>Redis哨兵模式适配器</blockquote>
 * <p/>
 * <b>功能</b>： <blockquote>Redis哨兵模式适配器</blockquote>
 *
 * @author <a href="mailto:1284676837@qq.com">Leon</a>
 *
 */
@Configuration
@ConditionalOnProperty(name = "spring.redis.type",
        havingValue = IConstance.SENTINEL)
public class SentinelBean {
    /**
     * master名字
     */
    @Value("${spring.redis.sentinel.master:master}")
    private String master;
    /**
     * 哨兵
     */
    @Value("${spring.redis.sentinel.nodes:10.144.4.222:8080}")
    private String nodes;
    /**
     * 密码
     */
    @Value("${spring.redis.password:}")
    private String password;

    /**
     *
     * 创建一个新的实例 SentinelBean.
     *
     */
    public SentinelBean() {
    }

    /**
     *
     * 创建一个新的实例 SentinelBean.
     *
     * @param master
     *            主节点名称
     * @param nodes
     *            哨兵集群
     * @param password
     *            密码
     */
    public SentinelBean(String master, String nodes, String password) {
        super();
        this.nodes = nodes;
        this.master = master;
        this.password = password;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 哨兵模式配置
     *
     * @return RedisSentinelConfiguration
     */
    @Bean
    public RedisSentinelConfiguration sentinelConfiguration() {
        RedisSentinelConfiguration redisRentinelConfiguration
                = new RedisSentinelConfiguration();
        // IP,端口号
        /*
         * RedisNode redisNode = new RedisNode(host, port); // master 主节点
         * redisNode.setName(master);
         */
        redisRentinelConfiguration.master(master);

        // 配置redis的哨兵sentinel
        Set<RedisNode> sentinels = new HashSet<>();
        String[] sens = nodes.split(",");
        for (String sen : sens) {
            String[] temp = sen.split(":");
            String senHost = temp[0];
            Integer senPort = Integer.valueOf(temp[1]);
            RedisNode senRedisNode = new RedisNode(senHost, senPort);
            sentinels.add(senRedisNode);
        }
        redisRentinelConfiguration.setSentinels(sentinels);
        return redisRentinelConfiguration;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 哨兵工厂
     *
     * @param jedisPoolConfig
     *            连接池配置
     * @param sentinelConfig
     *            哨兵模式配置
     * @return JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(
            JedisPoolConfig jedisPoolConfig,
            RedisSentinelConfiguration sentinelConfig) {
        JedisConnectionFactory jedisConnectionFactory
                = new JedisConnectionFactory(sentinelConfig, jedisPoolConfig);
        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        return jedisConnectionFactory;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 统一对外接口
     *
     * @param factory
     *            哨兵工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplateComm(
            RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String,
                Object>();
        GenerateRedisHelper.initDomainRedisTemplate(redisTemplate, factory);
        return redisTemplate;
    }
}
