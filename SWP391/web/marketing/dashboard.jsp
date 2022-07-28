<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Marketing - Dashboard</title>

        <!--bootstrap-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link href="../css/style.css" rel="stylesheet"/>
        <link href="../css/stylemarketingdashboard.css" rel="stylesheet"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>

        <div class="d-flex wrapper-login">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <section class="container-lg my-5">
                    <div class="row">
                        <div class="col-md-6" id="post-statistic">
                            <div class="posts-chart-wrapper">
                                <div class="flex">
                                    <div class="general-statistics-post" id="post-total">
                                        <span class="icon bg-green">
                                            <i class="far fa-clipboard"></i>
                                        </span>
                                        <span class="post-title">Post</span>
                                    </div>
                                    <div class="flex-1"></div>
                                    <span class="title">Time</span>
                                    <select onchange="newPostTimeRange(this);" class="time">
                                        <option value="1">Last 7 days</option>
                                        <option value="2">Last 1 month</option>
                                        <option value="3">Last 3 months</option>
                                    </select>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <canvas id="postChart" width="400" height="400"></canvas>
                                    </div>
                                    <div class="col-md-6 items-infor">
                                        <div class="flex flex-column">
                                            <div class="flex">
                                                <span class="bold">Total Posts: </span>
                                                <span>${requestScope.totalPosts}</span>
                                        </div>
                                        <div>
                                            <span id="post-in-time" class="bold">New Posts: </span>
                                            <span id="new-post"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6" id="product-statistic">
                        <div class="product-chart-wrapper">
                            <div class="flex">
                                <div class="general-statistics-post" id="post-total">
                                    <span class="icon bg-red">
                                        <i class="fas fa-tshirt"></i>
                                    </span>
                                    <span class="post-title">Product</span>
                                </div>
                                <div class="flex-1"></div>
                                <span class="title">Time</span>
                                <select onchange="newProductTimeRange(this);" class="time">
                                    <option value="1">Last 7 days</option>
                                    <option value="2">Last 1 month</option>
                                    <option value="3">Last 3 months</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <canvas id="productChart" width="400" height="400"></canvas>
                                </div>
                                <div class="col-md-6 items-infor">
                                    <div class="flex flex-column">
                                        <div class="flex">
                                            <span class="bold">Total Products: </span>
                                            <span>${requestScope.totalProducts}</span>
                                        </div>
                                        <div>
                                            <span id="post-in-time" class="bold">New Products: </span>
                                            <span id="new-product"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6" id="customer-statistic">
                        <div class="customers-chart-wrapper">
                            <div class="flex">
                                <div class="general-statistics-post" id="post-total">
                                    <span class="icon bg-yellow">
                                        <i class="fas fa-user"></i>
                                    </span>
                                    <span class="post-title">Customer</span>
                                </div>
                                <div class="flex-1"></div>
                                <span class="title">Time</span>
                                <select onchange="newCustomerTimeRange(this);" class="time">
                                    <option value="1">Last 7 days</option>
                                    <option value="2">Last 1 month</option>
                                    <option value="3">Last 3 months</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <canvas id="customerChart" width="400" height="400"></canvas>
                                </div>
                                <div class="col-md-6 items-infor">
                                    <div class="flex flex-column">
                                        <div class="flex">
                                            <span class="bold">Total Customers: </span>
                                            <span>${requestScope.totalAccounts}</span>
                                        </div>
                                        <div>
                                            <span id="post-in-time" class="bold">New Customers: </span>
                                            <span id="new-customer"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6" id="feedback-statistic">
                        <div class="feedbacks-chart-wrapper">
                            <div class="flex">
                                <div class="general-statistics-post" id="post-total">
                                    <span class="icon bg-lightblue">
                                        <i class="far fa-comment"></i>
                                    </span>
                                    <span class="post-title">Feedback</span>
                                </div>
                                <div class="flex-1"></div>
                                <span class="title">Time</span>
                                <select onchange="newFeedbackTimeRange(this);" class="time">
                                    <option value="1">Last 7 days</option>
                                    <option value="2">Last 1 month</option>
                                    <option value="3">Last 3 months</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <canvas id="feedbackChart" width="400" height="400"></canvas>
                                </div>
                                <div class="col-md-6 items-infor">
                                    <div class="flex flex-column">
                                        <div class="flex">
                                            <span class="bold">Total Feedbacks: </span>
                                            <span>${requestScope.totalFeedbacks}</span>
                                        </div>
                                        <div>
                                            <span id="post-in-time" class="bold">New Feedbacks: </span>
                                            <span id="new-feedback"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="bg-white">

                            <div class="trends-of-new-customer flex">
                                <div class="flex">
                                    <h3>Trends of new customers</h3>
                                    <div class="flex-1"></div>
                                    <span>Time</span>
                                    <select onchange="newCustomerTrend(this);" id="order-time">
                                        <option value="1">Last 7 days</option>
                                        <option value="2">Last 1 month</option>
                                        <option value="3">Last 3 months</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="bg-white">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="chart-wrapper" style="width: 60%; margin: 0 auto;">

                                        <canvas id="myChart" width="400" height="400"></canvas>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="customer-table-wrapper">

                                        <table class="table table-striped table-hover table-bordered">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Date</th>
                                                    <th scope="col">New Customer</th>
                                                </tr>
                                            </thead>
                                            <tbody id="customer-table-body">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="col-md-1">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <div id="ppp">

        </div>
    </body>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../js/marketingchart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</html>