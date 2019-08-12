package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.dao.MemberDao;
import cn.itcast.dao.OrderDao;
import cn.itcast.dao.OrdersettingDao;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Member;
import cn.itcast.pojo.Order;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderService;
import cn.itcast.utils.DateFormatUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrdersettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Result saveOrder(Map<String, String> map) {
        System.out.println(map.get("idCard")+"-----------------------------");
        String orderDate = map.get("orderDate");
        String phoneNumber = map.get("telephone");
        Order order = new Order();
        Member member = new Member();
        //检查用户预约时间是否已预约满
        if(orderSettingDao.selectCountByOrderDate(orderDate) > 0){
            OrderSetting orderSetting = orderSettingDao.getOrderSettingOfOrderDate(orderDate);
            if(orderSetting.getReservations() < orderSetting.getNumber() ){
                if(memberDao.countMemberByPhoneNumber(phoneNumber) == 1){
                     member = memberDao.findMemberByPhoneNumber(phoneNumber);
                }else {
                    member.setIdCard(map.get("idCard"));
                    member.setName(map.get("name"));
                    member.setSex(map.get("sex"));
                    member.setPhoneNumber(phoneNumber);
                    member.setRegTime(new Date());
                    memberDao.insertMember(member);
                }
                order.setMemberId(member.getId());
                order.setOrderDate(DateFormatUtils.parse2Date(orderDate));
                order.setOrderStatus(Order.ORDERSTATUS_NO);
                order.setOrderType(map.get("orderType"));
                order.setSetmealId(Integer.parseInt(map.get("setmealId")));
                if (orderDao.countOrder(order) == 0){
                    orderSetting.setReservations(orderSetting.getReservations() + 1);
                    orderSettingDao.updateReservationsOfOrderDate(orderSetting);
                    orderDao.insertOrder(order);
                }else {
                 return new Result(false,MessageConstant.HAS_ORDERED);
                }
                return new Result(true,MessageConstant.ORDER_SUCCESS,order);
            }else {
                return new Result(false,MessageConstant.ORDER_FULL);
            }
        }else {
            return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
    }

    @Override
    public Result findById(Integer id) {
        try {
            Map<String,String> map = orderDao.findMemberById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false ,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
