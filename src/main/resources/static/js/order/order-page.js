function updateCharCount(element) {
    if (element.value.length > 200) {
        element.value = element.value.slice(0, 200);
    }
    document.getElementById('charCount').textContent = element.value.length;
}