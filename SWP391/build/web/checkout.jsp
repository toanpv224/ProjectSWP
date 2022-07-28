<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Cart details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style1.css" />
    </head>

    <body>
        
        <div class="header">
            <c:import url="navbar.jsp"></c:import>
            </div>
            <div class="container-lg mb-5">
                <div class="row">
                    <div class="col-3">
                        <c:import url="sider.jsp"></c:import>
                    </div>
                    <div class="col-9">
                        <div class="p-3 bg-white rounded shadow-sm mb-3">
                            <div class="text-center">
                                <h3 class="pb-3 text-uppercase font-weight-bold">My Cart</h3>
                            </div>
                            <!-- Shopping cart table -->
                            <table class="table table-hover">
                                <thead>
                                    <tr class="text-center">
                                        
                                        <th>ID</th>
                                        <th>Product</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Total cost</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody class="table-group-divider">
                                <c:forEach begin="1" end="4">
                                    <tr style="height: 100px">
                                        <td class="align-middle">
                                            <p class="mb-0 product-id-cart-contact">
                                                ID
                                            </p>
                                        </td>
                                        <td class="align-middle" style="width: 30%">
                                            <div class="row">
                                                <div class="col-5 my-auto">
                                                    <img src="data:image/jpeg;base64,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" alt=""
                                                         class="img-fluid rounded" style="cursor: pointer"/>
                                                </div>
                                                    <!--<a href="product?id=${i.product.product_id}" class="text-decoration-none text-muted col" style="align-self: center">-->
                                                    <b class="product-title">Title</b>
                                                </a>
                                            </div>
                                        </td>
                                        <td class="align-middle">
                                            <b class="d-flex justify-content-center">
                                                <%--<c:if test="${i.product.sale_price != 0}"><fmt:formatNumber value="${i.product.sale_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>--%>
                                                <%--<c:if test="${i.product.sale_price == 0}"><fmt:formatNumber value="${i.product.original_price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>--%>
                                                 100.000đ
                                            </b>
                                        </td>
                                        <td class="align-middle">
                                            <div class="d-flex justify-content-center">
                                                <div class="change-item">
                                                    <a class="btn btn-outline-success" href="process?num=-1&id=${i.product.product_id}">-</a>
                                                    <b>
                                                        <!--${i.quantity}-->
                                                    </b>
                                                    <a class="btn btn-outline-success" href="process?num=1&id=${i.product.product_id}">+</a>
                                                    <div class="addmore-item">
                                                        <span style="color: #6b615f"><c:if test="${i.product.unit_in_stock==i.quantity}">
                                                            You got maximum product</span>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="align-middle">
                                            <b class="d-flex justify-content-center">
                                                <c:if test="${i.product.sale_price != 0}"><fmt:formatNumber value="${i.product.sale_price*i.quantity}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>
                                                <c:if test="${i.product.sale_price == 0}"><fmt:formatNumber value="${i.product.original_price*i.quantity}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></c:if>
                                            </b>
                                        </td>
                                        <td class="align-middle">
                                            <form action="process" method="post" class="d-flex justify-content-center">
                                                <input type="hidden" name="id" value="${i.product.product_id}" />
                                                <button type="submit" class="btn btn-danger">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <!-- End -->
                        <section class="home-sp">
                            <div class="page-trang table">
                                <a href="#"></a>
                                <a href="#"></a>
                            </div>
                        </section>

                    </div>
                    <div class="p-3 bg-white rounded shadow-sm">
                        <div class="text-center">
                            <h3 class="pb-3 text-uppercase font-weight-bold">Cart detail</h3>
                        </div>
                        <div class="p-4">
                            <ul class="list-unstyled mb-4">
                                <li class="d-flex justify-content-between py-3 border-bottom"><b class="text-muted">Sub total</b>
                                    <h5 class="font-weight-bold"><fmt:formatNumber value="${o.totalMoney}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></h5>
                                </li>
                                <li class="d-flex justify-content-between py-3 border-bottom"><b class="text-muted">Shipping fee</b><b>Free ship</b></li>
                                <li class="d-flex justify-content-between py-3 border-bottom"><b class="text-muted">Total</b>
                                    <h5 class="font-weight-bold"><fmt:formatNumber value="${o.totalMoney}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></h5>
                                </li>
                            </ul>
                            <div class="d-flex justify-content-end">
                                <a href="productslist" class="btn">Click to continue shopping</a>
                                <a href="checkout" class="btn btn-outline-primary ms-3">Order Now</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
    </body>
</html>