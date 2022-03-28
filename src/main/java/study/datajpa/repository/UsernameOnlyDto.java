package study.datajpa.repository;

import lombok.Getter;

@Getter
public class UsernameOnlyDto {

  private final String username;

  public UsernameOnlyDto(String username) {   // 파라미터명으로 분석. 따라서 파라미터명을 바꾸면 안됨.
    this.username = username;
  }
}
