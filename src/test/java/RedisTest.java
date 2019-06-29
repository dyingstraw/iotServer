import model.message.Message;
import org.springframework.beans.BeanUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.commands.JedisCommands;
import util.RedisUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-29 15:49
 **/
public class RedisTest {

    JedisPoolConfig config = new JedisPoolConfig();
    private static JedisPool jedisPool = new JedisPool(new JedisPoolConfig(),"192.168.1.102",6379,3000,"dyingstraw");

    public static Jedis getResource(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    public static void main(String[] args) {


        RedisUtil redisUtil = new RedisUtil();
        Jedis r =redisUtil.getJedis();
        for (int i = 0; i < 100; i++) {
            r.hset("auth",String.valueOf(i),"123456");
        }

    }

}
