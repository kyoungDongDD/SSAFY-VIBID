package com.vibid.member.repository;

import com.vibid.member.domain.Email;
import com.vibid.member.domain.Member;

import java.util.Optional;
import java.util.stream.DoubleStream;

public interface MemberRepository {

  Member save(Member member);

  Optional<Member> findById(Long id);

  Optional<Member> findByEmail(Email email);

  Optional<Member> findByEmailAndVendor(Email email, String vendor);
}
