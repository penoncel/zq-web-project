package com.mer.framework.Config.Shiro.RedisShiro;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义 RedisCacheManager
 */
public class RedisCacheManager implements CacheManager {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();
    private RedisSerializer keySerializer = new StringSerializer();
    private RedisSerializer valueSerializer = new ObjectSerializer();
    private IRedisManager redisManager;
    private static final int DEFAULT_EXPIRE = 1800;
    private int expire = 1800;
    public static final String DEFAULT_CACHE_KEY_PREFIX = "shiro:cache:";
    private String keyPrefix = "shiro:cache:";

    public RedisCacheManager() {
    }


    /**
     * 这里重写 reids cache 前缀
     * @param name
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("redis 登入用户 =" + keyPrefix);
        Cache cache = (Cache)this.caches.get(keyPrefix);
        if (cache == null) {
            cache = new RedisCache(this.redisManager, this.keySerializer, this.valueSerializer, this.keyPrefix, this.expire);
            this.caches.put(keyPrefix, cache);
        }

        return (Cache)cache;
    }

    public IRedisManager getRedisManager() {
        return this.redisManager;
    }

    public void setRedisManager(IRedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public String getKeyPrefix() {
        return this.keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public RedisSerializer getKeySerializer() {
        return this.keySerializer;
    }

    public void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    public RedisSerializer getValueSerializer() {
        return this.valueSerializer;
    }

    public void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public int getExpire() {
        return this.expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
