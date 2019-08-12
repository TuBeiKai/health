package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.dao.MemberDao;
import cn.itcast.dao.OrderDao;
import cn.itcast.entity.Result;
import cn.itcast.dao.ReportDao;
import cn.itcast.service.ReportService;
import cn.itcast.utils.DateFormatUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 查询用户注册数量
     *
     * @return
     */
    @Override
    public Result getMemberReport() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -12);
            List<String> listMonth = new ArrayList<>();
            List<Integer> listCount = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(calendar.MONTH, 1);
                listMonth.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
                String timeTemp = listMonth.get(i) + "-32";
                Integer countByMonth = reportDao.findMemberCountByMonth(timeTemp);
                listCount.add(countByMonth);
            }
            System.out.println(listCount);
            System.out.println(listMonth);
            Map<Object, Object> map = new HashMap<>();
            map.put("months", listMonth);
            map.put("memberCount", listCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

    }

    /**
     * 查询预约的体检套餐名称和数量（map集合）
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getSetmealReport() {
        List<Map<String, Object>> res = reportDao.getSetmealReport();
        return res;
    }

    /**
     * 获取运营相关数据，封装结果（map集合）并返回
     *
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String, Object> map = new HashMap<>();
        //报表查询日期
        String reportDate = DateFormatUtils.dateFormat(new Date());
        map.put("reportDate", reportDate);
        //查询今日新增会员
        Integer todayNewMember = memberDao.countTodayMembers(reportDate);
        map.put("todayNewMember",todayNewMember);
        //查询会员总数
        Integer totalMember = memberDao.countTotalMember();
        map.put("totalMember",totalMember);
        //查询本周新增会员
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE,2-date.get(Calendar.DAY_OF_WEEK));
        String mondayOfThisWeek = DateFormatUtils.dateFormat(date.getTime());
        Integer thisWeekNewMember = memberDao.countNewMemberAfterDate(mondayOfThisWeek);
        map.put("thisWeekNewMember",thisWeekNewMember);
        //本月新增会员总数
        Calendar instance = Calendar.getInstance();
        instance.set(instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),1);
        String firstDayOfMomth = DateFormatUtils.dateFormat(instance.getTime());
        Integer thisMonthNewMember = memberDao.countNewMemberAfterDate(firstDayOfMomth);
        map.put("thisMonthNewMember",thisMonthNewMember);
        //今日订单数
        Integer todayOrderNumber = orderDao.countDayOrderNumber(reportDate);
        map.put("todayOrderNumber",todayOrderNumber);
        //今日到诊人数
        Integer todayVisitsNumber = orderDao.countDayVisitNumber(reportDate);
        map.put("todayVisitsNumber",todayVisitsNumber);
        //本周订单数
        Integer thisWeekOrderNumber = orderDao.countOrderNumberAfterDay(mondayOfThisWeek);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        //本周到诊人数
        Integer thisWeekVisitsNumber = orderDao.countVisitNumberAfterDay(mondayOfThisWeek);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        //本月订单数
        Integer thisMonthOrderNumber = orderDao.countOrderNumberAfterDay(firstDayOfMomth);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        //本月到诊人数
        Integer thisMonthVisitsNumber = orderDao.countVisitNumberAfterDay(firstDayOfMomth);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        //热门套餐
        Integer number = 4 ;
        List<Map<String,Object>> hotSetmeal = reportDao.findfirstNSetmeals(number);
        map.put("hotSetmeal",hotSetmeal);
        return map;
    }

    @Override
    public Map<String, Object> FindMenberByMonth(String start,String end) {
        try {
            Date d1 = new SimpleDateFormat("yyyy-MM").parse(start);//定义起始日期
            Date d2 = new SimpleDateFormat("yyyy-MM").parse(end);//定义结束日期  可以去当前月也可以手动写日期。

            Calendar dd = Calendar.getInstance();//定义日期实例
            Calendar dd1 = Calendar.getInstance();

            dd1.setTime(d2);
            dd1.add(Calendar.MONTH,1);

            Date time = dd1.getTime();//结束日期
            dd.setTime(d1);//设置日期起始时间

            Map<String,Object> resultMap = new HashMap();
            List<Map<String,Object>> listCounts = new ArrayList<>();
            List<String> listMonths = new ArrayList<>();
            Integer count=null;

            while (dd.getTime().before(time)) {//判断是否到结束日期

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

                String str = sdf.format(dd.getTime());
                String ed=str+"-30";

                count = reportDao.findMenberByMonth(ed);
                dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
                Map<String,Object> map = new HashMap();

                map.put("value",count);
                map.put("name",str);

                listCounts.add(map);
                listMonths.add(str);
            }
            resultMap.put("memberCount",listCounts);
            resultMap.put("months",listMonths);
            return resultMap;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }
}
