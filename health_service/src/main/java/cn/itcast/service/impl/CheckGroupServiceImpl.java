package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.dao.CheckGroupDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 检查组-查询所有检查组信息
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public Result findPage(Integer currentPage, Integer pageSize, String queryString) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            Page<CheckGroup> items = checkGroupDao.selectCheckGroupByCondition(queryString);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,new PageResult(items.getTotal(),items.getResult()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL,null);
        }
    }

    @Override
    public Result addCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup) {
        try {
            checkGroupDao.addCheckGroup(checkGroup);
            addCheckGroupCheckItem(checkitemIds,checkGroup.getId());
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL,null);
        }
    }

    /**
     * 检查组-根据id查询检查组
     * @param id
     * @return
     */
    @Override
    public Result findCheckGroupById(Integer id) {
        try {
            CheckGroup checkGroup = checkGroupDao.selectCheckGroupById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL,null);
        }
    }

    /**
     * 检查组-根据id查询检查组对应的检查项id列表
     * @param id
     * @return
     */
    public Result findCheckGroupCheckItemById(Integer id){
        try {
            List<Integer> checkGroupCheckItemById = checkGroupDao.findCheckGroupCheckItemById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupCheckItemById);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL,null);
        }
    }

    /**
     * 检查组-编辑检查组
     * @param checkitemIds
     * @param checkGroup
     * @return
     */
    @Override
    public Result editCheckGroup(Integer[] checkitemIds, CheckGroup checkGroup) {
        try {
            checkGroupDao.editCheckGroup(checkGroup);
            checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
            addCheckGroupCheckItem(checkitemIds,checkGroup.getId());
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_FAIL,null);
        }
    }

    /**
     * 检查组-根据id删除检查组
     * @param id
     * @return
     */
    @Override
    public Result deleteCheckGroup(Integer id) {
        try {
            checkGroupDao.deleteCheckGroupCheckItem(id);
            checkGroupDao.deleteCheckGroup(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
        return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL,null);
        }
    }

    /**
     * 检查组-添加检查组于检查项的关系表信息
     * @param checkitmsIds
     * @param checkGroupId
     */
    public void addCheckGroupCheckItem(Integer[] checkitmsIds,Integer checkGroupId){
        Map<String, Integer> map = new HashMap<>();
        for (Integer checkitmsId : checkitmsIds) {
            map.put("checkitem_id",checkitmsId);
            map.put("checkgroup_id",checkGroupId);
            checkGroupDao.addCheckGroupCheckItem(map);
        }

    }
}
