package cn.itcast.service;

import cn.itcast.entity.Result;

import java.util.Map;

public interface OrderService {
    Result saveOrder(Map<String,String> map);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    Result findById(Integer id);
}
