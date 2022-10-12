package com.smhrd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.smhrd.domain.Member;

@Mapper
public interface MemberMapper {
	
	// 회원가입 
	public int join(Member member);
	
	// 로그인
	public Member login(Member member);
	
	// 회원정보수정
	public int update(Member member);
	
	// 회원탈퇴
	public int delete(Member member);
}
