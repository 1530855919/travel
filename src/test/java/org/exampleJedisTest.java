package org;

import cn.itcast.travel.util.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class exampleJedisTest {

    @Test
    public void test(){
        Jedis jedis = JedisUtil.getJedis();
        jedis.set("abc","佟离");
    }

}
