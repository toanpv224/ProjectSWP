<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Completion</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!--CSS-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css"/>
        <link rel="stylesheet" href="css/stylecartcontact.css"/>
        <link rel="stylesheet" href="css/stylecartcompletition.css"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <c:set var="o" value="${requestScope.cart}" />
        <c:set var="order" value="${requestScope.order}" />
       
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
                        <li class="breadcrumb-item active" aria-current="page">
                            Cart Completion
                        </li>
                    </ol>
                </nav>
                <div class="row">
                    <div class="col-3">
                    <c:import url="sider.jsp"></c:import>
                    </div>
                    <div class="col">
                        <div class="p-3 bg-white rounded shadow-sm mb-3">
                            <div>ID:${order.order_id}</div>
                            <!--cart status-->
                            <div class="status">
                                <div class="stepper">
                                    
                                <c:if test="${order.status==0||order.status==6}">
                                    <div class="stepper__step ">
                                        <div class="stepper__step-icon " style="border-color: #df8650bd;
    color: red;">
                                            <svg enable-background="new 0 0 32 32" viewBox="0 0 32 32" x="0" y="0" class="shopee-svg-icon icon-order-problem">
                                            <g>
                                            <g>
                                            <path d="m5 3.4v23.7c0 .4.3.7.7.7.2 0 .3 0 .3-.2.5-.4 1-.5 1.7-.5.9 0 1.7.4 2.2 1.1.2.2.3.4.5.4s.3-.2.5-.4c.5-.7 1.4-1.1 2.2-1.1s1.7.4 2.2 1.1c.2.2.3.4.5.4s.3-.2.5-.4c.5-.7 1.4-1.1 2.2-1.1.9 0 1.7.4 2.2 1.1.2.2.3.4.5.4s.3-.2.5-.4c.5-.7 1.4-1.1 2.2-1.1.7 0 1.2.2 1.7.5.2.2.3.2.3.2.3 0 .7-.4.7-.7v-23.7z" fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3">

                                            </path>
                                            </g>
                                            <line fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3" x1="16" x2="16" y1="9" y2="15">

                                            </line>
                                            <circle cx="16" cy="20.5" r="2" stroke="none">

                                            </circle>
                                            </g>
                                            </svg>
                                        </div>
                                        <div class="stepper__step-text">
                                            Order has been cancelled</div>
                                        <div class="stepper__step-date">
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${order.status>0}">
                                    <div class="stepper__step stepper__step--finish">
                                        <div class="stepper__step-icon   <c:if test="${order.status>0}">stepper__step-icon--finish</c:if>">
                                                <svg enable-background="new 0 0 32 32" viewBox="0 0 32 32" x="0" y="0"
                                                     class="shopee-svg-icon icon-order-order">
                                                <g>
                                                <path
                                                    d="m5 3.4v23.7c0 .4.3.7.7.7.2 0 .3 0 .3-.2.5-.4 1-.5 1.7-.5.9 0 1.7.4 2.2 1.1.2.2.3.4.5.4s.3-.2.5-.4c.5-.7 1.4-1.1 2.2-1.1s1.7.4 2.2 1.1c.2.2.3.4.5.4s.3-.2.5-.4c.5-.7 1.4-1.1 2.2-1.1.9 0 1.7.4 2.2 1.1.2.2.3.4.5.4s.3-.2.5-.4c.5-.7 1.4-1.1 2.2-1.1.7 0 1.2.2 1.7.5.2.2.3.2.3.2.3 0 .7-.4.7-.7v-23.7z"
                                                    fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10"
                                                    stroke-width="3">

                                                </path>
                                                <g>
                                                <line fill="none" stroke-linecap="round" stroke-miterlimit="10" stroke-width="3" x1="10"
                                                      x2="22" y1="11.5" y2="11.5">

                                                </line>
                                                <line fill="none" stroke-linecap="round" stroke-miterlimit="10" stroke-width="3" x1="10"
                                                      x2="22" y1="18.5" y2="18.5">

                                                </line>
                                                </g>
                                                </g>
                                                </svg>
                                            </div>
                                            <div class="stepper__step-text">
                                                Submitted</div>
                                            <div class="stepper__step-date">
                                            ${order.order_Date}</div>
                                    </div>


                                    <div class="stepper__step stepper__step--finish">
                                        <div class="stepper__step-icon <c:if test="${order.status>1}">stepper__step-icon--finish</c:if>">
                                                <svg enable-background="new 0 0 32 32" viewBox="0 0 32 32" x="0" y="0"
                                                     class="shopee-svg-icon icon-order-paid">
                                                <g>
                                                <path clip-rule="evenodd"
                                                      d="m24 22h-21c-.5 0-1-.5-1-1v-15c0-.6.5-1 1-1h21c .5 0 1 .4 1 1v15c0 .5-.5 1-1 1z"
                                                      fill="none" fill-rule="evenodd" stroke-miterlimit="10" stroke-width="3">

                                                </path>
                                                <path clip-rule="evenodd"
                                                      d="m24.8 10h4.2c.5 0 1 .4 1 1v15c0 .5-.5 1-1 1h-21c-.6 0-1-.4-1-1v-4" fill="none"
                                                      fill-rule="evenodd" stroke-miterlimit="10" stroke-width="3">

                                                </path>
                                                <path
                                                    d="m12.9 17.2c-.7-.1-1.5-.4-2.1-.9l.8-1.2c.6.5 1.1.7 1.7.7.7 0 1-.3 1-.8 0-1.2-3.2-1.2-3.2-3.4 0-1.2.7-2 1.8-2.2v-1.3h1.2v1.2c.8.1 1.3.5 1.8 1l-.9 1c-.4-.4-.8-.6-1.3-.6-.6 0-.9.2-.9.8 0 1.1 3.2 1 3.2 3.3 0 1.2-.6 2-1.9 2.3v1.2h-1.2z"
                                                    stroke="none">

                                                </path>
                                                </g>
                                                </svg>
                                            </div>
                                            <div class="stepper__step-text">
                                                payment information confirmation
                                            </div>
                                            <div class="stepper__step-date">
                                                </div>
                                        </div>

                                        <div class="stepper__step stepper__step--pending">
                                            <div class="stepper__step-icon  <c:if test="${order.status>2}">stepper__step-icon--finish</c:if>">
                                                <svg enable-background="new 0 0 32 32" viewBox="0 0 32 32" x="0" y="0"
                                                     class="shopee-svg-icon icon-order-received">
                                                <g>
                                                <polygon fill="none"
                                                         points="2 28 2 19.2 10.6 19.2 11.7 21.5 19.8 21.5 20.9 19.2 30 19.1 30 28"
                                                         stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3">

                                                </polygon>
                                                <polyline fill="none" points="21 8 27 8 30 19.1" stroke-linecap="round"
                                                          stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3">

                                                </polyline>
                                                <polyline fill="none" points="2 19.2 5 8 11 8" stroke-linecap="round"
                                                          stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3">

                                                </polyline>
                                                <line fill="none" stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10"
                                                      stroke-width="3" x1="16" x2="16" y1="4" y2="14">

                                                </line>
                                                <path
                                                    d="m20.1 13.4-3.6 3.6c-.3.3-.7.3-.9 0l-3.6-3.6c-.4-.4-.1-1.1.5-1.1h7.2c.5 0 .8.7.4 1.1z"
                                                    stroke="none">

                                                </path>
                                                </g>
                                                </svg>
                                            </div>
                                            <div class="stepper__step-text">
                                                active</div>
                                            <div class="stepper__step-date">

                                            </div>
                                        </div>
                                        <div class="stepper__step stepper__step--finish">
                                            <div class="stepper__step-icon  <c:if test="${order.status>3}">stepper__step-icon--finish</c:if>">
                                                <svg enable-background="new 0 0 32 32" viewBox="0 0 32 32" x="0" y="0"
                                                     class="shopee-svg-icon icon-order-shipping">
                                                <g>
                                                <line fill="none" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3" x1="18.1"
                                                      x2="9.6" y1="20.5" y2="20.5">

                                                </line>
                                                <circle cx="7.5" cy="23.5" fill="none" r="4" stroke-miterlimit="10" stroke-width="3">

                                                </circle>
                                                <circle cx="20.5" cy="23.5" fill="none" r="4" stroke-miterlimit="10" stroke-width="3">

                                                </circle>
                                                <line fill="none" stroke-miterlimit="10" stroke-width="3" x1="19.7" x2="30" y1="15.5"
                                                      y2="15.5">

                                                </line>
                                                <polyline fill="none" points="4.6 20.5 1.5 20.5 1.5 4.5 20.5 4.5 20.5 18.4"
                                                          stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3">

                                                </polyline>
                                                <polyline fill="none" points="20.5 9 29.5 9 30.5 22 24.7 22" stroke-linejoin="round"
                                                          stroke-miterlimit="10" stroke-width="3">

                                                </polyline>
                                                </g>
                                                </svg>
                                            </div>
                                            <div class="stepper__step-text">
                                                transporting </div>
                                            <div class="stepper__step-date">
                                            </div>
                                        </div>
                                        <div class="stepper__step">
                                            <div class="stepper__step-icon  <c:if test="${order.status>4}">stepper__step-icon--finish</c:if>">
                                                <svg enable-background="new 0 0 32 32" viewBox="0 0 32 32" x="0" y="0"
                                                     class="shopee-svg-icon icon-order-rating">
                                                <polygon fill="none"
                                                         points="16 3.2 20.2 11.9 29.5 13 22.2 19 24.3 28.8 16 23.8 7.7 28.8 9.8 19 2.5 13 11.8 11.9"
                                                         stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="3">

                                                </polygon>
                                                </svg>
                                            </div>
                                            <div class="stepper__step-text">
                                                Finish/rating</div>
                                            <div class="stepper__step-date">
                                            </div>
                                        </div>
                                        <div class="stepper__line">
                                            <div class="stepper__line-background" style="background: rgb(224, 224, 224);">
                                            </div>
                                            <!-- caculator status -->
                                            <div class="stepper__line-foreground"
                                                 style="width: calc((100% - 140px) * ${order.status/4}); background: rgb(45, 194, 88);">
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                       <c:if test="${order.status==1}">  
                            <!--return-->
                            <div class="_1umrlw">
                                <div class="_2c2kYQ">Warning!:If you do not complete order confirmation, the order will be deleted after 1 day by the administrator </div>
                                <div class="_2iv7q8">
                                    <button class="stardust-button stardust-button--primary _2x5SvJ"
                                            onclick="window.location.href = 'productslist'">Finish later</button>
                                </div>
                            </div>
                            <div class="_1umrlw">
                                <div class="_2c2kYQ">
                                    Note:You can cancel your order until the active status is confirmed
                                </div>
                                <form action="cancel_order" method="post">
                                <div class="_2iv7q8">
                                    <button  type="submit" class="stardust-button stardust-button--secondary _2x5SvJ">
                                        Cancel order
                                    </button>
                                <input hidden value="${order.order_id}" name="order_id"/>
                                </div>
                                </form>
                            </div>
                        </c:if>       
                       <c:if test="${order.status==2||order.status==3||order.status==4}">  
                            <!--return-->
                              <div class="_1umrlw">
                                <div class="_2c2kYQ"> Waiting to next processing</div>
                                <div class="_2iv7q8">
                                    <button class="stardust-button stardust-button--primary _2x5SvJ"
                                            onclick="window.location.href = 'productslist'">Back to product list</button>
                                </div>
                            </div>
<!--                            <div class="_1umrlw">
                                <div class="_2c2kYQ">Waiting to next processing </div>
                            </div>-->
                        </c:if>       
                       <c:if test="${order.status==5}">  
                            <!--return-->
                            <div class="_1umrlw">
                                <div class="_2c2kYQ">Order completion </div>
                            </div>
                        </c:if>       
                                
                            <!--line-->
                            <div class="j7z7l_">
                                <div class="_1AsWWl"></div>
                            </div>
                            <c:if test="${order.status==0||order.status>=2}"> 
                            <!--cart contact-->
                            <div class="text-center">
                                <h3 class="pb-3 text-uppercase font-weight-bold">Cart Contact</h3>
                            </div>
                            <form id="checkout" class="row g-3" action="cartcompletion" method="post">
                                <input hidden value="${order.order_id}" name="orderID">
                                <div class="col-6">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Full name*</span>
                                        <input disabled type="text" class="form-control" name="name"
                                               value="${order.ship_name}" aria-describedby="basic-addon1" required>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Phone</span>
                                        <input disabled type="tel" class="form-control" placeholder="+84" name="phone" value="${order.ship_mobile}"
                                               aria-label="tel" aria-describedby="basic-addon1" required>

                                    </div>
                                </div>
                                <div class="col-8">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Email</span>
                                        <input disabled type="mail" class="form-control" placeholder="email" name="email" value="${order.ship_email}"
                                               aria-label="email" aria-describedby="basic-addon1" required>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">City</span>
                                        <input disabled type="text" class="form-control" placeholder="city" name="city" value="${order.ship_city}"
                                               aria-label="city" aria-describedby="basic-addon1" required>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="input-group mb-3">
                                        <span class="input-group-text" id="basic-addon1">Gender</span>
                                        <select name="gender" id="inputState" class="form-select" required>
                                            <option disabled selected disabled hidden>Choose...</option>
                                            <option  disabled value="1"<c:if test="${order.ship_gender}">selected</c:if> >Male</option>
                                            <option  disabled value="0"<c:if test="${!order.ship_gender}">selected</c:if>>Female</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-8">
                                        <div class="input-group mb-3">
                                            <span class="input-group-text" id="basic-addon1">Address</span>
                                            <input disabled type="text" class="form-control" placeholder="Address"
                                                   name="address"
                                                   value="${order.ship_address}"
                                            aria-label="address" aria-describedby="basic-addon1" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="note" class="form-label">Note</label>
                                    <textarea disabled class="form-control" id="note" form="checkout" name="note" rows="3">${order.note}
                                    </textarea>
                                </div>        
                                <!--                            <div>
                                                                Already have an account? <a href="login">Login</a><br />
                                                            </div>-->

                        </div>

                        <div class="p-3 bg-white rounded shadow-sm cart-contain">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th >ID</th>
                                        <th colspan="2">Product</th>
                                        <th class="text-center">Price</th>
                                        <th class="text-center">Quantity</th>
                                        <th class="text-end">Sub total</th>
                                    </tr>
                                </thead>
                                <tbody class="table-group-divider">
                                    <c:forEach items="${o.items}" var="i">
                                        <tr>
                                            <td class="align-middle">
                                                <p class="mb-0 product-id-cart-contact">
                                                    ${i.product.product_id}
                                                </p>
                                            </td>
                                            <td style="width: 10%">
                                                <img src="${i.product.thumbnail}" alt="" width="10%" class="img-fluid rounded shadow-sm">
                                            </td>
                                            <td class="align-middle">
                                                <a href="product?id=${i.product.product_id}" class="text-decoration-none text-muted"><p class="mb-0 product-title">${i.product.briefInfor}</p></a>
                                            </td>
                                            <td class="align-middle">
                                                <span class="d-flex justify-content-center price-item">
                                                    <c:if test="${i.product.sale_price != 0}"><fmt:formatNumber value="${i.product.sale_price}" type="currency" currencySymbol="đ"/></c:if>
                                                    <c:if test="${i.product.sale_price == 0}"><fmt:formatNumber value="${i.product.original_price}" type="currency" currencySymbol="đ" /></c:if>
                                                    </span  >
                                                </td>
                                                <td class="align-middle">
                                                    <p class="text-center mb-0">${i.quantity}</p>
                                            </td>
                                            <td class="align-middle">
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
                                <li class="d-flex justify-content-between py-3 border-bottom">
                                    <div class="payment-container col-12" style=" pointer-events: none;">
                                        
                                        <div class="col-12 bg-white rounded main-banking">
                                            <div class="radio-toolbar">
                                                <input type="radio"
                                                       <c:if test="${order.payment=='Ship COD'||order.payment==null}">checked</c:if>
                                                       
                                                       id="radioApple" name="payment" value="1"  onclick="checkPayment(this.value)">
                                                <label for="radioApple">Ship COD</label>

                                                <input type="radio"
                                                       <c:if test="${order.payment=='BANK TRANSFER'}">checked</c:if>
                                                       id="radioBanana" name="payment"  value="2" onclick="checkPayment(this.value)">
                                                <label for="radioBanana">Banking Transfer</label>

                                                <input type="radio" 
                                                         <c:if test="${order.payment=='VN PAY'}">checked</c:if>
                                                       id="radioOrange" name="payment" value="3" onclick="checkPayment(this.value)">
                                                <label for="radioOrange">VNPAY QR</label> 
                                            </div>

                                           <div class="payment-content ship_cod">
                                            <c:import url="payment_ShipCOD.jsp"></c:import>
                                            </div>
                                           <div class="payment-content banking_system">
                                            <c:import url="payment_BankingSystem.jsp"></c:import>
                                            </div>
                                           <div class="payment-content vnpay">
                                            <c:import url="payment_VNPAY.jsp"></c:import>
                                            </div>
                                            <p>&nbsp;</p>
                                        </div>
                                    </div>
                                </li>
                                <li class="d-flex justify-content-between py-3 border-bottom">Sub Total
                                    <span class="price-item"><fmt:formatNumber value="${o.totalMoney}" type="currency" currencySymbol="đ"/></span>
                                </li>
                                <li class="d-flex justify-content-between py-3 border-bottom">Shopping Fee
                                    <input type="hidden" name="freight" value="${o.freight}">
                                   <span class="price-item"><fmt:formatNumber value="${o.freight}" type="currency" currencySymbol="đ"/></span>
                                </li>
                                <li class="d-flex justify-content-between py-3 border-bottom">Total Cost
                                    <h5 class="font-weight-bold price-item"><fmt:formatNumber value="${o.totalMoney}" type="currency" currencySymbol="đ"/></h5>
                                    <input type="hidden" name="total_price" value="${o.totalMoney+o.freight}"/>
                                </li>
                            </ul>
                                
                            <div class="col-12">
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <!--<a class="btn" href="showcart">Cancel order</a>-->
                                    <!--<button disabled class="btn btn-outline-primary" type="submit">Finish</button>-->
                                </div>
                            </div>
                     </form>
                   </c:if>              
                        <c:if test="${order.status>0&&order.status<2}"> 
                            
                       
                                 <!--cart contact-->
                            <div class="text-center title-item-pay">
                                <h3 class="pb-3 text-uppercase font-weight-bold ">Choose Payment Method</h3>
                            </div>
                                 
                        <div class="p-3 bg-white rounded shadow-sm cart-contain">
                            <table class="table table-hover">
                                <thead>
<!--                                    <tr>
                                        <th >ID</th>
                                        <th colspan="2">Product</th>
                                        <th class="text-center">Price</th>
                                        <th class="text-center">Quantity</th>
                                        <th class="text-end">Sub total</th>
                                    </tr>-->
                                </thead>
                                <tbody class="table-group-divider">
                                    
                               
                                </tbody>
                            </table>
                            <ul class="list-unstyled mb-4">
                                <li class="d-flex justify-content-between py-3 border-bottom">
                                    <div class="payment-container col-12">
                                        <div class="col-12">
<!--                                            <p class="total-cost-item">
                                                Total:
                                            </p>
                                            <span class="font-weight-bold price-item"><fmt:formatNumber value="${o.totalMoney}" type="currency" currencySymbol="đ"/></span>-->
                                        </div>
                                            
                                       
                                        <div class="col-12 bg-white rounded main-banking">
                                        <form action="cartcompletion" method="post" id="pay-ment">
                                              <input hidden value="${order.order_id}" name="orderID">
                                            <div class="radio-toolbar">
                                                <input type="radio"
                                                       <c:if test="${order.payment=='Ship COD'||order.payment==null}">checked</c:if>
                                                       
                                                       id="radioApple" name="payment" value="1"  onclick="checkPayment(this.value)">
                                                <label for="radioApple">Ship COD</label>

                                                <input type="radio"
                                                       <c:if test="${order.payment=='BANK TRANSFER'}">checked</c:if>
                                                       id="radioBanana" name="payment"  value="2" onclick="checkPayment(this.value)">
                                                <label for="radioBanana">Banking account</label>

                                                <input type="radio" 
                                                         <c:if test="${order.payment=='VN PAY'}">checked</c:if>
                                                       id="radioOrange" name="payment" value="3" onclick="checkPayment(this.value)">
                                                <label for="radioOrange">VNPAY QR</label> 
                                            </div>
                                        </form>             
                                           <div class="payment-content ship_cod">
                                            <c:import url="payment_ShipCOD.jsp"></c:import>
                                            </div>
                                           <div class="payment-content banking_system">
                                            <c:import url="payment_BankingSystem.jsp"></c:import>
                                            </div>
                                           <div class="payment-content vnpay">
                                            <c:import url="payment_VNPAY.jsp"></c:import>
                                            </div>
                                            <p>&nbsp;</p>
                                        </div>
                                    </div>
                                </li>
<!--                                <li class="d-flex justify-content-between py-3 border-bottom">Sub Total
                                    <span class="price-item"><fmt:formatNumber value="${o.totalMoney}" type="currency" currencySymbol="đ"/></span>
                                </li>
                                <li class="d-flex justify-content-between py-3 border-bottom">Shopping Fee
                                    <input type="hidden" name="freight" value="${o.freight}">
                                    <span class="price-item"><fmt:formatNumber value="${o.freight}" type="currency" currencySymbol="đ"/></span>
                                </li>
-->                                <li class="d-flex justify-content-between py-3 border-bottom">Total Cost
                                    <h5 class="font-weight-bold price-item"><fmt:formatNumber value="${o.totalMoney+o.freight}" type="currency" currencySymbol="đ"/></h5>
                                    <input type="hidden" name="total_price" value="${o.totalMoney+o.freight}"/>
                                </li>
                            </ul>
                                
                            <div class="col-12">
                                <div class="d-grid gap-2 d-md-flex  flex-ed justify-content-end">
                                    <button class="btn btn-outline-primary" type="button" onclick="ChoosePayment()">submit</button>
                                </div>
                            </div>
                     
                        </div>
                   </c:if>              


                    </div>
                </div>
            </div>
                                   
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

    <script src="https://unpkg.com/@popperjs/core@2"></script>
    <script src="js/form.js"></script>
    <script>
                                            const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
                                            const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
    </script>

</html>