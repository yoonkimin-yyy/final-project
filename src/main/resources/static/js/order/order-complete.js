document.querySelector('.order-info').style.display = 'none';
document.querySelector('.order-details').style.display = 'none';

function handleOrderStatus() {

    let intervalId = setInterval(function() {
        $.ajax({
            type: 'GET',
            url: '/api/order/pickupCheck',
            })
			.then(function (res) {
			    if (res.list) {
			        displayOrderDetails(res.result, res.list);
					clearInterval(intervalId);
			    } else if (res.result.pickupStatus === '거절') {
			        displayRefundDetails(res.result);
					clearInterval(intervalId);
			    }
			})
			.catch(function (err) {
			    console.log('에러:', err);
			});	
    }, 4000); 
}

function getCurrentTime() {
    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');  
    const day = now.getDate().toString().padStart(2, '0');
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

function displayOrderDetails(orderDataResult, orderDataList) {
	const orderNum = document.getElementById('orderNum');
	const orderDateElement = document.getElementById('orderDate');
	const circle = document.querySelector('.check-circle');
    const icon = circle.querySelector('i');
    const status = document.querySelector('.order-status');
	const currentTime = getCurrentTime(); 
   
	orderNum.textContent = orderDataResult.pickupStatusNo;
	orderDateElement.textContent = currentTime;

    document.querySelector('.order-info').style.display = 'flex';
    document.querySelector('.order-details').style.display = 'block';
	
	circle.classList.add('accepted');
    icon.className = 'fas fa-check';
    status.textContent = '사장님이 주문을 수락했습니다!';
    status.style.color = '#4CAF50';
    status.classList.add('animation-stopped');

    const orderItemsContainer = document.getElementById('orderItems');
    let totalAmount = 0;

    orderDataList.forEach(item => {
        const row = document.createElement('tr');
        const itemTotal = item.menuPrice * item.menuCount;
        totalAmount += itemTotal;
                    //<img src="${item.image}" class="product-image" alt="${item.image}">
        row.innerHTML = `
            <td>
                <div class="product-info">
                    <div>${item.menuName}</div>
                </div>
            </td>
            <td style="text-align: right;">${item.menuPrice + '원'}</td>
            <td style="text-align: center;">${item.menuCount + '개'}</td>
            <td style="text-align: right;">${(item.menuCount * item.menuPrice) + '원'}</td>
        `;
        orderItemsContainer.appendChild(row);
    });

    const totalRow = document.createElement('tr');
    totalRow.className = 'total-row';
    totalRow.innerHTML = `
        <td colspan="3" style="text-align: right; padding-right: 30px;">총 결제금액</td>
        <td style="text-align: right;" class="total-amount">${totalAmount + '원'}</td>
    `;
    orderItemsContainer.appendChild(totalRow);
	
}

function displayRefundDetails(orderDataResult) {
    const circle = document.querySelector('.check-circle');
    const icon = circle.querySelector('i');
    const status = document.querySelector('.order-status');
    const title = document.querySelector('.complete-title');
    const storeMessage = document.querySelector('.store-message');
	
	storeMessage.textContent = orderDataResult.refusalDetail;

    circle.classList.add('rejected');
    icon.className = 'fas fa-times';
    status.textContent = '주문이 취소되었습니다';
    status.style.color = '#FF5252';
    status.classList.add('animation-stopped');
    title.textContent = '환불 신청이 완료되었습니다';

    storeMessage.style.display = 'block';

    updateOrderTable(false);
}

handleOrderStatus();
