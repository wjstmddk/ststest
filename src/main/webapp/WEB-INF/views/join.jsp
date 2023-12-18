<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 양식</title>
<script src="/js/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
	<div class="wrap">
		<header>
			<jsp:include page="header.jsp" />
		</header>
		<section>
			<div class="content">
				<form action="/member/join" method="post" class="login-form"
					name="jFrm" onsubmit="return check()">
					
					<h2 class="login-header">회원 가입</h2>
					<input name="m_id" type="text" class="login-input" id="m_id" 
						title="아이디" autofocus placeholder="아이디"> 
					<span id="checkMsg">  </span>	
					<input  type="button" id="checkId" class="idcheck-btn" value="중복확인" >
					<input name="m_pw" type="password" class="login-input" 
						title="비밀번호" placeholder="비밀번호"> 
					<input name="m_name" type="text"
						class="login-input"  title="이름" placeholder="이름">
					<input name="m_birth" type="text" class="login-input"  title="생일"
						placeholder="생일">
					<input name="m_addr" type="text" class="login-input"
						 title="주소" placeholder="주소">
					<input name="m_phone" type="text" class="login-input"  title="연락처"
						placeholder="연락처">
					<input type="submit" class="login-btn"	value="가입">
				</form>
			</div>
		</section>
		<footer>
			<jsp:include page="footer.jsp" />
		</footer>
	</div>
	<script>
	let isCheck=false;  //true
	$('#checkId').on('click',function(){
		let id= $('#m_id').val();
		if(id ==''){
			$('#checkMsg').html('아이디를 입력하세요');
			$('#m_id').focus();
			return;
		}
		let sendId = {m_id:id};
		//sendId.m_pwd="1111";
		console.log(sendId);
		$.ajax({
			method:'get',
			url: '/member/idCheck', //?m_id='+$('#m_id').val(), //대/중/소 (가능하면 명사)
			//data: m_id='+$('#m_id').val(),
			data: sendId,
			//dataType: 'json', //text(html), jsonp
		}).done(function(res, status, xhr){
			console.log("res:", res);
			console.log("status:", status);
			console.log("xhr:", xhr);
			//메세지 출력
 			if(res=='ok'){
 				$('#checkMsg').html('사용가능한 아이디입니다').css('color','blue');	
 			}else{
 				$('#checkMsg').html('이미 사용중인 아이디입니다').css('color','red');
 			}
			
			//$('#checkMsg').html(res).css('color','blue');
			isCheck=true;
		}).fail((err,status)=>{
			console.log("err:", err)
			console.log("status:", status);
			//$('#checkMsg').html(err.responseText).css('color','red');
			isCheck=false;
		}); //fail End
	});//on end
	
	function check(){
		if(isCheck ==false){
			$('#checkMsg').html('아이디 중복 확인해주세요');
			return false;
		}
		const jfrm=document.jFrm;
		console.log(jfrm);
		let length = jfrm.length-1;
		//jQuery validation 활용
		for(let i=0; i<length; i++){
			if(jfrm[i].value=='' || jfrm[i].value==null){
				alert(jfrm[i].title+" 입력!!!");
				jfrm[i].focus();
				return false;
			}
		}
		return true;  //jfrm.submit();
	}
	</script>
</body>
</html>