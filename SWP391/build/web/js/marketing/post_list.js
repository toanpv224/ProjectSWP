//check sort option
function Checksort(e) {
    var sort = document.getElementsByClassName('sort-item');
    var current = document.getElementsByClassName(" sort-active");
    if (e.className === current[0].className && e.className.includes("fa-arrow-down-a-z")) {
        e.className = "sort-item fa-solid fa-arrow-up-a-z sort-active";
    } else if (e.className === current[0].className && e.className.includes("fa-arrow-up-a-z")) {
        e.className = "sort-item fa-solid fa-arrow-down-a-z sort-active";
    } else {
        current[0].className = current[0].className.replace(" sort-active", "");
        e.className += " sort-active";
    }
    $('#sort_input').val(e.getAttribute('value'));
    SubmitForm(1);
}

// check feature select
function CheckFeature(e) {

    var container = document.getElementById('list_category');
    var features = container.getElementsByClassName('shopee-tabs__nav-tab');
    for (var i = 0; i < features.length; i++) {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        e.className += " active";
    }
    document.getElementById('feature_input').value = e.getAttribute('value');
    SubmitForm(1);//start page 1
}
//increase page 1 unit
function Paging(numpage) {
    SubmitForm(numpage);
}
//select onchange  
function cat(numpage) {

    //remove option
    $('#sub_category')
            .find('option')
            .remove();
//    document.getElementById('post_list').submit();
//default
    $('#sub_category').append(`<option value="0">
                                       All
                                  </option>`);
    var catID = $('#category').children("option:selected").val();
    var list = document.getElementsByName('cid');
    for (var i = 0; i < list.length; i++) {
        var k = list[i].getAttribute('cat');
        if (k == catID) {
            //add option
            $('#sub_category').append(`<option value="${list[i].getAttribute('subcat')}">
                                       ${list[i].value}
                                  </option>`);
        }
    }
    SubmitForm(numpage);
}
//submit form
function SubmitForm(numpage) {
      var category_raw=document.getElementById("category");
    var check=$('.sort-active')[0].className.includes('fa-arrow-up-a-z');
    var op;
   op=(check==true)?1:0;
    $.ajax({
        "type": "post",
        // generate the request url from the query.
        // escape the query string to avoid errors caused by special characters 
        "url": "/swp/searchpostlist",
        "data": {
            op: op,
            search: document.getElementById("search").value,
            category: $('#category').children("option:selected").val(),
            sub_category: $('#sub_category').children("option:selected").val(),
            author: $('#author').children("option:selected").val(),
            feature: document.getElementById("feature_input").value,
            sort: $('#sort_input').val(),
            page: numpage
        },

        "success": function (data) {
//            console.log(data);
            $('#content tbody').empty();
            $('#content tbody').append(data);

        },
        "error": function (errorData) {
            console.log("lookup ajax error");
            console.log(errorData);
        }
    });
    submitpage(numpage);
}
function submitpage(numpage){
          var category_raw=document.getElementById("category");
    var check=$('.sort-active')[0].className.includes('fa-arrow-up-a-z');
    var op;
   op=(check==true)?1:0;
    $.ajax({
        "type": "get",
        // generate the request url from the query.
        // escape the query string to avoid errors caused by special characters 
        "url": "/swp/searchpostlist",
        "data": {
            op: op,
            search: document.getElementById("search").value,
            category: $('#category').children("option:selected").val(),
            sub_category: $('#sub_category').children("option:selected").val(),
            author: $('#author').children("option:selected").val(),
            feature: document.getElementById("feature_input").value,
            sort: $('#sort_input').val(),
            page: numpage
        },

        "success": function (data) {
            console.log(data);
           $("#footer").empty();
           $("#footer").append(data);

        },
        "error": function (errorData) {
            console.log("lookup ajax error");
            console.log(errorData);
        }
    });
}

// change featured
function ChangeFeature(postid, featured) {
    console.log(postid);
    console.log(featured);
    $.ajax({
        "type": "post",
        // generate the request url from the query.
        // escape the query string to avoid errors caused by special characters 
        "url": "/swp/updatefeaturepost",
        "data": {
            postid: postid,
            featured: featured
        },

        "success": function (data) {
            console.log(data);
//            alert(data);

        },
        "error": function (errorData) {
            console.log("lookup ajax error");
            console.log(errorData);
        }
    });
}

//choose filse
function chooseFile(fileInput) {
    if (fileInput.files && fileInput.files[0]) {
        console.log("123");
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#thumbnail-create').attr('src', e.target.result);
        }
        reader.readAsDataURL(fileInput.files[0]);
    }
}
function closeCategoryForm() {
    var x = document.getElementById('category-form');
    x.style.display = 'none';

}
function checkCategoryOption() {
    var x = $('#category-select').children("option:selected").val();
    if (x == 0) {//add more
        openCategoryForm();
    }
    //remove option
    $('#sub_category')
            .find('option')
            .remove();
    //    document.getElementById('post_list').submit();
//default
    $('#sub_category').append(`<option selected disabled>
                                       Select
                                  </option>`);
    var catID = $('#category-select').children("option:selected").val();
    var list = document.getElementsByName('cid');
    if (catID == 1) {
        $('#sub_category').append(`<option value="${1}">
                                       Review 
                                  </option>`);
        $('#sub_category').append(`<option value="${2}">
                                       Event
                                  </option>`);
    }
    if (catID == 2) {
        $('#sub_category').append(`<option value="${3}">
                                       Event  
                                  </option>`);
        $('#sub_category').append(`<option value="${4}">
                                       Stories
                                  </option>`);
    }
}
function openCategoryForm() {
    var x = document.getElementById('category-form');
    x.style.display = 'block';

    const text = 'Select';
    const $select = document.querySelector('#category-select');
    const $options = Array.from($select.options);
    const optionToSelect = $options.find(item => item.text === text);
    optionToSelect.selected = true;

}
//check input name-category
function CheckCategoryName(e) {
    const select = document.querySelector('#category-select');
    const options = Array.from(select.options);
    for (var i = 0; i < options.length; i++) {
        if (options[i].text === e.value && options[i].text !== 'Select' && options[i] !== 'Add More') {
            document.getElementById('category-error').style.display = 'block';
            document.getElementById('submit-category').disabled = true;
            return true;
        } else {
            document.getElementById('category-error').style.display = 'none';
            document.getElementById('submit-category').disabled = false;
        }
    }
}
function SubmitCategoryForm() {
    var name_form = document.getElementById('name').value;
    var description_form = document.getElementById('description').value;
//     document.getElementById('name-form').value=name.value;
//     document.getElementById('description-form').value=description.value;

    var form = document.forms['category-form_add'];

    $.ajax({
        "type": "post",
        // generate the request url from the query.
        // escape the query string to avoid errors caused by special characters 
        "url": "/swp/addpostcategory",
        "data": {
            name: name_form,
            description: description_form
        },

        "success": function (data) {
            console.log(data);
//            alert('successfully');
            closeCategoryForm();
            $('#category-content').empty().append(data);
            document.getElementById('name').value = '';
            document.getElementById('description').value = '';
        },
        "error": function (errorData) {
            console.log("lookup ajax error");
            console.log(errorData);
        }
    });
}





