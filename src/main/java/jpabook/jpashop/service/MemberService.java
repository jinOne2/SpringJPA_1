package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

     // Bean 에 등록된 MemberRepository를 Injection 해준다.
    private final MemberRepository memberRepository;

    // public MemberService(MemberRepository memberRepository){
    //   this.memberRepository = memberRepository;
    // } @RequiredArgsConstructor에서 만들어준다.. (final 이 있는 필드로 생성자를 만들어줌)

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재 하는 회원 입니다.");
        }

    }

    // 회원 전체 조회
     //읽기의 경우에는 readOnly true로 설정해주면 성능이 좋아짐
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
