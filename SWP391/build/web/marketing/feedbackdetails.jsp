<%@page import="dal.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="feedback" value="${requestScope.feedback}"></c:set>
<fmt:setLocale value = "vi_VN"/>
<!Doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Marketing - Feedback Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/style.css">
        <!--            <link rel="stylesheet" href="../asserts/fontawesome-free-6.0.0/css/all.min.css"/>
                    <link rel="stylesheet" href="../asserts/fontawesome-free-6.0.0/css/fontawesome.min.css"/>-->
        <!--<script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
        <style>
            i.star-rate.checked{
                color: orange;
            }
        </style>

    </head>
    <body>
        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <div class="container-lg my-5">
                    <div>
                        <h3>Feedback Details</h3>
                        <div class="row">
                            <div class="col-4">
                                <div class="p-3 bg-white rounded shadow-sm">
                                    <h5>User information</h5>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <th>Fullname:</th>
                                                <td>${feedback.fullname}</td>
                                        </tr>
                                        <tr>
                                            <th>Phone:</th>
                                            <td>${feedback.phone}</td>
                                        </tr>
                                        <tr>
                                            <th>Gender:</th>
                                            <td>${feedback.gender ? "Male":"Female"}</td>
                                        </tr>
                                        <tr>
                                            <th>Email:</th>
                                            <td>${feedback.email}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <c:if test="${requestScope.isproductfeedback}">
                                <div class="p-3 bg-white rounded shadow-sm mt-3">
                                    <h5>Product Information</h5>
                                    <div>
                                        <div class="card">
                                            <a href="product?id=${feedback.product.product_id}" class="product-thumbnail"><img src="../${feedback.product.thumbnail}" class="card-img-top" alt="..." id="product-thumbnail" onmouseover="zoomIn(this);" onmouseout="zoomOut(this);"></a>
                                            <div class="card-body">
                                                <a href="product?id=${feedback.product.product_id}" data-bs-toggle="tooltip" title="${feedback.product.name}">
                                                    <h6 class="card-title product-title font-weight-bold">${feedback.product.name}</h6>
                                                </a>
                                                <h6 class="card-subtitle mb-2 text-muted">
                                                    <c:if test="${feedback.product.sale_price != 0}"><span class="text-decoration-line-through"><fmt:formatNumber value="${feedback.product.original_price}" type="currency"/></span> <span style="color: red;"> <fmt:formatNumber value="${feedback.product.sale_price}" type="currency"/></span></c:if>
                                                    <c:if test="${feedback.product.sale_price == 0}"><span><fmt:formatNumber value="${feedback.product.original_price}" type="currency" /></span></c:if>
                                                    </h6>
                                                    <p class="card-text">${feedback.product.briefInfor}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="col-8">
                            <div class="p-3 bg-white rounded shadow-sm">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div><h5>Status</h5></div>
                                    <div>
                                        <div class="form-check form-switch form-switch-md">
                                            <input class="featured-btn form-check-input" id="feedback-status-checkbox" style="cursor: pointer" type="checkbox" role="switch" data-bs-toggle="modal" data-bs-target="${feedback.status==1 ? "#confirm-hide-feedback" : "#confirm-show-feedback"}" <c:if test="${feedback.status==1}">checked</c:if>/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="py-3 bg-white rounded shadow-sm mt-3">
                                    <div class="px-3">
                                        <div class="d-flex justify-content-between">
                                                    <div><b>ID: </b>${feedback.id}</div>
                                        <div><fmt:formatDate value="${feedback.feedbackDate}" pattern="HH:mm dd/MM/yyyy"/></div>
                                    </div>
                                </div>
                                <hr>
                                <div class="px-3">
                                    <div>
                                        <i class="fa-solid fa-star star-rate ${feedback.star >= 1 ? "checked" : ""}"></i>
                                        <i class="fa-solid fa-star star-rate ${feedback.star >= 2 ? "checked" : ""}"></i>
                                        <i class="fa-solid fa-star star-rate ${feedback.star >= 3 ? "checked" : ""}"></i>
                                        <i class="fa-solid fa-star star-rate ${feedback.star >= 4 ? "checked" : ""}"></i>
                                        <i class="fa-solid fa-star star-rate ${feedback.star >= 5 ? "checked" : ""}"></i>
                                    </div>
                                    <div class="">
                                        <div class="my-3">
                                            ${feedback.content}
                                        </div>
                                        <div style="max-width: 300px">
                                            <c:if test="${feedback.image_url != null }"><img src="../${feedback.image_url}"></c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal" id="confirm-show-feedback" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Do you want to show this feedback?</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                            <button type="button" class="btn btn-primary" onclick="showFeedback(${requestScope.isproductfeedback ? "2":"1"}, ${feedback.id})">Yes</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal" id="confirm-hide-feedback" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Do you want to hide this feedback?</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                            <button type="button" class="btn btn-primary" onclick="hideFeedback(${requestScope.isproductfeedback ? "2":"1"}, ${feedback.id})">Yes</button>
                        </div>
                    </div>
                </div>
            </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../js/marketing/feedbackdetails.js"></script>

</html>