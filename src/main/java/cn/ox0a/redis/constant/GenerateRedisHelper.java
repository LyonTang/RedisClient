//******************************************************************
//系统名称：Redis
//模块名称：TODO
//版本信息
//版本:1.0    日期:2019年3月4日    作者:Leon     备注:新建
//******************************************************************
package cn.ox0a.redis.constant;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>概述</b>：
 * <blockquote>为restTemplate定制属性</blockquote>
 * <p/>
 * @author  <a href="mailto:1284676837@qq.com">Leon</a>
 **/
public class GenerateRedisHelper {
    /**
     *
     * <b>功能</b>：<br/>
     * 为RedisTemplate初始化配置
     *
     * @param redisTemplate
     *            redisTemplate
     * @param factory
     *            factory
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void initDomainRedisTemplate(RedisTemplate<String,
            Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer
                = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        redisTemplate.setEnableTransactionSupport(true);
    }
}
