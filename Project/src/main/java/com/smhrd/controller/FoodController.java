package com.smhrd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String foodDetail(Food food, HttpSession session) {

		// List<Food> list = mapper.foodList(food);
		// System.out.println("이미지클릭 foodList : "+ list);
		// model.addAttribute("list", list);

		Food resultF = mapper.foodDetail(food);
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
	@GetMapping("/foodList.do")
	public @ResponseBody List<Food> foodList(HttpSession session, Food food) {
		// 세션 저장 -> 어떤 세션을 저장해야 할까? -> 푸드 세션?? , 회원 세션??
		Member memberSess = (Member) session.getAttribute("member");
		Food foodSess = (Food) session.getAttribute("Food");

		List<Food> list = mapper.foodList(food);
		System.out.print("foodList : " + list);

		return list;
	}

	// 최근 본 음식 목록 출력
	// @RequestMapping("/recentList.do")
	// public String recentList(HttpSession session) {
	// 세션 저장 -> 어떤 세션을 저장해야 할까? -> 푸드 세션?? , 회원 세션??
	// Member member = (Member) session.getAttribute("member");
	// Food food = (Food) session.getAttribute("Food");
	// }

	// 식재료 텍스트 받아와서 해당 식재료 포함하는 음식 조회
	/*
	 * @RequestMapping("/upload.do") public String showFood(Upload upload, Model
	 * model) { List<Upload> result = mapper.showFood(upload);
	 * 
	 * model.addAttribute("result", result);
	 * 
	 * if (result != null) { System.out.println("result:" + result);
	 * 
	 * return "view/upload";
	 * 
	 * } else { // 에러메시지 띄우기 return "redirect:/"; } }
	 */

	@RestController
	public class ListController {
		@Autowired
		private FoodMapper mapper;

		@RequestMapping("/ingredientAuto.do")
		public List<Ingredient> IngredientAuto(String ingredient) {
			System.out.println("ingredient출력" + ingredient);

			List<Ingredient> ingredient_name = mapper.IngredientAuto(ingredient);

			return ingredient_name;
		}

		@RequestMapping("/realtionList.do")
		public List<Food> RelationList(String list, Model model) {

			System.out.println("list출력" + list);
			System.out.println("list타입출력" + list.getClass().getName());

			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(list).getAsJsonObject().get("taglist");

			String[] chList = json.toString().replace("[", "").replace("]", "").replace("'", "").replace("\"", "")
					.split(",");

			ArrayList<Integer> intList = new ArrayList<Integer>();

			for (String str : chList) {
				intList.add(Integer.parseInt(str));
				System.out.println("출력확인 : " + (Integer.parseInt(str)));
			}
			ArrayList<Relationship> relationlist;

			System.out.println(intList.size());

			relationlist = (ArrayList<Relationship>) mapper.RelationList(new FoodVO(intList.size(), intList));

			System.out.println(relationlist.size());

			intList.clear();

			for (int i = 0; i < relationlist.size(); i++) {
				System.out.println("개별 food_seq 출력 확인" + relationlist.get(i).getFood_seq());
				intList.add(relationlist.get(i).getFood_seq());
			}
			List<Food> foodlist = null;
			if (intList.size() != 0)
				foodlist = mapper.FoodList(intList);

			return foodlist;

		}

	}
}
