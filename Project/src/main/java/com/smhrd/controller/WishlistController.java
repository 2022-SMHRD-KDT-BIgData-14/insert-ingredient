package com.smhrd.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smhrd.domain.Food;
import com.smhrd.domain.Member;
import com.smhrd.domain.Wishlist;
import com.smhrd.mapper.WishlistMapper;

@Controller
public class WishlistController {
	@Autowired
	private WishlistMapper mapper;
	
	/*
	 * @GetMapping("/saveWish.do") public String saveWish(Wishlist list) { int cnt =
	 * mapper.saveWish(list);
	 * 
	 * if(cnt == 1) { // 찜하기 성공 return } }
	 */
	
	// 찜하기
	@RequestMapping("/saveWish.do")
	public String savelist(HttpSession session) {
		// 회원이라면, wishlist 테이블에 저장하고 마이페이지로 이동
		
		// 회원 정보 세션 가져오기
		Member resultM = (Member) session.getAttribute("member"); //★
		// 음식 정보 세션 가져오기
		Food resultF = (Food) session.getAttribute("Food"); //★
		
		System.out.println("---찜---");
		System.out.println("resultM" + resultM);
		System.out.println("resultF" + resultF);
		
		
		// 회원일 때, 음식정보도 널이 아닐 때,
		if(resultM != null && resultF != null) {
			// 찜 성공 시, 마이페이지로 이동
			System.out.println("음식, 회원 -> null이 아님");
			int cnt = mapper.savelist(resultM.getUser_id(), resultF.getFood_seq());
			
			 if (cnt == 1) { 
				 System.out.println("wishlist 담기 성공");
				 session.setAttribute("Member", resultM); 
				 session.setAttribute("Food", resultF); 
				 return "view/detail";
			 } 
			 else {
				 // 찜 취소 시, 페이지는 그대로, 버튼 변경
				 System.out.println("찜 취소 됨!");
				 
				 int cntNo = mapper.noWish(resultM.getUser_id(), resultF.getFood_seq());
				 if( cntNo == 1) {
					 System.out.println("wishlist 삭제 성공"); 
					 return "view/detail"; 
					 
				 }else {
					 System.out.println("wishlist 삭제 실패"); 
					 return "view/detail";
				 }
			 }
		}	
		// 비회원이라면, 로그인 페이지로 이동	
		else {
			System.out.println("비회원..");
			return "view/login";
		}
	}
	
	// 찜 목록 띄우기
	@RequestMapping("/showWishAjax.do")
	public @ResponseBody List<Wishlist> showWishAjax(Model model) {
		List<Wishlist> list = mapper.showWishAjax();
		model.addAttribute("list", list);
		System.out.println("찜 ajax 성공");
		if (list == null) {
			System.out.println("찜 list 텅 비어있음");
		}
		return list;
	}
	
	// 찜 취소
	/*
	 * @RequestMapping("/noWish.do") public void noWish(HttpSession session) {
	 * 
	 * // 회원, 음식, 찜 정보 저장 Member result_mem = (Member)
	 * session.getAttribute("member"); Food result_food = (Food)
	 * session.getAttribute("Food"); Wishlist result_wish = (Wishlist)
	 * session.getAttribute("wishlist");
	 * 
	 * // 찜 취소 시, 페이지는 그대로, 버튼 변경 int cnt = mapper.noWish(result_mem.getUser_id(),
	 * result_food.getFood_seq(), result_wish.getWish_or_not());
	 * 
	 * if (cnt == 1) { System.out.println("wishlist 삭제 성공"); } else {
	 * System.out.println("wishlist 삭제 실패"); }
	 * 
	 * 
	 * }
	 */
	

			 
				
			
			
			
			
	
}
