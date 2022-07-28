<%@page import="dal.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!Doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <title>Marketing - Products List</title>
        <link rel="stylesheet" href="../css/style.css">
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>

    </head>

    <body>
        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <div class="container-lg my-5">
                    <div class="p-3 bg-white rounded shadow-sm">
                        <div class="top-head">
                            <form id="productSearchForm" action="productslist" method="post">
                                <input type="number" id="page" name="page" value="${requestScope.pageNumber}" hidden>
                            <!--<span class="input-group-text" id="basic-addon1">Sort by</span>-->
                            <!--                                                                <select class="form-control" name="orderOption" id="order-by" onchange="searchProductByChangeOrderOption()" aria-describedby="basic-addon1">
                                                <option value="newest" ${requestScope.orderOption eq "newest"?"selected":""}/> Newest 
                                            <option value="oldest" ${requestScope.orderOption eq "oldest"?"selected":""}/> Oldest 
                                            <option value="lowestPrice" ${requestScope.orderOption eq "lowestPrice"?"selected":""}/> Lowest Price 
                                            <option value="highestPrice" ${requestScope.orderOption eq "highestPrice"?"selected":""}/> Highest Price 
                                        </select>-->
                            <div class="mb-3 d-flex justify-content-between">
                                <div class="d-flex">
                                    <div class="me-3">
                                        <div class="input-group">
                                            <label class="input-group-text" for="inputGroupSelect01">Featured</label>
                                            <select class="form-select" id="inputGroupSelect01" name="featured">
                                                <option value="-1"  ${requestScope.choosen_featured eq -1?"selected":""}> All </option>
                                                <option value="1" ${requestScope.choosen_featured eq 1?"selected":""}> On </option>
                                                <option value="0" ${requestScope.choosen_featured eq 0?"selected":""}> Off </option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="me-3">
                                        <div class="input-group">
                                            <span class="input-group-text" id="basic-addon1">Category</span>
                                            <select class="form-select" name="categoryId" aria-describedby="basic-addon1" id="select-category" onchange="updateSubCategry()">
                                                <%
                                                    CategoryDAO category_dao = new CategoryDAO();
                                                %>
                                                <option value="-1" ${requestScope.choosen_category_id eq -1 ? "selected" : ""}> All </option>
                                                <c:forEach var="category" items="<%= category_dao.getProductCategory()%>">
                                                    <option value="${category.category_id}"  ${requestScope.choosen_category_id eq category.category_id ? "selected" : ""}> ${category.category_name} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="me-3">
                                        <div class="input-group">
                                            <span class="input-group-text" id="basic-addon1">Sub Category</span>
                                            <select class="form-select" name="subCategoryId" aria-describedby="basic-addon1" id="select-sub-category">
                                                <option value="-1"> All </option>
                                                <c:forEach var="category" items="<%= category_dao.getProductCategory()%>">
                                                    <c:if test="${requestScope.choosen_category_id eq category.category_id}">
                                                        <c:forEach var="subcategory" items="${category.subCategoryList}">
                                                            <option value="${subcategory.id}"  ${requestScope.choosen_sub_category_id eq subcategory.id ? "selected" : ""}> ${subcategory.name} </option>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <input name="search_key" type="hidden">
                                <div>
                                    <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modal-search">
                                        <i class="fa-solid fa-magnifying-glass"></i>
                                    </button>
                                    <input class="btn btn-primary" type="submit" value="Filter">
                                </div>
                            </div>
                            <div>
                                <span class="me-3" id="basic-addon1">Sort by</span>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="product_id asc" ${requestScope.choosen_orderby eq "product_id asc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-1-9"></i>
                                        <span>ID</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="product_id desc" ${requestScope.choosen_orderby eq "product_id desc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-9-1"></i>
                                        <span>ID</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="name asc" ${requestScope.choosen_orderby eq "name asc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-a-z"></i>
                                        <span>Title</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="name desc" ${requestScope.choosen_orderby eq "name desc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-z-a"></i>
                                        <span>Title</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="original_price asc"  ${requestScope.choosen_orderby eq "original_price asc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-short-wide"></i>
                                        <span>List price</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="original_price desc" ${requestScope.choosen_orderby eq "original_price desc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-wide-short"></i>
                                        <span>List price</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="sale_price asc"  ${requestScope.choosen_orderby eq "sale_price asc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-short-wide"></i>
                                        <span>Sale price</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="sale_price desc"  ${requestScope.choosen_orderby eq "sale_price desc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-wide-short"></i>
                                        <span>Sale price</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="unit_in_stock asc"  ${requestScope.choosen_orderby eq "unit_in_stock asc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-short-wide"></i>
                                        <span>Quantity</span>
                                    </div>
                                </label>
                                <label>
                                    <input type="radio" class="btn-check" name="orderby" value="unit_in_stock desc"  ${requestScope.choosen_orderby eq "unit_in_stock desc"?"checked":""}>
                                    <div class="btn btn-outline-primary">
                                        <i class="fa-solid fa-arrow-down-wide-short"></i>
                                        <span>Quantity</span>
                                    </div>
                                </label>
                            </div>
                        </form>
                    </div>
                    <div>
                        <hr>
                    </div>
                    <div>
                        <table class="table table-hover">
                            <thead class="sticky-0 bg-white">
                                <tr>
                                    <th>Id</th>
                                    <th>Thumbnail</th>
                                    <th>Title</th>
                                    <th>List Price</th>
                                    <th>Sale Price</th>
                                    <th>Category</th>
                                    <th>Quantity</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.productListByPage}" var="i">
                                    <tr>
                                        <td class="align-middle">${i.product_id}</td>
                                        <td style="width: 10%;">
                                            <a href="product?id=${i.product_id}" class="product-thumbnail">
                                                <img src="../${i.thumbnail}" class="card-img-top w-100 rounded" alt="..." id="product-thumbnail" onmouseover="zoomIn(this);" onmouseout="zoomOut(this);">
                                            </a>
                                        </td>
                                        <td style="width: 20%">
                                            <a href="product?id=${i.product_id}" data-bs-toggle="tooltip" title="${i.name}">
                                                <h6 class="card-title product-title font-weight-bold">${i.name}</h6>
                                            </a>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.original_price}" type="currency" currencySymbol="đ" maxFractionDigits="0" />
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.sale_price}" type="currency" currencySymbol="đ" maxFractionDigits="0" />
                                        </td>
                                        <td>
                                            <a href="productlist?category=${i.subCategory.category.category_id}">${i.subCategory.category.category_name}</a>
                                            <span> ,</span>
                                            <a href="productlist?category=${i.subCategory.id}">${i.subCategory.name}</a>
                                        </td>
                                        <td>${i.unit_in_stock}</td>
                                        <td class="align-middle">
                                            <div class="form-check form-switch">
                                                <input class="featured-btn form-check-input" style="cursor: pointer" data-product-id="${i.product_id}" type="checkbox" role="switch" id="flexSwitchCheckDefault" <c:if test="${i.featured}">checked</c:if>/>
                                                    <label class="form-check-label" for="flexSwitchCheckDefault"></label>
                                                </div>
                                            </td>
                                            <td class="align-middle">
                                                <a href="product?id=${i.product_id}"><i class="fa-solid fa-pen-to-square"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                    <div class="paging">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <li class="page-item<c:if test="${requestScope.pageNumber <= 1}"> disabled"</c:if> <c:if test="${requestScope.pageNumber > 1}"> cursor-pointer" onclick="nextProductPage(${requestScope.pageNumber - 1});"</c:if>>
                                    <a class="page-link">Previous</a>
                                    </li>
                                        <li class="page-item"><span class="page-link">${requestScope.pageNumber}</span></li>
                                <li class="page-item<c:if test="${requestScope.pageNumber >= requestScope.numberPage}"> disabled"</c:if> <c:if test="${requestScope.pageNumber < requestScope.numberPage}"> cursor-pointer" onclick="nextProductPage(${requestScope.pageNumber + 1});"</c:if>>
                                    <a class="page-link">Next</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
        <div id="corner-buttons" class="fixed-end-bottom">
            <div>
                <div class="px-3 py-2 bg-white rounded-pill shadow-sm mb-3 corner-button">
                    <a href="addproduct" class="cursor-pointer d-flex"><i class="fa-solid fa-circle-plus fs-4"></i><div class="button-title"><span> Add new Product</span></div></a>
                </div>
            </div>
        </div>
        <div class="modal" tabindex="-1" id="modal-search">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input class="form-control" type="text" name="search-bar" placeholder="Search product by name" onkeydown="submitform(event)">
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script>
                            const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                            const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
    </script>
    <script>
        function submitform(event) {
            if (event.key === 'Enter') {
                var input = $('input[name="search-bar"]').val();
                $('input[name="search_key"]').val(input);
                $('#productSearchForm').submit();
            }
        }
    </script>
    <script>
        function nextProductPage(pageNumber) {
            document.getElementById('page').value = pageNumber;
            document.getElementById('productSearchForm').submit();
        }
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
    </script>

</html>