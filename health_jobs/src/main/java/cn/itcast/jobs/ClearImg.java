package cn.itcast.jobs;

import cn.itcast.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import java.util.Set;

public class ClearImg {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        //获取redis缓存中的垃圾图片
        Set<String> clearImgs = jedisPool.getResource().sdiff("allUploadPictures", "setmealPicDbResources");
        for (String clearImg : clearImgs) {
            QiniuUtils.delete(clearImg);
            jedisPool.getResource().srem("allUploadPictures",clearImg);
        }
    }
}
