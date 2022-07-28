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
        <link href="../css/styleadminsettings.css" rel="stylesheet"/>
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
                        <h2>Settings List</h2>
                        <div class="left">
                            <form class="flex flex-column" action="settingslist" method="post" id="submit-frm">                               
                                <div class="filter flex flex-column">
                                    <h3>Filter</h3>
                                    <div class="setting-types flex flex-column" id="">
                                        <span class="filter-option">Type</span>
                                        <c:set value="${requestScope.checkedType}" var="cs"/>
                                        <c:set value="${requestScope.type}" var="sl"/>
                                        <c:forEach begin="0" end="${sl.size()-1}" var="i">
                                            <div class="set-type"><input type="checkbox" value="${sl[i]}" name="type" <c:if test="${cs[i]}">checked</c:if>/> ${sl[i]}</div>
                                            </c:forEach>

                                    </div>
                                    <div class="filter-status flex flex-column">
                                        <span class="filter-option">Status</span>
                                        <div><input type="checkbox" value="1" name="status" ${requestScope.activate?"checked":""}/>Activate</div>
                                        <div><input type="checkbox" value="0" name="status" ${requestScope.deactivate?"checked":""}/>Deactivate</div>
                                    </div>
                                    <div class="hidden-input">
                                        <input type="hidden" name="orderOption" value="${requestScope.orderOption}" id="orderOption"/>
                                        <input type="hidden" name="sequence" value="${requestScope.sequence}" id="sequence"/>
                                        <input type="hidden" name="page" value="${requestScope.pageNumber}" id="pageNumber"/>
                                    </div>
                                </div>
                                <div class="search-order">
                                    <span class="filter-option">Search setting</span>
                                    <div>   
                                        <input type="text" name="key" placeholder="Search setting" value="${requestScope.key}"/>
                                    </div>
                                </div>

                                <button type="submit" class="submit-btn">Filter</button>

                            </form>
                        </div>
                        <div class="right">
                            <div class="sort-bar-setting flex">
                                <div class="flex-1"></div>
                                <div class="sort-bar-title">Sort</div>
                                <div>
                                    <select name="orderOption" onchange="submitFormWithNewOrderOption(this);">
                                        <option value="setting_id" ${requestScope.orderOption eq "setting_id"?"selected":""}>Setting ID</option>
                                        <option value="type" ${requestScope.orderOption eq "type"?"selected":""}>Type</option>
                                        <option value="value" ${requestScope.orderOption eq "value"?"selected":""}>Value</option>
                                        <option value="[order]" ${requestScope.orderOption eq "[order]"?"selected":""}>Order</option>
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
                            <div class="main-setting">
                                <c:if test="${requestScope.settingsList.size() != 0}">
                                    <div class="settings-wrapper">
                                        <table class="table .table-striped table-hover .table-bordered">
                                            <thead>
                                                <tr>
                                                    <th scope="col">SettingID</th>
                                                    <th scope="col">Type</th>
                                                    <th scope="col">Value</th>
                                                    <th scope="col">Order</th>
                                                    <th scope="col">Status</th>
                                                    <th scope="col">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody class="table-group-divider">
                                                <c:forEach items="${requestScope.settingsList}" var="i">
                                                    <tr>
                                                        <td>${i.settingId}</td>
                                                        <td>${i.type}</td>
                                                        <td>${i.value}</td>
                                                        <td>${i.order}</td>
                                                        <td>
                                                            <span class="activated">
                                                                <c:if test="${i.status == 1}">Activated</c:if>
                                                                </span>
                                                                <span class="deactivated">
                                                                <c:if test="${i.status != 1}">Deactivated</c:if>
                                                                </span>
                                                            </td>
                                                            <td>
                                                                <a class="btn btn-primary" href="settingdetails?settingID=${i.settingId}" role="button">Detail</a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
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
                                <c:if test="${requestScope.settingsList.size() == 0}">
                                    <div style="margin-top: 20px;
                                         padding: 20px;
                                         background: #fff;">Their is no settings</div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </section>
            </aside>
        </div>
        <div class="add-settings" id="add-new-settings">
            <button class="btn btn-success btn-block" type="button" data-toggle="modal" data-target="#setting-form-modal">+ New settings</button>
        </div>
        <div class="modal fade" role="dialog" tabindex="-1" id="setting-form-modal">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Create Setting</h5>
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <nav>
                            <h5>Type:</h5>
                            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button" role="tab" aria-controls="nav-home" aria-selected="true">Role</button>
                                <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button" role="tab" aria-controls="nav-profile" aria-selected="false">Permission</button>
                            </div>
                        </nav>
                        <div class="tab-content" id="nav-tabContent">
                            <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab" tabindex="0">
                                <form action="addsetting" method="POST">
                                    <input type="hidden" value="Role" name="type">
                                    <input type="hidden" value="Set Role" name="name">

                                    <div class="row mt-2">
                                        <label class="labels"><h5>Value</h5></label><br>
                                        <div class="form-floating">
                                            <input class="form-control" placeholder="Leave a comment here" name="rName" id="floatingTextarea"/>
                                            <label style="margin-left: 10px;" for="floatingTextarea">Role Name</label>
                                        </div>
                                    </div>

                                    <div class="row mt-2">
                                        <label class="labels"><h5>Active</h5></label>
                                        <div>
                                            <div class="form-check form-switch">
                                                <input class="form-check-input" type="checkbox" name="active" role="switch" id="flexSwitchCheckCheckedDisabled" checked disabled>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row mt-2">
                                        <div class="col-md-12 rounded ">
                                            <label class="labels"><h5>Description</h5></label><br>
                                            <div class="form-floating">
                                                <textarea class="form-control" placeholder="Leave a description here" id="floatingTextarea2" style="height: 100px"></textarea>
                                                <label for="floatingTextarea2">Description</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-center">
                                        <input class="btn btn-primary profile-button" type="submit" value="Add" style="width: auto;"/>
                                    </div>
                                </form>
                            </div>
                            <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab" tabindex="0">
                                <form action="addsetting" method="POST">
                                    <input type="hidden" value="Permission" name="type">
                                    <input type="hidden" value="Set Permission" name="name">

                                    <div class="row mt-2">
                                        <label class="labels"><h5>Value</h5></label><br>
                                        <div class="input-group mb-3">
                                            <label class="input-group-text" for="inputGroupSelect01">Value</label>
                                            <select name="value" class="form-select" id="inputGroupSelect01">
                                                <c:forEach items="${requestScope.value}" var="v">
                                                    <option value="${v}">${v}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col-md-12 rounded ">
                                            <label class="labels"><h5>Order</h5></label><br>
                                            <div class="input-group mb-3">
                                                <label class="input-group-text" for="inputGroupSelect01">Order</label>
                                                <select name="order" class="form-select" id="inputGroupSelect01">
                                                    <c:forEach items="${requestScope.order}" var="o">
                                                        <option value="${o.rId}">${o.rName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <label class="labels"><h5>Active</h5></label>
                                        <div>
                                            <div class="form-check form-switch">
                                                <input class="form-check-input" type="checkbox" name="active" role="switch" id="flexSwitchCheckCheckedDisabled" checked disabled>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row mt-2">
                                        <div class="col-md-12 rounded ">
                                            <label class="labels"><h5>Description</h5></label><br>
                                            <div class="form-floating">
                                                <textarea class="form-control" placeholder="Leave a description here" id="floatingTextarea2" style="height: 100px"></textarea>
                                                <label for="floatingTextarea2">Description</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-center">
                                        <input class="btn btn-primary profile-button" type="submit" value="Add" style="width: auto;"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="../js/admin.js"></script> 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>

</html>