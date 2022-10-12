package com.smhrd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.smhrd.domain.Food;

@Mapper
public interface FoodMapper{
	// 음식 상세 페이지
	public Food foodDetail(Food food);
	
	// 음식 상세 페이지(test)
	public Food detail_a(Food food);
	
	// 음식 목록 출력하기
	public List<Food> foodList(Food food);
	
	
}
