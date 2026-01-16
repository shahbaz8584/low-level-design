package DesignPattern.StructuralDesign.ProxyPattern;

/**
 * Skeleton Redis client for caching. Implement using Jedis or Spring Data Redis.
 *
 * Example (Jedis) pseudocode:
 *  private JedisPool pool = new JedisPool("localhost", 6379);
 *  try (Jedis j = pool.getResource()) {
 *      j.set(key, serializedValue);
 *  }
 *
 * This class intentionally throws UnsupportedOperationException until you
 * implement it with a real Redis client and (de)serialization for EmployeeData.
 */
public class RedisCacheClient {

    public RedisCacheClient() {
        // No-op. Implement initialization if you add Redis dependencies.
    }

    public EmployeeData get(int empId) {
        throw new UnsupportedOperationException("Redis not configured. Add Jedis/Spring Redis and implement RedisCacheClient.");
    }

    public void set(int empId, EmployeeData data) {
        throw new UnsupportedOperationException("Redis not configured. Add Jedis/Spring Redis and implement RedisCacheClient.");
    }

    public void del(int empId) {
        throw new UnsupportedOperationException("Redis not configured. Add Jedis/Spring Redis and implement RedisCacheClient.");
    }
}
