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
 * <b>概述</b>：
 * <blockquote>请补充</blockquote>
 * <p/>
 * <b>功能</b>：
 * <blockquote>请补充</blockquote>
 * @author  <a href="mailto:1284676837@qq.com">Leon</a>
 **/
public class NormalBeanTest extends SpBootTest{
    private NormalBean normalBean = new NormalBean("xxxxxx", "192.168.0.39", 6379);

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    /**
     * Test method for {@link NormalBean#jedisConnectionFactory(redis.clients.jedis.JedisPoolConfig)}.
     */
    @Test
    public void testJedisConnectionFactory() {
        assertNotNull(normalBean.jedisConnectionFactory(jedisPoolConfig));
    }

    /**
     * Test method for {@link
     * +.beans.NormalBean#redisTemplateComm(org.springframework.data.redis.connection.RedisConnectionFactory)}.
     */
    @Test
    public void testRedisTemplateComm() {
        assertNotNull(normalBean.redisTemplateComm(normalBean.jedisConnectionFactory(jedisPoolConfig)));
    }

}
