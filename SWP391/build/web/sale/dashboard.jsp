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
        <link href="css/stylesale.css" rel="stylesheet"/>
        <link href="css/stylesaleheadersider.css" rel="stylesheet"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <!--<script src="../asserts/fontawesome-free-6.0.0/css/all.css"></script>-->
        <!--<link rel="stylesheet" href="../asserts/fontawesome-free-6.0.0/css/all.min.css"/>-->
    </head>
    <body>
        <jsp:directive.include file="salenavbar.jsp"/>
        <div class="wrapper-login">
            <jsp:directive.include file="sidebar.jsp"/>
            <aside class="right-side">
                <section class="content container">
                        <div class="row" style="margin-bottom: 20px;">
                            <div class="col-md-1">

                            </div>
                            <div class="chart-wrapper2 col-md-6">
                                <div class="flex">

                                    <div class="sale-member"><span>Select sale member:</span>
                                        <select onchange="changeSale(this);">
                                            <option value="0" <c:if test="${requestScope.saleIdOrder == 0}">selected</c:if>>All</option>
                                        <c:forEach items="${requestScope.saleList}" var="i">
                                            <option value="${i.account.user_id}" <c:if test="${requestScope.saleIdOrder == i.account.user_id}">selected</c:if>>${i.account.full_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <div class="order-trends-start-date flex">
                                        <span>Time</span>
                                        <select onchange="changeTime(this);" id="timeOrder">
                                            <option value="1" ${requestScope.timeOrder eq "1"?"selected":""}>Last 7 days</option>
                                            <option value="2" ${requestScope.timeOrder eq "2"?"selected":""}>Last 1 month</option>
                                            <option value="3" ${requestScope.timeOrder eq "3"?"selected":""}>Last 3 months</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="sm-btn" onclick="submitFrm();">
                                    <button><i class="fas fa-search"></i></button>
                                </div>
                            </div>
                            <canvas id="myChart2" width="400" height="400"></canvas>
                        </div>
                        <div class="col-md-1">

                        </div>
                        <div class="sale-profile col-md-3 flex flex-column">
                            <div class="sale-username">Sale Member</div>
                            <div class="sale-img flex">
                                <div class="img-wrapper">

                                    <c:if test="${requestScope.sale.account.image_url != null}"><img src="${requestScope.sale.account.image_url}" class="img-circle img-thumbnail"/></c:if>
                                    <c:if test="${requestScope.sale.account.image_url == null}"><img src="https://th.bing.com/th/id/R.5ba3c5f393890bd25dfc0ab9676c9f25?rik=iSc2O0kIYbJtCg&pid=ImgRaw&r=0&sres=1&sresct=1" class="img-circle img-thumbnail"/></c:if>
                                    </div>
                                    <div>
                                        <span class="name">${requestScope.sale.account.full_name}</span>
                                    <span class="role">Sale Member</span>
                                </div>
                            </div>
                            <div class="sale-brief-infor">
                                <div class="achievement">
                                    <h4>Achievement</h4>
                                    <div class="total-achievement"><span>Total order: ${requestScope.totalOrderAllTime}</span><span>Revenue: <fmt:formatNumber type="currency" value="${requestScope.totalRevenueAllTime}" currencySymbol="VND"></fmt:formatNumber></span></div>
                                    <div class="success-achievement"><span>Success order: ${requestScope.totalSuccessOrderAllTime}</span></div>
                                    <div><span>Success rate: <fmt:formatNumber value="${requestScope.successRate}" type="number" maxFractionDigits="2"></fmt:formatNumber></span> <span>Average order per day:</span><span><fmt:formatNumber value="${requestScope.totalOrderAllTime/requestScope.totalDays}" type="number" maxFractionDigits="1"></fmt:formatNumber></span></div>
                                        <hr/>
                                        <!--                                    <div><span>Success/Total order this month:</span><span>100/101</span></div>
                                                                            <div><span>Success/Total order this week:</span><span>50/51</span></div>
                                                                            <div><span>Success/Total order today:</span><span>5/6</span></div>-->
                                        <div class="table-responsive">
                                            <div>
                                                <span id="timeOrder2">
                                                <c:if test="${requestScope.timeOrder == 1}">Last 7 days</c:if>
                                                <c:if test="${requestScope.timeOrder == 2}">Last 1 month</c:if>
                                                <c:if test="${requestScope.timeOrder == 3}">Last 3 months</c:if>
                                                </span>
                                            </div>
                                            <table class="table">
                                                <tr>
                                                    <th>Revenue</th>
                                                    <td><fmt:formatNumber value="${requestScope.revenueBytime}" type="currency" currencySymbol="VND" maxFractionDigits="0"></fmt:formatNumber></td>
                                                </tr>
                                                <tr>
                                                    <th>Success/Total order</th>
                                                        <td>${requestScope.countSuccessOrder}/${requestScope.countTotalOrder}</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-1">

                        </div>
                    </div>
                    <div class="row">
                        <div class="filter-bar">
                            <form class="frm" action="sale" method="post" id="frm">
                                <div class="frm-container flex">
                                    <div style="flex: 1;"></div>
                                    <div class="start-date flex">
                                        <span>Time</span>
                                        <select name="time">
                                            <option value="1" ${requestScope.time eq "1"?"selected":""}>Last 7 days</option>
                                            <option value="2" ${requestScope.time eq "2"?"selected":""}>Last 1 month</option>
                                            <option value="3" ${requestScope.time eq "3"?"selected":""}>Last 3 months</option>
                                        </select>
                                    </div>
                                    <div></div>
                                    <div class="sale-list">
                                        <span>Sale</span><select name="saleId">
                                            <option value="0" <c:if test="${requestScope.saleId == 0}">selected</c:if>>All</option>
                                            <c:forEach items="${requestScope.saleList}" var="i">
                                                <option value="${i.account.user_id}" <c:if test="${requestScope.saleId == i.account.user_id}">selected</c:if>>${i.account.full_name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="status-list">
                                        <span>Order Status</span>
                                        <select name="status">
                                            <option value="0" <c:if test="${requestScope.orderStatus == 0}">selected</c:if>>All</option>
                                            <c:forEach items="${requestScope.statusList}" var="i">
                                                <option value="${i.id}" <c:if test="${requestScope.orderStatus == i.id}">selected</c:if>>${i.describe}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="sm-btn">
                                        <button><i class="fas fa-search"></i></button>
                                    </div>
                                </div>
                                <div class="hidden-input">
                                    <input type="hidden" name="orderTrendSaleId" value="${requestScope.saleIdOrder}" id="orderTrendSaleId">
                                    <input type="hidden" name="orderTrendTime" value="${requestScope.timeOrder}" id="orderTrendTime">
                                </div>
                            </form>
                        </div>
                        <div>

                            <h2>Revenues trends</h2>
                            <div class="flex">
                                <div class="revenues-table">
                                    <c:set value="${requestScope.dates}" var="d"></c:set>
                                    <c:set value="${requestScope.revenue}" var="r"></c:set>
                                        <table class="table table-striped table-hover table-bordered">
                                            <thead>
                                                <tr>
                                                    <th scope="col">#</th>
                                                    <th scope="col">Date</th>
                                                    <th scope="col">Revenue(VND)</th>
                                                </tr>
                                            </thead>
                                            <tbody class="table-group-divider">
                                            <c:forEach begin="0" end="${d.size()-1}" var="i">
                                                <tr>
                                                    <th scope="row">${i+1}</th>
                                                    <td>${d.get(i)}</td>
                                                    <td><fmt:formatNumber type="currency" value="${r.get(i)}" currencySymbol="" maxFractionDigits="0"></fmt:formatNumber></td>
                                                    </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="chart-wrapper">

                                    <canvas id="myChart" width="400" height="400"></canvas>
                                </div>
                            </div>

                        </div>
                    </div>
                </section>
            </aside>
        </div>
    </body>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
    </script>
    <!--<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>-->
    <!--<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>-->
    <script src="js/salechart.js"></script>
    <script>
                                    let labels = [], datas = [];
                                    let type = 'bar';
                                    let colors = [];
                                    color = getColorArray(${requestScope.dates.size()}).split(";");
        <c:forEach items="${requestScope.revenue}" var="i">
                                    datas.push(${i});
        </c:forEach>
        <c:forEach items="${requestScope.dates}" var="i">
                                    labels.push('${i}');
        </c:forEach>


                                    let labels2 = [], datas1 = [], datas2 = [];
                                    let colors2 = [];
                                    colors2 = getColorArray(${requestScope.totalOrder.size()}).split(";");
        <c:forEach items="${requestScope.labels}" var="i">
                                    labels2.push('${i}');
        </c:forEach>
        <c:forEach items="${requestScope.totalOrder}" var="i">
                                    datas1.push('${i}');
        </c:forEach>
        <c:forEach items="${requestScope.successOrder}" var="i">
                                    datas2.push('${i}');
        </c:forEach>
                                    window.onload = function () {
                                        createChart(type, labels, datas, color);
                                        createChart2(type, labels2, datas1, datas2, color)
                                    };
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    
</html>