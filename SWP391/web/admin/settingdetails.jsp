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
        <script>
            var ids = [];
            <c:forEach items="${requestScope.students}" var="s">
            ids.push(${sessionScope.account.getUser_id()});
            </c:forEach>
            function submitForm()
            {
                document.getElementById('frmImgUpdate').submit();
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
                hideControlsbyClassName('editmode' + ${requestScope.settingsList.settingId});
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

            function Cancel_onclick(id, type, value, order, active, description)
            {
                hideControlsbyClassName('editmode' + id);
                showControlsbyClassName('viewmode' + id);
                document.getElementById('edit_type' + id).value = type;
                document.getElementById('edit_value' + id).value = value;
                document.getElementById('edit_order' + id).value = order;
                document.getElementById('edit_description' + id).value = description;
                document.getElementById('edit_active' + id).checked = active;
            }

        </script>
    </head>
    <body>
        <jsp:directive.include file="navbar.jsp"/>
        <div class="wrapper-login">
            <jsp:directive.include file="sidebar.jsp"/>
            <aside class="right-side">
                <section class="content container">
                    <div class="row" style="margin-bottom: 20px;">
                        <h2>Settings Detail</h2>
                        <div class="bg-light rounded-4 ">
                            <form action="settingdetails" method="POST">
                                <div class="settings-wrapper " style="margin-top: 10px;">
                                    <div class="d-flex flex-row-reverse align-items-center mb-3">
                                        <input class="btn btn-primary profile-button viewmode${requestScope.settingsList.settingId}" type="button" value="Edit" style="width: auto;"  onclick="Edit_onclick(${requestScope.settingsList.settingId});"/>
                                    </div>
                                    <input type="hidden" name="ID" value="${requestScope.settingsList.settingId}"/>
                                    <input type="hidden" name="name" value="${requestScope.settingsList.name}"/>
                                    <input type="hidden" name="type" value="${requestScope.settingsList.type}"/>
                                    <input type="hidden" name="value" value="${requestScope.settingsList.value}"/>
                                    <input type="hidden" name="order" value="${requestScope.settingsList.order}"/>
                                    <div class="row mt-2">
                                        <div class="col-md-6 rounded ">
                                            <label class="labels"><h5>ID</h5></label><br>
                                            <span>${requestScope.settingsList.settingId}</span>
                                        </div>
                                        <div class="col-md-6 rounded ">
                                            <label class="labels"><h5>Type</h5></label><br>
                                            <span>${requestScope.settingsList.type}</span>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="col-md-6 rounded ">
                                            <label class="labels"><h5>Value</h5></label><br>
                                            <span>${requestScope.settingsList.value}</span>
                                        </div>
                                        <div class="col-md-6 rounded ">
                                            <label class="labels"><h5>Order</h5></label><br>
                                            <span>${requestScope.settingsList.order}</span>
                                        </div>
                                    </div>
                                    <div class="row mt-2">
                                        <div class="form-check form-switch ">
                                            <label class="labels"><h5>Active</h5></label>
                                            <label class="form-check-label" for="flexSwitchCheckDisabled"></label>
                                            <input class="form-check-input viewmode${requestScope.settingsList.settingId}" type="checkbox" role="switch" id="flexSwitchCheckDisabled" <c:if test="${requestScope.settingsList.status == 1}">checked</c:if>  disabled>
                                            <input class="form-check-input editmode${requestScope.settingsList.settingId}" 
                                                   type="checkbox" 
                                                   role="switch" 
                                                   id="edit_active${requestScope.settingsList.settingId}" 
                                                   value="on"
                                                   name="active"  <c:if test="${requestScope.settingsList.status == 1}">checked</c:if>/>
                                            </div>
                                        </div>
                                        <div class="row mt-2">
                                            <div class="col-md-12 rounded ">
                                                <label class="labels"><h5>Description</h5></label><br>
                                                <div class="callout viewmode${requestScope.settingsList.settingId}">
                                                <c:if test="${requestScope.settingsList.description == null}">(Nah)</c:if>
                                                <c:if test="${requestScope.settingsList.description != null}">${requestScope.settingsList.description}</c:if>
                                                </div>
                                                <div class="form-floating">
                                                    <textarea class="form-control editmode${requestScope.settingsList.settingId}" 
                                                          placeholder="<c:if test="${requestScope.settingsList.description == null}">(Nah)</c:if>
                                                          <c:if test="${requestScope.settingsList.description != null}">${requestScope.settingsList.description}</c:if>" style="height: 100px"
                                                          id="edit_description${requestScope.settingsList.settingId}" name="description"
                                                          value="<c:if test="${requestScope.settingsList.description == null}">(Nah)</c:if>
                                                          <c:if test="${requestScope.settingsList.description != null}">${requestScope.settingsList.description}</c:if>"></textarea>
                                                <label class="editmode${requestScope.settingsList.settingId}"  for="edit_description${requestScope.settingsList.settingId}">Description</label>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="mt-5 text-center" style="margin-bottom: 20px;">
                                        <input class="btn btn-primary profile-button editmode${requestScope.settingsList.settingId}" type="submit" value="Save" style="width: auto;"/>
                                        <input class="btn btn-primary profile-button editmode${requestScope.settingsList.settingId}" type="button" value="Cancel" style="width: auto;"
                                               onclick="Cancel_onclick(${requestScope.settingsList.settingId},
                                                               '${requestScope.settingsList.type}',
                                                               '${requestScope.settingsList.value}',
                                                               '${requestScope.settingsList.order}',
                                               ${requestScope.settingsList.status},
                                                               '${requestScope.settingsList.description}');"/>
                                    </div>
                                </div>
                            </form>
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
                                <form action="admin/addsetting" method="POST">
                                    <input type="hidden" name="ID" value="${requestScope.settingsList.settingId}"/>
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
                                <form action="admin/addsetting" method="POST">
                                    <input type="hidden" name="ID" value="${requestScope.settingsList.settingId}"/>
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
    <script>
        hideEditModeControls();
    </script>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
    </script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="../js/admin.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>
</html>