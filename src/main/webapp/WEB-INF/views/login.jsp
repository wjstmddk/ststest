<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 양식</title>
<script src="/js/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="/css/style.css">
<script>
	//$(()=>{
		let m='${msg}';
		if(m!=''){
			alert(m);
		}	
	//})
</script>
</head>
<body>
<h1>login.jsp</h1>
<div class="wrap">
<header>
	<jsp:include page="header.jsp" />
</header>

<section>
        <div class="content">
            <form action="/member/login" method="post" class="login-form">
 				<h2 class="login-header">로그인</h2>
 				<input type="text" class="login-input" name="m_id" autofocus required placeholder="아이디">
 				<input type="password" class="login-input" name="m_pw" required placeholder="비밀번호">
 				<input type="submit" class="login-btn" value="로그인">           
            </form>
        </div>
    </section>

<footer>
	<jsp:include page="footer.jsp"></jsp:include>
</footer>

</div>

</body>
</html>