<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html id="content">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Cart details</title>
        <!--CSS-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/stylecartdetails.css" />
        <link rel="stylesheet" href="css/styleupdateorder.css"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <c:set var="o" value="${requestScope.order}"/>
        <c:set var="items" value="${o.orderDetailList}"/>
        <div class="header">
            <c:import url="navbar.jsp"></c:import>
            </div>
            <div class="container-lg" >
                <div class="row">
                    <div class="col-3">
                    <c:import url="sider.jsp"></c:import>
                    </div>
                    <div class="col-9" >
                        <div class="p-3 bg-white rounded shadow-sm mb-3">
                            <div class="text-center">
                                <h3 class="pb-3 text-uppercase font-weight-bold" style="color: #000;">My Order</h3>
                            </div>
                            <div style="    color: #000000c7;
                                 font-size: 65%;">
                                Note*: You can only change receiver information. If you want to change ordered products's quantity, please cancel and reorder.
                            </div>
                            <table class="table table-hover">
                                <thead>
                                    <tr class="text-center">

                                        <th>ID</th>
                                        <th>Product</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Sub total</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody class="table-group-divider">
                                <c:forEach items="${items}" var="i">

                                    <tr style="height: 100px">
                                        <td class="align-middle">
                                            <p class="mb-0 product-id-cart-contact">
                                                ${i.product.product_id}
                                            </p>
                                        </td>
                                        <td class="align-middle" style="width: 30%">
                                            <div class="row">
                                                <div class="col-5 my-auto">
                                                    <img src="${i.product.thumbnail}" alt="" class="img-fluid rounded" style="cursor: pointer"/>
                                                </div>
                                                <a href="product?id=${i.product.product_id}" class="text-decoration-none text-muted col" style="align-self: center">
                                                    <b class="product-title">${i.product.briefInfor}</b>
                                                </a>
                                            </div>
                                        </td>
                                        <td class="align-middle" id="price-items">
                                            <b class="d-flex justify-content-center" >
                                                <c:if test="${i.product.sale_price != 0}"><fmt:formatNumber value="${i.product.sale_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>
                                                <c:if test="${i.product.sale_price == 0}"><fmt:formatNumber value="${i.product.original_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>
                                                </b>
                                            </td>
                                            <td class="align-middle">
                                                <div class="d-flex justify-content-center">
                                                    <div class="change-item">
                                                        <b id="quantity" class="quantity">${i.quantity}</b>
                                                </div>
                                            </div>
                                        </td>
                                        <!--format number of price-->
                                        <td class="align-middle" >

                                            <b class="d-flex justify-content-center"  style="color: red;">
                                                <c:if test="${i.product.sale_price != 0}"><fmt:formatNumber value="${i.product.sale_price*i.quantity}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>
                                                <c:if test="${i.product.sale_price == 0}"><fmt:formatNumber value="${i.product.original_price*i.quantity}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>
                                                </b> 
                                            </td>

                                        </tr>
                                </c:forEach>
                                <!--</div>-->
                            </tbody>
                        </table>
                        <div style="border-bottom: 1px solid black; margin-top: 10px; margin-bottom: 10px;"></div>
                        <div class="p-3 bg-white rounded shadow-sm">
                            <div class="p-4">
                                <ul class="list-unstyled mb-4">
                                    <li class="d-flex justify-content-between py-3 border-bottom"><b class="text-muted" >Sub total</b>
                                        <h5 class="font-weight-bold" id="subtotal-final" style="color: red;"><fmt:formatNumber value="${o.total_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></h5>
                                    </li>
                                    <li class="d-flex justify-content-between py-3 border-bottom"><b class="text-muted">Shipping fee</b><b style="color: red;"><c:if test="${o.freight != 0}"><fmt:formatNumber value="${o.freight}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if><c:if test="${o.freight == 0}">Free ship</c:if></b></li>
                                        <li class="d-flex justify-content-between py-3 border-bottom"><b class="text-muted">Total</b>
                                                <h5 class="font-weight-bold" id="total-final" style="color: red;"><fmt:formatNumber value="${o.total_price + o.freight}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></h5>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- Shopping cart table -->
                        <div class="update-form shadow-sm" style="margin-top: 15px;" id="updateForm">
                            <form action="updateorder" method="get">
                                <div class="receiver-information" style="border: none;">
                                    <h4>Receiver's information</h4>
                                    <input type="hidden" value="${o.order_id}" name="orderId"/>
                                    <div class="flex infor-row">
                                        <div class="receiver-fullname flex">
                                            <span><label for="receiver-name">Full name:</label></span>
                                            <span><input type="text" placeholder="Receiver name" id="receiver-name" name="shipName" value="${o.ship_name}" required/></span>
                                        </div>
                                        <div class="receiver-gender flex">
                                            <span style="margin-right: 15px; font-weight: bold; color: 21252b;">Gender: </span>
                                            <input type="radio" name="receiverGender" <c:if test="${o.ship_gender}">checked</c:if> value="1"/> <span style="font-family: 'unicons-line';">Male</span>
                                            <input type="radio" name="receiverGender" <c:if test="${!o.ship_gender}">checked</c:if> value="0"/> <span style="font-family: 'unicons-line';">Female</span>
                                            </div>
                                        </div>
                                        <div class="receiver-email infor-row">
                                            <span><label for="email">Email</label></span>
                                            <span><input type="email" id="email" name="shipEmail" value="${o.ship_email}" required/></span>
                                    </div>
                                    <div class="receiver-mobile infor-row">
                                        <span><label for="receiver-mobile">Mobile</label></span>
                                        <span><input type="text" id="receiver-mobile" name="shipMobile" value="${o.ship_mobile}" required/></span>
                                    </div>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <c:if test="${requestScope.message != null}"><span style="    color: red;
                                          font-weight: 500;
                                          font-family: system-ui;
                                          display: flex;
                                          align-items: center;">${requestScope.message}</span></c:if>
                                        <button class="btn btn-outline-primary ms-3" type="submit">Save change</button>
                                    </div>
                                </form>

                            </div>
                            <!-- End -->

                        </div>
                    </div>
                </div>
            </div>
            <!--footer-->
        <c:import url="footer.jsp"></c:import>
        <!--end footer-->
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="js/script.js"></script>

    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script>
        const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
        const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
    </script>
</html>