<%@page import="dal.CategoryDAO"%>
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
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
        <link href="../css/stylesaleheadersider.css" rel="stylesheet"/>
        <link href="../css/userslist.css" rel="stylesheet"/>
        <link href="css/admin.css" rel="stylesheet"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <script>
            var ids = [];
            <c:forEach items="${requestScope.accounts}" var="a">
            ids.push(${a.getUser_id()});
            </c:forEach>
            function submitForm()
            {
                document.getElementById('frmSearch').submit();
            }
            function submitUpdate()
            {
                document.getElementById('frmUpdate').submit();
            }

            function hideControlsbyClassName(name)
            {
                var controls = document.getElementsByClassName(name);
                for (var i = 0; i < controls.length; i++)
                {
                    controls[i].style.display = 'none';
                }
            }
            function showControlsbyClassName(name)
            {
                var controls = document.getElementsByClassName(name);
                for (var i = 0; i < controls.length; i++)
                {
                    controls[i].style.display = 'inline';
                }
            }
            function hideEditModeControls()
            {
                for (var i = 0; i < ids.length; i++)
                {
                    hideControlsbyClassName('editmode' + ids[i]);
                }
            }
            function hideViewModeControls()
            {
                for (var i = 0; i < ids.length; i++)
                {
                    hideControlsbyClassName('viewmode' + ids[i]);
                }
            }
            function showEditModeControls(id)
            {
                for (var i = 0; i < ids.length; i++)
                {
                    showControlsbyClassName('editmode' + ids[i]);
                }
            }
            function showViewModeControls()
            {
                for (var i = 0; i < ids.length; i++)
                {
                    showControlsbyClassName('viewmode' + ids[i]);
                }
            }

            function Edit_onclick(id)
            {
                hideControlsbyClassName('viewmode' + id);
                showControlsbyClassName('editmode' + id);
            }

            function Cancel_onclick(id, name, gender, email, phone, active, rid)
            {
                hideControlsbyClassName('editmode' + id);
                showControlsbyClassName('viewmode' + id);
                document.getElementById('edit_name' + id).value = name;
                document.getElementById('edit_email' + id).value = email;
                document.getElementById('edit_phone' + id).value = phone;
                document.getElementById('edit_gender' + id).checked = gender;
                document.getElementById('edit_active' + id).checked = active;
                var options = document.getElementById('edit_rid' + id).getElementsByTagName('option');
                for (var i = 0; i < options.length; i++)
                {
                    if (options[i].value == rid)
                    {
                        document.getElementById('edit_rid' + id).selectedIndex = i;
                        break;
                    }
                }
            }
            function submitFormWithNewPage(page) {
                document.getElementById('pageNumber').value = page;
                document.getElementById('frmSearch').submit();
            }
        </script>
    </head>
    <body>
        <jsp:directive.include file="navbar.jsp"/>
        <div class="wrapper-login">
            <jsp:directive.include file="sidebar.jsp"/>
            <aside class="right-side">
                <section class="content container">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title text-uppercase mb-0">Manage Users</h5>
                            </div>
                            <form action="userslist" id="frmSearch" method="GET">
                                <input type="hidden" name="page" value="${requestScope.pageNumber}" id="pageNumber"/>
                                <div class="mb-3 d-flex justify-content-between" style="padding-left: 10px; padding-right: 10px;">
                                    <div class="d-flex">
                                        <div class="me-3 ">
                                            Sort by :
                                        </div>
                                        <div class="me-3">
                                            <div class="input-group">
                                                <span class="input-group-text" id="basic-addon1">Gender</span>
                                                <select  name="gender" onchange="submitFormWithNewPage(${requestScope.pageNumber});">
                                                    <option value="-1"  <c:if test="${requestScope.gender == -1}">selected</c:if>> All </option>
                                                    <option value="1" <c:if test="${requestScope.gender == 1}">selected</c:if>> Male </option>
                                                    <option value="0" <c:if test="${requestScope.gender == 0}">selected</c:if>> Female </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="me-3">
                                                <div class="input-group">
                                                    <span class="input-group-text" id="basic-addon1">Role</span>
                                                    <select name="rid" onchange="submitFormWithNewPage(${requestScope.pageNumber});" >
                                                        <option value="0">----ALL----</option>
                                                    <c:forEach items="${requestScope.listRoles}" var="r">
                                                        Department: <option
                                                            <c:if test="${requestScope.rid eq r.getrId()}">
                                                                selected="selected"
                                                            </c:if>
                                                            value="${r.getrId()}">${r.getrName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="me-3">
                                            <div class="input-group">
                                                <span class="input-group-text" id="basic-addon1">Status</span>
                                                <select name="active" onchange="submitFormWithNewPage(${requestScope.pageNumber});">
                                                    <option value="-1"  <c:if test="${requestScope.active == -1}">selected</c:if>> All </option>
                                                    <option value="1" <c:if test="${requestScope.active == 1}">selected</c:if>> Activate </option>
                                                    <option value="0" <c:if test="${requestScope.active == 0}">selected</c:if>> Deactivate </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="me-auto">
                                                <div class="search-box">
                                                    <button class="btn-search"><i class="fas fa-search"></i></button>
                                                    <input type="text" name="search" class="input-search" placeholder="Type to Search..." onchange="submitForm();">
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <button class="btn btn-success btn-block" type="button" data-toggle="modal" data-target="#user-form-modal">New User</button>
                                            <input class="btn btn-primary" type="submit" value="Filter">
                                        </div>
                                    </div>
                                </form>

                                <div class="table-responsive">
                                    <table class="table no-wrap user-table mb-0">
                                        <thead>
                                            <tr>
                                                <th scope="col" class="border-0 text-uppercase font-medium pl-4">#</th>
                                                <th scope="col" class="border-0 text-uppercase font-medium">Name</th>
                                                <th scope="col" class="border-0 text-uppercase font-medium">Gender</th>
                                                <th scope="col" class="border-0 text-uppercase font-medium">Contact</th>
                                                <th scope="col" class="border-0 text-uppercase font-medium">Activate</th>
                                                <th scope="col" class="border-0 text-uppercase font-medium">Role</th>
                                                <th scope="col" class="border-0 text-uppercase font-medium">Manage</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.accounts}" var="a">
                                        <form action="uupdateacc" id="frmUpdate" method="POST">
                                            <tr>
                                                <td class="pl-4">
                                                    ${a.getUser_id()}
                                                    <input type="hidden" name="id" value="${a.getUser_id()}"/>
                                                </td>
                                                <td>
                                                    <span class="viewmode${a.getUser_id()}"><h5 class="font-medium mb-0">${a.getFull_name()}</h5></span>
                                                    <input id="edit_name${a.getUser_id()}" type="text" value="${a.getFull_name()}" class="editmode${a.getUser_id()}" name="name"/>
                                                </td>
                                                <td>
                                                    <span class="viewmode${a.getUser_id()}">${a.gender?"Male":"Female"}</span>
                                                    <input id="edit_gender${a.getUser_id()}" name="gender" class="editmode${a.getUser_id()}" type="checkbox" <c:if test="${a.gender}">checked="checked"</c:if>/>
                                                    </td>
                                                    <td>
                                                        <span class="text-muted viewmode${a.getUser_id()}">${a.getEmail()}</span>
                                                    <input id="edit_email${a.getUser_id()}" type="text" value="${a.getEmail()}" class="editmode${a.getUser_id()}" name="email"/><br>
                                                    <span class="text-muted viewmode${a.getUser_id()}">${a.getPhone()}</span>
                                                    <input id="edit_phone${a.getUser_id()}" type="text" value="${a.getPhone()}" class="editmode${a.getUser_id()}" name="phone"/><br>
                                                </td>
                                                <td>
                                                    <div class="form-check form-switch">
                                                        <label class="form-check-label" for="flexSwitchCheckDisabled"></label>
                                                        <input class="form-check-input viewmode${a.getUser_id()}" type="checkbox" role="switch" id="flexSwitchCheckDisabled" <c:if test="${a.getActive() == 1}">checked</c:if>  disabled>
                                                        <input class="form-check-input editmode${a.getUser_id()}" type="checkbox" role="switch" id="edit_active${a.getUser_id()}" name="active"   <c:if test="${a.getActive() == 1}">checked</c:if>/>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <span class="viewmode${a.getUser_id()}">${a.getRole().getrName()}</span>
                                                    <select id="edit_rid${a.getUser_id()}" name="did" class="editmode${a.getUser_id()}"  >
                                                        <c:forEach items="${requestScope.listRoles}" var="d">
                                                            <option
                                                                <c:if test="${a.role.rId eq d.rId}">
                                                                    selected="selected"
                                                                </c:if>
                                                                value="${d.rId}">${d.rName}</option>
                                                        </c:forEach>
                                                    </select>

                                                </td>
                                                <td>
                                                    <a href="user?id=${a.getUser_id()}" class="btn btn-outline-info btn-circle btn-lg btn-circle ml-2 viewmode${a.getUser_id()}"><i class="fa-solid fa-arrow-up-right-from-square"></i></a>
                                                    <button type="button" class="btn btn-outline-info btn-circle btn-lg btn-circle ml-2 viewmode${a.getUser_id()}" onclick="Edit_onclick(${a.getUser_id()});"><i class="fa fa-edit"></i> </button>
                                                    <input class="btn btn-primary editmode${a.getUser_id()}" type="submit" value="Save">
                                                    <button type="button" class="btn btn-outline-info btn-circle btn-lg btn-circle ml-2 editmode${a.getUser_id()}" 
                                                            onclick="Cancel_onclick(${a.getUser_id()}, '${a.getFull_name()}',${a.gender}, '${a.getEmail()}', '${a.getPhone()}',${a.getActive()},${a.role.rId});"><i class="fa-solid fa-rectangle-xmark"></i></button>
                                                </td>
                                            </tr>
                                        </form>
                                    </c:forEach>
                                    </tbody>
                                </table>
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
                            </div>
                        </div>
                    </div>
                </section>
            </aside>
        </div>
        <!-- User Form Modal -->
        <div class="modal fade" role="dialog" tabindex="-1" id="user-form-modal">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Create User</h5>
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="py-1">
                            <form class="form" action="admin/adduser" method="post">

                                <div class="row">
                                    <div class="col">
                                        <p class="text-danger">${requestScope.signmess}</p>
                                        <div class="row">
                                            <div class="col">
                                                <div class="input-box">
                                                    <input name="fullname" type="text" spellcheck="false" value="${requestScope.acc.getFull_name()}" required />
                                                    <label for="">Fullname</label>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="form-group">
                                                    <div style="margin-bottom: 30px;">
                                                        <label>Gender </label>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" name="gender" id="exampleRadios1" value="1" checked>
                                                            <label class="form-check-label" for="exampleRadios1">
                                                                Male
                                                            </label>
                                                        </div>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" name="gender" id="exampleRadios2" value="0" >
                                                            <label class="form-check-label" for="exampleRadios2">
                                                                Female
                                                            </label>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col">
                                                <div class="input-box">
                                                    <input name="phone" type="text" spellcheck="false" value="${requestScope.acc.getPhone()}" required />
                                                    <label for="">Phone Number</label>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="input-box">
                                                    <input name="mail" type="text" spellcheck="false" value="${requestScope.acc.getEmail()}" required />
                                                    <label for="">Email</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col mb-3">
                                                <div class="input-box">
                                                    <input name="address" type="text" spellcheck="false" value="${requestScope.acc.getAddress()}" required />
                                                    <label for="">Address</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col">
                                                <div class="input-box">
                                                    <input name="user" type="text" spellcheck="false" value="${requestScope.acc.getUsername()}" required />
                                                    <label for="">Username</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12 col-sm-6 mb-3">
                                        <div class="mb-2"><b>Password</b></div>
                                        <div class="col">
                                            <div class="row">

                                                <div class="input-box">
                                                    <input class="p-input1" name="pass" type="password" spellcheck="false" required />
                                                    <label for="">Password</label>
                                                    <i class="uil uil-eye-slash toggle1"></i>
                                                </div>
                                            </div>

                                            <div class="row">

                                                <div class="input-box">
                                                    <input class="p-input2" name="repass" type="password" spellcheck="false" required />
                                                    <label for="">Confirm password</label>
                                                    <i class="uil uil-eye-slash toggle2"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 col-sm-5 offset-sm-1 mb-3">
                                        <div class="mb-2"><b>Role</b></div>
                                        <div class="row">
                                            <div class="col">
                                                <c:forEach items="${requestScope.listRoles}" var="r">
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="radio" name="role" id="exampleRadios1" <c:if test="${r.getrId() == 1}">checked</c:if> value="${r.getrId()}" >
                                                            <label class="form-check-label" for="exampleRadios1">
                                                            ${r.getrName()}
                                                        </label>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col d-flex justify-content-end">
                                        <button class="btn btn-primary" type="submit">Add User</button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
        hideEditModeControls();
    </script>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="css/js/userslist.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>

</html>