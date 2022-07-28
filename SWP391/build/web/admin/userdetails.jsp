<%@page import="dal.AccountDAO"%>
<%@page import="dal.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="user" value="${requestScope.user}"></c:set>

<%
    AccountDAO accountDAO = new AccountDAO();
%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>User Details</title>

        <!--bootstrap-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
        <link href="../css/stylesaleheadersider.css" rel="stylesheet"/>
        <!--<link href="css/userslist.css" rel="stylesheet"/>-->
        <link href="../css/admin.css" rel="stylesheet"/>
        <link href="../css/user.css" rel="stylesheet"/>
        <style>
            input[aria-label-name="role-selection"]:checked + div {
                background: rgb(135, 206, 250) !important;
            }
        </style>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <jsp:directive.include file="navbar.jsp"/>
        <div class="wrapper-login">
            <jsp:directive.include file="sidebar.jsp"/>
            <aside class="right-side">
                <section class="content container">
                    <div class="col-md-12">
                        <div class="card mb-3">
                            <div class="card-body">
                                <div class="d-flex justify-content-end">           
                                    <a class="btn btn-primary cursor-pointer" data-bs-toggle="modal" data-bs-target="#modal-add-new-user">Add new user</a>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <h2 class="card-title text-uppercase mb-0">User Details</h2>
                                </div>
                                <div class="mt-3">
                                    <div class="p-2">
                                        <div class="row">
                                            <div class="col-3">
                                                <div class="user-avatar-container">
                                                    <img src="../${user.image_url != null ? user.image_url : "https://npsot.org/wp/speaker/files/2022/02/placeholder-image-person-jpg.jpg"}" class="w-100 rounded" alt="...">
                                                </div>
                                            </div>
                                            <div class="col-9" style="font-size: 1.3rem">
                                                <div class="row">
                                                    <div class="col-4 mb-3">
                                                        <b>User ID: </b>
                                                        <span>${user.user_id}</span>
                                                    </div>
                                                    <div class="col-8 mb-3">
                                                        <b>Fullname: </b>
                                                        <span>${user.full_name}</span>
                                                    </div>
                                                    <div class="col-4 mb-3">
                                                        <b>Gender: </b>
                                                        <span>${user.gender ? "Male" : "Female"}</span>
                                                    </div>
                                                    <div class="col-8 mb-3">
                                                        <b>Email: </b>
                                                        <span>${user.email}</span>
                                                    </div>
                                                    <div class="col-4 mb-3">
                                                        <b>Mobile: </b>
                                                        <span>${user.phone}</span>
                                                    </div>
                                                    <div class="col-8 mb-3">
                                                        <b>Address: </b>
                                                        <span>${user.address}</span>
                                                    </div>
                                                    <div class="col-4 mb-3">
                                                        <b>Active: </b>
                                                        <span id="user_active_status" style="color: ${user.active == 1 ? "green" : "red"}">${user.active == 1 ? "Yes" : "No"}</span>
                                                        <a id="button_activate_disactivate" class="btn ${user.active == 1 ? "btn-outline-danger" : "btn-outline-success"} cursor-pointer ms-3" data-bs-toggle="modal" data-bs-target="${user.active == 1 ? "#confirm-disactivate" : "#confirm-activate"}">${user.active == 1 ? "Disactivate" : "Activate"}</a>
                                                    </div>
                                                    <div class="col-8 mb-3">
                                                        <b>Role: </b>
                                                        <span id="user_role_name">${user.role.rName}</span>
                                                        <a class="btn btn-light dropdown-togglecursor-po cursor-pointer ms-3" role="button" id="dropdownMenuLink" data-bs-toggle="modal" data-bs-target="#select-role">
                                                            Edit Role
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </aside>
        </div>
        <div class="modal" id="confirm-activate" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Do you want to activate this account?</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                        <button type="button" class="btn btn-primary" onclick="activate(${user.user_id})">Yes</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal" id="confirm-disactivate" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Do you want to disactivate this account?</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                        <button type="button" class="btn btn-primary" onclick="disactivate(${user.user_id})">Yes</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal" id="select-role" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Change user role?</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="model-body">
                        <div class="p-3">
                            <form id="form-change-role">
                                <c:forEach var="role" items="<%= accountDAO.getListRole()%>">

                                    <div class="mb-3">
                                        <label class="w-100">
                                            <input class="dropdown-item" type="radio" aria-label-name="role-selection" name="role_id" value="${role.rId}" ${user.role.rId == role.rId ? "checked" : ""} hidden>
                                            <div class="py-3 px-5 rounded bg-light" style="cursor: pointer;">${role.rName}</div>
                                        </label>
                                    </div>
                                </c:forEach>
                                <input type="hidden" name="id" value="${user.user_id}">
                                <input type="hidden" name="action" value="changerole">
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                        <button type="button" class="btn btn-primary" onclick="changerole(${user.user_id})">Save</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" role="dialog" tabindex="-1" id="modal-add-new-user">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Create User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon1">Mail</span>
                                <input name="mail" type="text" class="form-control" placeholder="@gmail.com" aria-label="mail" aria-describedby="basic-addon1">
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon1">Username</span>
                                <input name="username" type="text" class="form-control" placeholder="username" aria-label="username" aria-describedby="basic-addon1">
                            </div>
                            <div class="col-6 mb-3">
                                <div class="input-group">
                                    <span class="input-group-text" id="basic-addon1">Full Name</span>
                                    <input name="fullname" type="text" class="form-control" placeholder="fullname" aria-label="fullname" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="col-6 d-flex align-items-center mb-3">
                                <span>Gender:&nbsp;&nbsp;</span>
                                <div class="form-check form-check-inline">
                                    <label>
                                        <input class="form-check-input" type="radio" name="gender" value="1">
                                        <span>Male</span>
                                    </label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <label>
                                        <input class="form-check-input" type="radio" name="gender" value="0">
                                        <span>Female</span>
                                    </label>
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <div class="input-group">
                                    <span class="input-group-text" id="basic-addon1">Phone</span>
                                    <input name="phone" type="text" class="form-control" placeholder="phone" aria-label="phone" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="col-8 mb-3">
                                <div class="input-group">
                                    <span class="input-group-text" id="basic-addon1">Address</span>
                                    <input name="address" type="text" class="form-control" placeholder="address" aria-label="address" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="col-6 mb-3">
                                <div class="position-relative">
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon1">Role</span>
                                        <select class="form-control" name="role_id" aria-describedby="basic-addon1">
                                            <c:forEach var="role" items="<%= accountDAO.getListRole()%>">
                                                <option value="${role.rId}">${role.rName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="position-absolute top-50" style="right: 10px; transform: translate(-50%, -50%);">
                                        <i class="fa-solid fa-caret-down"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div><h4 style="color: red" id="error-notify-create-user"></h4></div>
                        <div><h4 style="color: green" id="success-notify-create-user"></h4></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" onclick="createuser()">Create</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal" id="notify-create-user" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Create successful</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <h5 class="modal-content">Check mail to get account information.</h5>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Ok</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="../js/admin/userdetails.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>

</html>