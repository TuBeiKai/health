package cn.itcast.controller;


import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrdersettingService;
import cn.itcast.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;

    /**
     * 解析上传的excel文件，保存ordersettiing数据
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            List<String[]> infos = POIUtils.readExcel(excelFile);
            HashMap<String, String> map = new HashMap<>();
            for (String[] info : infos) {
                map.put("orderDate", info[0]);
                map.put("number", info[1]);
                ordersettingService.addOrdersetting(map);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    /**
     * 查询请求月份的所有ordersettings
     *
     * @param date
     * @return
     */
    @RequestMapping("/findOrdersettings")
    public Result findOrdersettingsOfOneMonth(String date) {
        String beginDate = date + "-01";
        String endDate = date + "-31";
        HashMap<String, String> rangeDate = new HashMap<>();
        rangeDate.put("beginDate", beginDate);
        rangeDate.put("endDate", endDate);
        Result result = ordersettingService.findOrdersettingsOfOneMonth(rangeDate);
        return result;
    }

    /**
     * 编辑ordersetting
     * @param temp
     * @return
     */
    @RequestMapping("/editOrdersettingNumber")
    public Result editOrdersettingNumber(@RequestBody OrderSetting temp) {
        Result result = ordersettingService.editOrdersettingNumber(temp);
        return result;
    }


}
