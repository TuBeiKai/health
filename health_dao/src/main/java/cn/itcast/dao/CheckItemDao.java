package cn.itcast.dao;

import cn.itcast.pojo.CheckItem;
import com.github.pagehelper.Page;
import java.util.List;

public interface CheckItemDao {

    /**
     * 检查项-根据条件查询检查项
     * @param queryString
     * @return
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 检查项-添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 检查项-根据id查询检查项
     * @param id
     * @return
     */
    CheckItem findCheckItemById(Integer id);

    /**
     * 检查项-更新检查项
     * @param checkItem
     * @return
     */
    void updateCheckItem(CheckItem checkItem);

    /**
     * 检查项-根据检查项id查询检查组
     * @param id
     * @return
     */
    Integer countCheckItemGroupIdById(Integer id);

    /**
     * 检查项-根据id删除检查项
     * @param id
     */
    void deleteCheckItem(Integer id);

    List<CheckItem> selectAllCheckItems();

    CheckItem findCheckitemDetail(Integer id);
}
