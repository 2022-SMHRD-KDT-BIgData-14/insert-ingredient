
/* 업로드 페이지로 이동 함수 */
function goUpload(){
	location.href="goUpload.do";
}

/* 메인 페이지로 이동 함수 */
function goMain(){
	location.href="goMain.do";
}

/* 마이페이지로 이동 함수 */
function goMypage(){
	location.href="goMypage.do";
}


/* 임시 메인으로 이동 함수 */
function goMain_a(){
	location.href="goMain_a.do";
}

// 음식 검색
var food_name;
var food_seq;

$(function() {
	$('.insert-start').autocomplete({
		source: function(request, response) {
			console.log(request.term)
			$.ajax({
				url: 'foodAuto.do',
				type: 'get',
				datatype: "json",
				data: {
					food: request.term
				},
				success: function(food_name) {

					console.log(food_name)

					response($.map(food_name, function(item) {
						return {
							label: item.food_name,
							value: item.food_name,
							food_seq: item.food_seq
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
		}
	});
});
