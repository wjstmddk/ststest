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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-serialize-object/2.5.0/jquery.serialize-object.min.js"
	integrity="sha512-Gn0tSSjkIGAkaZQWjx3Ctl/0dVJuTmjW/f9QyB302kFjU4uTNP4HtA32U2qXs/TRlEsK5CoEqMEMs7LnzLOBsA=="
	crossorigin="anonymous" referrerpolicy="no-referrer">
 </script>
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
<!-- 한글패치 -->
<script
	src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
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
		
		$('#summernote').summernote({
			toolbar: false,//상세보기에선 툴바필요없음
			height : 400, // 에디터 높이
			minHeight : null, // 최소 높이
			maxHeight : null, // 최대 높이
			focus : true, // 에디터 로딩후 포커스를 맞출지 여부
			lang : "ko-KR", // 한글 설정
			placeholder : '최대 2048자까지 쓸 수 있습니다' //placeholder 설정
		});
		//서머노트 쓰기 비활성화를 해야 수정 불가
		$('#summernote').summernote('disable');
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
<%-- 					<div class="d_content p-85 content_h">${board.b_contents}</div> --%>
						<div class="d_content p-85 content_h">
							<!-- summernote form태그없을때는 div, form태그시는 textarea -->
							<div id="summernote">${board.b_contents}</div>
						</div>
					</div>
					<!-- 첨부파일 영역 -->
					<div>
						<div class="t_content p-15 file_h">FILES</div>
						<div class="d_content p-85 file_h" style="overflow: auto;">
							<c:if test="${empty board.bfList}">
                            첨부된 파일이 없습니다.
                        	</c:if>
							<c:if test="${!empty board.bfList}">
								<c:forEach var="file" items="${board.bfList}">
									<a
										href="/board/download?bf_sysname=${file.bf_sysname}
									        &bf_oriname=${file.bf_oriname}">
										<span class="file-title"> <i class="fa fa-file-o"
											style="font-size: 24px"></i>${file.bf_oriname}
									</span>
									</a>
								</c:forEach>
							</c:if>
						</div>
					</div>
					<!-- 파일 첨부 영역 끝 -->

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
						<input type="hidden" name="r_writer" id="r_writer"
							value="${mb.m_id}"> <input type="button" value="댓글 전송"
							class="btn-write" onclick="replyInsert()"
							style="width: 100%; margin-bottom: 30px;">
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
								<td class="p-30"><fmt:parseDate value="${ritem.r_date}"
										pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime"
										type="both" /> <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss"
										value="${parsedDateTime}" /></td>
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
	<template id="tr_template">
		<tr>
			<td class="p-20">{r_writer}</td>
			<td class="p-50">{r_contents}</td>
			<td class="p-30">{r_date}</td>
		</tr>
	</template>

	<script>
		$('#upbtn').hide();
		$('#delbtn').hide();
		let mid = '${mb.m_id}';
		let bwriter = '${board.b_writer}';
		console.log(mid, bwriter);
		if (mid == bwriter) {
			$('#upbtn').show();
			$('#delbtn').show();
		}
		//D버튼: 게시글  삭제
		function delete_board(bnum) {
			//alert(bnum);
			let conf = confirm("정말 삭제하겠습니까?");
			if (conf == true) {
				location.href = '/board/delete?b_num=' + bnum;
			}
		}
		//B버튼: 게시글리스트
		const backbtn = function() {
			let url = "/board/list?";
			let col = '${sessionScope.sDto.colname}';
			let keyw = '${sDto.keyword}';

			if (col == '') {
				url += 'pageNum=${sessionScope.pageNum}';
			} else {
				url += 'colname=${sDto.colname}' + '&keyword=${sDto.keyword}'
						+ '&pageNum=${sDto.pageNum}'
			}
			location.href = url;
		}
		
		function upload_board(bnum) {
			location.href = '/board/update?b_num=' + bnum;
		}
	
		function replyInsert(){
			//let data={}
			//data.r_contents=$('#r_contents').val() 
			//data.r_bnum=$('#r_bnum').val()  //원글번호
			//data.r_writer='${mb.m_id}' //글쓴 사람 아이디, session값으로 처리
			//data.r_date=//db 기본값
			
			let data=$('#rform').serializeObject();
			//form태그의 문자열data를 js객체로 생성, 단 file태그 있을 때는 FormDate객체를 사용할것.
			//data.r_writer='${mb.m_id}' form태그에 없으면 추가 데이터...
			console.dir(data);
			$.ajax({
				method: 'post', //'get'(select) 'post'(insert,delete,update)
				//url: '/board/reply',  //List로 리턴
				//url: '/board/reply2', //Map으로 리턴
				url: '/board/reply3', //replyDto로 리턴
				//data: data,
				//data: $('#rform').serialize()  //r_contents='댓글3'&r_writer='aaa'...
				//1. urlEncoded방식(get,post): 문자열에 특수문자는 못넘김
				//header의 contentType기본값
				//contentType: 'application/x-www-form-urlencoded'
				//2. json방식(post만): 서버에 json형식 데이터 전송--->서버에서 @RequestBoby로 받을것 
				contentType: 'application/json;charset=UTF-8',
				data: JSON.stringify(data) 
				//서버의 ContentType에 리턴할 값의 타입이 자동인식(생략가능)
				//dataType: 'json', //text(html), jsonp, xml	   
			}).done(function(res){
				//debugger;
				console.log("res:",res) //
				//댓글리스트를 id="rtable" 출력
// 				let rList=''
// 				$.each(res, function(i, reply){
// 					rList+='<tr><td class="p-20">'+reply.r_writer+'</td>'
// 				           +'<td class="p-50">'+reply.r_contents+'</td>'
// 				           +'<td class="p-30">'+reply.r_date+'</td></tr>'	
// 				});//end each 
//				$('#rtable').html(rList)
//				$('#r_contents').val('').focus()

				//template 태그 활용
// 				const $table=$('#rtable');
// 				const $tmpl=$('#tr_template').html();
// 				$table.empty(); //기존 테이블 댓글 삭제
// 				for(const r of res){
// 					$table.append($tmpl.replace('{r_writer}',r.r_writer)
// 					     .replace('{r_contents}',r.r_contents)
// 					     .replace('{r_date}',r.r_date))
// 				}

			// Map<String,Object> 리턴한 댓글리스트  	
// 				console.log("res.bDto",res.bDto) 
// 				console.log("res.rList",res.rList) 
// 				if(res.rList!=null && res.rList.length!=0){
// 					mapReplyListToRtable(res.rList); 	
// 				}
			// ReplyDto 리턴한 최신댓글을 rtable의 맨위로 추가  	
 				//console.log("res",res) 
				if (res != null && res != '') {
					replyToRtable(res)
 				}
 			}).fail((res)=>console.log("res:",res))
 		}//end function
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
 		function mapReplyListToRtable(rList){
 			let rListHtml=''
 			rList.forEach(function(reply, idx){
 				rListHtml+='<tr><td class="p-20">'+reply.r_writer+'</td>'
   		           			+'<td class="p-50">'+reply.r_contents+'</td>'
		           			+'<td class="p-30">'+reply.r_date+'</td></tr>'
 			}) //end forEach
 			$('#rtable').html(rListHtml)
			$('#r_contents').val('').focus()
 		}
	</script>
</body>
</html>