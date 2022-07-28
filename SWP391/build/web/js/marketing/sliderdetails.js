        
function showSlider(id) {
    if (confirm('Do you want to show this slider?')) {
        $.ajax({
            url: 'editslider',
            type: 'post',
            data: {
                action: "activate",
                slider_id: id
            },
            success: function (repsonse) {
                document.getElementById('sliderStatusSwitch').checked = true;
                document.getElementById('sliderStatusSwitch').setAttribute('onchange', "hideSlider(" + id + ")");

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                document.getElementById('sliderStatusSwitch').checked = false;
            }
        });
    } else {
        document.getElementById('sliderStatusSwitch').checked = false;
    }
}

function hideSlider(id) {
    if (confirm('Do you want to hide this slider?')) {
        $.ajax({
            url: 'editslider',
            type: 'post',
            data: {
                action: "disactivate",
                slider_id: id
            },
            success: function (repsonse) {
                document.getElementById('sliderStatusSwitch').checked = false;
                document.getElementById('sliderStatusSwitch').setAttribute('onchange', "showSlider(" + id + ")");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                document.getElementById('sliderStatusSwitch').checked = true;
            }
        });
    } else {
        document.getElementById('sliderStatusSwitch').checked = true;
    }
}

function editSliderInformation(id) {
    var form = document.getElementById('form-change-slider-information');
    $.ajax({
        url: 'editslider',
        type: 'post',
        data: {
            action: "change_information",
            slider_id: id,
            title: form.querySelector('input[name="title"]').value,
            backlink: form.querySelector('input[name="backlink"]').value,
            notes: form.querySelector('textarea[name="notes"]').value
        },
        success: function (repsonse) {
            $('#modal-change-slider-information').modal('hide');
            document.querySelector('div.slider-information').innerHTML = repsonse;

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            document.getElementById('alert-modal-change-slider-information').innerHTML = "update information fails";
        }
    });
}

function uploadSliderImage(event) {
    var selectedFile = event.target.files[0];
    var reader = new FileReader();

    var imgtag = document.querySelector('#modal-change-slider-image img[name="slider-image-preview"]');

    reader.onload = function (event) {
        imgtag.src = event.target.result;
    };

    reader.readAsDataURL(selectedFile);
    $('#modal-change-slider-image').modal('show');
}

function editSliderImage() {
    var form = document.getElementById('form-change-slider-image');
    var data = new FormData(form);
    $.ajax({
        url: 'editslider',
        type: 'post',
        data: data,
        contentType: false,
        processData: false,
        success: function (response) {
            $('#modal-change-slider-image').modal('hide');
            document.querySelector('.slider-image img').src = '../' + response;
            document.querySelector('#modal-change-slider-image img[name="slider-image-preview"]').src = "";
            document.querySelector('#form-change-slider-image input[name="input-slider-image"]').value = "";
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            document.getElementById('alert-modal-change-slider-image').innerHTML = "update image fails";
        }
    });
}

$('#modal-change-slider-image').on('hidden.bs.modal', function () {
    document.querySelector('#modal-change-slider-image img[name="slider-image-preview"]').src = "";
    document.querySelector('#form-change-slider-image input[name="input-slider-image"]').value = "";
});