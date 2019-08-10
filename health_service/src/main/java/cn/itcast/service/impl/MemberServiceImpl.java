package cn.itcast.service.impl;

import cn.itcast.dao.MemberDao;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        Member member = memberDao.findMemberByPhoneNumber(telephone);
        return member;
    }

    @Override
    public void add(Member member) {
        memberDao.insertMember(member);
    }
}
