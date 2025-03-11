function updateCharCount(element) {
	if (element.value.length > 200) {
		element.value = element.value.slice(0, 200);
	}
	document.getElementById('charCount').textContent = element.value.length;
}

function requestPay() {
	const totalCountText = document.getElementById('final-total').textContent;
	const totalCount = totalCountText.replace('원', '');
	const name = document.getElementById('nameInput').value;
	const phoneNumber = document.getElementById('phoneNumberInput').value;
	const requestDetail = document.getElementById('requestDetailInput').value;
	const storeName = document.getElementsByClassName('store-name')[0].textContent;
	var merchantUid = 'merchant_' + new Date().getTime();


	if (name === "" || phoneNumber === "") {
		alert("픽업정보를 입력해 주세요");
		return;
	}

	$.ajax({
		type: "POST",
		url: "/api/order/accountCheck",
		data: {
			'totalCount': totalCount
		},
		success: function (res) {
			if (res) {
				$.ajax({
					type: 'POST',
					url: '/api/order/prepare',
					data: {
						'merchantUid': merchantUid,
						'amount': totalCount
					},
					success: function (res) {
						if (res) {
							payment();
						}
					},
					error: function (err) {
						return;
					}
				});
			}
		},
		error: function (err) {
			return;
		}
	});

	function payment() {

		var payment = {
			recipientsName: name,
			recipientsPhoneNum: phoneNumber,
			requestContent: requestDetail,
			account: totalCount,
			status: '결제완료'
		};

		IMP.init('imp40758275');
		IMP.request_pay({
			pg: "html5_inicis",
			pay_method: "card",
			merchant_uid: merchantUid,
			name: storeName,
			amount: totalCount,
			buyer_name: name,
			buyer_tel: phoneNumber,
		},
			function (rsp) {
				if (rsp.success) {
					$.ajax({
						type: 'POST',
						url: '/api/order/validation/' + rsp.imp_uid,
						data: JSON.stringify(payment),
						contentType: "application/json"
					}).done(function (data) {
						if (data != "" && payment.account == data.response.amount) {
							payment.impUid = rsp.imp_uid;
							payment.merchantUid = rsp.merchant_uid;

							$.ajax({
								url: '/api/order/success',
								method: 'POST',
								data: JSON.stringify(payment),
								contentType: "application/json"
							}).then(function (res) {
								if (res === '주문정보가 성공적으로 저장되었습니다.') {
									window.location.href = '/order/complete';
								}
							}).catch(function (err) {
								alert("결제 오류로 취소되었습니다.");
								return;
							});
						} else {
							alert("결제 오류로 취소되었습니다.");
							return;
						}
					}).catch(function (err) {
						alert("결제에 실패했습니다.");
					})
				}
			}
		);
	}
}

