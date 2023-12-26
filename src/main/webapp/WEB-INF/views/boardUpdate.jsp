<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정창</title>
<script src="/js/jquery-3.7.0.min.js"></script>
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
	$(function() {
		$('#summernote').summernote({
	   		  height: 300,                 // 에디터 높이
	   		  minHeight: null,             // 최소 높이
	   		  maxHeight: null,            // 최대 높이
	   		  focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
	   		  lang: "ko-KR",					// 한글 설정
	   		  placeholder: '최대 2048자까지 쓸 수 있습니다'	//placeholder 설정
	     });

		//로그인한 회원 정보 및 로그아웃 출력
		let loginName = "${mb.m_name}";
		$("#m_name").html(loginName + "님");
		$(".suc").css("display", "block");
		$(".bef").css("display", "none");
	});
</script>
</head>
<body>
	<div class="wrap">
		<header>
			<jsp:include page="header.jsp"></jsp:include>
		</header>
		<section>
			<div class="content">
				<form action="/board/update" class="write-form" 
				method="post" enctype="multipart/form-data">
					<div class="user-info">
						<div class="user-info-sub">
							<p>등급 [${mb.g_name}]</p>
							<p>POINT [${mb.m_point}]</p>
						</div>
					</div>
					<h2 class="login-header">글수정</h2>
					<!-- 제목, 내용, 파일 -->
					<input type="hidden" name="b_num" value="${board.b_num}"> <input
						type="text" class="write-input" name="b_title" autofocus
						placeholder="제목" required value="${board.b_title}">
					<!-- 					<textarea rows="15" name="b_contents" placeholder="내용을 적어주세요." -->
					<%-- 						class="write-input ta">${board.b_contents}</textarea> --%>
					<textarea id="summernote" name="b_contents" class="write-input ta"
						readonly>${board.b_contents}</textarea>
					<div class="filebox">
						<!-- 첨부된 파일 목록 출력 -->
						<div id="bfile" style="margin-bottom: 10px;">
							<c:if test="${empty board.bfList}">
								<label style="width: 100%;">첨부파일 없음</label>
							</c:if>
							<c:if test="${!empty board.bfList}">
								<c:forEach var="file" items="${board.bfList}">
									<label style="width: 100%;" onclick="del('${file.bf_sysname}')">
										<i class="fa fa-file-o" style="font-size: 24px"></i>
										${file.bf_oriname}
									</label>
								</c:forEach>
							</c:if>
						</div>
						<!-- 새로운 파일 첨부 -->
						<label for="attachments">파일 추가</label> <input type="file"
							name="attachments" id="attachments" multiple> <input
							type="text" class="upload-name" value="파일선택" readonly>
					</div>
					<div class="btn-area">
						<input type="submit" class="btn-write" value="U"> <input
							type="reset" class="btn-write" value="R"> <input
							type="button" class="btn-write" value="B" onclick="backbtn()">
					</div>
				</form>
			</div>
		</section>
		<footer>
			<jsp:include page="footer.jsp"></jsp:include>
		</footer>
	</div>
	<script>
    function backbtn(){
        location.href = "/board/detail?b_num=${board.b_num}";
    }

    // 파일 제목 처리용 함수
    $("#attachments").on("change", function(){
        //파일 선택 창에서 업로드할 파일을 선택한 후
        //'열기' 버튼을 누르면 change 이벤트가 발생.
        console.log($("#attachments"));
        let files = $("#attachments")[0].files;
        console.log(files);

        let fileName = "";

        if(files.length > 1){
            fileName = files[0].name + " 외 "
                + (files.length - 1) + "개";
        } else if(files.length == 1){
            fileName = files[0].name;
        } else {
            fileName = "파일선택";
        }
        $(".upload-name").val(fileName);
    });

    function del(sysname) {
        //alert(sysname);
        let con = confirm("파일을 삭제할까요?");

        if(con == true){
            //삭제할 파일명
            let objdata = {"sysname":sysname};
            //파일 목록을 다시 불러오기 위해 게시글 번호 추가
            objdata.b_num = ${board.b_num};
            console.log(objdata);

            $.ajax({
                url: "/board/delFile",
                type: "post",
                data: objdata,  
            }).done(function(res){
            	console.log("res:",res);
                console.log(res.length);
	
                let flist = '';
                if(res.length == 0){
                	flist += '<label style="width: 100%;">첨부파일 없음</label>';
                }else {
                	//for of, $.each
                    res.forEach(file=>{
                    	flist +='<label style="width: 100%;" onclick="del(\''
                    			+ file.bf_sysname + '\')">  '
				         		+'<i class="fa fa-file-o" style="font-size: 24px"></i>'
				         		+ file.bf_oriname+'</label>';
           			})
                }
                $("#bfile").html(flist);
            }).fail(function(err){
            	console.log(err);
                alert("삭제 실패");
            });
        }//end if
    }//end del function 
</script>

</body>
</html>