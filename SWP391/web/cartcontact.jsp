<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Contacts</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/stylecartcontact.css"/>
        <!--<link rel="stylesheet" href="css/stylecartcontact.css"/>-->
    </head>
    <body>
        <c:set var="o" value="${requestScope.cart}" />
        <c:set var="freight" value="${requestScope.cart.freight}" />
        <div class="header">
            <c:import url="navbar.jsp"></c:import>
            </div>
            <div class="container-lg mb-5">
                <nav style="--bs-breadcrumb-divider: '>'" aria-label="breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="showcart" class="text-decoration-none text-muted">Cart Details</a></li>
                        <li class="breadcrumb-item active" aria-current="page">
                            Cart Contacts
                        </li>
                    </ol>
                </nav>
                <div class="row">
                    <div class="col-3">
                        <div class="sider">
                        <c:import url="sider.jsp"></c:import>
                        </div>
                    </div>
                    <div class="col">
                        <div class="p-3 content shadow-sm mb-3">
                            <div class="text-center">
                                <h3 class="pb-3 text-uppercase font-weight-bold">Cart Contact</h3>
                            </div>
                            <form class="row g-3" id="checkout" action="checkout" method="post">
                                <div class="col-6">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Full name*</span>
                                        <input type="text" class="form-control" name="name"
                                               value="${requestScope.cus.full_name}" aria-describedby="basic-addon1" required>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="basic-addon1">Phone</span>
                                    <input type="tel" class="form-control" placeholder="+84" name="phone" value="${requestScope.cus.phone}"
                                           aria-label="tel" aria-describedby="basic-addon1" required>
                                    
                                </div>
                            </div>
                            <div class="col-8">
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="basic-addon1">Email</span>
                                    <input type="mail" class="form-control" placeholder="email" name="email" value="${requestScope.cus.email}"
                                           aria-label="email" aria-describedby="basic-addon1" required>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="basic-addon1">City</span>
                                    <input type="text" class="form-control" placeholder="city" name="city" value="${requestScope.cus.city}"
                                           aria-label="city" aria-describedby="basic-addon1" required>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="basic-addon1">Gender</span>
                                    <select name="gender" id="inputState" class="form-select" required>
                                    <option selected disabled hidden>Choose...</option>
                                    <option value="1"<c:if test="${requestScope.cus.gender}">selected</c:if> >Male</option>
                                    <option  value="0"<c:if test="${!requestScope.cus.gender}">selected</c:if>>Female</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-8">
                                <div class="input-group mb-3">
                                    <span class="input-group-text" id="basic-addon1">Address</span>
                                    <input type="text" class="form-control" placeholder="Address"
                                           name="address"
                                            value="${requestScope.cus.address}"
                                           aria-label="address" aria-describedby="basic-addon1" required>
                                </div>
                            </div>
                           <div class="mb-3">
                            <label for="note" class="form-label">Note</label>
                            <textarea class="form-control" id="note" form="checkout" name="note" rows="3"></textarea>
                          </div>        
<!--                            <div>
                                Already have an account? <a href="login">Login</a><br />
                            </div>-->
                            <div class="col-12">
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a class="btn" href="showcart">Back to cart</a>
                                    <button class="btn btn-outline-primary" type="submit">Order Now</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="p-3 content shadow-sm mb-3">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                        <th colspan="2">ID</th>
                                        <th colspan="2">Product</th>
                                                <th colspan="1">
                                                    <div class="price-cart-contact">
                                                        Price
                                                    </div>
                                            </th>
                                    <th class="text-center">Quantity</th>
                                    <th class="text-end " >Total Cost</th>
                                </tr>
                            </thead>
                            <tbody class="table-group-divider">
                                <c:forEach items="${o.items}" var="i">
                                    <tr>
                                        <td colspan="1" style="width: 10%">
                                            <p class="mb-0 product-id-cart-contact" style="font-size: 18px">
                                                 ${i.product.product_id}
                                            </p>
                                        </td>
                                        <td colspan="2" style="width: 10%">
                                            <img src="${i.product.thumbnail}" alt="" width="70" class="img-fluid rounded shadow-sm">
                                        </td>
                                        <td class="align-middle">
                                            <a href="product?id=${i.product.product_id}" class="text-decoration-none text-muted"><p class="mb-0 product-title">${i.product.briefInfor}</p></a>
                                        </td>
                                        <td class="align-middle">
                                              <p class="d-flex justify-content-center ">
                                                <c:if test="${i.product.sale_price != 0}"><fmt:formatNumber value="${i.product.sale_price}" type="currency" currencySymbol="đ"/></c:if>
                                                <c:if test="${i.product.sale_price == 0}"><fmt:formatNumber value="${i.product.original_price}" type="currency" currencySymbol="đ" /></c:if>
                                            </p>
                                        </td>
                                        <td class="align-middle">
                                            <p class="text-center mb-0">${i.quantity}</p>
                                        </td>
                                        <td class="align-middle ">
                                            <p class="text-end mb-0 price-item">
                                                <c:if test="${i.product.sale_price != 0}"><fmt:formatNumber value="${i.product.sale_price*i.quantity}" type="currency" currencySymbol="đ"/></c:if>
                                                <c:if test="${i.product.sale_price == 0}"><fmt:formatNumber value="${i.product.original_price*i.quantity}" type="currency" currencySymbol="đ" /></c:if>
                                            </p>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <ul class="list-unstyled mb-4">
                            <li class="d-flex justify-content-between py-3 border-bottom" price-item>Sub Total
                                <span class="price-item"><fmt:formatNumber value="${o.totalMoney}" type="currency" currencySymbol="đ"/></span>
                            </li>
                             <li class="d-flex justify-content-between py-3 border-bottom">Shopping Fee
                                 <span class="price-item"><fmt:formatNumber value="${freight}" type="currency" currencySymbol="đ"/></span>
                            </li>
                            <li class="d-flex justify-content-between py-3 border-bottom">Total Cost
                                <h5 class="font-weight-bold price-item"><fmt:formatNumber value="${o.totalMoney+freight}" type="currency" currencySymbol="đ"/></h5>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="js/form.js"></script>
    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script>
        const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
        const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
    </script>
</html>
