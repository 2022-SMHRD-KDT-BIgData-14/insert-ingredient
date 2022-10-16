package com.smhrd.controller;

import java.util.HashMap;
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
public class WishlistController {
	@Autowired
	private WishlistMapper mapper;
	
	// wishlist 추가
	@RequestMapping("/addWish.do")
	public String addWish(HttpSession session) {
		// 회원 정보 세션 가져오기
		Member resultM = (Member) session.getAttribute("member");
		System.out.println("member출력 확인" + resultM.getUser_id());
		
		// 음식 정보 세션 가져오기
		Food resultF = (Food) session.getAttribute("Food");
		System.out.println("food출력 확인" + resultF.getFood_seq());
		
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("resultF", resultF.getFood_seq());
		map.put("resultM", resultM.getUser_id());
		System.out.println("hashmap출력확인" + map.values());
		
		
		int addWishresult = mapper.addWish(map);
		System.out.println("addWishresult확인" + addWishresult);
		
		if(addWishresult == 1) {
			session.setAttribute("addWishresult", 1);
			System.out.println("wishlist 추가 성공");
		}else {
			session.setAttribute("addWishresult", 0);
			System.out.println("wishlist 추가 실패");
		}
		
		return "redirect:/witsh_detail.do";
	}
	
	@RequestMapping("/delWish.do")
	public String delWish(HttpSession session) {
		// 회원 정보 세션 가져오기
		Member resultM = (Member) session.getAttribute("member");
		// 음식 정보 세션 가져오기
		Food resultF = (Food) session.getAttribute("Food");
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("resultF", resultF.getFood_seq());
		map.put("resultM", resultM.getUser_id());
		System.out.println("hashmap출력확인" + map.values());
		
		int delWishresult = mapper.delWish(map);
		System.out.println("addWishresult확인" + delWishresult);
		
		if(delWishresult == 1) {
			session.setAttribute("delWishresult", 1);
			System.out.println("wishlist 삭제 성공");
		}else {
			session.setAttribute("delWishresult", 0);
			System.out.println("wishlist 삭제 실패");
		}
		
		return "redirect:/witsh_detail.do";
		
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
}
