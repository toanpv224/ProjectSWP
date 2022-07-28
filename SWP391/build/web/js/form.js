/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//document.getElementById('radioApple').click();
function buy(id){
    var quantity=document.f.num.value;
    document.f.action="buy?id="+id+"&num="+quantity;
    document.f.submit();
}
function checkPayment(value){
    var list=document.getElementsByClassName('payment-content');
        for (var i = 0; i < list.length; i++) {
        list[i].style.display='none';
    }
       
    if(value=='1'){
        document.getElementsByClassName('ship_cod')[0].style.display='block';
    }else if(value=='2'){
     document.getElementsByClassName('banking_system')[0].style.display='block';
    }else{
      document.getElementsByClassName('vnpay')[0].style.display='block';  
    }
        
}

function ChoosePayment(){
    $('#pay-ment').submit();
}


