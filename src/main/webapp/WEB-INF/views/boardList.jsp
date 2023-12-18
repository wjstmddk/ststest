<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/js/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="/css/style.css">
<script>
	let m = '${msg}';
	if (m != '') {
		alert(m);
	}
	$(()=>{
		if(${mb!=null}){
			let loginName='${mb.m_name}'; //로그아웃시 mb속성 삭제할것.
			$('#m_name').html(loginName+' 님');
			$('.suc').css('display','block');  //.show()
			$('.bef').css('display','none');   //.hide() 
		}else{  //로그인 없이 게시글 리스트 요청시
			$('.suc').css('display','none');
			$('.bef').css('display','block');
		}
	}) //ready

</script>
</head>
<body>
	<div class="wrap">
		<header>
			<jsp:include page="header.jsp"></jsp:include>
		</header>
		<section>
			<div class="content">
				<div class="board-form">
					<div class="user-info">
						<div class="user-info-sub">
							<p>등급 [${mb.g_name}]</p>
							<p>POINT [${mb.m_point}]</p>
						</div>
					</div>
					<h2 class="login-header">게시글 목록</h2>
					<div class="search-area">
						<select id="sel">
							<option value="b_title" selected>제목</option>
							<option value="b_contents">내용</option>
						</select> 
						<input type="text" id="keyword">
						<button id="search">검색</button>
					</div>
					<div class="data-area">
						<div class="title-row">
							<div class="t-no p-10">번호</div>
							<div class="t-title p-30">제목</div>
							<div class="t-name p-15">작성자</div>
							<div class="t-date p-30">작성일</div>
							<div class="t-view p-15">조회수</div>
						</div>
						<div class="data-row">
							<c:if test="${empty bList}">
								<div style="width: 100%">게시글이 없습니다.</div>
							</c:if>
							<c:if test="${!empty bList}">
								<c:forEach var="bitem" items="${bList}">  
									<div class="t-no p-10">${bitem.b_num}</div>
									<div class="t-title p-30">
										<a href="/board/detail?b_num=${bitem.b_num}">${bitem.b_title}</a>
									</div>
									<div class="t-name p-15">${bitem.b_writer}</div>
									<div class="t-date p-30">
									    <!-- LocalDateTime일때 -->
 										<fmt:parseDate value="${bitem.b_date}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
 										<fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${parsedDateTime}" /> 										
 										<!-- TimeStamp일때 -->
										<%-- <fmt:formatDate value="${bitem.b_date}" pattern="yyyy-MM-dd HH:mm:ss">
										</fmt:formatDate> --%>
									</div>
									<div class="t-view p-15">${bitem.b_views}</div>
								</c:forEach>
							</c:if>
						</div>
					</div>
					<div class="btn-area">
						<div class="paging">${paging}</div>
						<button class="wr-btn" onclick="location.href='/board/write'">
							글쓰기</button>
					</div>
				</div>
			</div>
		</section>
		<footer>
			<jsp:include page="footer.jsp"></jsp:include>
		</footer>
	</div>
	<!-- wrap -->
	<script>
		//검색기능
		$('#search').click(function(){
			let keyword = $('#keyword').val();
			if(keyword==''){
				alert('검색어를 입력하세요');
				return;
			}
			let select = $('#sel').val(); //b_title or b_contents
			if(select=='') return;
			console.log(keyword, select);
			location.href='/board/list?colname='+select+'&keyword='+keyword
					      +"&pageNum=1";
			//localhost/board/list?colname=b_title&keyword=33&pageNum=1
			
		}); //click end
	</script>
</body>
</html>