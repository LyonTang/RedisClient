
package cn.ox0a.redis.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import cn.ox0a.redis.constant.GenerateRedisHelper;
import cn.ox0a.redis.constant.IConstance;

import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * <b>概述</b>： <blockquote>Redis单节点模式适配器</blockquote>
 * <p/>
 * <b>功能</b>： <blockquote>Redis单节点模式适配器</blockquote>
 *
 * @author <a href="mailto:1284676837@qq.com">Leon</a>
 *
 */
@Configuration
@ConditionalOnProperty(name = "spring.redis.type",
        havingValue = IConstance.NORMAL)
public class NormalBean {

    /**
     * 密码
     */
    @Value("${spring.redis.password:}")
    private String password;
    /**
     * redis ip/host
     */
    @Value("{spring.redis.normal.host:10.144.4.159}")
    private String host;
    /**
     * redis端口
     */
    @Value("{spring.redis.normal.port:6379}")
    private int port;

    /**
     *
     * 创建一个新的实例 NormalBean.
     *
     */
    public NormalBean() {
    }

    /**
     *
     * 创建一个新的实例 NormalBean.
     *
     * @param password
     *            password
     * @param host
     *            host
     * @param port
     *            port
     */
    public NormalBean(String password, String host, int port) {
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 单节点工厂
     *
     * @param jedisPoolConfig
     *            连接池配置
     * @return JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(
            JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory
                = new JedisConnectionFactory(jedisPoolConfig);

        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        if (!StringUtils.isEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        // 客户端超时时间，单位毫秒
        jedisConnectionFactory.setTimeout(5000);

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
