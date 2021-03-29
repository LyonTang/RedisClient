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
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import cn.ox0a.redis.constant.GenerateRedisHelper;
import cn.ox0a.redis.constant.IConstance;

import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * <b>概述</b>： <blockquote>Redis集群模式适配器</blockquote>
 * <p/>
 * <b>功能</b>： <blockquote>Redis集群模式适配器</blockquote>
 *
 * @author <a href="mailto:1284676837@qq.com">Leon</a>
 *
 */
@Configuration
@ConditionalOnProperty(name = "spring.redis.type",
        havingValue = IConstance.CLUSTER)
public class ClusterBean {

    /**
     * 节点设置
     */
    @Value("${spring.redis.cluster.nodes:10.144.4.221:8080}")
    private String clusternodes;
    /**
     * 密码
     */
    @Value("${spring.redis.password:}")
    private String password;

    /**
     *
     * <b>功能</b>：<br/>
     * 集群配置
     *
     * @return RedisClusterConfiguration
     */
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration
                = new RedisClusterConfiguration();
        String[] serverArray = clusternodes.split(",");
        Set<RedisNode> nodes = new HashSet<RedisNode>();
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new RedisNode(ipPortPair[0].trim(), Integer.valueOf(
                    ipPortPair[1].trim())));
        }
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(100);
        return redisClusterConfiguration;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 集群工厂
     *
     * @param jedisPoolConfig
     *            连接池配置
     * @param redisClusterConfiguration
     *            集群配置
     * @return JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(
            JedisPoolConfig jedisPoolConfig,
            RedisClusterConfiguration redisClusterConfiguration) {
        JedisConnectionFactory jedisConnectionFactory
                = new JedisConnectionFactory(redisClusterConfiguration,
                        jedisPoolConfig);
        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        return jedisConnectionFactory;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 对外统一接口
     *
     * @param factory
     *            工厂
     * @return RedisTemplate redisTemplate使用Autowired获取
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
