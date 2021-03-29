//******************************************************************
//系统名称：Redis
//模块名称：TODO
//版本信息
//版本:1.0    日期:2019年3月1日    作者:Leon     备注:新建
//******************************************************************

package cn.ox0a.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * <b>概述</b>： <blockquote>Redis操作客户端</blockquote>
 * <p/>
 * <b>功能</b>： <blockquote>提供对缓存的增删改查接口，简化操作</blockquote>
 *
 * @author <a href="mailto:1284676837@qq.com">Leon</a>
 **/
public class RedisClient {
    /**
     * redis操作接口
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplateComm;

    /**
     *
     * <b>功能</b>：<br/>
     * 存入缓存
     *
     * @param key
     *            键值
     * @param value
     *            缓存内容
     * @param expireTime
     *            超时时间，小于0不设置，单位秒
     * @return Boolean 是否成功
     */
    public Boolean set(String key, Object value, long expireTime) {
        // 如果必要，封装统一的键值判断接口
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            if (expireTime < 0) {
                set(key, value);
            } else {
                redisTemplateComm.opsForValue().set(key, value, expireTime,
                        TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * <b>功能</b>：<br/>
     * 存入缓存
     *
     * @param key
     *            键值
     * @param value
     *            缓存内容
     * @return Boolean 成功与否
     */
    public Boolean set(String key, Object value) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplateComm.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 获取缓存
     *
     * @param key
     *            键值
     * @return Object | null if key is empty
     */
    public Object get(String key) {
        return StringUtils.isEmpty(key) ? null
                : redisTemplateComm.opsForValue().get(key);
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 根据正则模糊匹配key
     *
     * @param keyPattern
     *            正则，支持*、?、[] <br/>
     *            * 匹配任意多字符 <br/>
     *            ? 匹配单个字符 <br/>
     *            [] 匹配括号内某一个字符 <br/>
     * @return Map<String, Object> 所有匹配的内容
     */
    public Map<String, Object> getByKeyLike(String keyPattern) {
        if (StringUtils.isEmpty(keyPattern)) {
            return null;
        }
        Set<String> keys = redisTemplateComm.keys(keyPattern);
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : keys) {
            result.put(key, get(key));
        }
        return result;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 清除缓存
     *
     * @param keys
     *            键值 <br/>
     *            单个字符串或字符串数组
     * @return Boolean 成功与否
     */
    @SuppressWarnings("unchecked")
    public Boolean remove(String... keys) {
        if (keys == null) {
            return false;
        }
        for (String key : keys) {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
        }
        try {
            if (keys.length == 1) {
                redisTemplateComm.delete(keys[0]);
            } else {
                redisTemplateComm.delete(CollectionUtils.arrayToList(keys));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 设置超时时间
     *
     * @param key
     *            键值
     * @param expireTime
     *            超时时间，单位秒
     * @return Boolean 成功与否
     */
    public Boolean expire(String key, long expireTime) {
        if (!isKeyExists(key) || expireTime < 0) {
            return false;
        }
        try {
            redisTemplateComm.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * <b>功能</b>：<br/>
     * 判断键值是否存在
     *
     * @param key
     *            键值
     * @return Boolean 是否存在
     */
    public Boolean isKeyExists(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return redisTemplateComm.hasKey(key);
    }

}
