const orderItems = [
    { name: "크로와상", price: 4500, quantity: 1, image: "https://api.a0.dev/assets/image?text=delicious%20croissant%20bread&aspect=1:1" },
    { name: "초코 브레드", price: 5500, quantity: 1, image: "https://api.a0.dev/assets/image?text=chocolate%20bread%20with%20chips&aspect=1:1" },
    { name: "갈릭브레드", price: 4800, quantity: 1, image: "https://api.a0.dev/assets/image?text=fresh%20baked%20garlic%20bread&aspect=1:1" },
    { name: "단팥빵", price: 3500, quantity: 1, image: "https://api.a0.dev/assets/image?text=sweet%20red%20bean%20bread&aspect=1:1" },
    { name: "크림치즈빵", price: 5000, quantity: 1, image: "https://api.a0.dev/assets/image?text=cream%20cheese%20bread&aspect=1:1" },
    { name: "고구마빵", price: 4200, quantity: 1, image: "https://api.a0.dev/assets/image?text=sweet%20potato%20bread&aspect=1:1" },
    { name: "우유식빵", price: 4800, quantity: 1, image: "https://api.a0.dev/assets/image?text=fresh%20milk%20bread&aspect=1:1" }
];

// 주문일시 표시
document.getElementById('orderDate').textContent = new Date().toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
});

// 주문 상품 목록 생성
const orderItemsContainer = document.getElementById('orderItems');
let totalAmount = 0;

orderItems.forEach(item => {
    const row = document.createElement('tr');
    const itemTotal = item.price * item.quantity;
    totalAmount += itemTotal;

    row.innerHTML = `
        <td>
            <div class="product-info">
                <img src="${item.image}" class="product-image" alt="${item.name}">
                <div>${item.name}</div>
            </div>
        </td>
        <td style="text-align: right;">₩${item.price.toLocaleString()}</td>
        <td style="text-align: center;">${item.quantity}</td>
        <td style="text-align: right;">₩${itemTotal.toLocaleString()}</td>
    `;
    orderItemsContainer.appendChild(row);
});

// 총 금액 행 추가
const totalRow = document.createElement('tr');
totalRow.className = 'total-row';
totalRow.innerHTML = `
    <td colspan="3" style="text-align: right; padding-right: 30px;">총 결제금액</td>
    <td style="text-align: right;" class="total-amount">₩${totalAmount.toLocaleString()}</td>
`;
orderItemsContainer.appendChild(totalRow);

// 페이지 로드시 주문 상세 내역을 숨깁니다
document.querySelector('.order-info').style.display = 'none';
document.querySelector('.order-details').style.display = 'none';

function handleAccept() {
    const circle = document.querySelector('.check-circle');
    const icon = circle.querySelector('i');
    const status = document.querySelector('.order-status');
    
    // 주문 상세 내역을 보이게 합니다
    document.querySelector('.order-info').style.display = 'flex';
    document.querySelector('.order-details').style.display = 'block';
    
    circle.classList.add('accepted');
    icon.className = 'fas fa-check';
    status.textContent = '사장님이 주문을 수락했습니다!';
    status.style.color = '#4CAF50';
    status.classList.add('animation-stopped');
}

function handleReject() {
    const circle = document.querySelector('.check-circle');
    const icon = circle.querySelector('i');
    const status = document.querySelector('.order-status');
    const title = document.querySelector('.complete-title');
    const storeMessage = document.querySelector('.store-message');
    
    // 주문 상세 내역을 보이게 합니다
    document.querySelector('.order-info').style.display = 'flex';
    document.querySelector('.order-details').style.display = 'block';
    
    circle.classList.add('rejected');
    icon.className = 'fas fa-times';
    status.textContent = '주문이 취소되었습니다';
    status.style.color = '#FF5252';
    status.classList.add('animation-stopped');
    title.textContent = '환불 신청이 완료되었습니다';
    
    // 가게 메시지 표시
    storeMessage.style.display = 'block';
    
    // 가격을 0원으로 표시하도록 테이블 업데이트
    updateOrderTable(false);
}

function updateOrderTable(isAccepted) {
    const orderItemsContainer = document.getElementById('orderItems');
    const orderNumberElement = document.querySelector('.order-info-item:first-child .info-value');
    const orderDateElement = document.querySelector('.order-info-item:last-child .info-value');
    
    // 주문번호와 주문일시를 하이폰으로 변경
    orderNumberElement.textContent = '-';
    orderDateElement.textContent = '-';
    
    // 초기화: 기존 상품 목록 지우기
    orderItemsContainer.innerHTML = '';

    // 취소된 주문은 한 줄로만 표시
    const row = document.createElement('tr');
    row.innerHTML = `
        <td>-</td>
        <td style="text-align: right;">-</td>
        <td style="text-align: center;">-</td>
        <td style="text-align: right;">-</td>
    `;
    orderItemsContainer.appendChild(row);

    // 총 결제금액 행 추가
    const totalRow = document.createElement('tr');
    totalRow.className = 'total-row';
    totalRow.innerHTML = `
        <td colspan="3" style="text-align: right; padding-right: 30px;">총 결제금액</td>
        <td style="text-align: right;" class="total-amount">₩0</td>
    `;
    orderItemsContainer.appendChild(totalRow);
}