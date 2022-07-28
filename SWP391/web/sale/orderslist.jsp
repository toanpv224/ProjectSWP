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
                    <div class="row flex">
                        <h1>Orders List</h1>
                        <div class="left">
                            <form class="flex flex-column" action="orderslist" method="post" id="submit-frm">                               
                                <div class="filter flex flex-column">
                                    <h3>Filter</h3>
                                    <div class="filter-option">Order date</div>
                                    <div class="start-date flex flex-column">
                                        <span>From</span>
                                        <input type="date" name="startDate" value="${requestScope.startDate}"/>
                                    </div>
                                    <div class="end-date flex flex-column">
                                        <span>To</span>
                                        <input type="date" name="endDate" value="${requestScope.endDate}" onchange="checkEndDateInput(this);"/>
                                    </div>
                                    <div class="sale-name flex flex-column" id="sale-list">
                                        <span class="filter-option">Sale</span>
                                        <c:set value="${requestScope.checkedSale}" var="cs"/>
                                        <c:set value="${requestScope.saleList}" var="sl"/>
                                        <c:forEach begin="0" end="${sl.size()-1}" var="i">
                                            <div><input type="checkbox" value="${sl.get(i).account.user_id}" name="saleId" <c:if test="${cs[i]}">checked</c:if>/> ${sl.get(i).account.full_name}</div>
                                            </c:forEach>

                                    </div>
                                    <div class="flex" id="more-sale-container">

                                        <div id="more-sale" onclick="showSale();">More </div><i class="fa-solid fa-angle-down"></i>
                                    </div>
                                    <div class="filter-status flex flex-column">
                                        <span class="filter-option">Status</span>
                                        <c:set value="${requestScope.statusList}" var="sttl"/>
                                        <c:set value="${requestScope.checkedStatus}" var="cstt"/>
                                        <c:forEach begin="0" end="${sttl.size()-1}" var="i">
                                            <div><input type="checkbox" value="${sttl.get(i).id}" name="status" <c:if test="${cstt[i]}">checked</c:if>/> ${sttl.get(i).describe}</div>
                                            </c:forEach>
                                    </div>
                                    <div class="hidden-input">
                                        <input type="hidden" name="orderOption" value="${requestScope.orderOption}" id="orderOption"/>
                                        <input type="hidden" name="sequence" value="${requestScope.sequence}" id="sequence"/>
                                        <input type="hidden" name="page" value="${requestScope.pageNumber}" id="pageNumber"/>
                                    </div>
                                </div>
                                <div class="search-order">
<!--                                    <h3>Search Title</h3>-->
                                    <span class="filter-option">Search order</span>
                                    <div>   
                                        <input type="text" name="key" placeholder="Search order" value="${requestScope.key}"/>
                                    </div>
                                </div>

                                <button type="submit" class="submit-btn">Filter</button>

                            </form>
                        </div>
                        <div class="right">
                            <div class="sort-bar-order flex">
                                <div class="flex-1"></div>
                                <div class="sort-bar-title">Sort</div>
                                <div>
                                    <select name="orderOption" onchange="submitFormWithNewOrderOption(this);">
                                        <option value="order_date" ${requestScope.orderOption eq "order_date"?"selected":""}>Order date</option>
                                        <option value="a.full_name" ${requestScope.orderOption eq "a.full_name"?"selected":""}>Customer name</option>
                                        <option value="total_price" ${requestScope.orderOption eq "total_price"?"selected":""}>Total Cost</option>
                                        <option value="status" ${requestScope.orderOption eq "status"?"selected":""}>Status</option>
                                    </select>
                                </div>
                                    
                                <div>
                                    <select name="orderOption" onchange="submitFormWithSequence(this);">
                                        <option value="desc" ${requestScope.sequence eq "desc"?"selected":""}>Descending</option>
                                        <option value="asc" ${requestScope.sequence eq "asc"?"selected":""}>Ascending</option>
                                    </select>
                                </div>
                            </div> 
                            <div class="main-order">
                                <div class="order-list">
                                    <c:if test="${requestScope.myorders.size() > 0}">
                                        <div class="flex my-order-header-bar bg-white">
                                            <div style="width: 40%;">Order</div>
                                            <div style="width: 20%;
                                                 text-align: center;">Customer Name</div>
                                            <div style="width: 15%;
                                                 text-align: center;">Total price</div>
                                            <div style="width: 15%;
                                                 text-align: center">Status</div>

                                        </div>
                                        <c:forEach items="${requestScope.myorders}" var="i">
                                            <div class="order-items bg-white flex flex-column">
                                                <div class="flex" style="padding: 10px; padding-left: 30px; border-bottom: 1px solid #21252930;">
                                                    <div class="id"><a href="orderdetails?orderId=${i.order_id}">Order ID: ${i.order_id}</a></div>
                                                    <div class="order-date">Order Date: <fmt:formatDate value="${i.orderDate}" type="both"/></div>
                                                </div>
                                                <div class="flex" style="    border:1px solid #21252930;
                                                     margin: 15px;">
                                                    <div class="first-product-image"><img src="../${i.orderDetailList.get(0).product.thumbnail}"></div>
                                                    <div class="products-name">
                                                        <div>

                                                            <div>${i.orderDetailList.get(0).product.name}</div><c:if test="${i.orderDetailList.size() > 1}">
                                                                <div> and ${i.orderDetailList.size()-1} more</div></c:if>
                                                                <div style="height: 10px;"></div>
                                                            </div>
                                                        </div>
                                                        <div class="customer-name">
                                                            <span>${i.account.full_name}</span>
                                                    </div>
                                                    <div class="total-cost flex"><span><fmt:formatNumber type="currency" value="${i.total_price}" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></span></div>
                                                        <div class="status flex">
                                                            <span class="<c:if test="${i.status == 6}">canceled-status</c:if><c:if test="${i.status != 6}">status</c:if>">
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
                                                        <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber-1})"><</span>
                                                    </li>
                                                    <c:forEach begin="0" end="${requestScope.numberPage - 1}" var="i">

                                                        <li class="page-item" onclick="submitFormWithNewPage(${i+1})">
                                                            <span class="page-link ${requestScope.pageNumber == (i+1)?"active":""}">${i+1}</span>
                                                        </li>
                                                    </c:forEach>
                                                    <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                                        <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber+1})">></span>
                                                    </li>
                                                </c:if>
                                                <c:if test="${requestScope.numberPage >= 3}">
                                                    <c:if test="${requestScope.pageNumber == 1}">
                                                        <li class="page-item <c:if test="${requestScope.pageNumber == 1}">disabled</c:if>">
                                                            <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber-1})"><</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(1)">
                                                            <span class="page-link active">1</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(2)">
                                                            <span class="page-link">2</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(3)">
                                                            <span class="page-link">3</span>
                                                        </li>

                                                        <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                                            <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber+1})">></span>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${requestScope.pageNumber == requestScope.numberPage}">
                                                        <li class="page-item <c:if test="${requestScope.pageNumber == 1}">disabled</c:if>">
                                                            <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber-1})"><</span>
                                                        </li>

                                                        <li class="page-item" onclick="submitFormWithNewPage(${requestScope.pageNumber-2})">
                                                            <span class="page-link">${requestScope.pageNumber-2}</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(${requestScope.pageNumber-1})">
                                                            <span class="page-link">${requestScope.pageNumber-1}</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(${requestScope.pageNumber})">
                                                            <span class="page-link active">${requestScope.pageNumber}</span>
                                                        </li>
                                                        <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                                            <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber+1})">></span>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${requestScope.pageNumber != 1 && requestScope.pageNumber != requestScope.numberPage}">
                                                        <li class="page-item <c:if test="${requestScope.pageNumber == 1}">disabled</c:if>">
                                                            <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber-1})"><</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(${requestScope.pageNumber-1})">
                                                            <span class="page-link">${requestScope.pageNumber-1}</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(${requestScope.pageNumber})">
                                                            <span class="page-link active">${requestScope.pageNumber}</span>
                                                        </li>
                                                        <li class="page-item" onclick="submitFormWithNewPage(${requestScope.pageNumber+1})">
                                                            <span class="page-link">${requestScope.pageNumber+1}</span>
                                                        </li>
                                                        <li class="page-item <c:if test="${requestScope.pageNumber == requestScope.numberPage}">disabled</c:if>">
                                                            <span class="page-link" onclick="submitFormWithNewPage(${requestScope.pageNumber+1})">></span>
                                                        </li>
                                                    </c:if>

                                                </c:if>
                                            </ul>
                                        </nav>

                                    </c:if>
                                    <c:if test="${requestScope.myorders.size() == 0}">
                                        <div class="my-order-header-bar bg-white">
                                            <div class="no-orders" id="no-orders">There is no orders</div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </aside>
        </div>
    </body>
    <script src="../js/salefunction.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</html>