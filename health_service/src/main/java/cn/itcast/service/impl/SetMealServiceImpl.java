package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.dao.SetMealDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.CheckGroupService;
import cn.itcast.service.SetMealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    /**
     * 分页查询套餐
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public Result findPage(Integer currentPage, Integer pageSize, String queryString) {
        try {
            PageHelper.startPage(currentPage,pageSize);
            Page<Setmeal> meals = setMealDao.selectSetMealByCondition(queryString);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,new PageResult(meals.getTotal(),meals.getResult()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL,null);
        }
    }

    @Override
    public Result findAllCheckGroup() {
        try {
            List<CheckGroup> checkGroups =  setMealDao.selectAllCheckGroup();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL,null);
        }
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Override
    public Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.insertSetmeal(setmeal);
        addCheckgroupIdsOfSetmeal(checkgroupIds,setmeal.getId());
        return null;
    }

    @Override
    public Result getSetmeals() {
        try {
            List<Setmeal> setmeals = setMealDao.findAllSetmeals();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeals);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL,null);
        }
    }

    /**
     * 根据id查询套餐详情
     * @param id
     * @return
     */
    @Override
    public Result findSetmealDetailById(Integer id) {
        try {
            Setmeal setmeal = setMealDao.findSetmealDetailById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL,null);
        }
    }

    /**
     * 套餐id与检查组id插入中间表
     * @param checkgroupIds
     */
    public void addCheckgroupIdsOfSetmeal(Integer[] checkgroupIds,Integer id) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Integer checkgroupId : checkgroupIds) {
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkgroupId);
            setMealDao.insertSetmealCheckgroupList(map);
        }

    }
}
