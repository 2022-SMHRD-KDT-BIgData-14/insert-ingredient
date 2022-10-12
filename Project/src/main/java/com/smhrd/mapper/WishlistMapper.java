package com.smhrd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.smhrd.domain.Member;
import com.smhrd.domain.Wishlist;

@Mapper
public interface WishlistMapper {
	// 찜하기
	public int savelist(String user_id, int food_seq);
	
	// 찜 목록 확인
	public List<Wishlist> showWishAjax();

	// 찜 취소
	public int noWish(String user_id, int food_seq);
	
}
