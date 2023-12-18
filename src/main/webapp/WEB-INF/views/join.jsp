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
	<script >
	function check(){
		const jfrm=document.jFrm;
		console.log(jfrm);
		let length = jfrm.length-1;
		for(let i=0; i<length; i++){
			if(jfrm[i].value=='' || jfrm[i].value==null){
				alert(jfrm[i].title+" 입력!!!");
				jfrm[i].focus();
				return false;
			}
		}
		return true;  //jfrm.submit()하면 안됨;
	}
	</script>
</body>
</html>