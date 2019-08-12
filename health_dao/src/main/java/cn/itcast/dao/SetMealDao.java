package cn.itcast.dao;

import cn.itcast.pojo.CheckGroup;
import cn.itcast.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.HashMap;
import java.util.List;

public interface SetMealDao {
    Page<Setmeal> selectSetMealByCondition(String queryString);

    List<CheckGroup> selectAllCheckGroup();

    void insertSetmealCheckgroupList(HashMap<String, Integer> map);

    void insertSetmeal(Setmeal setmeal);

    List<Setmeal> findAllSetmeals();

    /**
     * 根据id查询套餐详情
     * @param id
     * @return
     */
    Setmeal findSetmealDetailById(Integer id);
}
