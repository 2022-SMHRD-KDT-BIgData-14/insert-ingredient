// Food DTO
package com.smhrd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {
	
	private int food_seq;
	private String food_name;
	private String food_img;
	private String ingredient_name;
	private String introduce;
	private String process_article;
	private String process_img;
	private String youtube;
}
