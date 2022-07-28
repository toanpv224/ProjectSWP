<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Information</title>
        <!-- CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/styleorderinformation.css"/>
    </head>

    <body>
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
                <span> > </span>
                <span><a href="orderinformation?orderId=${requestScope.myOrder.order_id}">Order informations</a></span>
        </div>
        <div class="container">
            <div class="row">
                <!-- sider -->
                <div class="col-3">
                    <div class="sider">
                        <!-- sider -->
                        <c:import url="sider.jsp"></c:import>
                            <!-- end sider -->
                        </div>
                    </div>
                    <!-- content -->
                    <div class="col-9">
                        <!-- order information -->
                        <div class="content order-information-container bg-white">
                        <c:set value="${requestScope.myOrder}" var="order"></c:set>
                            <div class="row">
                                <div class="col-5">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title">Order Information</h5>
                                            <table>
                                                <tr>
                                                    <td>ID:</td>
                                                    <td>${order.order_id}</td>
                                            </tr>
                                            <tr>
                                                <td>Order Date:</td>
                                                <td><fmt:formatDate value="${order.orderDate}" type="both"/></td>
                                            </tr>
                                            <tr>
                                                <td>Total Cost:</td>
                                                <td><fmt:formatNumber type="currency" value="${order.total_price}" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></td>
                                                </tr>
                                                <tr>
                                                    <td>Status:</td>
                                                <c:if test="${order.status == 6}"><td>Cancel</td></c:if>
                                                <c:if test="${order.status == 1}"><td>Submitted</td></c:if>
                                                <c:if test="${order.status == 2}"><td>Payment information confirmation</td></c:if>
                                                <c:if test="${order.status == 3}"><td>Active</td></c:if>
                                                <c:if test="${order.status == 4}"><td>Transporting</td></c:if>
                                                <c:if test="${order.status == 5}"><td>Finished</td></c:if>

                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-7">
                                    <div class="card">
                                        <div class="card-body receiver-infor">
                                            <h5 class="card-title">Receiver Information</h5>
                                            <table>
                                                <tr>
                                                    <td>Fullname:</td>
                                                    <td>${order.ship_name}</td>
                                            </tr>
                                            <tr>
                                                <td>Gender:</td>
                                                <c:if test="${order.ship_gender == true}"><td>Male</td></c:if>
                                                <c:if test="${order.ship_gender == false}"><td>Female</td></c:if>
                                                </tr>
                                                <tr>
                                                    <td>Email:</td>
                                                    <td>${order.ship_email}</td>
                                            </tr>
                                            <tr>
                                                <td>Mobile:</td>
                                                <td>${order.ship_mobile}</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                                                <c:if test="${requestScope.myOrder.status == 1}"><span class="note"><span>*NOTE:</span> Your order is not finished yet!! Click <a href="cartcompletion?orderid=${requestScope.orderID_encoded}">here</a> to finish your order</span></c:if>
                            </div>
                        </div>
                        <div class="content order-details bg-white">
                            <h3 style="font-weight: bold;
                                font-family: emoji;
                                padding-left: 10px;">Ordered Products</h3>
                        <c:set value="${order.orderDetailList}" var="orderDetails"></c:set>
                            <table class="table">
                                <tbody>
                                <c:forEach items="${orderDetails}" var="i">

                                    <tr>
                                        <td><img src="${i.product.thumbnail}" alt=""></td>
                                        <td>
                                            <h5><a href="product?id=${i.product.product_id}">${i.product.name}</a></h5>
                                            <h6 class="card-subtitle mb-2 text-muted">Category: ${i.category.category_name}</h6>
                                            <p><fmt:formatNumber type="currency" value="${i.price}" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></p>
                                            </td>
                                            <td>
                                                <span>Quantity</span>
                                                <p class="quantity">${i.quantity}</p>
                                        </td>
                                        <td>
                                            <span>Sub Total</span>
                                            <p class="subtotal"><fmt:formatNumber type="currency" value="${i.price * i.quantity}" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></p>
                                            </td>
                                            <td>
                                                <span id="rebuy-message${i.product.product_id}" class="rebuy-message"></span>
                                                <div>
                                                    <a class="btn action" style="    border-radius: 3px;
                                                   border-color: black;
                                                   margin-left: 30px;
                                                   margin-bottom: 10px;
                                                   color: black;
                                                   display: block;" onclick="reBuy(${i.product.product_id}, ${i.quantity}, ${i.product.unit_in_stock});">Rebuy</a>
                                                <a href="feedback?id=${i.product.product_id}" class="btn action" style="    border-radius: 3px;
                                                   border-color: black;
                                                   margin-left: 30px;
                                                   margin-bottom: 10px;
                                                   color: black;
                                                   display: block;">Feedback</a>
                                            </div>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <form action="buy" method="post" id="frm1">
                            <input type="hidden" value="" name="id" id="rebuy-product-id"/>
                            <input type="hidden" value="" name="quantity" id="rebuy-product-quantity"/>
                        </form>
                        <form action="" method="" id="frm">
                            <input type="hidden" value="${requestScope.myOrder.order_id}" name="orderId"/>
                        </form>
                        <c:if test="${order.status == 1}">

                            <div style="padding-left: 10px;">
                                <button type="submit" class="btn btn-outline-danger modify-action" style="background: #dc3545; color: #fff; border-radius: 3px;" <c:if test="${order.status == 1}">onclick="cancel();"</c:if>>Cancel</button>
                                <button type="button" class="btn btn-outline-primary modify-action" style="background: #0d6efd; color: #fff; border-radius: 3px;" <c:if test="${order.status == 1}">onclick="update();"</c:if>>Update</button>
                                </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="js/updateorderinformation.js"></script>
</html>