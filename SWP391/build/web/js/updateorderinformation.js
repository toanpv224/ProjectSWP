function update() {
    var form = document.getElementById('frm');
    form.action = "showcart";
    form.method = "post";
    form.submit();
}
function cancel() {
    var form = document.getElementById('frm');
    form.action = "updateorder";
    form.method = "post";
    form.submit();
}
function reBuy(id, quantity, unitInStock){
    if(quantity > unitInStock){
        document.getElementById('rebuy-message'+id).innerHTML = 'OUT OF STOCKS';
    } else {
        var frm = document.getElementById('frm1');
        frm.querySelector('#rebuy-product-id').value = id;
        frm.querySelector('#rebuy-product-quantity').value = quantity;
        frm.submit();
    }
}