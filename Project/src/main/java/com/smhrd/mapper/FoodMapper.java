package com.smhrd.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.smhrd.domain.Food;
import com.smhrd.domain.FoodVO;
import com.smhrd.domain.Ingredient;
import com.smhrd.domain.Relationship;

@Mapper
public interface FoodMapper{
	// 음식 상세 페이지
	public Food foodDetail(Food food_seq);
	
	// 음식 상세 페이지(test)
	public Food detail_a(Food food);
	
	// 최근 본 음식 목록 출력하기
	public List<Food> recentList(Food food);
	
	// food_name autocomplete
	public List<Food> FoodAuto(String food);
	
	// ingredient_name autocomplete
	public List<Ingredient> IngredientAuto(String ingredient);
	
	public List<Relationship> RelationList(FoodVO foodVO);
	
	public List<Food> FoodList(ArrayList<Integer> intList);
	
	
	// 식재료 이름에 맞는 식재료 번호 가져오기1
	public Integer ingreSeq(String ingredient_name);
}
