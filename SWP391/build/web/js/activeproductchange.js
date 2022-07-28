
document.querySelectorAll('.featured-btn').forEach(b => {
    b.addEventListener('change', function () {
        var id = b.getAttribute('data-product-id');
        if (b.checked) {
            if (confirm('Do you want to show product with id = ' + id + '?')) {
                $.ajax({
                    url: 'editproduct',
                    type: 'post',
                    data: {
                        action: 'activate',
                        product_id: id
                    },
                    success: function (response) {
                        b.checked = true;
                        console.log(response);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        b.checked = false;
                    }
                });
            } else {
                b.checked = false;
            }
        } else {
            if (confirm('Do you want to hide product with id = ' + id + '?')) {
                $.ajax({
                    url: 'editproduct',
                    type: 'post',
                    data: {
                        action: 'disactivate',
                        product_id: id
                    },
                    success: function (response) {
                        b.checked = false;
                        console.log(response);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        b.checked = true;
                    }
                });
            } else {
                b.checked = true;
            }
        }
    });
})
