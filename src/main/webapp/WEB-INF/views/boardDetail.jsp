<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세 보기- boardDetail</title>
<script src="/js/jquery-3.7.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-serialize-object/2.5.0/jquery.serialize-object.min.js"
 integrity="sha512-Gn0tSSjkIGAkaZQWjx3Ctl/0dVJuTmjW/f9QyB302kFjU4uTNP4HtA32U2qXs/TRlEsK5CoEqMEMs7LnzLOBsA==" crossorigin="anonymous" referrerpolicy="no-referrer">
 </script>
<link rel="stylesheet" href="/css/style.css">
<script>
	//메시지 출력 부분
	let m = "${msg}";
	if (m != "") {
		alert(m);
	}
$(()=>{
		if(${mb!=null}){
			let loginName='${mb.m_name}'; //로그아웃시 mb속성 삭제할것.
			$('#m_name').html(loginName+' 님');
			$('.suc').css('display','block');
			$('.bef').css('display','none');	
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
				<div class="write-form">
					<div class="user-info">
						<div class="user-info-sub">
							<p>등급 [${mb.g_name}]</p>
							<p>POINT [${mb.m_point}]</p>
						</div>
					</div>
					<h2 class="login-header">상세 보기</h2>
					<div>
						<div class="t_content p-15">NUM</div>
						<div class="d_content p-85">${board.b_num}</div>
					</div>
					<div>
						<div class="t_content p-15">WRITER</div>
						<div class="d_content p-35">${board.b_writer}</div>
						<div class="t_content p-15">DATE</div>
						<div class="d_content p-35">
							<!-- LocalDateTime일때 -->
							<fmt:parseDate value="${board.b_date}"
								pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
							<fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss"
								value="${parsedDateTime}" />
							<!-- TimeStamp일때 -->
							<%-- <fmt:formatDate value="${board.b_date}" pattern="yyyy-MM-dd HH:mm:ss">
								</fmt:formatDate> --%>
						</div>
					</div>
					<div>
						<div class="t_content p-15">TITLE</div>
						<div class="d_content p-85">${board.b_title}</div>
					</div>
					<div>
						<div class="t_content p-15 content_h">CONTENTS</div>
						<div class="d_content p-85 content_h">${board.b_contents}</div>
					</div>
					
					<div class="btn-area">
						<button class="btn-write" id="upbtn"
							onclick="upload_board('${board.b_num}')">U</button>
						<button class="btn-write" id="delbtn"
							onclick="delete_board('${board.b_num}')">D</button>
						<button class="btn-sub" onclick="backbtn()">B</button>
					</div>
					<!-- 댓글 입력 양식 -->
					<form id="rform">
						<!-- 게시글 정보(글번호), 댓글 내용, 접속자(작성자) -->
						<input type="hidden" name="r_bnum" id="r_bnum"
							value="${board.b_num}">
						<textarea name="r_contents" rows="3" class="write-input ta"
							id="r_contents" placeholder="댓글을 적어주세요."></textarea>
						<input type="hidden" name="r_id" id="r_id" value="${mb.m_id}">
						<input type="button" value="댓글 전송" class="btn-write"
							onclick="replyInsert()" style="width: 100%; margin-bottom: 30px;">
					</form>
					<table style="width: 100%">
						<!-- 제목 테이블 -->
						<tr class="rtbl-head">
							<td class="p-20">WRITER</td>
							<td class="p-50">CONTENTS</td>
							<td class="p-30">DATE</td>
						</tr>
					</table>

					<table style="width: 100%;" id="rtable">
						<c:forEach var="ritem" items="${rList}">
							<tr>
								<td class="p-20">${ritem.r_writer}</td>
								<td class="p-50">${ritem.r_contents}</td>
								<!-- LocalDateTime을 jstl에서 사용하기: pattern에 꼭 'T'추가할것.-->
								<td class="p-30">
								<fmt:parseDate value="${ritem.r_date}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both" />
								<fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${parsedDateTime}" />
								</td>
								<!-- private Timestamp r_date 일때-->
<%-- 								<td class="p-30"> <fmt:formatDate value=" ${ritem.r_date}" --%>
<%-- 										pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td> --%>
								<!-- private String r_date 일때 -->
<%-- 								<td class="p-30">${ritem.r_date}</td> --%>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</section>
		<footer>
			<jsp:include page="footer.jsp"></jsp:include>
		</footer>
	</div>

	<script>
		function replyInsert(){
			let data={}
			data.r_contents=$('#r_contents').val() 
			data.r_bnum=$('#r_bnum').val()  //원글번호
			data.r_writer='${mb.m_id}' //글쓴 사람 아이디, session값으로 처리
			//data.r_date=//db 기본값
			console.log("data:",data);
			$.ajax({
				method: 'post', //'get'(select) 'post'(insert,delete,update)
				url: '/board/reply',
				data: data,
/* 				data: {r_contents:$('#r_contents').val(),
					   r_bnum:$('#r_bnum').val()  }, */
				//서버의 ContentType에 리턴할 값의 타입이 자동인식(생략가능)
				dataType: 'json', //text(html), jsonp, xml	 				
			}).done(function(res){
// 				console.log("res:",res)
// 				result=""
// 				close.Model();
// 				$('#rtable').empty();
// 	 				$.each(res,function(index,item){
// 	 					$("#rtable").append("<tr>")
// 	 					$("#rtable").append("<td>"+item.r_writer+"</td>")
// 	 					$("#rtable").append("<td>"+item.r_contents+"</td>")
// 	 					$("#rtable").append("<td>")
// 	 					$("#rtable").append(item.r_date)
// 	 					$("#rtable").append("</td>")
// 	 					$("#rtable").append("</tr>")
				$('#rtable').load(location.href+' #rtable')

				
// 				})

			}).fail((res)=>console.log("res:",res))
		}
		function replyToRtable(reply){
 			let replyHtml = '';
			replyHtml += '<tr><td class="p-20">' + reply.r_writer
					+ '</td>' + '<td class="p-50">'
					+ reply.r_contents + '</td>'
					+ '<td class="p-30">' + reply.r_date
					+ '</td></tr>'
			$('#rtable').prepend(replyHtml);
			$('#r_contents').val('').focus();
		}
	</script>
</body>
</html>