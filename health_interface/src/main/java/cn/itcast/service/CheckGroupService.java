package cn.itcast.service;

import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    Result findPage(Integer currentPage, Integer pageSize, String queryString);

    Result addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup);

    Result findCheckGroupById(Integer id);

    Result findCheckGroupCheckItemById(Integer id);

    Result editCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup);

    Result deleteCheckGroup(Integer id);
}
