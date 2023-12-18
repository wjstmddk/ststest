function msgPrint(){
	if(m!=''){
	alert(m);
	}
}

function loginStatus(){
	if(mb){
		let loginName=m_name; //로그아웃시 mb속성 삭제할것.
		$('#m_name').html(loginName+' 님');
		$('.suc').css('display','block');  //.show()
		$('.bef').css('display','none');   //.hide() 
	}else{  //로그인 없이 게시글 리스트 요청시
		$('.suc').css('display','none');
		$('.bef').css('display','block');
	}
}

