//******************************************************************
//系统名称：Redis
//模块名称：TODO
//版本信息
//版本:1.0    日期:2019年3月4日    作者:Leon     备注:新建
//******************************************************************

package cn.ox0a.redis.beans;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ox0a.redis.SpBootTest;

import redis.clients.jedis.JedisPoolConfig;

/**
 * <b>概述</b>： <blockquote>请补充</blockquote>
 * <p/>
 * <b>功能</b>： <blockquote>请补充</blockquote>
 *
 * @author <a href="mailto:1284676837@qq.com">Leon</a>
 **/
public class SentinelBeanTest extends SpBootTest{

    private SentinelBean sentinelBean = new SentinelBean("mymaster", "10.144.4.122:32312", "AAAA");
    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    /**
     * Test method for
     * {@link SentinelBean#sentinelConfiguration()}.
     */
    @Test
    public void testSentinelConfiguration() {
        assertNotNull(sentinelBean.sentinelConfiguration());
    }

    /**
     * Test method for
     * {@link SentinelBean#jedisConnectionFactory(redis.clients.jedis.JedisPoolConfig, org.springframework.data.redis.connection.RedisSentinelConfiguration)}.
     */
    @Test
    public void testJedisConnectionFactory() {
        assertNotNull(sentinelBean.jedisConnectionFactory(jedisPoolConfig,
                sentinelBean.sentinelConfiguration()));
    }

    /**
     * Test method for
     * {@link SentinelBean#redisTemplateComm(org.springframework.data.redis.connection.RedisConnectionFactory)}.
     */
    @Test
    public void testRedisTemplateComm() {
        assertNotNull(sentinelBean.redisTemplateComm(sentinelBean
                .jedisConnectionFactory(jedisPoolConfig, sentinelBean
                        .sentinelConfiguration())));
    }

}
