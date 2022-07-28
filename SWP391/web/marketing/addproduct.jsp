<%@page import="dal.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!Doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Marketing - Product Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/style.css">
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>

    </head>
    <body>
        <c:set var="product" value="${requestScope.product}"></c:set>
            <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <div class="container-lg my-5">
                    <div class="p-3 bg-white rounded shadow-sm">
                        <form class="product_details" enctype="multipart/form-data" method="post" action="addproduct">
                            <div class="d-flex justify-content-between sticky-0 bg-white pt-3 border-bottom border-2 mb-3 pb-1">
                                <input type="submit" class="btn btn-outline-primary py-0" value="Add Product">
                                <div>
                                    <div class="form-check form-switch form-switch-md">
                                        <label>
                                            <input class="featured-btn form-check-input cursor-pointer" type="checkbox" role="switch" name="featured" value="1" checked>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6 mb-3">
                                    <h3 class="mt-3">Thumbnail</h3>
                                    <div class="images-hover images-viewer">
                                        <img src="" alt="" aria-label="thumbnail" name="thumbnail">
                                        <div id="images-options" class="w-100" style="height: 200px">
                                            <label class="w-100 h-100">
                                                <div class="border border-primary border-2 h-100 rounded d-flex cursor-pointer" aria-label="upload-button"><i class="fa-solid fa-plus mx-auto my-auto text-primary"></i></div>
                                                <input type="file" accept=".jpg, .jpeg, .png" name="thumbnail" onchange="uploadThumbnail(event)" hidden>
                                            </label>
                                        </div>
                                    </div>
                                    <script>
                                        function uploadThumbnail(event) {
                                            var selectedFile = event.target.files[0];
                                            var reader = new FileReader();

                                            var imgtag = document.querySelector('img[aria-label="thumbnail"]');
                                            imgtag.title = selectedFile.name;

                                            reader.onload = function (event) {
                                                imgtag.src = event.target.result;
                                            };

                                            reader.readAsDataURL(selectedFile);
                                            var b = document.getElementById("images-options");
                                            b.style = "";
                                            b.classList = "p-1 px-3 mask bg-white rounded-pill shadow-sm d-flex";
                                            var anode = document.createElement("a");
                                            anode.classList = "cursor-pointer";
                                            anode.setAttribute('aria-label', 'View');
                                            var inode = document.createElement("i");
                                            inode.classList = "fa-solid fa-eye";
                                            anode.appendChild(inode);
                                            b.querySelector('div[aria-label="upload-button"]').remove();
                                            anode.addEventListener('click', function () {
                                                document.querySelector('#modal-viewimage img').setAttribute("src", imgtag.getAttribute("src"));
                                                document.getElementById('modal-viewimage').style = "display: block";
                                            });
                                            b.appendChild(anode);
                                            var node = document.createElement("i");
                                            node.classList = "me-2 fa-solid fa-upload link";
                                            b.querySelector('label').appendChild(node);
                                        }
                                    </script>
                                    <h3 class="mt-5">Product Images</h3>
                                    <div id="product-images" class="row">
                                    <c:forEach var="image" items="${product.images}">
                                        <div class="col-4 mb-2">
                                            <div class="images-hover images-viewer">
                                                <img src="../${image.path}" alt="">
                                                <div class="p-1 px-3 mask bg-white rounded-pill shadow-sm d-flex">
                                                    <a class="me-2 cursor-pointer" aria-label="View"><i class="fa-solid fa-eye"></i></a>
                                                    <form class="form-delete-product-image">
                                                        <input type="text" name="action" value="deleteImage" hidden>
                                                        <input type="text" name="product_id" value="${product.product_id}" hidden>
                                                        <input type="text" name="product_image" value="${image.path}" hidden>
                                                        <a class="cursor-pointer" aria-label="submit"><i class="fa-solid fa-trash"></i></a>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="col-4 mb-2">
                                        <img src="" alt="" aria-label="imageupload">
                                        <div id="product-image-options">
                                            <label class="w-100 h-100">
                                                <div class="border border-primary border-2 h-100 rounded d-flex cursor-pointer" aria-label="upload-button"><i class="fa-solid fa-plus mx-auto my-auto text-primary"></i></div>
                                                <input type="file" accept=".jpg, .jpeg, .png" name="image" id="images" onchange="uploadProductImage(event)" hidden>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                    <script>
                                        function uploadProductImage(event) {
                                            var selectedFile = event.target.files[0];
                                            var reader = new FileReader();

                                            var imgtag = document.querySelector('img[aria-label="imageupload"]');
                                            imgtag.title = selectedFile.name;

                                            reader.onload = function (event) {
                                                imgtag.src = event.target.result;
                                            };

                                            reader.readAsDataURL(selectedFile);
                                            var b = document.getElementById("product-image-options");
                                            b.style = "";
                                            b.classList = "p-1 px-3 mask bg-white rounded-pill shadow-sm d-flex";
                                            var anode = document.createElement("a");
                                            anode.classList = "cursor-pointer";
                                            anode.setAttribute('aria-label', 'View');
                                            var inode = document.createElement("i");
                                            inode.classList = "fa-solid fa-eye";
                                            anode.appendChild(inode);
                                            b.querySelector('div[aria-label="upload-button"]').remove();
                                            anode.addEventListener('click', function () {
                                                document.querySelector('#modal-viewimage img').setAttribute("src", imgtag.getAttribute("src"));
                                                document.getElementById('modal-viewimage').style = "display: block";
                                            });
                                            b.appendChild(anode);
                                            var node = document.createElement("i");
                                            node.classList = "me-2 fa-solid fa-upload link";
                                            b.querySelector('label').appendChild(node);
                                        }
                                    </script>
                            </div>
                            <div class="col-6">
                                <h3 class="mt-3">Product Information</h3>
                                <div class="row">
                                    <div class="col-12">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Name</span>
                                            <input type="text" class="form-control" placeholder="Name" name="name" aria-describedby="basic-addon1" required>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Model</span>
                                            <input type="text" class="form-control" placeholder="Name" name="model" aria-describedby="basic-addon1" required>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Unit in Stock</span>
                                            <input type="number" class="form-control" min="0" placeholder="Unit in Stock" name="unit_in_stock" aria-describedby="basic-addon1" required>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Category</span>
                                            <select class="form-control" name="categoryId" aria-describedby="basic-addon1" id="select-category" onchange="updateSubCategry()" required>
                                                <%
                                                    CategoryDAO category_dao = new CategoryDAO();
                                                %>
                                                <c:forEach var="category" items="<%= category_dao.getProductCategory()%>">
                                                    <option value="${category.category_id}"> ${category.category_name} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Sub Category</span>
                                            <select class="form-control" name="subCategoryId" aria-describedby="basic-addon1" id="select-sub-category" required>
                                            </select>
                                        </div>
                                    </div>
                                    <script>
                                        function updateSubCategry() {
                                            var selected = document.getElementById('select-category').querySelector('option:checked').value;
                                            var div = document.getElementById('select-sub-category');
                                            $.ajax({
                                                url: 'getsubcategory',
                                                type: 'post',
                                                data: {
                                                    cid: selected
                                                },
                                                success: function (response) {
                                                    div.innerHTML = response;
                                                },
                                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                                    b.checked = false;
                                                }
                                            });
                                        }
                                    </script>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Original Price</span>
                                        <input type="number" class="form-control" placeholder="Original Price" name="original_price" aria-describedby="basic-addon1" name="original_price" required>
                                    </div>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Sale Price</span>
                                        <input type="number" class="form-control" placeholder="Sale Price" name="sale_price" aria-describedby="basic-addon1" name="sale_price" required>
                                    </div>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text">Brief Information</span>
                                        <textarea class="form-control" name="brief_infor" placeholder="Brief Information" required></textarea>
                                    </div>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text">Product Details</span>
                                        <textarea class="form-control" name="product_details" placeholder="Product Details" required></textarea>
                                    </div>
                                </div>
<!--                                <h3 class="mt-5">Product Specification</h3>
                                <div class="border border-primary border-2 w-100 rounded d-flex cursor-pointer mb-3" style="height: 50px"><i class="fa-solid fa-plus mx-auto my-auto text-primary"></i></div>-->
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div>
            <div id="modal-thumbnail" class="modal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h6 class="modal-title">Use this image as thumbnail?</h6>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <span class="message"></span>
                            <img src="" alt="" id="thumbnail-image-load">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="Close">Close</button>
                            <button type="button" class="btn btn-primary"  aria-label="Confirm">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="modal-product-image" class="modal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h6 class="modal-title">Add this images?</h6>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <img src="" alt="" id="product-image-load">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="Close">Close</button>
                            <button type="button" class="btn btn-primary"  aria-label="Confirm">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="modal-viewimage" class="modal modal-xl">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <img src="" alt="" id="image-load">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.querySelectorAll('.images-viewer').forEach(d => {
                var image = d.querySelector('img');
                d.querySelector('a[aria-label="View"]').addEventListener('click', function () {
                    document.querySelector('#modal-viewimage img').setAttribute("src", image.getAttribute("src"));
                    document.getElementById('modal-viewimage').style = "display: block";

                });
            });
        </script>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script>
            const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
            const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
    </script>
    <script src="../js/textarea_auto_resize.js"></script>
    <script>
            document.querySelectorAll('.modal#modal-thumbnail').forEach(d => {
                d.querySelectorAll('button[aria-label="Close"]').forEach(b => {
                    b.addEventListener("click", function () {
                        d.style = "";
                        document.querySelector('#form-thumbnail input[type="file"]').value = null;
                    });
                });
            });
            document.querySelectorAll('.modal#modal-thumbnail').forEach(d => {
                d.querySelector('button[aria-label="Confirm"]').addEventListener("click", function () {
                    var form = document.getElementById('form-thumbnail');
                    var data = new FormData(form);
                    $.ajax({
                        url: 'editproduct',
                        type: 'post',
                        data: data,
                        contentType: false,
                        processData: false,
                        success: function (response) {
                            d.style = "";
                            document.querySelector('#form-thumbnail input[type="file"]').value = null;
                            document.querySelector('img[aria-label="thumbnail"]').setAttribute('src', "../" + response);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            d.querySelector('span.message').innerHTML = "Upload Fails";
                        }
                    });
                });
            });
    </script>
    <script>
        document.querySelectorAll('.modal#modal-product-image').forEach(d => {
            d.querySelectorAll('button[aria-label="Close"]').forEach(b => {
                b.addEventListener("click", function () {
                    d.style = "";
                    document.querySelector('#form-product-image input[type="file"]').value = null;
                });
            });
            d.querySelector('button[aria-label="Confirm"]').addEventListener("click", function () {
                var form = $('#form-product-image');
                var data = new FormData(form);
                $.ajax({
                    url: 'editproduct',
                    type: 'post',
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (repsonse) {
                        d.style = "";
                        document.querySelector('#form-product-image input[type="file"]').value = null;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                    }
                });
            });
        });
    </script>
    <script>
        document.querySelectorAll('.modal#modal-viewimage').forEach(d => {
            d.querySelectorAll('button[aria-label="Close"]').forEach(b => {
                b.addEventListener("click", function () {
                    d.style = "";
                });
            });
        });

    </script>
    <script>
        document.querySelectorAll('form-delete-product-image').forEach(f => {
            f.querySelector('a[aria-label="submit"]').addEventListener("click", function () {
                var data = new FormData(f);
                $.ajax({
                    url: 'editproduct',
                    type: 'post',
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (response) {
                        d.style = "";
                        document.querySelector('#form-thumbnail input[type="file"]').value = null;
                        document.querySelector('img[aria-label="thumbnail"]').setAttribute('src', "../" + response);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        d.querySelector('span.message').innerHTML = "Upload Fails";
                    }
                });
            });
        });

    </script>
</html>