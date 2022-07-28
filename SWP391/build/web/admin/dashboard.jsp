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

        <link href="css/stylesaleheadersider.css" rel="stylesheet"/>
        <link href="css/admin.css" rel="stylesheet"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:directive.include file="navbar.jsp"/>
        <div class="wrapper-login">
            <jsp:directive.include file="sidebar.jsp"/>
            <aside class="right-side">
                <section class="content container">
                    <div class="row" style="margin-bottom: 20px;">
                        <div class="col-md-6 row order-statistic-wrapper">
                            <div class="select-time-wrapper flex" style="height: 40px; line-height: 40px;">
                                <span style="font-size: 24px;
                                      font-weight: bold;">New Order</span>
                                <div class="flex-1"></div>
                                <label for="order-time">Time</label>
                                <select onchange="newOrderStatistic(this);" id="order-time">
                                    <option value="1">Last 7 days</option>
                                    <option value="2">Last 1 month</option>
                                    <option value="3">Last 3 months</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <div class="chart-wrapper">
                                    <canvas id="myChart" width="400" height="400"></canvas>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <table class="table table-hover table-bordered" id="order-table">
                                    <thead>
                                        <tr>
                                            <th>Submitted order</th>
                                            <th>Success order</th>
                                            <th>Canceled order</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td id="submitted-order"></td>
                                            <td id="success-order"></td>
                                            <td id="cancel-order"></td>
                                        </tr>
                                    </tbody>
                                </table>

                            </div>
                        </div>
                        <div class="col-md-6" id="revenue-wrapper">
                            <div>
                                <div class="flex" style="    height: 40px;
                                     line-height: 40px;">
                                    <span>Revenue</span>
                                    <div class="flex-1"></div>
                                    <label>Time</label>
                                    <select onchange="newRevenueTime(this);">
                                        <option value="1">Last 7 days</option>
                                        <option value="2">Last 1 month</option>
                                        <option value="3">Last 3 months</option>
                                    </select>
                                </div>
                                <table class="table table-hover table-bordered" id="">
                                    <thead>
                                        <tr>
                                            <th>Product category</th>
                                            <th>Revenue</th>
                                        </tr>
                                    </thead>
                                    <tbody id="revenue-body">
                                        <c:forEach items="${requestScope.cateList}" var="i">
                                            <tr>
                                                <td id="submitted-order" class="${i.category_id == 0? "bold":""}">${i.category_name}</td>
                                                <td id="revenue-statistic"></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 feedback-wrapper">
                            <div class="flex feedback-wrapper-header">
                                <h3>Feedbacks</h3>
                                <div class="flex-1">
                                </div>
                                <div>
                                    <span>Time</span>
                                    <select name="feedback" onchange="newFeedbackTime(this);">
                                        <option value="1">Last 7 days</option>
                                        <option value="2">Last 1 month</option>
                                        <option value="3">Last 3 months</option>
                                    </select>
                                </div>
                            </div>
                            <table>
                                <tr>
                                    <th>Product Category</th>
                                    <th>Average rated</th>
                                </tr>
                                <c:forEach items="${requestScope.cateList}" var="i">
                                    <tr>
                                        <td id="submitted-order" class="${i.category_id == 0? "bold":""}">${i.category_name}</td>
                                        <td id="revenue-statistic">
                                            <div class="progress">
                                                <div class="progress-bar progress-bar-striped progress-bar-animated bg-danger" role="progressbar" aria-valuenow="5" aria-valuemin="0" aria-valuemax="5" id="progress-bar"></div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div class="col-md-8 customer-wrapper">
                            <div class="row">
                                <div class="flex">
                                    <span class="title">Customer</span>
                                    <div class="flex-1"></div>
                                    <div class="select-time-wrapper">
                                        <label for="order-time">Time</label>
                                        <select onchange="newCustomerTime(this);" id="order-time">
                                            <option value="1">Last 7 days</option>
                                            <option value="2">Last 1 month</option>
                                            <option value="3">Last 3 months</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="chart2-wrapper">
                                        <canvas id="myChart2" width="400" height="400"></canvas>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="table-wrapper">

                                        <table class="table table-striped table-hover table-bordered">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Date</th>
                                                    <th scope="col">Newly Registered Customer</th>
                                                    <th scope="col">Newly Bought Customer</th>
                                                </tr>
                                            </thead>
                                            <tbody id="customer-table-body">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row order-count">
                        <div class="sort-bar">
                            <h3>Trends of order count</h3>
                            <div class="flex-1"></div>
                            <div class="select-time-wrapper">
                                <label for="order-time">Time</label>
                                <select onchange="newOrderTime(this);" id="order-time">
                                    <option value="1">Last 7 days</option>
                                    <option value="2">Last 1 month</option>
                                    <option value="3">Last 3 months</option>
                                </select>
                            </div>
                        </div>
                        <div class="row" style="    width: 90%;
                             margin-left: auto;
                             margin-right: auto;">
                            <div class="col-md-7">
                                <div class="chart3-wrapper">
                                    <canvas id="myChart3" width="400" height="400"></canvas>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class="table-wrapper">

                                    <table class="table table-striped table-hover table-bordered">
                                        <thead>
                                            <tr>
                                                <th scope="col">Date</th>
                                                <th scope="col">Success Order</th>
                                                <th scope="col">All Order</th>
                                            </tr>
                                        </thead>
                                        <tbody id="order-table-body">

                                        </tbody>
                                    </table>
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="js/admin.js"></script>
</html>