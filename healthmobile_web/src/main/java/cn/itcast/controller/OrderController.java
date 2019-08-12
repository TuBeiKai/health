package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Order;
import cn.itcast.service.OrderService;
import cn.itcast.utils.SMSUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 保存用户套餐预约信息
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result saveOrder(@RequestBody Map<String, String> map) {
        String telephone = map.get("telephone");
        String redisCode = jedisPool.getResource().get(RedisConstant.SMS_ORDER_VALIDATECODE +"_"+ telephone);
        Result result = null;
        if (redisCode != null && redisCode.equals(map.get("validateCode"))) {
            try {
                map.put("orderType", Order.ORDERTYPE_WEIXIN);
                result = orderService.saveOrder(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        } else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        if(result.isFlag()){
            String orderDate = map.get("orderDate");
            try {
                SMSUtils.sendShortMesseage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 查询用户及预约套餐信息
     * @param id //用户id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Result result = orderService.findById(id);
        return result;
    }
}
