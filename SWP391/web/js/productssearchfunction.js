/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function searchProduct(){
    var key = document.getElementById('productSeachKey').value;
    var category = document.getElementsByClassName('form-check-input');
    var action = "productslist?key=";
    action += key;
    for (var i = 0; i < category.length; i++) {
        if (category[i].checked) {

            action += "&category=" + category[i].value;
        }
    }
    window.location.replace(action);
}
function searchProductByChangeOrderOption(){
    document.getElementById('order-option').value = document.getElementById('order-by').value;
    document.getElementById('productSearchForm').submit();
}
function nextProductPage(pageNumber){
    document.getElementById('page').value = pageNumber;
    document.getElementById('productSearchForm').submit();
}
function autoCollapse(){
    document.getElementById('category-active').click();
}