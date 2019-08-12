package cn.itcast.service;

import cn.itcast.entity.Result;
import cn.itcast.pojo.OrderSetting;

import java.util.HashMap;
import java.util.Map;

public interface OrdersettingService {
    /**
     * 设置预定
     * @param map
     */
    void addOrdersetting(HashMap<String, String> map);

    /**
     * 查询请求月份的所有ordersettings
     * @param rangeDate
     * @return
     */
    Result findOrdersettingsOfOneMonth(Map<String,String> rangeDate);

    /**
     * 编辑ordersetting
     * @param temp
     * @return
     */
    Result editOrdersettingNumber(OrderSetting orderSetting);
}
