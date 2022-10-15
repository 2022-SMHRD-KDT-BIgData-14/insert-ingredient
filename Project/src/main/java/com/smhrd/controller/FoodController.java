package com.smhrd.controller;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.smhrd.domain.Food;
import com.smhrd.domain.FoodVO;
import com.smhrd.domain.Ingredient;
import com.smhrd.domain.Member;
import com.smhrd.domain.Relationship;
import com.smhrd.mapper.FoodMapper;

@Controller
public class FoodController {
	@Autowired
	private FoodMapper mapper;

	// 음식 이미지 클릭 시, 레시피 상세 페이지로 이동
	@RequestMapping("/detail.do")
	public String foodDetail(Food food_seq, HttpSession session) {

		Food resultF = mapper.foodDetail(food_seq);
		System.out.println("result : " + resultF);

		// 회원 정보 세션 가져오기
		Member resultM = (Member) session.getAttribute("member");

		// 로그인 먼저 된 후, 푸드 세션 보내기
		if (resultM != null) {
			if (resultF != null) {
				session.setAttribute("Food", resultF);
				System.out.println("food세션 보내기 성공");
				return "view/detail";
			} else {
				System.out.println("food세션 보내기 실패");
				return "redirect:/";
			}
		} else {
			return "view/login";
		}
	}

	// 음식 목록 출력
	//@GetMapping("/foodList.do")
	//public @ResponseBody List<Food> foodList(HttpSession session, Food food) {
		// 세션 저장 -> 어떤 세션을 저장해야 할까? -> 푸드 세션?? , 회원 세션??
	//	Member memberSess = (Member) session.getAttribute("member");
	//	Food foodSess = (Food) session.getAttribute("Food");
	//	List<Food> list = mapper.foodList(food);
	//	System.out.print("foodList : " + list);
	//	return list;
	//}

	// 최근 본 음식 목록 출력
	@RequestMapping("recentList.do")
	public @ResponseBody List<Food> recentList(Member mem, Food food, HttpSession session){
		List<Food> recentList = mapper.recentList(food);
		
		session.setAttribute("user_id", mem.getUser_id());
		session.setAttribute("recentList", recentList);
		return recentList;
		
	}
	// @RequestMapping("/recentList.do")
	// public String recentList(HttpSession session) {
	// 세션 저장 -> 어떤 세션을 저장해야 할까? -> 푸드 세션?? , 회원 세션??
	// Member member = (Member) session.getAttribute("member");
	// Food food = (Food) session.getAttribute("Food");
	// }
	
	// 이미지 업로드
	// @PostMapping("http://127.0.0.1:5000/fileUpload")
	
	
	// -------------------------------------------------------------------------
	
	// ajax 통신을 위한 restcontroller
	@RestController
	public class ListController {
		@Autowired
		private FoodMapper mapper;

		// main.html food autocomplete
		@RequestMapping("/foodAuto.do")
		public List<Food> FoodAuto(String food) {
			
			List<Food> food_name = mapper.FoodAuto(food);
			
			return food_name;
		}
		
		// upload.html ingredient autocomplete
		@RequestMapping("/ingredientAuto.do")
		public List<Ingredient> IngredientAuto(String ingredient) {

			List<Ingredient> ingredient_name = mapper.IngredientAuto(ingredient);

			return ingredient_name;
		}
		
		// upload.html taglist를 통한 foodlist출력
		@RequestMapping("/realtionList.do")
		public List<Food> RelationList(String list) {

			System.out.println("realtionList.do 들어옴");
			System.out.println("태그 list:" + list);
			// Json 파싱
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(list).getAsJsonObject().get("taglist");
			
			// 불필요한 string 제거
			String[] chList = json.toString()
								  .replace("[", "").replace("]", "").replace("'", "").replace("\"", "").split(",");

			ArrayList<Integer> intList = new ArrayList<Integer>();
			
			// 정제한 요소 intList에 담기
			for (String str : chList) {
				intList.add(Integer.parseInt(str));
			}
			
			ArrayList<Relationship> relationlist;
			
			// 조합 가능한 음식 목록 찾기
			relationlist = (ArrayList<Relationship>) mapper.RelationList(new FoodVO(intList.size(), intList));

			intList.clear();

			for (int i = 0; i < relationlist.size(); i++) {
				intList.add(relationlist.get(i).getFood_seq());
			}
			
			// 조합 가능한 음식 목록의 column정보를 모두 가져와 return
			List<Food> foodlist = null;
			if (intList.size() != 0) {
				foodlist = mapper.FoodList(intList);
			}

			return foodlist;

		}
		
		@RequestMapping("/imgrealtionList.do")
		public List<Food> imgrealtionList(String list) {

			System.out.println("realtionList.do 들어옴");
			System.out.println("태그 list:" + list);
			// Json 파싱
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(list).getAsJsonObject().get("taglist");
			
			// 불필요한 string 제거
			String[] chList = json.toString()
								  .replace("[", "").replace("]", "").replace("'", "").replace("\"", "").split(",");

			ArrayList<Integer> intList = new ArrayList<Integer>();
			
			// 정제한 요소 intList에 담기
			for (String str : chList) {
				intList.add(Integer.parseInt(str));
			}
			
			ArrayList<Relationship> relationlist;
			
			// 조합 가능한 음식 목록 찾기
			relationlist = (ArrayList<Relationship>) mapper.RelationList(new FoodVO(intList.size(), intList));

			intList.clear();

			for (int i = 0; i < relationlist.size(); i++) {
				intList.add(relationlist.get(i).getFood_seq());
			}
			
			// 조합 가능한 음식 목록의 column정보를 모두 가져와 return
			List<Food> foodlist = null;
			if (intList.size() != 0) {
				foodlist = mapper.FoodList(intList);
			}

			return foodlist;

		}
		

	}
}
