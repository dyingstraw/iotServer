package util;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.commands.*;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-29 16:40
 **/
@Slf4j
public class RedisUtil   {
    private static JedisPool jedisPool ;
    static  {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestWhileIdle(false);
        config.setTimeBetweenEvictionRunsMillis(-1);
        jedisPool=new JedisPool(config,"192.168.1.102",6379,3000);
        log.info("redis is running");
    }

    private static Jedis getResource(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    public static Jedis getJedis(){
        final Jedis jedis = getResource();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(jedis.getClass());
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                Object result = method.invoke(jedis, objects);
                log.info("active:{}",jedisPool.getNumActive());
                jedis.close();
                return result;
            }
        });
        return (Jedis) enhancer.create();
    }

    public static long writeMap2Redis(String key,Map<String,String> map){
        Long count=0L;
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> temp = it.next();
            count +=getJedis().hset(key,temp.getKey(),temp.getValue());
        }
        return count;
    }







}
