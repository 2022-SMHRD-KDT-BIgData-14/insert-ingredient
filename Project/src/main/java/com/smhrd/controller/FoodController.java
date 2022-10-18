package com.smhrd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
				
				HashMap<String, Object> map = new HashMap<>();
				map.put("resultF", resultF.getFood_seq());
				map.put("resultM", resultM.getUser_id());
				
				System.out.println("hashmap출력확인" + map.values());
				
				Integer checkWish = (int)mapper.searchWish(map);
				System.out.println("addWishresult확인" + checkWish);
				if(checkWish == 1) {
					// wishlist에 추가되어있을 경우
					session.setAttribute("checkWish", 1);
				}else {
					// wishlist에 추가되어있지 않을 경우
					session.setAttribute("checkWish", 0);
				}
				
				return "view/detail";
			} else {
				System.out.println("food세션 보내기 실패");
				return "redirect:/";
			}
		} else {
			return "view/login";
		}
	}
	
	
	@RequestMapping("/witsh_detail.do")
	public String witsh_detail(HttpSession session) {
		
		System.out.println("witsh_detail");
		Food resultF = (Food)session.getAttribute("Food");
		
		System.out.println("result : " + resultF);

		// 회원 정보 세션 가져오기
		Member resultM = (Member) session.getAttribute("member");

		// 로그인 먼저 된 후, 푸드 세션 보내기
		if (resultM != null) {
			if (resultF != null) {
				session.setAttribute("Food", resultF);
				System.out.println("food세션 보내기 성공");
				
				
				HashMap<String, Object> map = new HashMap<>();
				map.put("resultF", resultF.getFood_seq());
				map.put("resultM", resultM.getUser_id());
				
				System.out.println("hashmap출력확인" + map.values());
				
				Integer checkWish = (int)mapper.searchWish(map);
				System.out.println("addWishresult확인" + checkWish);
				if(checkWish == 1) {
					// wishlist에 추가되어있을 경우
					session.setAttribute("checkWish", 1);
				}else {
					// wishlist에 추가되어있지 않을 경우
					session.setAttribute("checkWish", 0);
				}
				
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

		
		// 업로드했을 때 식재료 시퀀스 알아내기.. (식재료 1개)
		// 식재료 이름을 통해 식재료 번호 가져오는 mapper 만들어보기
		@RequestMapping("/print_ingreSeq1.do")
		public Integer ingreSeq(@RequestBody String ingredient_data) {
			System.out.println("data확인해보기"+ingredient_data);
			
			//ingredient_data=pork
			//[ingredient_data, pork]
			String[] data = ingredient_data.split("=");
				
			Integer seq = mapper.ingreSeq(data[1]);
			
			System.out.println("seq" + seq);
			
			return seq==null? 0 : seq;
		}
		
		// 식재료 번호 통해 식재료 이름 가져오기
		@RequestMapping("/print_ingreName.do")
		public String ingreName(@RequestBody String ingredient_arr) {
			System.out.println("data확인해보기: " + ingredient_arr);
			
			// 담아온 데이터 리스트로 담아오는거 성공
			List<String> list = new ArrayList<String>();
			
			ingredient_arr = ingredient_arr.replace("+", "");
			ingredient_arr = ingredient_arr.replace("ingredient_arr%5B%5D", "");
			ingredient_arr = ingredient_arr.replace("&", "");
			
			System.out.println("잘대체됐니.."+ingredient_arr);
			String[] data = ingredient_arr.split("=");
			
			for(int i=1; i<data.length; i++){
			    //System.out.println(data[i]);
				list.add(data[i]);
			}
			System.out.println("data잘렸니?" + list);
			
			// 이름으로 바꾸기
			List<String> name_list = mapper.ingreName(list);
			
			String ajaja = "{\"data\": \"" + name_list.toString() +"\" }";
			
			// 대괄호 제거
			ajaja = ajaja.replace("[", "");
			ajaja = ajaja.replace("]", "");
			
			System.out.println("ajaja" + ajaja);
			return ajaja;
			
			
		}
	}
}
