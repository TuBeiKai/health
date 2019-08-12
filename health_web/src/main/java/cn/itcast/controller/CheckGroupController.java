package cn.itcast.controller;


import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 检查组-分页查询检查组
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findAll(@RequestBody QueryPageBean queryPageBean) {
        Result result = checkGroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return result;
    }

    /**
     * 检查组-新增检查组
     *
     * @return
     */
    @RequestMapping("/add")
    public Result addCheckGroup(Integer[] checkitemIds, @RequestBody CheckGroup checkGroup) {
        Result result = checkGroupService.addCheckGroup(checkitemIds, checkGroup);
        return result;
    }

    /**
     * 检查组-根据id查询检查组
     * @param id
     * @return
     */
    @RequestMapping("/findCheckGroupById")
    public Result findCheckGroupById(Integer id) {
        Result result = checkGroupService.findCheckGroupById(id);
        return result;
    }

    /**
     * 检查组-根据id查询检查组对应的检查项id列表
     * @param id
     * @return
     */
    @RequestMapping("/findCheckItemIdsById")
    public Result findCheckItemIdsById(Integer id){
        Result result = checkGroupService.findCheckGroupCheckItemById(id);
        return result;
    }

    /**
     * 检查组-修改检查组
     *
     * @return
     */
    @RequestMapping("/edit")
    public Result editCheckGroup(Integer[] checkitemIds, @RequestBody CheckGroup checkGroup) {
        Result result = checkGroupService.editCheckGroup(checkitemIds, checkGroup);
        return result;
    }

    /**
     * 根据id删除检查组
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteCheckGroup(Integer id) {
        Result result = checkGroupService.deleteCheckGroup(id);
        return result;
    }
}
