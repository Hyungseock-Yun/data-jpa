package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  @GetMapping("/members/{id}")
  public String findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUsername();
  }

  // 도메인 컨버터의 경우, 조회용으로만 사용. 영속성 컨텍스트의 영향을 받지 않기 때문.
  @GetMapping("/members2/{id}")
  public String findMember2(@PathVariable("id") Member member) {
    return member.getUsername();
  }

  @GetMapping("/members")
  public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable) {    // PagebleDefault로 조절 가능
    return memberRepository.findAll(pageable).map(MemberDto::new);
  }

//  @PostConstruct
  public void init() {
    for (int i = 0; i < 100; i++) {
      memberRepository.save(new Member("user" + i, i));
    }
  }

}
