package cn.itcast.controller;


import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisConstant;
import cn.itcast.entity.QueryPageBean;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetMealService;
import cn.itcast.utils.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 套餐-分页查询套餐
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findAll(@RequestBody QueryPageBean queryPageBean) {
        Result result = setMealService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return result;
    }

    /**
     * 查询所有检查组
     * @return
     */
    @RequestMapping("/findAllCheckGroups")
    public Result findAllCheckGroups(){
        Result result = setMealService.findAllCheckGroup();
        return result;
    }

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upload(MultipartFile imgFile){

        String originalFilename = imgFile.getOriginalFilename();
        try {
            String filename = UUID.randomUUID().toString();
            String newFilename = filename +"."+ FilenameUtils.getExtension(originalFilename);
            byte[] bytes = imgFile.getBytes();
            QiniuUtils.upload(bytes,newFilename);
            jedisPool.getResource().sadd("allUploadPictures",newFilename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,newFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL,null);
        }
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        try {
            setMealService.addSetmeal(setmeal,checkgroupIds);
            //保存套餐成功后 图片保存数据库记录 集合setmealPicResources
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return  new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

}
