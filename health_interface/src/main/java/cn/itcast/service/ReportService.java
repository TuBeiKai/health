package cn.itcast.service;

import cn.itcast.entity.Result;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 查询用户注册数量
     * @return
     */
    Result getMemberReport();

    /**
     * 查询预约的体检套餐
     * @return
     */
    List<Map<String,Object>> getSetmealReport();

    /**
     * 获取运营数据
     * @return
     */
    Map<String,Object> getBusinessReportData();
}
