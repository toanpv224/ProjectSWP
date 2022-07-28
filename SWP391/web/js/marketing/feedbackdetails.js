function showFeedback(type, id) {
    console.log('show feedback');
    $.ajax({
        url: 'editfeedback',
        type: 'post',
        data: {
            id: id,
            action: "showfeedback",
            type: type
        },
        success: function (repsonse) {
            $('#confirm-show-feedback').modal('hide');
            document.querySelector('#feedback-status-checkbox').setAttribute('data-bs-target', '#confirm-hide-feedback');
            document.querySelector('#feedback-status-checkbox').checked = true;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            document.querySelector('#feedback-status-checkbox').checked = false;
        }
    });
}
function hideFeedback(type, id) {
    console.log('hide feedback');
    $.ajax({
        url: 'editfeedback',
        type: 'post',
        data: {
            id: id,
            action: "hidefeedback",
            type: type
        },
        success: function (repsonse) {
            $('#confirm-hide-feedback').modal('hide');
            document.querySelector('#feedback-status-checkbox').setAttribute('data-bs-target', '#confirm-show-feedback');
            document.querySelector('#feedback-status-checkbox').checked = false;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            document.querySelector('#feedback-status-checkbox').checked = true;
        }
    });
}

$('#confirm-hide-feedback').on('hidden.bs.modal', function () {
    document.querySelector('#feedback-status-checkbox').checked = true;
});
$('#confirm-show-feedback').on('hidden.bs.modal', function () {
    document.querySelector('#feedback-status-checkbox').checked = false;
});