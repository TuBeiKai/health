package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;


    /**
     * 使用手机号和验证码登录
     * @param response
     * @param map
     * @return
     */
    @RequestMapping("/check")
    public Result checkUserByValidatenCode(HttpServletResponse response, @RequestBody Map<String, String> map) {

        String telephone = map.get("telephone");
        String redisCode = jedisPool.getResource().get(RedisConstant.SMS_LOGIN_VALIDATECODE + "_" + telephone);

        if (redisCode != null && redisCode.equals(map.get("validateCode"))) {
            Member member = memberService.findByTelephone(telephone);
            if (member == null) {
                //当前用户不是会员，自动完成注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //登录成功
            //写入Cookie，跟踪用户
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");//路径
            cookie.setMaxAge(60 * 60 * 24 * 30);//有效期30天
            response.addCookie(cookie);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

}
