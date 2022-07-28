function clearCheckboxRatedStar() {
    document.querySelectorAll('input[name="rated_star"]').forEach(i => {
        i.checked = false;
    });
    document.querySelector('input[name="rated_star"]').checked = true;
}

function clearCheckboxRatedStarAll() {
    document.querySelector('input[name="rated_star"]').checked = false;
}

function showProductOption() {
    document.getElementById('sub-product-select').style = "opacity: 1";
}

function hideProductOption() {
    document.getElementById('sub-product-select').style = "opacity: 0";
}
function nextPage(page){
    document.querySelector('input[name="page"').value = page;
    document.getElementById('feedbackSearchForm').submit();
}
