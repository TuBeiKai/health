package cn.itcast.dao;

import cn.itcast.pojo.Order;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    /**
     * 插入预约数据
     */
   void insertOrder(Order order);

    /**
     * 根据order查询存在个数
     */
    Integer countOrder(Order order);

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    Map<String,String> findMemberById(Integer id);

    /**
     * 根据日期查询预约数
     * @param reportDate
     * @return
     */
    Integer countDayOrderNumber(String reportDate);

    /**
     * 根据日期查询到诊数
     * @param reportDate
     * @return
     */
    Integer countDayVisitNumber(String reportDate);

    /**
     * 查询指定日期后的预约数
     * @param reportDate
     * @return
     */
    Integer countOrderNumberAfterDay(String reportDate);

    /**
     * 查询指定日期后的到诊数
     * @param reportDate
     * @return
     */
    Integer countVisitNumberAfterDay(String reportDate);
}
