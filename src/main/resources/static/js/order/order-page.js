function updateCharCount(element) {
	if (element.value.length > 200) {
        element.value = element.value.slice(0, 200);
    }
    document.getElementById('charCount').textContent = element.value.length;
	
}

// 결제하기 버튼	
function requestPay() {
	const totalCountText = document.getElementById('final-total').textContent;
	const totalCount = totalCountText.replace('원', ''); // 가격 가져오고 replace로 '원' 제거
	const name = '홍길동' //document.getElementById('nameInput').value;
	const phoneNumber = '010-0000-0000' //document.getElementById('phoneNumberInput').value;
	const requestDetail = document.getElementById('requestDetailInput').value;
	const storeName = document.getElementsByClassName('store-name')[0].textContent;	
	var merchantUid = 'merchant_'+new Date().getTime(); // 고유한 주문번호 생성 
	
	// 이름 전화번호 필수 입력
    if (name === "" || phoneNumber === "") {
        alert("픽업정보를 입력해 주세요");
		return;
	}
		
	// 총가격 검증
	$.ajax({
		type : "POST",
		url : "/order/accountCheck",
		data : {
			'totalCount' : totalCount 
		},
		success: function(res) {
			if(res) {
				// 포트원 사전검증(고유번호, 총가격 넘기기)
				$.ajax({
					type : 'POST',
					url : '/order/prepare',
					data : {
						'merchantUid' : merchantUid,
						'amount' : totalCount
					},
					success: function(res) {
						if(res) {
							payment(); // 결제 호출
						} 
					},
					error: function(err) {
						return;
					}				
				}); // ajax()
			} // if
		}, // function(res)
		error: function(err) {
			return;
		}
	}); // ajax()
	
	// 결제창 
	function payment() {
		
		// PaymentDto
		var payment = {
			recipientsName : name,
			recipientsPhoneNum : phoneNumber,
			requestContent : requestDetail,
			account : totalCount,
			status : '결제완료'
		};
		
		IMP.init('imp40758275'); // 가맹점 식별코드
		IMP.request_pay({
			pg : "html5_inicis", // pg사 kg이니시스
			pay_method: "card", // 결제방식 card 
		    merchant_uid : merchantUid, // 주문번호 
		    name : storeName, // 가게이름
		    amount : totalCount, // 총가격
		    buyer_name : name, // 픽업하는 사람
		    buyer_tel : phoneNumber, // 연락처
		},
		function(rsp) {
			if(rsp.success) { 
				// DB저장 & 사후검증
				$.ajax({
					type : 'POST',
					url : '/order/validation/' + rsp.imp_uid,
					data : JSON.stringify(payment),
					contentType:"application/json"
				}).done(function(data) {
					if(data != "") {
						if(payment.account == data.response.amount) { // payment에 정보 추가
							payment.impUid = rsp.imp_uid;
							payment.merchantUid = rsp.merchant_uid;
							$.ajax({
								url : '/order/success',
								method : 'POST',
								data : JSON.stringify(payment),
								contentType:"application/json"
							}).then(function(res) {
								// 완료페이지이동 절대경로 controller거쳐서
								window.location.href = '/order/complete'; 
							}).catch(function(err) {
								alert("결제 오류로 취소되었습니다.");
								return;
							}); // ajax
						} // if(payment.account == data.response.amount)
					} else { // if(data != "")
						alert("결제 오류로 취소되었습니다.");
						return;
					}
				}).catch(function(err) {
					alert("결제에 실패했습니다.");
				}) 
			} // if(rsp.success)
		} // function(rsp)
		); // request_pay
	} // payment()
} // requestPay

