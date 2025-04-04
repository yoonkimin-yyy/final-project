function submitAnswer(inquiryNo, btn) {
  const textarea = btn.previousElementSibling;
  const answer = textarea.value.trim();
  if (!answer) return alert('답변 내용을 입력해주세요.');

  alert('답변이 저장되었습니다.');
  btn.textContent = '저장 완료';
  btn.disabled = true;
}

function handleSearch() {
  const type = document.getElementById('searchType').value;
  const keyword = document.getElementById('searchInput').value.toLowerCase();

  const items = document.querySelectorAll('.admin-inquiry-list-item');

  items.forEach(item => {
    const title = item.querySelector('.admin-inquiry-item-title').textContent.toLowerCase();
    const name = item.querySelector('.admin-inquiry-item-meta span').textContent.split('|')[0].trim().toLowerCase();

    let match = false;
    if (type === 'title') {
      match = title.includes(keyword);
    } else if (type === 'name') {
      match = name.includes(keyword);
    } else {
      match = title.includes(keyword) || name.includes(keyword);
    }

    item.style.display = match ? 'block' : 'none';
  });
}

document.getElementById('searchInput').addEventListener('input', handleSearch);
document.getElementById('searchType').addEventListener('change', handleSearch);








