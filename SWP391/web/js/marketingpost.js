var editor = CKEDITOR.replace('post-detail', {
    filebrowserBrowseUrl: '../ckfinder/ckfinder.html',
    filebrowserImageBrowseUrl: '../ckfinder/ckfinder.html?type=Images',
    filebrowserFlashBrowseUrl: '../ckfinder/ckfinder.html?type=Flash',
    filebrowserUploadUrl: '../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
    filebrowserImageUploadUrl: '../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
    filebrowserFlashUploadUrl: '../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
});

editor.config.width = '90%';
editor.config.height = 500;
editor.config.autoGrow_bottomSpace = 500;
editor.config.readOnly = true;

document.querySelector('#upload-thumbnail').addEventListener('click', function () {
    document.querySelector('#change-thumbnail-frm input[type="file"]').click();
});


document.querySelector('#preview-thumbnail').querySelector('button[aria-label="close"]').addEventListener('click', function () {
    document.querySelector('#change-thumbnail-frm input[type="file"]').value = null;
});
document.querySelector('#preview-thumbnail').querySelector('button[aria-label="save"]').addEventListener('click', function () {
    var form = document.getElementById('change-thumbnail-frm');
    var formData = new FormData(form);
    $.post({
        url: 'postdetails',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
            document.querySelector('#preview-thumbnail').querySelector('button[aria-label="close"]').click();
            document.querySelector('#change-thumbnail-frm input[type="file"]').value = null;
            document.querySelector('img[aria-label="thumbnail"]').setAttribute('src', "../" + response);
            document.querySelector('#thumbnail-view').setAttribute('src', "../" + response);
        }
    });
});

document.querySelector('button[aria-label="cancel-edit"]').addEventListener('click', function () {
    cancelEdit();
    returnSubcate();
});
document.querySelector('button[aria-label="save-post"]').addEventListener('click', function () {
    var form = document.getElementById('frm');
    form.submit();
});

function updateThumbnail(event) {
    var file = event.target.files[0];

    var fileReader = new FileReader();

    fileReader.onload = function () {
        var url = fileReader.result;
        document.getElementById('preview-thumbnail-image').src = url;
    };

    fileReader.readAsDataURL(file);
    document.getElementById('abc').click();


}
function editPostInformation() {
    document.querySelectorAll('#frm [aria-label="changable-fields"]').forEach(input => {
        input.disabled = false;
    });
    document.querySelector('button[aria-label="save-post"]').style.display = "";
    document.querySelector('button[aria-label="cancel-edit"]').style.display = "";
    document.querySelector('button[aria-label="edit-post"]').style.display = "none";
    CKEDITOR.instances['post-detail'].setReadOnly(false);
}

function thumbnailHover() {
    var a = document.querySelector('.thumbnail-action');
    a.style.opacity = 1;
}
function thumbnailOut() {
    var a = document.querySelector('.thumbnail-action');
    a.style.opacity = 0;
}
function cancelEdit() {
    document.getElementById('post-title').value = document.getElementById('preview-title').innerHTML;
    document.getElementById('post-subtitle').value = document.getElementById('preview-subtitle').innerHTML;
    var cateId = document.querySelector('#preview-cate').getAttribute('value-holder');
    var subCateId = document.querySelector('#preview-sub-cate').getAttribute('value-holder');
    document.querySelectorAll('#post-cate-select option').forEach(option => {
        if (option.value === cateId) {
            option.selected = true;
        }
    });
    var featured = document.getElementById('preview-featured').getAttribute('value-holder');
    if (featured === "true") {
        document.getElementById('product-feature').checked = true;
    } else {
        document.getElementById('product-feature').checked = false;
    }

    document.getElementById('post-detail').value = "a";
    document.querySelectorAll('#frm [aria-label="changable-fields"]').forEach(input => {
        input.disabled = true;
    });
    document.querySelector('button[aria-label="save-post"]').style.display = "none";
    document.querySelector('button[aria-label="cancel-edit"]').style.display = "none";
    document.querySelector('button[aria-label="edit-post"]').style.display = "";
    CKEDITOR.instances['post-detail'].setReadOnly(true);
    var originalContent = document.getElementById('preview-post-detail');
    
    CKEDITOR.instances['post-detail'].setData(originalContent.innerHTML);
//    returnSubcate(subCateId);
}
function changeCategory() {
    var selected = document.getElementById('post-cate-select').querySelector('option:checked').value;
    var div = document.getElementById('post-sub-cate-select');
    $.ajax({
        url: 'changepostcategory',
        type: 'post',
        data: {
            cid: selected
        },
        success: function (response) {
            console.log(response);
            div.innerHTML = response;
        }
    });
}
function returnSubcate() {
    document.querySelector('#post-sub-cate-select').innerHTML = document.getElementById('original-subcate').innerHTML;
}