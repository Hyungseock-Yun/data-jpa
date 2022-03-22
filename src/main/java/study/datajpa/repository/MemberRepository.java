package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

  List<Member> findTop3HelloBy();

  List<Member> findByUsername(@Param("username") String username);

  @Query("select m from Member m where m.username = :username and m.age = :age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);

  @Query("select m.username from Member m")
  List<String> findUsernameList();

  // Dto로 반환
  @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();

  @Query("select m from Member m where m.username in :names")
  List<Member> findByNames(@Param("names") Collection<String> names);

  List<Member> findListByUsername(String username);   // 컬렉션

  Member findMemberByUsername(String username);   // 단건

  Optional<Member> findOptionalByUsername(String username);   // 단건 Optional

//  @Query(value = "select m from Member m left join m.team t",
//          countQuery = "select count(m) from Member m")
  Page<Member> findByAge(int age, Pageable pageable);     // '더보기' 사용 시 Slice 사용

  // 벌크연산 뒤에는 DB에 바로 반영하기 때문에 영속성 컨텍스트에는 연산 이전 데이터가 남아있다.
  // 따라서 clearAutomatically로 영속성 컨텍스트를 초기화 시켜줘야한다.
  @Modifying(clearAutomatically = true)
  @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);

  @Query("select m from Member m left join fetch m.team")
  List<Member> findMemberFetchJoin();

  @Override
  @EntityGraph(attributePaths = {"team"})   // 1 + N을 해결하기 위한 fetch join
  List<Member> findAll();

  @Query("select m from Member m")
  @EntityGraph(attributePaths = {"team"})   // 1 + N을 해결하기 위한 fetch join
  List<Member> findMemberEntityGraph();

//  @EntityGraph(attributePaths = {"team"})   // 1 + N을 해결하기 위한 fetch join
  @EntityGraph("Member.all")    // Member.java에 EntityGraph 사용
  List<Member> findEntityGraphByUsername(@Param("username") String username);

  @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))    // Hint를 사용하여 update하지 않고 조회만.
  Member findReadOnlyByUsername(String username);

  @Lock(LockModeType.PESSIMISTIC_WRITE)   // select for update
  List<Member> findLockByUsername(String username);
}
