package cn.itcast.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao {
    /**
     * 查找截至当前月份的注册会员数量
     * @param timeTemp
     * @return
     */
    Integer findMemberCountByMonth(String timeTemp);

    /**
     * 关联t_setmeal和t_order表，查询套餐名称及预约数量
     * @return
     */
    List<Map<String,Object>> getSetmealReport();

    /**
     * 查询预约排名前number的套餐信息
     * @param number
     * @return
     */
    List<Map<String,Object>> findfirstNSetmeals(Integer number);

    Integer findMenberByMonth(String ed);
}
