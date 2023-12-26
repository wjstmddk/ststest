<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
<script src="/js/jquery-3.7.0.min.js"></script>
</head>
<body>
<!-- 컨트롤러에서 LocalDateTime 형식으로 날짜받기 -->
<form id="rform">
	원글번호:<input id="r_bnum" type="text" name="r_bnum"> <br>
	내용:<input id="r_contents" type="text" name="r_contents"><br>
	아이디:<input id="r_id" type="text" name="r_id"><br>
	날짜:<input id="r_date" type="datetime-local" name="r_date"><br>
	<button type="button" onclick="go()">전송ajax</button>
</form>
<hr>
<!-- mybatis에 파라미터 2개 처리하기 -->
<form>
${msg}
<div>dsjkfsdlkfj</div>
	아이디:<input id="id" type="text" name="id"> <br>
	비번:<input id="pw" type="text" name="pw"><br>
	<button type="button" onclick="go2()">로그인</button>
</form>

<script>
	function go2(){
		let id=$('#id').val();
		let pw=$('#pw').val();
		$.ajax({
			method:'get', 
			url: '/test/'+id+'/'+pw,   //test/cha/1111
		   //data: {id:id,pwd:pwd}
			//contentType: 'application/x-www-form-urlencoded'
		   //dataType:'html',
		}).done(res=>console.log(res))
		.fail(err=>console.log(err));
	}
	function go(){
		 //+":00"; //레거시에서는 포맷SS가 있으면 :00추가
 		let date=$('#r_date').val(); //+":00";
 		console.log(date);
 		let obj={r_bnum:$('#r_bnum').val(), 
 				 r_contents:$('#r_contents').val(),
 				 r_id:$('#r_id').val(), r_date:date};
		//let qStr=$('#rform').serialize()
		$.ajax({
			method:'post', 
			url: '/test/time',
			data:obj,
			//data:qStr,
		}).done(res=>console.log(res))
		.fail(err=>console.log(err));
	} //go End
</script>
</body>
</html>