package com.vibid.member.service;

import com.vibid.auth.domain.Authority;
import com.vibid.auth.domain.Role;
import com.vibid.exception.DuplicationException;
import com.vibid.exception.NotFoundException;
import com.vibid.member.controller.dto.RequestJoinDto;
import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;
import com.vibid.member.domain.Password;
import com.vibid.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Optional.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Optional<Member> findById(Long memberId) {
    return memberRepository.findById(memberId);
  }

  @Override
  @Transactional
  public Optional<Member> join(RequestJoinDto requestJoinDto) {
    final Email email = Email.from(requestJoinDto.getEmail());
    final Password password = Password.of(requestJoinDto.getPassword(), requestJoinDto.getPasswordConfirm());
    final String nickname = requestJoinDto.getNickname();
    if(StringUtils.isBlank(nickname))
      throw new IllegalArgumentException("닉네임은 공백일 수 없습니다.");

    if(nickname.length() > 20 )
      throw new IllegalArgumentException("닉네임은 20자를 초과할 수 없습니다.");

    if(memberRepository.findByEmailAndVendor(email,"vibid").isPresent())
      throw new DuplicationException(Member.class, email);


    Member member = Member.builder()
      .principal(email)
      .credential(passwordEncoder.encode(password.getCredential()))
      .nickname(nickname)
      .build();

    Authority authority = Authority.from(Role.USER);
    authority.setMember(member);

    memberRepository.save(member);

    return ofNullable(member);
  }

  @Override
  public boolean checkExistEmail(Email email) {
    return memberRepository.findByEmail(email).isPresent();
  }

  @Override
  @Transactional
  public Member login(Email email, Password password) {
    Member member = memberRepository.findByEmailAndVendor(email,"vibid")
      .orElseThrow(() -> new NotFoundException(Email.class, email));
    member.login(passwordEncoder, password.getCredential());
    member.checkLock();
    member.checkEmailAuthentication();
    member.loginSuccess();
    return member;

  }

  @Override
  @Transactional
  public Optional<Member> updateNickname(Long memberId, String nickname) {
    return memberRepository.findById(memberId)
      .map(member -> {
        member.changeNickname(nickname);
        return member;
      });
  }

  @Override
  @Transactional
  public Member updatePassword(Member member, Password password) {
    member.changePassword(passwordEncoder.encode(password.getCredential()));
    return member;
  }

  @Override
  public Optional<Member> findByEmail(Email address) {
    return memberRepository.findByEmail(address);
  }

  @Override
  @Transactional
  public Optional<Member> updateProfileUrl(Long memberId, String fileName) {
    return memberRepository.findById(memberId)
      .map(member -> {
        member.changeProfileImageUrl(fileName);
        return member;
      });
  }

  @Override
  @Transactional
  public void withdrawal(Long id) {
    Member member = memberRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Member.class, id));
    member.withdrawal();
  }
}
