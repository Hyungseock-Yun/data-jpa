package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

// 이름을 상속받는 Repository 이름 + Impl로 맞춰야한다. (ex: MemberRepository + Impl)
// 그러면 SpringDataJPA가 알아서 매칭.
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

  private final EntityManager em;

  @Override
  public List<Member> findMemberCustom() {
    return em.createQuery("select m from Member m")
      .getResultList();
  }
}
