function openFilterOverlay() {
    document.getElementById('modal-filer').style = "display: block;";
}

function closeModalFilter() {
    document.getElementById('modal-filer').style = "";
}

function openModalSearch() {
    document.getElementById('modal-search').style = "display: block;";
}

function closeModalSearch() {
    document.getElementById('modal-search').style = "";
}

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

function showProductList() {
    document.getElementById('product-list').style = "opacity: 1";
    $.ajax({
        url: 'getproductslist',
        type: 'post',
        data: {
            key: ""
        },
        success: function (response) {
            document.getElementById('product-list').innerHTML = response;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
        }
    });
}

function hideProductList() {
    document.getElementById('product-list').style = "opacity: 0";
}