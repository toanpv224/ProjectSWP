function Edit(){
    $("#normal"). css("display", "none");
    $("#edit"). css("display", "block");
    document.getElementById("mess").innerText='';
}
function Paging(numpage) {
    SubmitForm(numpage);
}
//submit form
function SubmitForm(numpage) {
    
   var id=document.getElementById("user_id").value;
   console.log(id);
    $.ajax({
        "type": "post",
        // generate the request url from the query.
        // escape the query string to avoid errors caused by special characters 
        "url": "/swp/marketing/searchcustomer",
        "data": {
           id:id,
           page: numpage
        },

        "success": function (data) {
//            console.log(data);
            $('#content tbody').empty();
            $('#content tbody').append(data);
         submitpage(numpage);
            
        },
        "error": function (errorData) {
            console.log("lookup ajax error");
            console.log(errorData);
        }
    });
}
function submitpage(numpage){
    var id=document.getElementById("user_id").value;
      $.ajax({
        "type": "get",
        // generate the request url from the query.
        // escape the query string to avoid errors caused by special characters 
        "url": "/swp/marketing/searchcustomer",
        "data": {
            id:id,
           page: numpage
        },

        "success": function (data) {
//            console.log(data);
        $("#footer").empty();
           $("#footer").append(data);

        },
        "error": function (errorData) {
            console.log("lookup ajax error");
            console.log(errorData);
        }
    });
}
function update(){
    if(validation()){
        
     var id=document.getElementById("user_id").value;
     var name=document.getElementById("name").value;
     var gender=$('#gender').children("option:selected").val();
     var phone=document.getElementById("phone").value;
     var address=document.getElementById("address").value;
     var status=$('#status').children("option:selected").val();
        $.ajax({
        "type": "post",
        // generate the request url from the query.
        // escape the query string to avoid errors caused by special characters 
        "url": "/swp/marketing/updatecustomer",
        "data": {
            id:id,
           name:name,
           gender:gender,
           phone:phone,
           address:address,
           status:status
        },

        "success": function (data) {
    SubmitForm(1);
    
    document.getElementById("name-td").innerText=name;
    document.getElementById("gender-td").innerText=$('#gender').children("option:selected").text();
    document.getElementById("mobile-td").innerText=phone;
    document.getElementById("address-td").innerText=address;
    document.getElementById("status-td").innerText=$('#status').children("option:selected").text();
    document.getElementById("mess").innerText=data;
    $("#normal"). css("display", "block");
      $("#edit"). css("display", "none");
        },
        "error": function (errorData) {
            console.log("lookup ajax error");
            console.log(errorData);
        }
    }); 
   }
    
}

function CheckName(){
    var name =document.getElementById('name').value;
    if(name==''){
        $('#err-name').show();
        return false;
    }
    $('#err-name').hide();
    return true;
}
function CheckPhone(){
    var phone =document.getElementById('phone').value;
    if(phone==''){
        $('#err-phone').show();
        return false;
    }
     $('#err-phone').hide();
    return true;
}
function CheckAddress(){
    var address =document.getElementById('address').value;
    if(address.trim()==''){
         $('#err-address').show();
        return false;
    }
   $('#err-address').hide();
    return true;
}
function validation(){
    var check=true;
    if(!CheckName()){
       check= false;
    }
    if(!CheckAddress()){
        check=false;
    }
    if(!CheckPhone()){
        check=false;
    }
    return check;
}
