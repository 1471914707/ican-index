package com.ican.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class JedisAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);


    public static void print(int index, Object obj) {
        System.out.println(String.format("%d,%s", index, obj.toString()));
    }

    public static void mainx4(String[] args) {
        JedisAdapter jedisAdapter = new JedisAdapter();
        jedisAdapter.createSchoolActive();
        //jedisAdapter.clearSchoolDayLogin("华南师范学院");
        Random random = new Random();
        for (int i = 0; i < random.nextInt(1000); i++) {
            jedisAdapter.incSchoolDayLogin("广东技术师范学院");
        }
        for (int i = 0; i < random.nextInt(1000); i++) {
            jedisAdapter.incSchoolDayLogin("暨南大学");
        }
        for (int i = 0; i < random.nextInt(1000); i++) {
            jedisAdapter.incSchoolDayLogin("华南理工大学");
        }
        for (int i = 0; i < random.nextInt(1000); i++) {
            jedisAdapter.incSchoolDayLogin("华南师范大学");
        }
        for (int i = 0; i < random.nextInt(1000); i++) {
            jedisAdapter.incSchoolDayLogin("中山大学");
        }
        for (int i = 0; i < random.nextInt(1000); i++) {
            jedisAdapter.incSchoolDayLogin("华南农业大学");
        }
        for (int i = 0; i < random.nextInt(1000); i++) {
            jedisAdapter.incSchoolDayLogin("广东金融学院");
        }
    }

    public static void mainx2(String[] args) {
        Jedis jedis = new Jedis();
        //jedis.flushAll();
        Object o = jedis.get(RedisKeyUtil.getUserIdKey());
        Integer id = 0;
        if (o == null) {
            id = 100000;
            jedis.set(RedisKeyUtil.getUserIdKey(), "100000");
        } else {
            jedis.incr(RedisKeyUtil.getUserIdKey());
            id = Integer.valueOf(jedis.get(RedisKeyUtil.getUserIdKey()));
        }
        System.out.println(id);
    }

    public static void mainx3(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushAll();
        // get,set
        jedis.set("hello", "world");
        print(1, jedis.get("hello"));
        jedis.rename("hello", "newhello");
        print(1, jedis.get("newhello"));
        jedis.setex("hello2", 15, "world");

        // 数值操作
        jedis.set("pv", "100");
        jedis.incr("pv");
        jedis.decrBy("pv", 5);
        print(2, jedis.get("pv"));
        print(3, jedis.keys("*"));

        // 列表操作, 最近来访, 粉丝列表，消息队列
        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; ++i) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(4, jedis.lrange(listName, 0, 12)); // 最近来访10个id
        print(5, jedis.llen(listName));
        print(6, jedis.lpop(listName));
        print(7, jedis.llen(listName));
        print(8, jedis.lrange(listName, 2, 6)); // 最近来访10个id
        print(9, jedis.lindex(listName, 3));
        print(10, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
        print(10, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "bb"));
        print(11, jedis.lrange(listName, 0, 12));


        // hash, 可变字段
        String userKey = "userxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "18666666666");
        print(12, jedis.hget(userKey, "name"));
        print(13, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(14, jedis.hgetAll(userKey));
        print(15, jedis.hexists(userKey, "email"));
        print(16, jedis.hexists(userKey, "age"));
        print(17, jedis.hkeys(userKey));
        print(18, jedis.hvals(userKey));
        jedis.hsetnx(userKey, "school", "zju");
        jedis.hsetnx(userKey, "name", "yxy");
        print(19, jedis.hgetAll(userKey));

        // 集合，点赞用户群, 共同好友
        String likeKey1 = "newsLike1";
        String likeKey2 = "newsLike2";
        for (int i = 0; i < 10; ++i) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * 2));
        }
        print(20, jedis.smembers(likeKey1));
        print(21, jedis.smembers(likeKey2));
        print(22, jedis.sunion(likeKey1, likeKey2));
        print(23, jedis.sdiff(likeKey1, likeKey2));
        print(24, jedis.sinter(likeKey1, likeKey2));
        print(25, jedis.sismember(likeKey1, "12"));
        print(26, jedis.sismember(likeKey2, "12"));
        jedis.srem(likeKey1, "5");
        print(27, jedis.smembers(likeKey1));
        // 从1移动到2
        jedis.smove(likeKey2, likeKey1, "14");
        print(28, jedis.smembers(likeKey1));
        print(29, jedis.scard(likeKey1));

        // 排序集合，有限队列，排行榜
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "Jim");
        jedis.zadd(rankKey, 60, "Ben");
        jedis.zadd(rankKey, 90, "Lee");
        jedis.zadd(rankKey, 75, "Lucy");
        jedis.zadd(rankKey, 80, "Mei");
        print(30, jedis.zcard(rankKey));
        print(31, jedis.zcount(rankKey, 61, 100));
        // 改错卷了
        print(32, jedis.zscore(rankKey, "Lucy"));
        jedis.zincrby(rankKey, 2, "Lucy");
        print(33, jedis.zscore(rankKey, "Lucy"));
        jedis.zincrby(rankKey, 2, "Luc");
        print(34, jedis.zscore(rankKey, "Luc"));
        print(35, jedis.zcount(rankKey, 0, 100));
        // 1-4 名 Luc
        print(36, jedis.zrange(rankKey, 0, 10));
        print(36, jedis.zrange(rankKey, 1, 3));
        print(36, jedis.zrevrange(rankKey, 1, 3));
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "60", "100")) {
            print(37, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }

        print(38, jedis.zrank(rankKey, "Ben"));
        print(39, jedis.zrevrank(rankKey, "Ben"));

        String setKey = "zset";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");
        print(40, jedis.zlexcount(setKey, "-", "+"));
        print(41, jedis.zlexcount(setKey, "(b", "[d"));
        print(42, jedis.zlexcount(setKey, "[b", "[d"));
        jedis.zrem(setKey, "b");
        print(43, jedis.zrange(setKey, 0, 10));
        jedis.zremrangeByLex(setKey, "(c", "+");
        print(44, jedis.zrange(setKey, 0, 2));

        /*
        jedis.lpush("aaa", "A");
        jedis.lpush("aaa", "B");
        jedis.lpush("aaa", "C");
        print(45, jedis.brpop(0, "aaa"));
        print(45, jedis.brpop(0, "aaa"));
        print(45, jedis.brpop(0, "aaa"));
        */


        JedisPool pool = new JedisPool();
        for (int i = 0; i < 100; ++i) {
            Jedis j = pool.getResource();
            j.get("a");
            j.close();
        }
    }

    public static void main5(String[] args) {
        JedisAdapter jedisAdapter = new JedisAdapter();
        jedisAdapter.set("林嘉瑜", "hello");
    }

    private static Jedis jedis = null;
    private static JedisPool pool = null;

    public JedisAdapter() {
        pool = new JedisPool("localhost", 6379);
    }

   /* @Override
    public void afterPropertiesSet() throws Exception {
        //jedis = new Jedis("localhost");
        pool = new JedisPool("localhost", 6379);
    }*/

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setex(String key, String value) {
        // 验证码, 防机器注册，记录上次注册时间，有效期3天
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.setex(key, 10, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setObject(String key, Object obj) {
        set(key, JSON.toJSONString(obj));
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (value != null) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

    public int getUserId() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Object obj = jedis.get(RedisKeyUtil.getUserIdKey());
            Integer id = 0;
            if (obj == null) {
                id = 100000;
                jedis.set(RedisKeyUtil.getUserIdKey(), "100000");
            } else {
                jedis.incr(RedisKeyUtil.getUserIdKey());
                id = Integer.valueOf(jedis.get(RedisKeyUtil.getUserIdKey()));
            }
            return id;
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return -1;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void createSchoolActive() {
        String key = RedisKeyUtil.getSchoolDayLogin();
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Map<String, String> map = jedis.hgetAll(key);
            if (map == null) {
                jedis.hset(key, "广东技术师范学院", "0");
            }
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void clearSchoolDayLogin(String schoolName) {
        String key = RedisKeyUtil.getSchoolDayLogin();
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Map<String, String> map = jedis.hgetAll(key);
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    jedis.hset(key, entry.getKey(), "0");
                }
            }
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void incSchoolDayLogin(String schoolName) {
        String key = RedisKeyUtil.getSchoolDayLogin();
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Object obj = jedis.hget(key, schoolName);
            if (obj == null) {
                jedis.hset(key, schoolName, "1");
            } else {
                jedis.hset(key, schoolName, (Integer.valueOf((String) obj) + 1) + "");
            }
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Map<String, String> getSchoolDayLogin() {
        String key = RedisKeyUtil.getSchoolDayLogin();
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Map<String, String> map = jedis.hgetAll(key);
            return map;
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
