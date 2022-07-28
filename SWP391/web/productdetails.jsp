<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Details</title>
        <!-- CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <div class="header">
            <!-- navbar -->
            <c:import url="navbar.jsp"></c:import>
                <!-- end navbar -->
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-3">
                            <!-- sider -->
                        <c:import url="sider.jsp"></c:import>
                            <!-- end sider -->
                    </div>
                    <div class="col-9">
                        <div class="p-3 bg-white rounded shadow-sm">
                        <c:set var="prodct" value="${requestScope.product}"></c:set>
                            <div class="row product-details-container mb-3">
                                <div class="col-6">
                                    <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="false">
                                        <div class="carousel-indicators">
                                        <%
                                            int index = 0;
                                        %>
                                        <c:forEach var="product_image" items="${product.images}">
                                            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="<%= index%>" aria-current="true" aria-label="Slide <%= index + 1%>"></button>
                                            <%
                                                index++;
                                            %>
                                        </c:forEach>
                                    </div>
                                    <div class="carousel-inner rounded">
                                        <c:forEach var="product_image" items="${product.images}">
                                            <div class="carousel-item">
                                                <img src="${product_image.path}" class="d-block w-100" alt="${product_image.description}">
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.name} ${product.briefInfor} <c:if test="${product.model!=null}">(${product.model})</c:if></h5>
                                        <h6 class="card-subtitle mb-2 text-muted">Category: 
                                            <a href="productlist?category=${product.subCategory.category.category_id}">${product.subCategory.category.category_name}</a>
                                            <span> ,</span>
                                            <a href="productlist?category=${product.subCategory.id}">${product.subCategory.name}</a>
                                        </h6>
                                        <div class="mb-2 ms-3">
                                            <c:if test="${product.sale_price != 0}">
                                                <span class="text-decoration-line-through"><fmt:formatNumber value="${product.original_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span>
                                                <h4 style="color: red;"> <fmt:formatNumber value="${product.sale_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></h4>
                                            </c:if>
                                            <c:if test="${product.sale_price == 0}"><h4><fmt:formatNumber value="${product.original_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></h4></c:if>
                                            </div>
                                            <form action="buy" method="post">
                                                <input type="text" name="id" value="${product.product_id}" hidden="true">
                                            <div class="input-group">
                                                <label class="input-group-text" for="typeNumber">Quantity</label>
                                                <c:if test="${product.unit_in_stock==0}">
                                                    <input style="color: red" type="text" class="form-control" value="Out of stock" readonly disabled/>
                                                </c:if>
                                                <c:if test="${product.unit_in_stock!=0}">
                                                    <input type="number" id="typeNumber" name="quantity" class="form-control" value="1" min="1" max="${product.unit_in_stock}" />
                                                </c:if>
                                            </div>
                                            <input <c:if test="${product.unit_in_stock==0}">readonly disabled</c:if> class="btn btn-outline-primary mt-3" type="submit" value="Add to Cart">
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="accordion">
                                <div class="card">
                                    <a class="text-muted text-uppercase" data-bs-toggle="collapse" href="#collapseOne">
                                        <h5 class="mt-2 text-center">Overview</h5>
                                    </a>
                                    <div id="collapseOne" class="collapse" data-bs-parent="#accordion">
                                        <hr class="mt-0">
                                        <div class="card-body">
                                        ${product.product_details}
                                    </div>
                                </div>
                            </div>

                            <div class="card">
                                <a class="text-muted text-uppercase" data-bs-toggle="collapse" href="#collapseTwo">
                                    <h5 class="mt-2 text-center">Specifications</h5>
                                </a>
                                <div id="collapseTwo" class="collapse" data-bs-parent="#accordion">
                                    <hr class="mt-0">
                                    <div class="card-body product-specification">
                                        <c:if test="${requestScope.isExit}"><c:import url="file/product_details/product_${product.product_id}.html" charEncoding="UTF-8"></c:import></c:if>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="js/carousel.js"></script>

    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script>
        const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
        const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
    </script>
</html>