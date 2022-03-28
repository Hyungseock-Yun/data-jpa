package study.datajpa.repository;

public interface NestedClosedProjections {

  String getUsername();

  // 2번째 부터는 Entity 통째로 들고옴. (조회 Entity가 2개면 최적화가 안됨)
  TeamInfo getTeam();

  interface TeamInfo {
    String getName();
  }

}
