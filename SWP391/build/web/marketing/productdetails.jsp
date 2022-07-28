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
                <div class="container-lg">
                    <div class="p-3 bg-white rounded shadow-sm my-5">
                        <div class="product_details">
                            <div class="d-flex justify-content-between sticky-0 bg-white pt-3 border-bottom border-2 mb-3 pb-1">
                                <div>
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon1">Updated Date</span>
                                        <input type="text" class="form-control" placeholder="Updated Date" aria-label="Updated Date" aria-describedby="basic-addon1" value="${product.updated_date}" disabled>
                                </div>
                            </div>
                            <div>
                                <div class="form-check form-switch form-switch-md">
                                    <input class="featured-btn form-check-input" style="cursor: pointer" data-product-id="${product.product_id}" type="checkbox" role="switch" id="flexSwitchCheckDefault" <c:if test="${product.featured}">checked</c:if>/>
                                        <label class="form-check-label" for="flexSwitchCheckDefault"></label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6 mb-3">
                                    <h3 class="mt-3">Thumbnail</h3>
                                    <div class="images-hover images-viewer">
                                        <img src="../${product.thumbnail}" alt="" aria-label="thumbnail">
                                    <div class="p-1 px-3 mask bg-white rounded-pill shadow-sm">
                                        <a class="me-2 cursor-pointer" aria-label="View"><i class="fa-solid fa-eye"></i></a>
                                        <label>
                                            <form id="form-thumbnail" enctype="multipart/form-data">
                                                <i class="fa-solid fa-upload link"></i>
                                                <input type="file" accept=".jpg, .jpeg, .png" name="thumbnail" onchange="uploadThumbnail(event)" hidden>
                                                <input type="text" name="action" value="changeThumbnail" hidden>
                                                <input type="text" name="product_id" value="${product.product_id}" hidden>
                                            </form>
                                        </label>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center mt-5">
                                    <h3>Product Images</h3>
                                    <div>
                                        <label for="images">
                                            <a class="cursor-pointer ms-3"><i class="fa-solid fa-plus mx-auto my-auto text-primary"></i></a>
                                        </label>
                                        <form id="form-product-image" enctype="multipart/form-data">
                                            <input type="text" name="action" value="addImage" hidden>
                                            <input type="text" name="product_id" value="${product.product_id}" hidden>
                                            <input type="file" accept=".jpg, .jpeg, .png" id="images" name="image" onchange="uploadProductImage(event)" hidden>
                                        </form>
                                    </div>
                                </div>

                                <div id="product-images" class="row">
                                    <c:forEach var="image" items="${product.images}">
                                        <div class="col-4 mb-2 product-image">
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
                                </div>
                            </div>

                            <div class="col-6">
                                <div class="d-flex justify-content-between">
                                    <h3 class="mt-3">Product Information</h3>
                                    <div class="my-auto">
                                        <button type="button" class="btn btn-outline-primary" style="display: none" onclick="updateProductInformation()" aria-label="save-product-information">Save</button>
                                        <button type="button" class="btn btn-outline-primary" onclick="editProductInformation()" aria-label="edit-product-information">Edit</button>
                                    </div>
                                </div>
                                
                                <form id="product-information" class="row">
                                    <input type="text" name="product_id" value="${product.product_id}" hidden>
                                    <input type="text" name="action" value="updateproductinformation" hidden>
                                    <div class="col-12">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Name</span>
                                            <input type="text" class="form-control" placeholder="Name" name="name" aria-describedby="basic-addon1" value="${product.name}" disabled>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Model</span>
                                            <input type="text" class="form-control" placeholder="Model" name="model" aria-describedby="basic-addon1" value="${product.model}" disabled>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Unit in Stock</span>
                                            <input type="number" class="form-control" min="0" placeholder="Unit in Stock" name="unit_in_stock" aria-describedby="basic-addon1" value="${product.unit_in_stock}" disabled>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Category</span>
                                            <select class="form-control" name="categoryId" aria-describedby="basic-addon1" id="select-category" onchange="updateSubCategry()" disabled>
                                                <%
                                                    CategoryDAO category_dao = new CategoryDAO();
                                                %>
                                                <c:forEach var="category" items="<%= category_dao.getProductCategory()%>">
                                                    <option value="${category.category_id}"  ${product.subCategory.category.category_id eq category.category_id ? "selected" : ""}> ${category.category_name} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Sub Category</span>
                                            <select class="form-control" name="subCategoryId" aria-describedby="basic-addon1" id="select-sub-category" disabled>
                                                <c:forEach var="category" items="<%= category_dao.getProductCategory()%>">
                                                    <c:if test="${product.subCategory.category.category_id eq category.category_id}">
                                                        <c:forEach var="subcategory" items="${category.subCategoryList}">
                                                            <option value="${subcategory.id}"  ${product.subCategory.id eq subcategory.id ? "selected" : ""}> ${subcategory.name} </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Original Price</span>
                                        <input type="number" class="form-control" placeholder="Original Price" name="original_price" aria-describedby="basic-addon1" id="original_price" disabled>
                                        <script>
                                            document.getElementById('original_price').value = <fmt:formatNumber type = "number" groupingUsed = "false" value = "${product.original_price}" />;
                                        </script>
                                    </div>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Sale Price</span>
                                        <input type="number" class="form-control" placeholder="Sale Price" name="sale_price" aria-describedby="basic-addon1" id="sale_price" disabled>
                                        <script>
                                            document.getElementById('sale_price').value = <fmt:formatNumber type = "number" groupingUsed = "false" value = "${product.sale_price}" />;
                                        </script>
                                    </div>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text">Brief Information</span>
                                        <textarea class="form-control" name="brief_infor" aria-label="With textarea" disabled>${product.briefInfor}</textarea>
                                    </div>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text">Product Details</span>
                                        <textarea class="form-control" name="product_details" aria-label="With textarea" disabled>${product.product_details}</textarea>
                                    </div>
                                </form>
                                <!--<h3 class="mt-5">Product Specification</h3>-->
                                <!--                                <c:if test="${requestScope.isExit}"><c:import url="../file/product_details/product_${product.product_id}.html" charEncoding="UTF-8"></c:import></c:if>
                                                                <div class="border border-primary border-2 w-100 rounded d-flex cursor-pointer mb-3" style="height: 50px"><i class="fa-solid fa-plus mx-auto my-auto text-primary"></i></div>-->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div
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
            function uploadThumbnail(event) {
                var selectedFile = event.target.files[0];
                var reader = new FileReader();

                var imgtag = document.getElementById("thumbnail-image-load");
                imgtag.title = selectedFile.name;

                reader.onload = function (event) {
                    imgtag.src = event.target.result;
                };

                reader.readAsDataURL(selectedFile);
                document.getElementById("modal-thumbnail").style = "display: block";
            }

        </script>
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
    <script src="../js/activeproductchange.js"></script>
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
                var form = document.getElementById('form-product-image');
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
                        var node = document.createElement("div");

//                        document.getElementById('product-images').appendChild();
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
        function uploadProductImage(event) {
            var selectedFile = event.target.files[0];
            var reader = new FileReader();

            var imgtag = document.getElementById("product-image-load");
            imgtag.title = selectedFile.name;

            reader.onload = function (event) {
                imgtag.src = event.target.result;
            };

            reader.readAsDataURL(selectedFile);
            document.getElementById("modal-product-image").style = "display: block";
        }
    </script>
    <script>
        function addEventDelete() {

            document.querySelectorAll('.product-image').forEach(f => {
                f.querySelector('a[aria-label="submit"]').addEventListener("click", function () {
                    var data = new FormData(f.querySelector('form.form-delete-product-image'));
                    if (confirm('Do you want to delete this pictures?')) {
                        $.ajax({
                            url: 'editproduct',
                            type: 'post',
                            data: data,
                            contentType: false,
                            processData: false,
                            success: function (response) {
                                f.style = "display: none";
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                d.querySelector('span.message').innerHTML = "Upload Fails";
                            }
                        });
                    }
                });
            });
        }
        addEventDelete();

    </script>
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
    <script>
                                    function editProductInformation() {
                                        document.querySelectorAll('#product-information [disabled]').forEach(input => {
                                            input.disabled = false;
                                        });
                                        document.querySelector('button[aria-label="save-product-information"]').style = "";
                                        document.querySelector('button[aria-label="edit-product-information"]').style = "display: none";
                                    }
                                </script>
                                <script>
                                    function updateProductInformation() {
                                        var form = document.getElementById('product-information');
                                        var data = new FormData(form);
                                        $.ajax({
                                            url: 'editproduct',
                                            type: 'post',
                                            data: data,
                                            contentType: false,
                                            processData: false,
                                            success: function (response) {
                                                document.querySelectorAll('#product-information input,select,textarea').forEach(input => {
                                                    input.disabled = true;
                                                });
                                                console.log(response);
                                                document.querySelector('button[aria-label="save-product-information"]').style = "display: none";
                                                document.querySelector('button[aria-label="edit-product-information"]').style = "";
                                            },
                                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                                d.querySelector('span.message').innerHTML = "Upload Fails";
                                            }
                                        });
                                    }
                                </script>
</html>