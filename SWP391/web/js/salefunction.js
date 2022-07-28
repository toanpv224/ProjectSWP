function submitFormDefault() {
    document.getElementById('submit-frm').submit();
}
function submitFormWithNewOrderOption(item) {
    document.getElementById('orderOption').value = item.value;
    document.getElementById('submit-frm').submit();
}
function submitFormWithSequence(item) {
    document.getElementById('sequence').value = item.value;
    document.getElementById('submit-frm').submit();
}
function submitFormWithNewPage(page) {
    document.getElementById('pageNumber').value = page;
    document.getElementById('submit-frm').submit();
}
function showSale() {
        document.getElementById('sale-list').style.height = "100%";
        document.getElementById('more-sale-container').style.display="none";
        document.getElementById('no-orders').style.height ="1160px";

}
function checkEndDateInput(item) {


    var now = new Date();
    var endDate = new Date(item.value);

    var today = "";
    var year = now.getFullYear();
    today += year + "-";
    var month = now.getMonth() + 1;
    if (month < 10) {
        today += "0" + month + "-";
    } else {
        today += month + "-";
    }
    var date = now.getDate();
    if (date < 10) {
        today += "0" + date;
    } else {
        today += date;
    }

    if (endDate.getTime() > now.getTime()) {
        alert("End date can not greater than now");
        item.value = today;
    }
}

document.querySelector('#edit-sale-order button[aria-label="close"]').addEventListener('click', function(){
    document.getElementById('newNote').value = document.getElementById('sale-note-origin').innerHTML;
    document.querySelectorAll('#newStatus option').forEach(option => {
       if(option.value === document.getElementById('newStatus').getAttribute('value-holder')){
           option.selected = true;
       } 
    });
});
document.querySelector('#edit-sale-order button[aria-label="save"]').addEventListener('click', function(){
    document.getElementById('edit-order-frm').submit();
});
document.querySelector('#assign-sale button[aria-label="close"]').addEventListener('click', function(){
    document.getElementById('newSaleId').value = document.getElementById('sale-name-origin').getAttribute('data-bind');
    document.getElementById('btn-sale-name').innerHTML = document.getElementById('sale-name-origin').getAttribute('data-bind2');
});
document.querySelector('#assign-sale button[aria-label="save"]').addEventListener('click', function(){
    document.getElementById('assign-frm').submit();
});
function filterFunction() {
  var input, filter, a, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  div = document.getElementById("myDropdown");
  a = div.getElementsByTagName("a");
  for (i = 0; i < a.length; i++) {
    txtValue = a[i].textContent || a[i].innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      a[i].style.display = "";
    } else {
      a[i].style.display = "none";
    }
  }
}
function changeValue(item){
    document.getElementById('newSaleId').value = item.getAttribute('data-bind');
    document.getElementById('btn-sale-name').innerHTML = item.innerHTML;
}