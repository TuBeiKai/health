package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.dao.CheckItemDao;
import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.pojo.CheckItem;
import cn.itcast.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 检查-查询检查项页面
     *
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public Result findPage(Integer currentPage, Integer pageSize, String queryString) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            Page<CheckItem> items = checkItemDao.selectByCondition(queryString);
            Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, new PageResult(items.getTotal(), items.getResult()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL, null);
    }

    /**
     * 检查项-新增检查项
     *
     * @param checkItem
     * @return
     */
    @Override
    public Result addItem(CheckItem checkItem) {
        Result result = new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS, null);
        try {
            checkItemDao.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return result;
    }

    /**
     * 检查项-根据id查询检查项
     *
     * @param id
     * @return
     */
    @Override
    public Result findCheckItemById(Integer id) {
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, null);
        try {
            CheckItem checkItem = checkItemDao.findCheckItemById(id);
            result.setData(checkItem);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 检查项-编辑检查项
     *
     * @param checkItem
     * @return
     */
    @Override
    public Result editCheckItem(CheckItem checkItem) {
        Result result = new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS, null);
        try {
            checkItemDao.updateCheckItem(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return result;
    }

    public Result deleteCheckItem(Integer id) {
        Result result = new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL, null);
        try {
            System.out.println("========================================"+id);
            Integer count = checkItemDao.countCheckItemGroupIdById(id);
            System.out.println("----------------------------------------");
            if (count > 0) {
                throw new RuntimeException("检查项和检查组已经关联，无法删除");
            }
            checkItemDao.deleteCheckItem(id);
            result.setMessage(MessageConstant.DELETE_CHECKGROUP_SUCCESS);
            result.setFlag(true);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;

    }

    /**
     * 检查项-查询所有检查项
     * @return
     */
    @Override
    public Result findAll() {
        try {
            List<CheckItem> list = checkItemDao.selectAllCheckItems();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL,null);
        }
    }


}
