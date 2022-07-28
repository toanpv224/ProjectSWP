<%@page import="dal.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!Doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <title>Marketing - Feedbacks List</title>
        <link rel="stylesheet" href="../css/style.css">
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <style>
            i.star-rate.checked{
                color: orange;
            }
            #product-list-container{
                height: 300px;
                overflow-y: scroll;
                overflow-x: hidden;
            }
        </style>

    </head>

    <body>
        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <!--side bar-->
                <div class="container-lg my-5">
                    <div class="p-3 bg-white rounded shadow-sm">
                        <div class="top-head">
                            <form id="feedbackSearchForm" action="feedbackslist" method="post">
                                <input type="number" id="page" name="page" value="${requestScope.pageNumber}" hidden>
                            <div class="mb-3 d-flex justify-content-between">
                                <div>
                                    <a class="btn btn-outline-primary cursor-pointer"  data-bs-toggle="modal" data-bs-target="#modal-filer">Choose Filter</a>
                                    <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modal-search"><i class="fa-solid fa-magnifying-glass"></i></button>
                                </div>
                                <div>
                                    <div id="modal-filer" class="modal modal-xl fade" style="transition: .2s">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="btn-close"  data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-6 mb-3">
                                                            <h3>Status</h3>
                                                            <div class="d-flex">
                                                                <div class="me-3">
                                                                    <label>
                                                                        <input class="me-2" type="radio" name="status" value="-1" ${requestScope.status eq -1 ? "checked": ""}>All
                                                                    </label>
                                                                </div>
                                                                <div class="me-3">
                                                                    <label>
                                                                        <input class="me-2" type="radio" name="status" value="1" ${requestScope.status eq 1 ? "checked": ""}>Showed
                                                                    </label>
                                                                </div>
                                                                <div>
                                                                    <label>
                                                                        <input class="me-2" type="radio" name="status" value="0" ${requestScope.status eq 0 ? "checked": ""}>Hidden
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-6 mb-3">
                                                            <h3>Rated Star</h3>
                                                            <div class="d-flex">
                                                                <div class="me-3">
                                                                    <label>
                                                                        <input class="me-2" type="checkbox" name="rated_star" value="-1" onclick="clearCheckboxRatedStar()">All
                                                                    </label>
                                                                </div>
                                                                <div class="me-3">
                                                                    <label>
                                                                        <input class="me-2" type="checkbox" name="rated_star" value="1" onclick="clearCheckboxRatedStarAll()">1
                                                                    </label>
                                                                </div>
                                                                <div class="me-3">
                                                                    <label>
                                                                        <input class="me-2" type="checkbox" name="rated_star" value="2" onclick="clearCheckboxRatedStarAll()">2
                                                                    </label>
                                                                </div>
                                                                <div class="me-3">
                                                                    <label>
                                                                        <input class="me-2" type="checkbox" name="rated_star" value="3" onclick="clearCheckboxRatedStarAll()">3
                                                                    </label>
                                                                </div>
                                                                <div class="me-3">
                                                                    <label>
                                                                        <input class="me-2" type="checkbox" name="rated_star" value="4" onclick="clearCheckboxRatedStarAll()">4
                                                                    </label>
                                                                </div>
                                                                <div>
                                                                    <label>
                                                                        <input class="me-2" type="checkbox" name="rated_star" value="5" onclick="clearCheckboxRatedStarAll()">5
                                                                    </label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row mb-3">
                                                        <div class="col-5">
                                                            <h3>Feedbacks Type</h3>
                                                            <div>
                                                                <label>
                                                                    <input class="me-2" type="radio" name="type" value="1" onclick="hideProductOption(event)" ${requestScope.type eq "1" ?  "checked" : ""}>General Feedbacks
                                                                </label>
                                                            </div>
                                                            <div>
                                                                <label>
                                                                    <input class="me-2" type="radio" name="type" value="2" onclick="showProductOption(event)" ${requestScope.type eq "2" ?  "checked" : ""}>Product Feedbacks
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <h3>Sort by</h3>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="feedback_id asc" ${requestScope.orderOption eq "feedback_id asc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-1-9"></i>
                                                                <span>ID</span>
                                                            </div>
                                                        </label>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="feedback_id desc" ${requestScope.orderOption eq "feedback_id desc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-9-1"></i>
                                                                <span>ID</span>
                                                            </div>
                                                        </label>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="full_name asc" ${requestScope.orderOption eq "full_name asc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-a-z"></i>
                                                                <span>Fullname</span>
                                                            </div>
                                                        </label>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="full_name desc" ${requestScope.orderOption eq "full_name desc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-z-a"></i>
                                                                <span>Fullname</span>
                                                            </div>
                                                        </label>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="email asc"  ${requestScope.orderOption eq "email asc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-a-z"></i>
                                                                <span>Email</span>
                                                            </div>
                                                        </label>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="email desc" ${requestScope.orderOption eq "email desc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-z-a"></i>
                                                                <span>Email</span>
                                                            </div>
                                                        </label>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="star asc"  ${requestScope.orderOption eq "star asc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-short-wide"></i>
                                                                <span>Rated star</span>
                                                            </div>
                                                        </label>
                                                        <label>
                                                            <input type="radio" class="btn-check" name="orderby" value="star desc"  ${requestScope.orderOption eq "star desc"?"checked":""}>
                                                            <div class="btn btn-outline-primary">
                                                                <i class="fa-solid fa-arrow-down-wide-short"></i>
                                                                <span>Rated star</span>
                                                            </div>
                                                        </label>
                                                    </div>
                                                </div>

                                                <div class="modal-footer">
                                                    <input class="btn btn-primary" type="submit" value="Filter">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div id="modal-search" class="modal">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h6 class="modal-title">Search Feedback</h6>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <input class="form-control" type="text" name="key" value="${requestScope.searchKey}" onkeydown="searchFeedback()" placeholder="Search feedback by fullname">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div>
                        <hr>
                    </div>
                    <div>
                        <table class="table table-hover">
                            <thead class="sticky-0 bg-white">
                                <tr>
                                    <th>Id</th>
                                    <th>Fullname</th>
                                    <th>Rated Star</th>
                                    <th>Content</th>
                                    <th>Product Name</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.feedbackslist}" var="feedback">
                                    <tr>
                                        <td class="align-middle">${feedback.id}</td>
                                        <td class="align-middle">${feedback.fullname}</td>
                                        <td class="align-middle">
                                            <i class="fa-solid fa-star star-rate ${feedback.star >= 1 ? "checked" : ""}"></i>
                                            <i class="fa-solid fa-star star-rate ${feedback.star >= 2 ? "checked" : ""}"></i>
                                            <i class="fa-solid fa-star star-rate ${feedback.star >= 3 ? "checked" : ""}"></i>
                                            <i class="fa-solid fa-star star-rate ${feedback.star >= 4 ? "checked" : ""}"></i>
                                            <i class="fa-solid fa-star star-rate ${feedback.star >= 5 ? "checked" : ""}"></i>
                                        </td>
                                        <td class="align-middle">${feedback.content}</td>
                                        <td class="align-middle">
                                            <c:if test="${feedback.product != null}">
                                                <div>${feedback.product.name}</div>
                                            </c:if>
                                        </td>
                                        <td class="align-middle"></td>
                                        <td class="align-middle"><a href="feedback?type=${requestScope.type}&id=${feedback.id}" class="btn btn-light"><i class="fa-solid fa-eye me-2"></i>View</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                    <div class="paging">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <li class="page-item<c:if test="${requestScope.pageNumber <= 1}"> disabled"</c:if> <c:if test="${requestScope.pageNumber > 1}"> cursor-pointer" onclick="nextPage(${requestScope.pageNumber - 1});"</c:if>>
                                    <a class="page-link">Previous</a>
                                    </li>
                                        <li class="page-item"><span class="page-link">${requestScope.pageNumber}</span></li>
                                <li class="page-item<c:if test="${requestScope.pageNumber >= requestScope.numberPage}"> disabled"</c:if> <c:if test="${requestScope.pageNumber < requestScope.numberPage}"> cursor-pointer" onclick="nextPage(${requestScope.pageNumber + 1});"</c:if>>
                                    <a class="page-link">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                function nextProductPage(pageNumber) {
                    document.getElementById('page').value = pageNumber;
                    document.getElementById('productSearchForm').submit();
                }
            </script>
        </body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

        <script src="../js/marketing/feedbackslist.js"></script>

        <script>
                var number = [
        <c:forEach var="item" items="${requestScope.stars}" varStatus="loop">
                "${item}"
            <c:if test="${!loop.last}">,</c:if>
        </c:forEach>
                ];

                document.querySelectorAll('input[name="rated_star"]').forEach(input => {
                    for (var i in number) {
                        if (input.value == number[i])
                            input.checked = true;
                    }
                });
    </script>

</html>