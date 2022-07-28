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
        <link href="../css/stylesaleheadersider.css" rel="stylesheet"/>
        <link href="../css/stylesaleorderslist.css" rel="stylesheet"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:directive.include file="salenavbar.jsp"/>
        <div class="wrapper-login">
            <jsp:directive.include file="sidebar.jsp"/>
            <aside class="right-side">
                <section class="content container">
                    <div class="row flex order-detail-header">
                        <span><a href="/swp/sale/orderslist"><i class="fa-solid fa-angle-left"></i>   Back</a></span>
                        <span class="flex-1"></span>
                        <span>Order ID: ${requestScope.myOrder.order_id}</span>
                        <span class="order-status">${requestScope.myOrder.orderStatus.describe} Order</span>
                    </div>
                    <div class="row flex" style="    background: #fff;
                         padding-top: 10px;
                         padding-bottom: 10px;
                         margin-top: 10px;">
                        <div class="left" style="width: 25%;    border-right: 1px solid rgba(0,0,0,.09);">
                            <div class="left-wrapper">
                                <div class="general-infor flex flex-column">
                                    <span class="bold">Order information</span>
                                    <div class="order-id">
                                        Order ID: ${requestScope.myOrder.order_id}
                                    </div>
                                    <div class="customer-infor flex flex-column">
                                        <span style="font-weight: bold;
                                              color: #212529 !important;
                                              font-size: 16px;">Customer:</span>
                                        <table>
                                            <tr><th>Fullname</th><td><span>${requestScope.myOrder.account.full_name}</span></td></tr>
                                            <tr><th>Email</th><td><span>${requestScope.myOrder.account.email}</span></td></tr>
                                            <tr><th>Phone</th><td><span>${requestScope.myOrder.account.phone}</span></td></tr>
                                        </table>
                                    </div>
                                    <div class="order-infor flex flex-column">
                                        <span>
                                            <span class="order-infor-fields">Order Date:</span> <fmt:formatDate value="${requestScope.myOrder.orderDate}" type="both" dateStyle="short"></fmt:formatDate>
                                            </span>
                                            <span>
                                                <span class="order-infor-fields">Total Cost:</span> <fmt:formatNumber value="${requestScope.myOrder.total_price}" type="currency" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber>
                                            </span>
                                            <span>
                                                <span class="order-infor-fields" id="sale-name-origin" data-bind="${requestScope.myOrder.sale.user_id}" data-bind2="${requestScope.myOrder.sale.full_name}">Sale name:</span> ${requestScope.myOrder.sale.full_name}
                                        </span>
                                        <span>
                                            <span class="order-infor-fields">Order status:</span> ${requestScope.myOrder.orderStatus.describe}
                                        </span>
                                    </div>
                                </div>
                                <div class="receiver-infor">
                                    <span class="bold">Receiver information</span>
                                    <div class="receiver-infor-wrapper flex flex-column">
                                        <span><span class="receiver-fields">Name:</span> ${requestScope.myOrder.ship_name}</span>
                                        <span><span class="receiver-fields">Gender:</span> ${requestScope.myOrder.ship_gender ? "Male":"Female"}</span>
                                        <span><span class="receiver-fields">Email:</span> ${requestScope.myOrder.ship_email}</span>
                                        <span><span class="receiver-fields">Mobile:</span> ${requestScope.myOrder.ship_mobile}</span>
                                        <span><span class="receiver-fields">Address:</span> ${requestScope.myOrder.ship_address}</span>
                                        <span><span class="receiver-fields">City:</span> ${requestScope.myOrder.ship_city}</span>
                                    </div>
                                </div>
                                <div class="receiver-infor">
                                    <span class="bold">Sale note</span>
                                    <div class="receiver-infor-wrapper flex flex-column">
                                        <textarea disabled class="sale-note" id="sale-note-origin">${requestScope.myOrder.sale_note}</textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="right" style="width: 75%">
                            <c:set value="${requestScope.myOrder.orderDetailList}" var="orderDetails"></c:set>
                                <table class="table">
                                    <tbody>
                                    <c:forEach items="${orderDetails}" var="i">

                                        <tr class="product-item">
                                            <td class="product-thumbnail-td"><img src="../${i.product.thumbnail}" alt="" class="product-thumbnail"></td>
                                            <td class="product-name-wrapper">
                                                <div>
                                                    <h5 class="orderdetail-product-name"><a href="../product?id=${i.product.product_id}">${i.product.name}</a></h5>
                                                    <h6 class="card-subtitle mb-2 text-muted">Category: ${i.category.category_name}</h6>
                                                    <h6 class="card-subtitle mb-2 text-muted">SubCategory: ${i.product.subCategory.name}</h6>
                                                    <p><fmt:formatNumber type="currency" value="${i.price}" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></p>

                                                    </div>
                                                </td>
                                                <td class="product-quantity">
                                                    <div>

                                                        <span>Quantity</span>
                                                        <p class="quantity">${i.quantity}</p>
                                                </div>
                                            </td>
                                            <td class="product-subtotal">
                                                <div>

                                                    <span>Sub Total</span>
                                                    <p class="subtotal"><fmt:formatNumber type="currency" value="${i.price * i.quantity}" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></p>
                                                    </div>
                                                </td>
                                            </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <c:if test="${requestScope.permission}">
                        <div class="row flex product-details-action">
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#edit-sale-order" id="edit-btn">
                                Edit
                            </button>
                            <c:if test="${sessionScope.account.role_id == 4}">                                
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#assign-sale" id="assign-btn">
                                    Assign
                                </button>
                            </c:if>

                            <!-- Modal -->
                            <div class="modal fade" id="edit-sale-order" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Edit order</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="orderdetails" method="post" id="edit-order-frm">
                                                <div style="height: 40px; line-height: 40px;">

                                                    <span style="font-weight: bold;">Order status:</span><select name="newStatus" id="newStatus" value-holder="${requestScope.myOrder.orderStatus.id}">
                                                        <c:forEach items="${requestScope.statusList}" var="i">
                                                            <option value="${i.id}" ${requestScope.myOrder.orderStatus.id == i.id?"selected":""}>${i.describe}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <br>
                                                <label for="newNote">Sale note:</label>
                                                <textarea name="newNote" id="newNote">${requestScope.myOrder.sale_note}
                                                </textarea>
                                                <input type="hidden" name="action" value="edit"/>
                                                <input type="hidden" name="orderId" value="${requestScope.myOrder.order_id}"/>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="close">Close</button>
                                            <button type="button" class="btn btn-primary" aria-label="save">Save changes</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="assign-sale" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Assign Sale</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <span>Assign for other sale:</span>
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-danger dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" id="btn-sale-name">
                                                    ${requestScope.myOrder.sale.full_name}
                                                </button>
                                                <ul class="dropdown-menu" id="myDropdown" style="    max-height: 70vh;
                                                    overflow-y: auto;">
                                                    <input type="text" placeholder="search" id="myInput" onkeyup="filterFunction();"/>
                                                    <c:forEach items="${requestScope.saleList}" var="i">
                                                        <li><a class="dropdown-item" data-bind="${i.user_id}" onclick="changeValue(this);">${i.full_name}</a></li>
                                                        </c:forEach>
                                                </ul>
                                            </div>
                                            <form action="orderdetails" method="post" id="assign-frm">
                                                <input type="hidden" id="newSaleId" value="${requestScope.myOrder.sale.user_id}" name="newSaleId"/>
                                                <input type="text" value="assign" name="action" hidden/>
                                                <input type="hidden" name="orderId" value="${requestScope.myOrder.order_id}"/>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="close">Close</button>
                                            <button type="button" class="btn btn-primary" aria-label="save">Save changes</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </section>
            </aside>
        </div>
    </body>
    <script src="../js/salefunction.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</html>