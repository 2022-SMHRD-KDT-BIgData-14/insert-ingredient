// Ingredient_Autocomplete
var ingredient_name;
var ingredient_seq;

// 식재료 검색
$(function() {
	$('#name').autocomplete({
		source: function(request, response) {
			$.ajax({
				url: 'ingredientAuto.do',
				type: 'get',
				datatype: "json",
				data: {
					ingredient: request.term
				},
				success: function(ingredient_name) {


					response($.map(ingredient_name, function(item) {
						return {
							label: item.ingredient_name,
							value: item.ingredient_name,
							ingredient_seq: item.ingredient_seq
						}
					}))
				},
				error: function(e) {
					alert("error");
				}
			});

		},
		autoFocus: true,
		minLength: 2,
		delay: 100,
		select: function(event, ui) {
			ingredient_seq = ui.item.ingredient_seq
		}
	});
});

// 음식 검색
$(function() {
	$('.insert-start').autocomplete({
		source: function(request, response) {
			console.log(request.term)
			$.ajax({
				url: 'ingredientAuto.do',
				type: 'get',
				datatype: "json",
				data: {
					ingredient: request.term
				},
				success: function(ingredient_name) {

					console.log(ingredient_name)

					response($.map(ingredient_name, function(item) {
						return {
							label: item.ingredient_name,
							value: item.ingredient_name,
							ingredient_seq: item.ingredient_seq
						}
					}))
				},
				error: function(e) {
					alert("error");
				}
			});

		},
		autoFocus: true,
		minLength: 2,
		delay: 100,
		select: function(event, ui) {
			console.log("selected : " + $(this).val());
			console.log("ingredient_seq출력" + ui.item.ingredient_seq)
			ingredient_seq = ui.item.ingredient_seq
		}
	});
});


var list = [];
// 식재료 태그 함수
$(document).ready(
	function() {
		var tag = {};
		var counter = 0;

		// 태그를 추가한다.
		function addTag(value) {
			tag[counter] = value; // 태그를 Object 안에 추가
			counter++; // counter 증가 삭제를 위한 del-btn의 고유 id 가 된다.
		}

		// 최종적으로 서버에 넘길때 tag 안에 있는 값을 array type 으로 만들어서 넘긴다.
		function marginTag() {

			return Object.values(tag).filter(
				function(word) {
					return word !== "";
				});
		}

// ingredient-tag
		$("#name")
			.on(
				"keyup",
				function(e) {
					var ingredient = $(this);

					// input 에 focus 되있을 때 엔터 및 스페이스바 입력시 구동
					if (e.key === "Enter"
						|| e.keyCode == 32) {

						var tagValue = ingredient.val(); // 값 가져오기

						// 값이 없으면 동작 안합니다.
						if (tagValue !== "") {

							// 같은 태그가 있는지 검사한다. 있다면 해당값이 array 로 return 된다.
							var result = Object
								.values(tag)
								.filter(
									function(
										word) {
										return word === tagValue;
									})

							// 태그 중복 검사
							if (result.length == 0) {
								$("#tag-list")
									.append(
										"<li class='tag-item'>"
										+ tagValue
										+ "<span class='del-btn' idx='" + counter + "' name='"+ ingredient_seq +"'>x</span></li>");
								addTag(tagValue);
								ingredient.val("");

								list.push(ingredient_seq)
								var taglist = {
									"taglist" : list
								}
								tagList(JSON.stringify(taglist))
								
							} else {
								alert("Duplicate Ingredient");
							}
						}
						e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
						
					}
				});
				
				
// food-tag
		$("#foodAuto")
			.on(
				"keyup",
				function(e) {
					var food = $(this);
					// input 에 focus 되있을 때 엔터 및 스페이스바 입력시 구동
					if (e.key === "Enter"
						|| e.keyCode == 32) {

						var tagValue = food.val(); // 값 가져오기

						// 값이 없으면 동작 안합니다.
						if (tagValue !== "") {

							// 같은 태그가 있는지 검사한다. 있다면 해당값이 array 로 return 된다.
							var result = Object
								.values(tag)
								.filter(
									function(
										word) {
										return word === tagValue;
									})

							// 태그 중복 검사
							if (result.length == 0) {
								$("#tag-list")
									.append(
										"<li class='tag-item'>"
										+ tagValue
										+ "<span class='del-btn' idx='" + counter + "'>x</span></li>");
								addTag(tagValue);
								food.val("");
							} else {
								alert("Duplicate Ingredient");
							}
						}
						e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
						
						// list = JSON.stringify(tag)
						list.push(ingredient_seq)
						var taglist = {
							"taglist" : list
						}
						tagList(JSON.stringify(taglist))
					}
				});

		// 삭제 버튼은 비동기적 생성이므로 document 최초 생성시가 아닌 검색을 통해 이벤트를 구현시킨다.
		$(document).on("click", ".del-btn", function(e) {
			var index = $(this).attr("idx");
			var name = $(this).attr("name");
			
				
			if(list.length==1){
				for(var i=0; i<list.length; i++){
					if(list[i] == name){
						list.splice(i, 1);
						i--;
					}
				}
				
				$('.upload-result').html('')
				$(this).parent().remove();
				return false
			}else{
				for(var i=0; i<list.length; i++){
					if(list[i] == name){
						list.splice(i, 1);
						i--;
					}
				}
				
				var taglist = {
					"taglist" : list
				}
	
				tagList(JSON.stringify(taglist))
				tag[index] = "";
				$(this).parent().remove();
			}
		});



	})







function tagList(list) {
	$.ajax({
		url: 'realtionList.do',
		type: 'get',
		datatype: "json",
		data: {"list":list},
		success: function(e) {
			Foodlist(e)
		},
		error: function() {
			console.log("foodlist 전달 실패")
		}
	})

}



function Foodlist(foodlist) {
	$('.upload-result').html('')
	for (var i = 0; i < foodlist.length; i++) {
		$('.upload-result').append(
		`
        <div style="cursor:pinter;" class="col-xs-12 col-md-4 section-container-spacer upload-section-line">
         
          <!-- 검색결과 호버01-1 -->
          <div class="grid-item-01">
            <div class="gutter-sizer"></div>
            <div class="grid-sizer-01"></div>
            <img class="img-responsive upload-print-img" src="${foodlist[i].food_img}">
            <a href="detail.do?food_seq=${foodlist[i].food_seq}" class="project-description">
              <!-- 여기에 찜 넣기 -->
              <div class="project-text-holder-01">
                <div class="project-text-inner-01">
                  <h1>${foodlist[i].food_name}</h1>
                </div>
              </div>
            </a>
          </div>
        </div>
		`
		)
		if(i === 8){
			return false;
		}
	};
}