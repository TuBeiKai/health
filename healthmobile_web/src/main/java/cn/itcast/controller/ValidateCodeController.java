package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisConstant;
import cn.itcast.entity.Result;
import cn.itcast.utils.SMSUtils;
import cn.itcast.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 发送短信
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;


    /**
     * 发送验证码
     * @param telephone
     * @return
     */
    @RequestMapping("/send4Order")
    public Result sendValidateCodeMessage(String telephone){
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
        try {
            SMSUtils.sendShortMesseage(SMSUtils.VALIDATE_CODE,telephone,code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(RedisConstant.SMS_ORDER_VALIDATECODE +"_"+telephone, 15 * 60, code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result sendLoginValidateCodeMessage(String telephone){
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
        try {
            SMSUtils.sendShortMesseage(SMSUtils.VALIDATE_CODE,telephone,code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(RedisConstant.SMS_LOGIN_VALIDATECODE +"_"+telephone, 15 * 60, code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
