package com.vibid.member.service;

import com.vibid.member.controller.dto.RequestJoinDto;
import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;
import com.vibid.member.domain.Password;

import java.util.Optional;

public interface MemberService {
  Optional<Member> findById(Long id);

  Optional<Member> join(RequestJoinDto joinDto);

  boolean checkExistEmail(Email email);

  Member login(Email email, Password password);

  Optional<Member> updateProfileUrl(Long memberId, String fileName);

  void withdrawal(Long id);

  Optional<Member> updateNickname(Long memberId, String nickname);

  Member updatePassword(Member member, Password password);

  Optional<Member> findByEmail(Email address);
}
