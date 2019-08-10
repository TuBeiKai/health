package cn.itcast.service;

import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckItem;

public interface CheckItemService {

    /**
     * 检查项-查询检查项页面
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    Result findPage(Integer currentPage, Integer pageSize, String queryString);


    /**
     * 检查项-新增检查项
     * @param checkItem
     * @return
     */
    Result addItem(CheckItem checkItem);

    /**
     * 检查项-根据id查询检查项
     * @param id
     * @return
     */
    Result findCheckItemById(Integer id);

    /**
     * 检查项-编辑检查项
     * @param checkItem
     * @return
     */
    Result editCheckItem(CheckItem checkItem);

    /**
     * 检查项-根据id删除检查项
     * @param id
     * @return
     */
    Result deleteCheckItem(Integer id);

    /**
     * 查询所有检查项
     * @return
     */
    Result findAll();
}
