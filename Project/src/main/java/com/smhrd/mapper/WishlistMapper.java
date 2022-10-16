package com.smhrd.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.smhrd.domain.Wishlist;

@Mapper
public interface WishlistMapper {
	
	// wishlist 추가
	public int addWish(HashMap<String, Object> map);
	
	// wishlist 삭제
	public int delWish(HashMap<String, Object> map);
	
	// 찜 목록 확인
	public List<Wishlist> showWishAjax();
	
}
