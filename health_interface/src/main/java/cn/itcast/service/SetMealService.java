package cn.itcast.service;

import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Setmeal;

public interface SetMealService {

    Result findPage(Integer currentPage, Integer pageSize, String queryString);

    Result findAllCheckGroup();

    Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    Result getSetmeals();

    /**
     * 根据id查询套餐详情
     * @param id
     * @return
     */
    Result findSetmealDetailById(Integer id);
}
