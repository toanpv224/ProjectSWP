<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Products List</title>
        <!-- CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/styleproductlist.css">
        <link rel="stylesheet" href="css/stylefooter.css">
        <link rel="stylesheet" href="css/stylemyorders.css">
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>

    <body onload="autoCollapse()" style="font-family:cursive; ">
        <div class="header">
            <!-- navbar -->
            <c:import url="navbar.jsp"></c:import>
                <!-- end navbar -->
            </div>
            <div class="breadcrumb-my-product container-lg flex" style="margin-top: 75px;
                 margin-bottom: 10px;">
                <span><a href="home">Home</a></span>
                <span> > </span>
                <span><a href="myorders">My order</a></span>
            </div>
            <div class="container-lg">
                <div class="row">
                    <div class="col-3">
                    <c:import url="sider.jsp"></c:import>
                    </div>
                    <div class="col-9">
                        <div class="content">
                        <c:if test="${requestScope.myorders.size() != 0}">
                            <div class="flex my-order-header-bar bg-white">
                                <div style="margin-right: 55%;">Order</div>
                                <div style="margin-right: 15%;">Total price</div>
                                <div>Status</div>

                            </div>
                            <c:forEach items="${requestScope.myorders}" var="i">
                                <div class="order-items bg-white flex flex-column">
                                    <div class="flex" style="padding: 10px; padding-left: 30px; border-bottom: 1px solid #21252930;">
                                        <div class="id"><a href="orderinformation?orderId=${i.order_id}">ID: ${i.order_id}</a></div>
                                        <div class="order-date">Order Date: <fmt:formatDate value="${i.orderDate}" type="both"/></div>
                                    </div>
                                    <div class="flex" style="    border:1px solid #21252930;
                                         margin: 15px;">
                                        <div class="first-product-image"><img src="${i.orderDetailList.get(0).product.thumbnail}"></div>
                                        <div class="products-name">
                                            <div>${i.orderDetailList.get(0).product.name}</div><c:if test="${i.orderDetailList.size() > 1}">
                                                <div> and ${i.orderDetailList.size()-1} more</div></c:if>
                                                <div style="height: 10px;"></div>
                                            </div>
                                            <div class="total-cost flex"><span><fmt:formatNumber type="currency" value="${i.total_price}" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></span></div>
                                            <div class="status flex">
                                                <span>
                                                <c:if test="${i.status == 6}">Cancel</c:if>
                                                <c:if test="${i.status == 1}">Submitted</c:if>
                                                <c:if test="${i.status == 2}">Payment information confirmation</c:if>
                                                <c:if test="${i.status == 3}">Active</c:if>
                                                <c:if test="${i.status == 4}">Transporting</c:if>
                                                <c:if test="${i.status == 5}">Finished</c:if>
                                                </span>
                                            </div>
                                        </div>





                                    </div>
                            </c:forEach>
                            <nav aria-label="...">
                                <ul class="pagination">
                                    <c:if test="${requestScope.numberPage < 3}">
                                        <li class="page-item <c:if test="${requestScope.pageNumber == 1}">disabled</c:if>">
                                            <span class="page-link"><a href="myorders?page=${requestScope.pageNumber-1}"><</a></span>
                                        </li>
                                        <li class="page-item"><a class="page-link" href="myorders?page=1">1</a></li>
                                        <li class="page-item"><a class="page-link" href="myorders?page=2">2</a></li>
                                        <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                            <a class="page-link" href="myorders?page=${requestScope.pageNumber+1}">></a>
                                        </li>
                                    </c:if>
                                    <c:if test="${requestScope.numberPage >= 3}">
                                        <c:if test="${requestScope.pageNumber == 1}">
                                            <li class="page-item <c:if test="${requestScope.pageNumber == 1}">disabled</c:if>">
                                                <span class="page-link"><a href="myorders?page=${requestScope.pageNumber-1}"><</a></span>
                                            </li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=1">1</a></li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=2">2</a></li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=3">3</a></li>
                                            <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                                <a class="page-link" href="myorders?page=${requestScope.pageNumber+1}">></a>
                                            </li>
                                        </c:if>
                                        <c:if test="${requestScope.pageNumber == requestScope.numberPage}">
                                            <li class="page-item <c:if test="${requestScope.pageNumber == 1}">disabled</c:if>">
                                                <span class="page-link"><a href="myorders?page=${requestScope.pageNumber-1}"><</a></span>
                                            </li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=${requestScope.pageNumber - 2}">${requestScope.pageNumber - 2}</a></li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=${requestScope.pageNumber - 1}">${requestScope.pageNumber - 1}</a></li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=${requestScope.pageNumber}">${requestScope.pageNumber}</a></li>
                                            <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                                <a class="page-link" href="myorders?page=${requestScope.pageNumber+1}">></a>
                                            </li>
                                        </c:if>
                                        <c:if test="${requestScope.pageNumber != 1 && requestScope.pageNumber != requestScope.numberPage}">
                                            <li class="page-item <c:if test="${requestScope.pageNumber == 1}">disabled</c:if>">
                                                <span class="page-link"><a href="myorders?page=${requestScope.pageNumber-1}"><</a></span>
                                            </li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=${requestScope.pageNumber - 1}">${requestScope.pageNumber - 1}</a></li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=${requestScope.pageNumber}">${requestScope.pageNumber}</a></li>
                                            <li class="page-item"><a class="page-link" href="myorders?page=${requestScope.pageNumber+1}">${requestScope.pageNumber+1}</a></li>
                                            <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                                <a class="page-link" href="myorders?page=${requestScope.pageNumber+1}">></a>
                                            </li>
                                        </c:if>

                                    </c:if>
                                </ul>
                            </nav>
                        </c:if>
                        <c:if test="${requestScope.myorders.size() == 0}">
                            <div class="bg-white" style="height: 100%;">
                                There is no ordes
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <!--footer-->
        <c:import url="footer.jsp"></c:import>
        <!--end footer-->
    </body>
    <script src="js/productssearchfunction.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script>
        const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
        const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
    </script>
    <script src="js/productanimation.js"></script>
</html>