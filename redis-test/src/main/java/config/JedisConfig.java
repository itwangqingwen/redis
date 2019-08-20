package config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConfig {
    private static String ADDR = "127.0.0.1";
    private static Integer PORT = 6379;
    private static Integer MAX_TOTAL = 1024;
    private static Integer MAX_IDLE = 200;
    private static JedisPool jedisPool = null;
    private static Integer TIMEOUT = 10000;
    private static Integer MAX_WAIT_MILLIS = 10000;
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(MAX_IDLE);
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            jedisPool = new JedisPool(config,ADDR, PORT,TIMEOUT);
            System.out.println("获取连接池成功");
        } catch (Exception e) {
           // e.printStackTrace();
            System.out.println("获取连接池失败");
        } finally {

        }
    }

    public synchronized static Jedis getJedis() {

        try {
            if (jedisPool != null) {
                System.out.println("开始获取连接池资源");
                Jedis jedis = jedisPool.getResource();
                System.out.println("连接池资源获取成功");
                return jedis;
            } else {
                System.out.println("连接池资源获取失败");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.close();
            return null;
        }
    }
    public static void close(){
        try {
            if(jedisPool!=null){
                jedisPool.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.close();
            System.out.println("jedisPool关闭完成");
        }
    }




}
