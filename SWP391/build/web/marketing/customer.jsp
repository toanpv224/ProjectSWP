<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post_List</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!--CSS-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/style.css"/>
        <link rel="stylesheet" href="../css/customer.css"/>

        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"/>
    </head>
    <body>



        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>

                <div class="container-lg">
                    <!--content--> 
                    <div class="p-3 rounded bg-white my-5">
                        <form id="post_list" action="searchpostlist" method="get">               
                            <!-- header  -->
                            <div data-v-b2f6143e="" data-v-75520c46="" id="product-filter-card" class="product-filter-card">


                            <c:forEach items="${requestScope.categories}" var="category">
                                <!--get data-->
                                <c:forEach items="${category.subcategories}" var="c"> 
                                    <input hidden="" cat="${category.category_id}"  subcat="${c.id}" value="${c.name}" id="cid" name="cid"/>
                                </c:forEach>
                            </c:forEach>

                            <div class="filter-container">

                                <div class="container">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">
                                            <div class="panel panel-primary">
                                                <div class="panel-heading">
                                                    <h3 class="panel-title">${requestScope.cus.full_name}</h3>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-md-3 col-lg-3 " align="center"> <img alt="User Pic" src="../${requestScope.cus.image_url}" class="img-circle img-responsive"> </div>
                                                        <div class=" col-md-9 col-lg-9 ">
                                                            <input type hidden name="user_id" value="${requestScope.cus.user_id}" id="user_id">
                                                            <table class="table table-hover table-user-information">
                                                                <!--edit-->
                                                                <tbody id="edit" class="body-tb edit">

                                                                    <tr>
                                                                        <td>Name:</td>
                                                                        <td>
                                                                            <input required type="text" oninput="CheckName()"
                                                                                   id="name" name="name" value="${requestScope.cus.full_name}">
                                                                            <span id="err-name" class="mess">Name is not empty</span>
                                                                        </td>

                                                                    </tr>
                                                                    <tr>
                                                                        <td>Gender</td>
                                                                        <td>
                                                                            <select required name="gender" id="gender" class="select-item">
                                                                                <option value="1"
                                                                                        <c:if test="${requestScope.cus.gender}">
                                                                                            selected
                                                                                        </c:if> 
                                                                                        >Male</option>
                                                                                <option value="0"
                                                                                        <c:if test="${!requestScope.cus.gender}">
                                                                                            selected
                                                                                        </c:if> 
                                                                                        >FeMale</option>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Email</td>
                                                                        <td>
                                                                            ${requestScope.cus.email}
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Mobile</td>
                                                                        <td>
                                                                            <input required name="phone" oninput="CheckPhone()"
                                                                                   id="phone" value="${requestScope.cus.phone}">
                                                                            <span id="err-phone" class="mess">Mobile is not emptys</span>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Address</td>
                                                                        <td>
                                                                            <input required name="address" id="address" oninput="CheckAddress()"
                                                                                   value=" ${requestScope.cus.address}">
                                                                            <span id="err-address" class="mess">Address is not empty</span>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Status</td>
                                                                        <td>
                                                                            <select required name="status" id="status" class="select-item">
                                                                                <option value="1" <c:if test="${requestScope.cus.feature}">selected</c:if> 
                                                                                        >Active</option>
                                                                                    <option value="0" <c:if test="${!requestScope.cus.feature}">selected</c:if>>Inactive</option>
                                                                                </select>

                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td class="submit-row">
                                                                                <button type="button" onclick="update()" class="btn btn-success">Save change</button>
                                                                            </td>

                                                                        </tr>

                                                                    </tbody>

                                                                    <!--original-->
                                                                    <tbody id="normal" class="body-tb normal">
                                                                        <tr>
                                                                            <td>Name:</td>
                                                                            <td id="name-td">
                                                                            ${requestScope.cus.full_name}
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Gender</td>
                                                                        <td id="gender-td">
                                                                            <c:if test="${requestScope.cus.gender}">
                                                                                Male
                                                                            </c:if>
                                                                            <c:if test="${!requestScope.cus.gender}">
                                                                                Female
                                                                            </c:if>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Email</td>
                                                                        <td id="email-td">
                                                                            ${requestScope.cus.email}
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td >Mobile</td>
                                                                        <td id="mobile-td">${requestScope.cus.phone}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td id>Address</td>
                                                                        <td id="address-td">
                                                                            ${requestScope.cus.address}
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td>Status</td>
                                                                        <td id="status-td">
                                                                            <c:if test="${requestScope.cus.feature}">
                                                                                Active
                                                                            </c:if>
                                                                            <c:if test="${!requestScope.cus.feature}">
                                                                                Inactive
                                                                            </c:if>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="submit-row">
                                                                            <button type="button" class="btn btn-primary" onclick="Edit()">
                                                                                <i class="fa-solid fa-pen-to-square edit-icon"></i>
                                                                                Edit</button>
                                                                        </td>
                                                                    </tr>
                                                                <b id="mess" style="color: green"></b>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>




                            <!-- NavBar  -->

                            <div class="product-list-main" >
                                <div class="py-2 px-3 position-sticky top-0 bg-light my-3 rounded" style="top: 56px; z-index: 1000;">
                                    <h1 class="title-history" >History</h1>
                                </div>
                                <!--Content-->
                                <!--                                        <div id="content">-->
                                <table  id="content" class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>
                                                Full Name
                                            </th>
                                            <th> 

                                                Email
                                            </th>
                                            <th>
                                                Gender
                                            </th>
                                            <th>
                                                Mobile
                                            </th>
                                            <th> 
                                                Address
                                            </th>
                                            <th> 
                                                Update By
                                            </th>
                                            <th> 
                                                Update Date
                                            </th>
                                    <input hidden id="sort_input" name="sort" value="1">
                                    </tr>
                                    </thead>
                                    <tbody id="listpost ">

                                        <c:forEach items="${requestScope.histories}" var="h">
                                            <tr>
                                                <td>${h.full_name}</td>
                                                <td class="">
                                                    ${h.email}
                                                </td>
                                                <td>
                                                    <c:if test="${h.gender}">
                                                        Male
                                                    </c:if>
                                                    <c:if test="${!h.gender}">
                                                        Female
                                                    </c:if>
                                                </td>
                                                <td >
                                                    ${h.phone}
                                                </td>
                                                <td >
                                                    ${h.address}
                                                </td>
                                                <td >
                                                    ${h.update_by}
                                                </td>
                                                <td >
                                                    ${h.update.date}
                                                </td>
                                            </tr>   
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <footer id="footer">
                                    <div class="pagination">
                                        <div class="content-paging content-paging-footer" name="page">
                                            <div class="title-paging"> <span>Page ${requestScope.curpage} of ${requestScope.maxpage}<span></div>
                                                        <nav class="" aria-label="...">
                                                            <ul class="pagination">
                                                                <c:if test="${requestScope.curpage!=1}">
                                                                    <li class="page-item" >
                                                                        <a class="page-link" value="${requestScope.curpage-1}" onclick="Paging(${requestScope.curpage-1})" >Previous</a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${requestScope.curpage==1}">
                                                                    <li class="page-item disabled">
                                                                        <a class="page-link" value="${requestScope.curpage}" >Previous</a>
                                                                    </li>
                                                                </c:if>

                                                                <select class="select-paginate" id="paging" onchange="SubmitForm($('#paging').children('option:selected').val())">
                                                                    <c:forEach begin="1" end="${requestScope.maxpage}" var="i">
                                                                        <option value="${i}"
                                                                                <c:if test="${requestScope.curpage==i}">selected</c:if>
                                                                                >${i}</option>
                                                                    </c:forEach>
                                                                </select>
                                                                <c:if test="${requestScope.curpage!=requestScope.maxpage}">
                                                                    <li class="page-item">
                                                                        <a class="page-link" onclick="Paging(${requestScope.curpage+1})" value="${requestScope.curpage+1}" >Next</a>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${requestScope.curpage==requestScope.maxpage}">
                                                                    <li class="page-item disabled">
                                                                        <a class="page-link" value="${requestScope.curpage}" >Next</a>
                                                                    </li>
                                                                </c:if>
                                                            </ul>
                                                        </nav>
                                                        </div>
                                                        </div>
                                                        </footer>

                                                        <!--</div>-->
                                                        </div>

                                                        </div>
                                                        </div>
                                                        </form>      
                                                        </div>
                                                        </div>
                                                        </div>
                                                        </div>
                                                        <script src="../js/marketing/customer.js"></script>
                                                        <!--Ajax-->
                                                        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
                                                        </body>
                                                        </html>