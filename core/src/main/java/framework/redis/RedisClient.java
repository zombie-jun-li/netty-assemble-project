package framework.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by jun.
 */
public class RedisClient {
    private final JedisPool jedisPool;

    public RedisClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public <T> T execute(Command<T> command) {
        try (Jedis jedis = jedisPool.getResource()) {
            return command.execute(jedis);
        }
    }

    public String get(String key) {
        return execute(jedis -> jedis.get(key));
    }

    public String set(String key, String value) {
        return execute(jedis -> jedis.set(key, value));
    }
}
