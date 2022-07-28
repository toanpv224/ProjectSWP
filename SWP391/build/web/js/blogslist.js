
function changeOrderPost(){
    var selected = document.getElementById('select-posts-order').value;
    document.querySelector('input[name="orderOption"]').value = selected;
    document.getElementById('form-blog-sider').submit();
}

function movePage(page){
    document.querySelector('input[name="page"]').value = page;
    document.getElementById('form-blog-sider').submit();
}