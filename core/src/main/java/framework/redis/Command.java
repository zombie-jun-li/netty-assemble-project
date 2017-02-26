package framework.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by jun.
 */
@FunctionalInterface
public interface Command<R> {
    R execute(Jedis jedis);
}
