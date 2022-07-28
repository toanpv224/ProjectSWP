<%@page import="java.util.List"%>
<%@page import="model.Slider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Online shopping system</title>

        <!--bootstrap-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/stylehomepage.css">
        <link rel="stylesheet" href="css/slider.css">
        <link rel="stylesheet" href="css/poststyle.css">
        <!-- ===== Boxicons CSS ===== -->
        <link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css" rel="stylesheet">
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <style>

            .rei_banner_container .rei_banner_body>div {
                width: 100%;
                display: inline-block;
                position: relative;
            }

            .rei_banner_container .rei_banner_bottombtns .banner-highlight {
                width: 46px;
                opacity: 1!important;
            }

            .rei_banner_container {
                width: 100%;
                position: relative;
                overflow-x: hidden;
            }

            .rei_banner_container .rei_banner_body {
                white-space: nowrap;
                letter-spacing: -6px;
                position: relative;
            }

            .rei_banner_container .rei_banner_body img {
                width: 100%;
                object-fit: cover;
                border-radius: 10px;
            }

            .rei_banner_container .rei_banner_body .slider-title {
                position: absolute;
                bottom: 50px;
                letter-spacing: 0px;
                width: 100%;
                text-align: center;
            }

            .rei_banner_container .rei_banner_body h4 {
                color: white;
            }

            .rei_banner_container .rei_banner_bottombtns {
                position: absolute;
                bottom: 30px;
                z-index: 200;
                text-align: center;
                width: 100%;
                display: flex;
                justify-content: center;
            }

            .rei_banner_container .rei_banner_bottombtns span {
                background: #fff;
                display: inline-block;
                width: 18px;
                height: 6px;
                border: 1px solid white;
                border-radius: 3px;
                opacity: 0.5;
                cursor: pointer;
            }
            .rei_banner_container .rei_banner_bottombtns div{
                width: fit-content;
                padding: 0 20px;
            }
            .rei_banner_container .rei_banner_bottombtns div:hover span{
                height: 18px;
                border-radius: 9px;
                transform: translate(0, 6px);
            }
        </style>
    </head>

    <body>
        <div class="header">
            <c:import url="navbar.jsp"></c:import>
            </div>
            <div class="container-lg">
                <!-- slider -->
                <div class="rei_banner_container slider mb-3">
                    <div class="rei_banner_body">
                    <c:forEach var="slider" items="${requestScope.slidersList}">
                        <div>
                            <a href="${slider.url}">
                                <div class="slider-image">
                                    <img src="${slider.imagePath}">
                                </div>
                            </a>
                            <div class="slider-title">
                                <h4>${slider.title}</h4>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="rei_banner_bottombtns">
                    <div>
                    <%
                        int i = 1;
                    %>
                    <c:forEach var="slider" items="${requestScope.slidersList}">
                        <span onclick="getBanner(<%= i%>)"></span>
                        <%
                            i++;
                        %>
                    </c:forEach>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-3">
                    <c:import url="sider.jsp"></c:import>
                    </div>
                    <div class="col-9">
                        <div class="p-3 bg-white rounded shadow-sm">
                            <div class="feature-item row mb-3">
                                <div class="text-center">
                                    <h3 class="pb-3 text-uppercase font-weight-bold">Feature items</h3>
                                </div>
                            <c:forEach var="product" items="${requestScope.productsList}">
                                <div class="col-3 mb-3">
                                    <div class="card product-card h-100">
                                        <a href="product?id=${product.product_id}">
                                            <img src="${product.thumbnail}" class="card-img-top">
                                        </a>
                                        <div class="card-body position-relative pb-5">
                                            <a href="product?id=${product.product_id}" data-bs-toggle="tooltip" title="${product.name}">
                                                <h6 class="card-title product-title">${product.name}</h6>
                                            </a>
                                            <h6 class="card-subtitle mb-2 text-muted">
                                                <c:if test="${product.sale_price != 0}">
                                                    <span class="text-decoration-line-through"><fmt:formatNumber value="${product.original_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span>
                                                    <span style="color: red;"> <fmt:formatNumber value="${product.sale_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span></c:if>
                                                <c:if test="${product.sale_price == 0}"><span><fmt:formatNumber value="${product.original_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></span></c:if>
                                                </h6>
                                                <p class="brief-infor">${product.briefInfor}</p>
                                                <a href="product?id=${product.product_id}" type="button" class="btn btn-outline-primary position-absolute" style="right: 9px; bottom: 18px">Details</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="d-flex justify-content-end">
                                <a href="productslist">View all Products</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="p-3 bg-white rounded shadow-sm">
                <div class="hot-post row mb-3">
                    <div class="text-center">
                        <h3 class="pb-3 text-uppercase font-weight-bold">Hot posts</h3>
                    </div>
                    <c:forEach var="post" items="${requestScope.hotPostsList}">
                        <div class="col-4 mb-3">
                            <div class="card post">
                                <a class="post-thumbnail" href="post?id=${post.post_id}">
                                    <img src="${post.thumbnail}" class="card-img-top" alt="...">
                                </a>
                                <div class="card-body">
                                    <h6 class="card-title mb-3">${post.title}</h6>
                                    <!--<h6 class="card-subtitle text-muted" style="font-size: .85rem">${post.sub_title}</h6>-->
                                </div>
                                <div class="mx-2 mb-2 text-muted opacity-75 d-flex justify-content-end">
                                    <fmt:formatDate value="${post.publication_date}" pattern="HH:mm dd/MM/yyyy"/>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="d-flex justify-content-end">
                        <a href="blogslist">View all Blogs</a>
                    </div>
                </div>
            </div>
        </div>
        <c:import url="footer.jsp"></c:import>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="js/carousel.js"></script>

    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script>
                                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
                                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
    </script>
    <script>
        var images = document.querySelectorAll('.rei_banner_body>div');
        var slider_btn = document.querySelectorAll('.rei_banner_bottombtns span');
        var i = 0;

        banner();

        function banner() {
            for (var index = 0; index < images.length; index++) {
                images[index].style = 'transform: translateX(-' + (100 * i) + '%); transition: 1s;'
                slider_btn[index].classList = '';
            }
            slider_btn[i].classList = 'banner-highlight';
            setTimeout(banner, 6000);
            i++;
            if (i == images.length)
                i = 0;
        }

        function getBanner(id) {
            i = id - 1;
            for (var index = 0; index < images.length; index++) {
                slider_btn[index].classList = '';
                images[index].style = 'transform: translateX(-' + (100 * i) + '%); transition: 1s;'
            }
            slider_btn[i].classList = 'banner-highlight';
        }
    </script>
</html>