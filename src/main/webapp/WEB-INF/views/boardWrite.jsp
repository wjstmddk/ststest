 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardWrite</title>
<script src="/js/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="/css/style.css">
<!-- summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css"
	rel="stylesheet">
<script	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
<!-- 한글패치 -->
<script	src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>



<script>
//로그인 실패 메세지
	let m='${msg}';
	if(m!=''){
		alert(m)
	}
$(()=>{
	$('#summernote').summernote({
		//toolbar: false,
		height : 300, // 에디터 높이
		minHeight : null, // 최소 높이
		maxHeight : null, // 최대 높이
		focus : true, // 에디터 로딩후 포커스를 맞출지 여부
		lang : "ko-KR", // 한글 설정
		placeholder : '최대 2048자까지 쓸 수 있습니다' //placeholder 설정
	});
	
	
	let loginName='${mb.m_name}';
	$('#m_name').html(loginName+' 님');
	$('.suc').css('display','block');
	$('.bef').css('display','none');
		
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
            <form action="/board/write" class="write-form" 
            method="post" enctype="multipart/form-data">
            
                <div class="user-info">
                    <div class="user-info-sub">
                        <p>등급 [${mb.g_name}]</p>
                        <p>POINT [${mb.m_point}]</p>
                    </div>
                </div>
                <h2 class="login-header">글쓰기</h2>
                <!-- 로그인한 id(숨김), 제목, 내용, 파일 -->
                <input type="hidden" name="b_writer" value="${mb.m_id}">
                <input type="text" name="b_title" class="write-input" 
                       autofocus placeholder="제목" required>
<!--                 <textarea rows="15" name="b_contents" -->
<!--                           placeholder="내용을 적어주세요." -->
<!--                           class="write-input ta"></textarea> -->
                     <textarea id="summernote" name="b_contents"></textarea>     
                          
                <div class="filebox">
                <!--  종류가 다른 첨부파일이 있다면 name속성이 다른 file태그를 여러개 생성할것. -->
                    <label for="attachments">업로드</label>
                    <input type="file" name="attachments" id="attachments" multiple>
                    <input type="text" class="upload-name"
                           value="파일선택" readonly>
                </div>
                <div class="btn-area">
                    <input type="submit" class="btn-write" value="W">
                    <input type="reset" class="btn-write" value="R">
                    <input type="button" class="btn-write"
                           value="B" onclick="backbtn()">
                </div>
            </form>
        </div>
    </section>
    <footer>
        <jsp:include page="footer.jsp"></jsp:include>
    </footer>
</div>
<script>
	$('#attachments').on('change', function(){
		//파일 선택후 열기 버튼을 누르면 change이벤트 발생
		console.log($('#attachments'));
		
		//let files =document.getElementById('attachments').files;
		let files =$('#attachments')[0].files;
		console.log(files); //['둘리.jpg','aaa.jpg']
		
		let fileName='';
		if(files.length > 1 ){
			fileName=files[0].name +' 외 '+(files.length-1)+'개';
		}else if(files.length==1){
			fileName=files[0].name;
		}else{
			fileName="파일 선택";
		}
		$(".upload-name").val(fileName);
	});
	const backbtn=function(){
		let url="/board/list?";
		let col= '${sDto.colname}';
		let keyw='${sDto.keyword}';
		
		if(col==''){
			url+='pageNum=${sessionScope.pageNum}';
		}else{
			url+='colname=${sDto.colname}'+'&keyword=${sDto.keyword}'
			     +'&pageNum=${sDto.pageNum}' 
		}
		location.href= url;
	}
	</script></script>
</body>
</html>