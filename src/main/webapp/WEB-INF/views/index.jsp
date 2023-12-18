<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
<script src="/js/jquery-3.7.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<link rel="stylesheet" href="/css/style.css">
<script defer src="/js/common.js"></script>

<script>
const m='${msg}'  //''  '회원가입성공'
const mb=${!empty mb} //true false
const m_name='${mb.m_name}'  //'홍길동'
console.log("m:",m,"mb:",mb,"m_name:",m_name);

$(()=>{
	msgPrint()
	loginStatus()
	
	//bxSlider 설정용 스크립트
    $(".slider").bxSlider({
        auto: true,
        slideWidth: 600,
    })
}) //ready end
</script>
</head>
<body>
	<jsp:include page="./header.jsp"></jsp:include>
	<section>
		<div class="content-home">
			<div class="slider">
				<div>
					<img src="/img/Chrysanthemum.jpg">
				</div>
				<div>
					<img src="/img/Desert.jpg">
				</div>
				<div>
					<img src="/img/Lighthouse.jpg">
				</div>
				<div>
					<img src="/img/Tulips.jpg">
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="footer.jsp" />
</body>
</html>