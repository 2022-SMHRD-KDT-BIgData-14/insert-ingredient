package com.smhrd.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smhrd.domain.Member;
import com.smhrd.mapper.MemberMapper;

@Controller
public class MemberController {
	@Autowired
	private MemberMapper mapper;
	
	// (welcome 페이지에서)회원가입 클릭 시, join 페이지 이동
	@RequestMapping("/goJoin.do")
	public String goJoin() {
		return "view/join";
	}
	
	// 회원가입
	@RequestMapping("/join.do")
	public String join(Member member) {
		int cnt = mapper.join(member);
		
		if (cnt == 1) {
			// 회원가입 성공 시 로그인 페이지로 이동
			return "view/login";
		}
		else {
			// 회원가입 실패 시 회원가입 페이지 그대로
			return "view/join";
		}
	}
	
	// 로그인 
	@RequestMapping("/login.do")
	public String login(Member member, HttpSession session) {
		Member result = mapper.login(member);
		
		if (result != null) {
			// 로그인 성공 시, 사용자 정보 세션 저장
			session.setAttribute("member", result);
			System.out.println("로그인 성공");
			return "redirect:/";
		}
		else {
			System.out.println("로그인 실패");
			// 로그인 실패 시, 로그인 페이지 그대로
			return "view/login";
		}
	}
	
	// 로그아웃
	@RequestMapping("/logout.do")
	public String logout(Member member, HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
			
		System.out.println("로그아웃 성공");
		return "redirect:/";
	}
	
	// 회원정보수정
	@RequestMapping("/update.do")
	public String update(Member member, HttpSession session) {
		int cnt = mapper.update(member);
		System.out.println("cnt : " + cnt);
		
		if (cnt == 1) {
			// 회원정보수정 성공 시 메인페이지로 이동
			System.out.println("회원정보수정 성공");
			return "index";
		}
		else {
			// 회원정보수정 실패 시 회원정보수정 페이지 그대로
			System.out.println(member);
			return "view/update";
		}
			
	}
	
	// 회원탈퇴
	 @RequestMapping("/delete.do") 
	 public String delete(Member member, HttpSession session) { 
		 int cnt = mapper.delete(member);
	 
		 System.out.println("cnt : " + cnt);
	  
		 if (cnt == 1) { System.out.println("회원탈퇴 성공"); return "index"; }
		 else { System.out.println("회원탈퇴 실패"); return "index"; }
	 }
	 
	
		/*
		 * @GetMapping("/delete.do") public String delete( String alertMsg ) {
		 * System.out.println("요청 들어옴 : " + alertMsg); return "delete"; }
		 */
	
}
