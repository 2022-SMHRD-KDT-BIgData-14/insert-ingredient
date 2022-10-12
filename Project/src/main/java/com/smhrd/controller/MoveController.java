package com.smhrd.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smhrd.domain.Food;
import com.smhrd.domain.Member;
import com.smhrd.domain.Wishlist;
import com.smhrd.mapper.WishlistMapper;

@Controller
public class MoveController {
	@Autowired
	private WishlistMapper mapper;
	
	// 메인페이지 이동 (welcome)
	@RequestMapping("/goMain.do")
	public String goMain() {
		return "index";
	}
	
	// 마이페이지 이동
	@RequestMapping("/goMypage.do")
	public String goMyPage(HttpSession session, Model model, Food food) {
		// 회원 정보 세션 가져오기
		Member resultM = (Member) session.getAttribute("member"); //★
		// 음식 정보 세션 가져오기
		// Food resultF = (Food) session.getAttribute("Food"); //★
		model.addAttribute("Food", food);
		List<Wishlist> list = mapper.showWishAjax();
		model.addAttribute("list", list);
		
		if(resultM != null) {
			//session.setAttribute("member", result);
			return "view/myPage"; 
		}
		else {
			return "view/detail";
		}
		
		//if(member.getUser_id() != null) {
		//	session.setAttribute("Food", food);
		//	session.setAttribute("member", member);
		//	return "view/myPage";
		//}
		//else {
		//	return "redirect:/view/detail";
		//}
		
			
		
	}
	
	// 로그인페이지 이동
	@RequestMapping("/goLogin.do")
	public String goLogin() {
		return "view/login";
	}
	
	// (최종) 업로드로 이동
	@RequestMapping("/goUpload.do")
	public String goUpload_a( HttpSession session) {
		//Member result = mapper.login(user_id);
		
		//System.out.println("유저result" + result);
		//System.out.println("유저아이디" + user_id);
		
		// 회원 정보 세션 가져오기
		Member result = (Member) session.getAttribute("member"); //★
		
		if(result != null) {
			//session.setAttribute("member", result);
			return "view/upload"; 
		}
		else {
			return "view/login";
		}
			
		// 회원
		//if(user_id != null) {
		//	mapper.saveWish(user_id);
		//	return "view/upload_a?" + user_id;
		//}
		// 비회원
		//else {
		//	
		//}
	}
	
	// 임시 메인 이동 (welcome 페이지)
	//@RequestMapping("/goMain_a.do")
	//public String goMain_a() {
	//	return "view/main_a";
	//}
	
}
