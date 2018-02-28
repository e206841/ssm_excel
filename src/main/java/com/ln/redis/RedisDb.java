package com.ln.redis;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.ln.core.utils.ApplicationUtils;
import com.ln.core.utils.FileUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDb {
	
    private static JedisPool jedisPool;
    // session 在redis过期时间是30分钟30*60
    private static int expireTime = 30*60;
    // 计数器的过期时间默认2天
    private static int countExpireTime = 2*24*3600; 
    private static String password = "";
    private static String redisIp = "127.0.0.1";
    private static int redisPort = 6379;
    private static int maxActive = 200;
    private static int maxIdle = 200;
    private static long maxWait = 5000;
    private static Logger logger = LoggerFactory.getLogger(RedisDb.class);

    static {
    	String filePath = null;
		try {
			filePath=ApplicationUtils.getWebFileAbsoluteClassPath("application.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Properties pro = FileUtil.loadProperties(filePath);
    	redisIp = pro.getProperty("redis.host");
    	redisPort = Integer.valueOf(pro.getProperty("redis.port"));
    	password = pro.getProperty("redis.password");
        initPool();        
    }
    // 初始化连接池
    public static void initPool(){
    	logger.debug("start init redis connection pool");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(false);
        
        if(password != null && !"".equals(password.trim())){
        	jedisPool = new JedisPool(config, redisIp, redisPort, 10000, password);
        }else{
        	jedisPool = new JedisPool(config, redisIp, redisPort, 10000);
        }
        
    }
    // 从连接池获取redis连接
    public static Jedis getJedis(){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
//            jedis.auth(password);
        } catch(Exception e){
        	e.printStackTrace();
        }
        
        return jedis;
    }
    // 回收redis连接
    public static void recycleJedis(Jedis jedis){
        if(jedis != null){
            try{
                jedis.close();
            } catch(Exception e){
            	e.printStackTrace();
            }
        }        
    }
    // 保存字符串数据
    public static void setString(String key, String value){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.set(key, value);
            } catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }
        
    } 
    // 获取字符串类型的数据
    public static String getString(String key){
        Jedis jedis = getJedis();
        String result = "";
        if(jedis != null){
            try{
                result = jedis.get(key);
            }catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }
        
        return result;
    }
    // 删除字符串数据
    public static void delString(String key){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.del(key);
            }catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }
    }
    
    /**
     * 删除字节类型的数据
     * @param key
     */
    public static void delObject(String key){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.del(key.getBytes());
            }catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }
    }
    
    /**
     * 保存byte型数据
     */
    public static void setObject(String key, byte[] value){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.set(key.getBytes(), value);
            } catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }    
    }
    
    /**
     * 更信息key的有效时间
     * @param key
     * @param seconds
     */
    public static void expireObject(String key, int seconds){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.expire(key.getBytes(), seconds);
            } catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }    
    }
    
    /**
     * 更信息key的有效时间
     * @param key
     * @param seconds
     */
    public static void expireString(String key, int seconds){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                jedis.expire(key, seconds);
            } catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }    
    }
    
    /**
     * 获取字节类型的数据
     * @param key
     * @return
     */
    public static byte[] getObject(String key){
        Jedis jedis = getJedis();
        byte[] bytes = null;
        if(jedis != null){
            try{
                bytes = jedis.get(key.getBytes());
            }catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }    
        return bytes;
    }
    
    // 更新byte类型的数据，主要更新过期时间
    public static void updateObject(byte[] key){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                // redis中session过期时间
                jedis.expire(key, expireTime);
            }catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }    
        
    }
    
    // key对应的整数value加1
    public static void inc(String key){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                if(!jedis.exists(key)){
                    jedis.set(key, "1");
                    jedis.expire(key, countExpireTime);
                } else {
                    // 加1
                    jedis.incr(key);
                }
            }catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }    
    }
    
    // 获取所有keys
    public static Set<String> getAllKeys(String pattern){
        Jedis jedis = getJedis();
        if(jedis != null){
            try{
                return jedis.keys(pattern);
            }catch(Exception e){
            	e.printStackTrace();
            } finally{
                recycleJedis(jedis);
            }
        }
        return null;
    }

}
