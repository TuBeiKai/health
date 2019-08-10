package cn.itcast.dao;

import cn.itcast.pojo.CheckGroup;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;

public interface CheckGroupDao {

    Page<CheckGroup> selectCheckGroupByCondition(String queryString);

    void addCheckGroupCheckItem(Map<String, Integer> map);

    Integer addCheckGroup(CheckGroup checkGroup);

    CheckGroup selectCheckGroupById(Integer id);

    List<Integer> findCheckGroupCheckItemById(Integer id);

    void editCheckGroup(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    void deleteCheckGroup(Integer id);

    CheckGroup findCheckgroupDetail(Integer id);
}
