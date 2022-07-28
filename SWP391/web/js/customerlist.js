
// check feature select
function CheckFeature(e){
    var container=document.getElementById('list_category');
    var features=container.getElementsByClassName('shopee-tabs__nav-tab');
    for (var i = 0; i < features.length ; i++) {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        e.className += " active";
    }
    document.getElementById('feature_input').value=e.getAttribute('value');
    SubmitForm(1);//start page 1
}
function Checksort(e){
     var sort=document.getElementsByClassName('sort-item');
    var current = document.getElementsByClassName(" sort-active");
    if(e.className===current[0].className &&e.className.includes("fa-arrow-down-a-z")){
        e.className="sort-item fa-solid fa-arrow-up-a-z sort-active";
    }else if(e.className===current[0].className &&e.className.includes("fa-arrow-up-a-z")){
        e.className="sort-item fa-solid fa-arrow-down-a-z sort-active";
    }else{
        current[0].className = current[0].className.replace(" sort-active", "");
            e.className += " sort-active";
    }
    $('#sort_input').val(e.getAttribute('value'));
    SubmitForm(1);
}


function Paging(numpage){
        SubmitForm(numpage);
}

function SubmitForm(numpage){
//    document.getElementById('post_list').submit();
    var category_raw=document.getElementById("category");
    var check=$('.sort-active')[0].className.includes('fa-arrow-up-a-z');
    var op;
   op=(check==true)?1:0;
   $.ajax({
    "type": "post",
    // generate the request url from the query.
    // escape the query string to avoid errors caused by special characters 
    "url": "/swp/marketing/searchcustomerlist",
    "data": {
             search:document.getElementById("search").value,
             option_search:$('#option_search').children("option:selected").val(),
             feature:document.getElementById("feature_input").value,
             page:numpage,
             orderID:$('#sort_input').val(),
             op
            },
            
    "success": function(data) {
//        console.log(data);
        $('#content').empty().append(data);
        
    },
    "error": function(errorData) {
        console.log("lookup ajax error");
        console.log(errorData);
    }
});
}

// change featured
    function ChangeFeature(id,status){
       
        $.ajax({
    "type": "post",
    // generate the request url from the query.
    // escape the query string to avoid errors caused by special characters 
    "url": "/swp/marketing/updatefeaturecustomer",
    "data": {
              user_id:id,
             status:status
            },
            
    "success": function(data) {
        console.log(id);
//        console.log(data);
        
    },
    "error": function(errorData) {
        console.log("lookup ajax error");
        console.log(errorData);
    }
});
}
 
//  change avatar

  function  ChangeAvt(data){
    var imageFile = data.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(imageFile);
    reader.onload = function(evt){
      $('#imagePreview').attr('src', evt.target.result);
      $('#imagePreview').hide();
      $('#imagePreview').fadeIn(650);
    }
   }




