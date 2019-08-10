package cn.itcast.dao;

import cn.itcast.pojo.Member;

public interface MemberDao {
    /**
     * 根据电话号码查找会员是否存在
     * @param telephone
     * @return
     */
    Integer countMemberByPhoneNumber(String telephone);

    /**
     * 根据电话号码查询会员信息
     * @param phoneNumber
     * @return
     */
    Member findMemberByPhoneNumber(String phoneNumber);

    /**
     * 添加会员信息
     * @param member
     */
    void insertMember(Member member);

    /**
     * 根据日期查询注册会员数量
     * @param reportDate
     * @return
     */
    Integer countTodayMembers(String reportDate);

    /**
     * 查询总会员数
     * @return
     */
    Integer countTotalMember();

    /**
     * 查询指定日期（包含）后注册的用户总数
     * @param mondayOfThisWeek
     * @return
     */
    Integer countNewMemberAfterDate(String mondayOfThisWeek);

}
