package cn.itcast.dao;

import cn.itcast.pojo.OrderSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrdersettingDao {

    /**
     * 统计当前日期的数量
     * @param orderDate
     * @return
     */
    Integer selectCountByOrderDate(String orderDate);

    /**
     * 根据日期，更新ordersetting表
     * @param map
     */
    void updateOrdersettingByOrderDate(HashMap<String, String> map);

    /**
     * 添加数据
     * @param map
     */
    void addOrdersetting(HashMap<String, String> map);

    /**
     * 查询请求月份的所有ordersettings
     * @param rangeDate
     * @return
     */
    List<OrderSetting> findOrdersettingsOfOneMonth(Map<String,String> rangeDate);

    /**
     *  根据日期，更新ordersetting表
     * @param orderSetting
     */
    void updateOrdersetting(OrderSetting orderSetting);


    /**
     * 根据OrderSetting添加数据
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据日期，查询该天预约信息
     * @param orderDate
     * @return
     */
    OrderSetting getOrderSettingOfOrderDate(String orderDate);

    /**
     * 更新预约数
     * @param orderSetting
     */
    void updateReservationsOfOrderDate(OrderSetting orderSetting);
}
