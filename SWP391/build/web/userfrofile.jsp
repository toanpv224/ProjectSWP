<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!--<link href="css/style.css" rel="stylesheet">-->
        <link rel="stylesheet" href="css/userfrofile.css">
        <link rel="stylesheet" href="css/stylelogin.css" />

        <title>User Profile</title>
        <style>
            .wrapper-login {
                margin: 30px auto;
            }
            .py-5{
                padding-top: 3px!important;
            }
            .rounded-box {
                width: 150px;
                height: 150px;
                display: block;
                margin: 0 auto;
            }

            .outer {
                width: 100% !important;
                height: 100% !important;
                max-width: 150px !important; /* any size */
                max-height: 150px !important; /* any size */
                margin: auto;
                background-color: #6eafd4;
                border-radius: 100%;
                position: absolute;
            }

            .inner {
                background-color: #999;
                width: 50px;
                height: 50px;
                border-radius: 100%;
                position: absolute;
                bottom: 0;
                right: 0;
            }

            .inner:hover {
                background-color: #5555ff;
            }
            .inputfile {
                opacity: 0;
                overflow: hidden;
                position: absolute;
                z-index: 1;
                width: 50px;
                height: 50px;
            }
            .inputfile + label {
                font-size: 1.25rem;
                text-overflow: ellipsis;
                white-space: nowrap;
                display: inline-block;
                overflow: hidden;
                width: 50px;
                height: 50px;
                pointer-events: none;
                cursor: pointer;
                line-height: 50px;
                text-align: center;
            }
            .inputfile + label svg {
                fill: #fff;
            }
            #imgInp,#change-photo {
                opacity: 0;
                position: absolute;
                z-index: -1;
            }
            .avatar{
                width:150px;
                height:150px;
                border-radius:50%;
                overflow:hidden;
            }
            .rounded-circle img{
                width:100%;
                height:100%;
                object-fit: cover;
            }
        </style>
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
                for (var i = 0; i < ids.length; i++)
                {
                    hideControlsbyClassName('editmode' + ${sessionScope.account.getUser_id()});
                    hideControlsbyClassName('editmode' + ${sessionScope.account.getActive()});
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

            function Cancel_onclick(id, name, gender, phone, city, county, address)
            {
                hideControlsbyClassName('editmode' + id);
                showControlsbyClassName('viewmode' + id);
                document.getElementById('edit_name' + id).value = name;
                document.getElementById('edit_phone' + id).value = phone;
                document.getElementById('edit_city' + id).value = city;
                document.getElementById('edit_county' + id).value = county;
                document.getElementById('edit_address' + id).value = address;
                document.getElementById('edit_gender' + id).checked = gender;
                var options = document.getElementById('edit_did' + id).getElementsByTagName('option');
                for (var i = 0; i < options.length; i++)
                {
                    if (options[i].value == did)
                    {
                        document.getElementById('edit_did' + id).selectedIndex = i;
                        break;
                    }
                }
            }

        </script>

    </head>

    <body>
        <div class="overlay-login" id="divThree">
            <div class="wrapper-login" style="max-width: 1000px; max-height: 620px; font-size:10px" >
                <div class="container rounded bg-white mt-5 mb-5">
                    <a class="close" href="#a" id="close" onclick="Cancel_onclick(${sessionScope.account.getUser_id()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');
                            Cancel_onclick(${sessionScope.account.getActive()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');">
                        &times;
                    </a>
                    <div class="row">
                        <div class="col-md-3 border-right">
                            <div class="d-flex flex-column align-items-center text-center p-3 py-5">

                                <div class="rounded-box">
                                    <div class="outer">
                                        <div style="position: absolute;">
                                            <input class="btn btn-primary profile-button viewmode${sessionScope.account.getActive()}" type="button" value="Edit" style="width: auto;"  onclick="Edit_onclick(${sessionScope.account.getActive()});"/>
                                        </div>
                                        <img class="rounded-circle avatar" id="blah" alt="your image" src="/swp/${sessionScope.account.getImage_url()}">
                                        <form action="/swp/updateacc" id="frmImgUpdate" method="POST" enctype="multipart/form-data">
                                            <div class="inner editmode${sessionScope.account.getActive()}">
                                                <input type="hidden" name="id" value="${sessionScope.account.getUser_id()}"/>
                                                <input type="hidden" name="type" value="1"/>
                                                <input accept="image/*" type="file" name="file" id="imgInp">
                                                <label for="imgInp" style="cursor: pointer;"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 20 17"><path d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z"></path></svg></label>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <span class="font-weight-bold">
                                    ${sessionScope.account.getFull_name()}
                                </span>
                                <span class="text-black-50">
                                    ${sessionScope.account.getEmail()}
                                </span>

                                <div class="mt-5 text-center editmode${sessionScope.account.getActive()}">
                                    <input class="btn btn-primary profile-button editmode${sessionScope.account.getActive()}" type="submit" onclick="submitForm();" value="Save" style="width: auto;"/>
                                    <input id="cancel-btn" class="btn btn-primary profile-button editmode${sessionScope.account.getActive()}" type="button" value="Cancel" style="width: auto;"
                                           onclick="Cancel_onclick(${sessionScope.account.getActive()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');"/>
                                </div>
                                <span>
                                </span>
                            </div>
                        </div>
                        <div class="col-md-9 border-right">
                            <form action="/swp/updateacc" method="POST" enctype="multipart/form-data">
                                <input type="hidden" name="type" value="0"/>
                                <input type="hidden" name="id" value="${sessionScope.account.getUser_id()}"/>
                                <div class="p-3 py-5">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h4 class="text-right">Profile Settings</h4>                                    
                                        <input class="btn btn-primary profile-button viewmode${sessionScope.account.getUser_id()}" type="button" value="Edit" style="width: auto;"  onclick="Edit_onclick(${sessionScope.account.getUser_id()});"/>

                                    </div>
                                    <div class="row mt-2">
                                        <div class="col-md-6">
                                            <label class="labels">FullName</label><br>
                                            <span class="viewmode${sessionScope.account.getUser_id()}" >${sessionScope.account.getFull_name()}</span>
                                            <input id="edit_name${sessionScope.account.getUser_id()}" name="fname" type="text" class="form-control editmode${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getFull_name()}" value="${sessionScope.account.getFull_name()}">
                                        </div>
                                        <div class="col-md-6">
                                            Gender<br>
                                            <span class="viewmode${sessionScope.account.getUser_id()}" >${sessionScope.account.isGender()?"Male":"Female"}</span>
                                            <select name="gender" class="editmode${sessionScope.account.getUser_id()}" style="margin-top: 5px;">
                                                <option value="1" <c:if test="${sessionScope.account.isGender()}">selected</c:if> >Male</option>
                                                <option value="0" <c:if test="${sessionScope.account.isGender()}">selected</c:if>>Female</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row mt-3">
                                            <div class="col-md-12">
                                                <label class="labels">Mobile Number</label><br>
                                                <span class="viewmode${sessionScope.account.getUser_id()}" >${sessionScope.account.getPhone()}</span>
                                            <input id="edit_phone${sessionScope.account.getUser_id()}" name="phone" type="text" class="form-control editmode${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getPhone()}" value="${sessionScope.account.getPhone()}">
                                        </div>
                                        <div class="col-md-6">
                                            <label class="labels">City</label><br>
                                            <span class="viewmode${sessionScope.account.getUser_id()}" >${sessionScope.account.getCity()}</span>
                                            <input id="edit_city${sessionScope.account.getUser_id()}" name="city" type="text" class="form-control editmode${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getCity()}" value="${sessionScope.account.getCity()}"></div>
                                        <div class="col-md-6">
                                            <label class="labels">Country</label><br>
                                            <span class="viewmode${sessionScope.account.getUser_id()}" >${sessionScope.account.getCountry()}</span>
                                            <input id="edit_country${sessionScope.account.getUser_id()}" name="country" type="text" class="form-control editmode${sessionScope.account.getUser_id()}"  placeholder="${sessionScope.account.getCountry()}" value="${sessionScope.account.getCountry()}"></div>
                                        <div class="col-md-12">
                                            <label class="labels">Address </label><br>
                                            <span class="viewmode${sessionScope.account.getUser_id()}" >${sessionScope.account.getAddress()}</span>
                                            <input id="edit_address${sessionScope.account.getUser_id()}" name="address" type="text" class="form-control editmode${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getAddress()}" value="${sessionScope.account.getAddress()}"></div>
                                    </div>

                                    <div class="mt-5 text-center">
                                        <input class="btn btn-primary profile-button editmode${sessionScope.account.getUser_id()}" type="submit" value="Save" style="width: auto;"/>
                                        <input class="btn btn-primary profile-button editmode${sessionScope.account.getUser_id()}" type="button" value="Cancel" style="width: auto;"
                                               onclick="Cancel_onclick(${sessionScope.account.getUser_id()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            const cancelBtn = document.querySelector("#cancel-btn");
            
            imgInp.onchange = evt => {
                const [file] = imgInp.files
                if (file) {
                    blah.src = URL.createObjectURL(file)
                }
            }
//            cancelBtn.onclick = evt => {
//                const  [file] = '${sessionScope.account.getImage_url()}'
//                if(file) {
//                    
//                }
//            }
            cancelBtn.addEventListener("click", function () {
                blah.src = '${sessionScope.account.getImage_url()}';
            });

            hideEditModeControls();
            Cancel_onclick(${sessionScope.account.getUser_id()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');
            Cancel_onclick(${sessionScope.account.getActive()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');
        </script>
        <!--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>-->
        <!--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-kjU+l4N0Yf4ZOJErLsIcvOU2qSb74wXpOhqTvwVx3OElZRweTnQ6d31fXEoRD1Jy" crossorigin="anonymous"></script>-->
    </body>

</html>