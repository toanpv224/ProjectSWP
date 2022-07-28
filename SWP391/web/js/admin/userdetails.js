function activate(id) {
    $.ajax({
        url: 'edituser',
        type: 'post',
        data: {
            id: id,
            action: "activate"
        },
        success: function (repsonse) {
            $('#confirm-activate').modal('hide');
            document.querySelector('#user_active_status').innerHTML = 'Yes';
            document.querySelector('#user_active_status').style = 'color: green';
            document.querySelector('#button_activate_disactivate').innerHTML = 'Disactivate';
            document.querySelector('#button_activate_disactivate').classList = 'btn btn-outline-danger cursor-pointer ms-3';
            document.querySelector('#button_activate_disactivate').setAttribute('data-bs-target', '#confirm-disactivate');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
function disactivate(id) {
    $.ajax({
        url: 'edituser',
        type: 'post',
        data: {
            id: id,
            action: "disactivate"
        },
        success: function (repsonse) {
            $('#confirm-disactivate').modal('hide');
            document.querySelector('#user_active_status').innerHTML = 'No';
            document.querySelector('#user_active_status').style = 'color: red';
            document.querySelector('#button_activate_disactivate').innerHTML = 'Activate';
            document.querySelector('#button_activate_disactivate').classList = 'btn btn-outline-success cursor-pointer ms-3';
            document.querySelector('#button_activate_disactivate').setAttribute('data-bs-target', '#confirm-activate');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {


        }
    });
}

function changerole(id) {
    var role_id = document.querySelector('input[aria-label-name="role-selection"]:checked').value;
    $.ajax({
        url: 'edituser',
        type: 'post',
        data: {
            id: id,
            role_id: role_id,
            action: "changerole"
        },
        success: function (repsonse) {
            $('#select-role').modal('hide');
            document.getElementById('user_role_name').innerHTML = repsonse;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            document.querySelectorAll('input[aria-label-name="role-selection"]').forEach(i => {
                if(i.value == role_id) i.checked = true;
            });
        }
    });
}

function createuser() {
    var modal = document.querySelector('#modal-add-new-user');
    var mail = modal.querySelector('input[name="mail"]').value;
    if(mail === null || mail == ''){
        $('#error-notify-create-user').html('Please enter mail');
        return;
    }
    var username = modal.querySelector('input[name="username"]').value;
    if(username === null || username == ''){
        $('#error-notify-create-user').html('Please enter username');
        return;
    }
    var fullname = modal.querySelector('input[name="fullname"]').value;
    if(fullname === null || fullname == ''){
        $('#error-notify-create-user').html('Please enter fullname');
        return;
    }
    if(modal.querySelector('input[name="gender"]:checked') === null){
        $('#error-notify-create-user').html('Please choose gender');
    }
    var gender = modal.querySelector('input[name="gender"]:checked').value;
    var phone = modal.querySelector('input[name="phone"]').value;
    var address = modal.querySelector('input[name="address"]').value;
    var role_id = modal.querySelector('select[name="role_id"] option:checked').value;
    $.ajax({
        url: 'addnewuser',
        type: 'post',
        data: {
            mail: mail,
            username: username,
            fullname: fullname,
            gender: gender,
            phone: phone,
            adress: address,
            role_id: role_id
        },
        success: function (repsonse) {
            $('#error-notify-create-user').html('');
            $('#success-notify-create-user').html('Create user successful');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#error-notify-create-user').html('Create user fail');
        }
    });
}