package cn.itcast.controller;

import cn.itcast.entity.Result;
import cn.itcast.service.SetMealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetMealService setMealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){

        Result result = setMealService.getSetmeals();
        return result;
    }

    /**
     * 根据id查询套餐详情
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Result result = setMealService.findSetmealDetailById(id);
        return result;
    }
}
