import com.sun.javafx.collections.MappingChange;
import config.JedisConfig;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class JedisPoolTest {

    Jedis jedis = JedisConfig.getJedis();
    @Test
    public void testString() {
        //添加数据
       jedis.set("name","zhang");
        System.out.println("拼接前:" + jedis.get("name"));//读取key为name的值

        //向key为name的值后面加上数据 ---拼接
        jedis.append("name", " san;");
        System.out.println("拼接后:" + jedis.get("name"));

        //删除某个键值对
        jedis.del("name");
        System.out.println("删除后:" + jedis.get("name"));

        //s设置多个键值对
        jedis.mset("computer","huashuo","cpu","coreI7","memory","8G");
        jedis.mset("name", "chenhaoxiang", "age", "20", "email", "chxpostbox@outlook.com");
        jedis.incr("age");//用于将键的整数值递增1。如果键不存在，则在执行操作之前将其设置为0。 如果键包含错误类型的值或包含无法表示为整数的字符串，则会返回错误。此操作限于64位有符号整数。
        System.out.println(jedis.get("name") + " " + jedis.get("age") + " " + jedis.get("email"));
        System.out.println(jedis.get("computer") + " " + jedis.get("cpu") + " " + jedis.get("memory"));
        JedisConfig.close();
    }
    @Test
    public void testHash() {
        //添加数据
        Map<String,String> map = new HashMap<String, String>();
        map.put("computer","huashuo");
        map.put("memory","8G");
        map.put("cpu","corei7");
        jedis.hmset("computer1",map);
        System.out.println("List查看元素 =========");
        List<String> list = jedis.hmget("computer1", "computer", "memory", "cpu");
        System.out.println(list);
        System.out.println("List查看元素 =========");
        System.out.println("迭代器查看元素 =========");
        Iterator<String> iterator = jedis.hkeys("computer1").iterator();
        while(iterator.hasNext()){
            String keys = iterator.next();
            System.out.println(jedis.hmget("computer1",keys));
           // System.out.println(jedis.hget("computer1",keys));
        }
        System.out.println("迭代器查看元素 =========");
        //删除数据
        jedis.hdel("computer1","memory");


        //删除某个键值对
        jedis.hdel("computer1","memory");
        iterator = jedis.hkeys("computer1").iterator();
        while(iterator.hasNext()){
            String keys = iterator.next();
            System.out.println(jedis.hmget("computer1",keys));
            // System.out.println(jedis.hget("computer1",keys));
        }
        System.out.println("迭代器查看元素 =========");

        JedisConfig.close();
    }
    @Test
    public void testList(){
        jedis.lpush("conpute","huashuo");
        jedis.lpush("conpute","dell");
        jedis.lpush("conpute","lenover");
        jedis.lpush("conpute","hongqi");
        jedis.lrem("conpute",1,"huashuo");
        Iterator<String> iterator = jedis.lrange("conpute",0 ,-1).iterator();
        int key = 1;
        while(iterator.hasNext()){
            System.out.println(iterator.next());

        }
        JedisConfig.close();

    }
    @Test
    public void testSet(){
        //添加
       /* jedis.sadd("user","chenhaoxiang");
        jedis.sadd("user","hu");
        jedis.sadd("user","chen");
        jedis.sadd("user","xiyu");
        jedis.sadd("user","chx");
        jedis.sadd("user","are");
        //移除user集合中的元素are
        jedis.srem("user","are");
        System.out.println("user中的value:"+jedis.smembers("user"));//获取所有加入user的value
        System.out.println("chx是否是user中的元素:"+jedis.sismember("user","chx"));//判断chx是否是user集合中的元素
        System.out.println("集合中的一个随机元素:"+jedis.srandmember("user"));//返回集合中的一个随机元素
        System.out.println("user中元素的个数:"+jedis.scard("user"));*/
        jedis.sadd("sch","1");
        jedis.sadd("sch","2");
        jedis.sadd("sch","3");
        jedis.sadd("sch","4");
        jedis.sadd("sch","chx");
        System.out.println("user中的value:"+jedis.smembers("sch"));//获取所有加入user的value
        System.out.println("chx是否是user中的元素:"+jedis.sismember("sch","chx"));//判断chx是否是user集合中的元素
        System.out.println("集合中的一个随机元素:"+jedis.srandmember("sch"));//返回集合中的一个随机元素
        System.out.println("user中元素的个数:"+jedis.scard("sch"));
        JedisConfig.close();
    }

    /**
     * 排序
     */
    @Test
    public void test(){
        jedis.del("mem");
        jedis.lpush("mem","1");
        jedis.lpush("mem","2");
        jedis.lpush("mem","6");
        jedis.lpush("mem","2");
        jedis.lpush("mem","5");
        jedis.lpush("mem","3");
        System.out.println( jedis.lrange("mem",0,jedis.llen("mem")+1));
        System.out.println(jedis.sort("mem"));
        System.out.println( jedis.lrange("mem",0,jedis.llen("mem")+1));
        JedisConfig.close();
    }


}
