package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.dao.OrdersettingDao;
import cn.itcast.entity.Result;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrdersettingService;
import cn.itcast.utils.DateFormatUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    /**
     * 存储文档中ordersetting数据，
     *
     * @param map
     */
    @Override
    public void addOrdersetting(HashMap<String, String> map) {
        String orderDate = map.get("orderDate");
        Integer count = ordersettingDao.selectCountByOrderDate(orderDate);
        if (count > 0) {
            ordersettingDao.updateOrdersettingByOrderDate(map);
        } else {
            map.put("reservations", "0");
            ordersettingDao.addOrdersetting(map);
        }


    }

    /**
     * 查找当前月份所有ordersettings
     *
     * @param rangeDate
     * @return
     */
    @Override
    public Result findOrdersettingsOfOneMonth(Map<String,String> rangeDate) {
        try {
            List<Map<Object, Object>> maps = new ArrayList<>();
            List<OrderSetting> list = ordersettingDao.findOrdersettingsOfOneMonth(rangeDate);
            if (list != null) {
                for (OrderSetting orderSetting : list) {
                    HashMap<Object, Object> map = new HashMap<>();
                    map.put("date",orderSetting.getOrderDate().getDate());
                    map.put("number",orderSetting.getNumber());
                    map.put("reservations",orderSetting.getReservations());
                    maps.add(map);
                }
            }
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,maps);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL,null);
        }
    }

    @Override
    public Result editOrdersettingNumber(OrderSetting orderSetting) {
        try {
            String dateFormat = DateFormatUtils.dateFormat(orderSetting.getOrderDate());
            Integer count = ordersettingDao.selectCountByOrderDate(dateFormat);
            if (count > 0) {
                ordersettingDao.updateOrdersetting(orderSetting);
            } else {
                orderSetting.setReservations(0);
                ordersettingDao.add(orderSetting);
            }
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
}
