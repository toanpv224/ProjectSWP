
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


function Paging(numpage){
        SubmitForm(numpage);
}

function SubmitForm(numpage){
//    document.getElementById('post_list').submit();
    var category_raw=document.getElementById("category");
        $.ajax({
    "type": "post",
    // generate the request url from the query.
    // escape the query string to avoid errors caused by special characters 
    "url": "/swp/searchsliderlist",
    "data": {
             search:document.getElementById("search").value,
             option_search:$('#option_search').children("option:selected").val(),
             feature:document.getElementById("feature_input").value,
             page:numpage
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
    "url": "/swp/updatefeatureslider",
    "data": {
              slider:id,
             status:status
            },
            
    "success": function(data) {
//        console.log(data);
        
    },
    "error": function(errorData) {
        console.log("lookup ajax error");
        console.log(errorData);
    }
});
}
