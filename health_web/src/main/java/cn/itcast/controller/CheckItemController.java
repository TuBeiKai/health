package cn.itcast.controller;

import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckItem;
import cn.itcast.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 检查项-分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping(value = "/findPage", method={RequestMethod.GET,RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        Result result = checkItemService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return result;
    }

    /**
     * 检查项-新增检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    public Result addItem(@RequestBody CheckItem checkItem) {
        Result result = checkItemService.addItem(checkItem);
        return result;
    }

    /**
     * 检查项-根据id查询检查项
     *
     * @param id
     * @return
     */
    @RequestMapping("/findCheckItemById")
    public Result findCheckItemById(Integer id) {
        Result result = checkItemService.findCheckItemById(id);
        return result;
    }

    /**
     * 检查项-编辑检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/editCheckItem")
    public Result editCheckItem(@RequestBody CheckItem checkItem) {
        Result result = checkItemService.editCheckItem(checkItem);
        return result;
    }

    /**
     * 根据id删除检查项
     * @param id
     * @return
     */
    @RequestMapping("/deleteCheckItem")
    @PreAuthorize("hasAnyAuthority(\"CHECKITEM_DELETE\")")
    public Result deleteCheckItem(Integer id){
        Result result = checkItemService.deleteCheckItem(id);
        return result;
    }

    /**
     * 查询所有检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        Result result = checkItemService.findAll();
        return result;
    }


}
