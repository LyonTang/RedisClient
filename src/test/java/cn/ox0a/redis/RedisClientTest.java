package cn.ox0a.redis;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisClientTest extends SpBootTest{
    @Autowired
    private RedisClient redisClient;
    
    @Test
    public void testSetStringObjectLong() {
        User user = new User();
        user.setName("Expire");
        user.setGender("male");
        // 缓存在1000s后失效
        assertTrue(redisClient.set("expire", user, 1000));
        // key为空,插入/更新失效
        assertFalse(redisClient.set(null, user, 1000));
        // 缓存不设置超时时间
        assertTrue(redisClient.set("expire2", user, -100));
        redisClient.remove("expire");
        redisClient.remove("expire2");
    }

    @Test
    public void testSetStringObject() {
        User user = new User();
        user.setName("Tang Liang");
        user.setGender("male");
        assertTrue(redisClient.set("Leon", user));
        assertFalse(redisClient.set(null, user));
    }

    @Test
    public void testGet() {
        assertEquals("male", ((User)redisClient.get("Leon")).getGender());
        assertNull(redisClient.get(""));
    }
    
    @Test
    public void testRemove() {
        User user = new User();
        user.setName("Park");
        user.setGender("male");
        redisClient.set("park", user);
        User user2 = new User();
        user2.setName("May");
        user2.setGender("female");
        redisClient.set("may", user2);
        // 删除多个
        assertTrue(redisClient.remove(new String[] {"park", "may"}));
        // 不允许key为空
        assertFalse(redisClient.remove(null));
    }
    
    @Test
    public void testRemove2() {
        // 删除单个
        assertTrue(redisClient.remove("Leon"));
        // 不允许key为空
        assertFalse(redisClient.remove(""));
    }
    
    @Test
    public void testGetByKeyLike() {
        User user = new User();
        user.setName("Park");
        user.setGender("male");
        redisClient.set("aac", user);
        User user2 = new User();
        user2.setName("May");
        user2.setGender("female");
        redisClient.set("aab", user2);
        Map<String, Object> resMap = redisClient.getByKeyLike("aa*");
        assertEquals(2, resMap.size());
        assertNull(redisClient.getByKeyLike(null));
    }
    
    @Test
    public void testExpire() {
        assertTrue(redisClient.expire("aac", 1000));
        assertFalse(redisClient.expire(null, 1000));
        assertFalse(redisClient.expire("aac", -1));
    }
    
    @Test
    public void testIsKeyExists() {
        assertTrue(redisClient.isKeyExists("aac"));
        assertFalse(redisClient.isKeyExists(null));
        assertFalse(redisClient.isKeyExists("aad"));
        redisClient.remove(new String[] {"aac", "aab"});
    }
}
