package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {

        Jedis jedis = JedisUtil.getJedis();
        //先从redis中查询
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> all = null;

        if(categorys==null || categorys.size()==0){
            //redis不存在则从mysql中查询
            all = categoryDao.findAll();

            //并将查询结果存储到redis中
            for(Category category:all){
                jedis.zadd("category",category.getCid(),category.getCname());
            }


        }else {

            all = new ArrayList<>();

            for(Tuple tuple:categorys){
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                all.add(category);
            }

        }

        //关闭jedis
        JedisUtil.close(jedis);

        return all;
    }
}
