function updateCharCount(element) {
    if (element.value.length > 200) {
        element.value = element.value.slice(0, 200);
    }
    document.getElementById('charCount').textContent = element.value.length;
	
}

function requestPay() {
	// 가격 가져오고 replace로 '원' 제거
	const totalCountText = document.getElementById('final-total').textContent;
	const totalCount = totalCountText.replace('원', '');
	const name = document.getElementById('nameInput').value;
	const phoneNumber = document.getElementById('phoneNumberInput').value;
	const requestDetail = document.getElementById('requestDetailInput').value;
	var merchant_uid = 'merchant_'+new Date().getTime(); // 고유한 주문번호 생성 
	// const storeName = document.getElementsByClassName("store-name");
	
    // 이름 전화번호 필수 입력
    /*if (name === "" || phoneNumber === "") {
        alert("픽업정보를 입력해 주세요");
		return;
	}*/
	
	// ajax로 스크립트조작 사전 검증	
	$.ajax({
		url : '/order/accountCheck',
		type : 'POST',
		data : {
			'totalCount': totalCount
		},
		success: function(res) {
			if (res === true) {
				IMP.init('imp40758275'); // 가맹점 식별코드
				IMP.request_pay({
					//pg : "html5_inicis", // pg사 kg이니시스
					pay_method: "card", // 결제방식 card 
				    merchant_uid : merchant_uid, // 주문번호 
				    name : '결제테스트', // storeName
				    amount : totalCount, // totalCount
				    buyer_name : name, // 픽업하는 사람
				    buyer_tel : phoneNumber, // 연락처
				    // buyer_email : 'iamport@siot.do', 이메일 필요없음 완성되면 삭제 
				    // buyer_addr : '서울특별시 강남구 삼성동', 주소 필요없음 완성되면 삭제
				    // buyer_postcode : '123-456' 우편번호 필요없음 완성되면 삭제
				}, 
				function (res) { 
					console.log(res);
					// 결제 결과를 처리하는 로직을 작성
					// 결제 완료 페이지 띄우고 db에 저장 
					if (res) {
						console.log('결제결제결제결제결제결제결제')
						/*fetch('/order/complete', {
							method : 'POST',
							headers : {
								'Content-Type' : 'application/json'
							},
							body : JSON.stringify({
								merchantUid : merchant_uid,
								name : '결제테스트',
								account : 100,
								recipientsName : name,
								recipientsPhoneNum : phoneNumber,
							})
						}) 
						.then(response => response.json())
						.then(data => {
							console.log('성공성공성공성공성공성공', data);
							
							location.href="/order/complete";
						})
						.catch(error => {
							console.log('실패실패실패실패실패실패', error);
						})*/
						// 사후 검증 필요??
					} else {
						// 결제 실패 
						// 결제 실패 시??? 그 딴건 존재하지 않아
						alert('해킹하지말아주세요ㅠㅠㅠㅠㅠ')
				        console.log("실패실패실패실패실패실패실패실패");
						console.log(merchant_uid);
						console.log(typeof totalCount);
			     	}
				}); // IMP.request_pay
			} else {	
          	// 서버에서 사전 검증 실패 시 처리
            	console.log('누구인가 스크립트 조작');
        	}
	    },
		error: function(error) {
	        // AJAX 요청이 실패했을 때 처리
			alert('서버와의 연결에 실패했습니다. 잠시 후 다시 시도해 주세요.');
		}
	}); // $.ajax
	
} // requestPay()


