

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ===== Boxicons CSS ===== -->
<link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css" rel="stylesheet">

<style>
    
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
    var idss = [];
    <c:forEach items="${requestScope.students}" var="s">
    idss.push(${sessionScope.account.getUser_id()});
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
    function hideEditModeeControlss()
    {
        for (var i = 0; i < idss.length; i++)
        {
            hideControlsbyClassName('editmodee' + ${sessionScope.account.getUser_id()});
            hideControlsbyClassName('editmodee' + ${sessionScope.account.getActive()});
        }
    }
    function hideViewModeeControls()
    {
        for (var i = 0; i < idss.length; i++)
        {
            hideControlsbyClassName('viewmodee' + idss[i]);
        }
    }
    function showEditModeeControls(id)
    {
        for (var i = 0; i < idss.length; i++)
        {
            showControlsbyClassName('editmodee' + idss[i]);
        }
    }
    function showViewModeeControls()
    {
        for (var i = 0; i < idss.length; i++)
        {
            showControlsbyClassName('viewmodee' + idss[i]);
        }
    }

    function Edit_onclickk(id)
    {
        hideControlsbyClassName('viewmodee' + id);
        showControlsbyClassName('editmodee' + id);
    }

    function Cancel_onclickk(id, name, gender, phone, city, county, address)
    {
        hideControlsbyClassName('editmodee' + id);
        showControlsbyClassName('viewmodee' + id);
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
<header class="header">
    <a class="logo">
        Admin
    </a>
    <nav class="navbar">
        <a class="navbar-btn sidebar-toggle">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </a>
        <div class="navbar-right">
            <ul class="nav navbar-nav flex-row">
                <c:if test="${sessionScope.account.getRole_id() >= 3 && sessionScope.account.getRole_id() <= 5}">

                    <li class="nav-item dropdown me-2">
                        <div class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="/swp/${sessionScope.account.getImage_url()}" class="img-thumbnail" style="width: 40px;height: 40px; border-radius: 50%; margin: -0.5rem 0">
                            My Account
                        </div>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <a class="dropdown-item" data-bs-toggle="modal" href="#exampleModalToggle2" role="button"
                                   onclick="Cancel_onclickk(${sessionScope.account.getActive()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');"
                                   >Profile</a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="dropdown-item" data-bs-toggle="modal" href="#exampleModalToggle" role="button">Change password</a>
                            </li>
                            <li><a class="dropdown-item" href="logout">Log out</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>
<div class="modal fade" id="exampleModalToggle2" aria-hidden="true" aria-labelledby="exampleModalToggleLabe2" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="form-container">
                <div class="row">
                    <div class="col-md-3 border-right">
                        <div class="d-flex flex-column align-items-center text-center p-3 py-5">

                            <div class="rounded-box">
                                <div class="outer">
                                    <div style="position: absolute;">
                                        <input class="btn btn-primary profile-button viewmodee${sessionScope.account.getActive()}" type="button" value="Edit" style="width: auto;"  onclick="Edit_onclickk(${sessionScope.account.getActive()});"/>
                                    </div>
                                    <img class="rounded-circle avatar" id="blah" alt="your image" src="/swp/${sessionScope.account.getImage_url()}">
                                    <form action="/swp/updateacc" id="frmImgUpdate" method="POST" enctype="multipart/form-data">
                                        <div class="inner editmodee${sessionScope.account.getActive()}">
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

                            <div class="mt-5 text-center editmodee${sessionScope.account.getActive()}">
                                <input class="btn btn-primary profile-button editmodee${sessionScope.account.getActive()}" type="submit" onclick="submitForm();" value="Save" style="width: auto;"/>
                                <input id="cancel-btn" class="btn btn-primary profile-button editmodee${sessionScope.account.getActive()}" type="button" value="Cancel" style="width: auto;"
                                       onclick="Cancel_onclickk(${sessionScope.account.getActive()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');"/>
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
                                    <input class="btn btn-primary profile-button viewmodee${sessionScope.account.getUser_id()}" type="button" value="Edit" style="width: auto;"  onclick="Edit_onclickk(${sessionScope.account.getUser_id()});"/>

                                </div>
                                <div class="row mt-2">
                                    <div class="col-md-6">
                                        <label class="labels">FullName</label><br>
                                        <span class="viewmodee${sessionScope.account.getUser_id()}" >${sessionScope.account.getFull_name()}</span>
                                        <input id="edit_name${sessionScope.account.getUser_id()}" name="fname" type="text" class="form-control editmodee${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getFull_name()}" value="${sessionScope.account.getFull_name()}">
                                    </div>
                                    <div class="col-md-6">
                                        Gender<br>
                                        <span class="viewmodee${sessionScope.account.getUser_id()}" >${sessionScope.account.isGender()?"Male":"Female"}</span>
                                        <select name="gender" class="editmodee${sessionScope.account.getUser_id()}" style="margin-top: 5px;">
                                            <option value="1" <c:if test="${sessionScope.account.isGender()}">selected</c:if> >Male</option>
                                            <option value="0" <c:if test="${sessionScope.account.isGender()}">selected</c:if>>Female</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-12">
                                            <label class="labels">Mobile Number</label><br>
                                            <span class="viewmodee${sessionScope.account.getUser_id()}" >${sessionScope.account.getPhone()}</span>
                                        <input id="edit_phone${sessionScope.account.getUser_id()}" name="phone" type="text" class="form-control editmodee${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getPhone()}" value="${sessionScope.account.getPhone()}">
                                    </div>
                                    <div class="col-md-6">
                                        <label class="labels">City</label><br>
                                        <span class="viewmodee${sessionScope.account.getUser_id()}" >${sessionScope.account.getCity()}</span>
                                        <input id="edit_city${sessionScope.account.getUser_id()}" name="city" type="text" class="form-control editmodee${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getCity()}" value="${sessionScope.account.getCity()}"></div>
                                    <div class="col-md-6">
                                        <label class="labels">Country</label><br>
                                        <span class="viewmodee${sessionScope.account.getUser_id()}" >${sessionScope.account.getCountry()}</span>
                                        <input id="edit_country${sessionScope.account.getUser_id()}" name="country" type="text" class="form-control editmodee${sessionScope.account.getUser_id()}"  placeholder="${sessionScope.account.getCountry()}" value="${sessionScope.account.getCountry()}"></div>
                                    <div class="col-md-12">
                                        <label class="labels">Address </label><br>
                                        <span class="viewmodee${sessionScope.account.getUser_id()}" >${sessionScope.account.getAddress()}</span>
                                        <input id="edit_address${sessionScope.account.getUser_id()}" name="address" type="text" class="form-control editmodee${sessionScope.account.getUser_id()}" placeholder="${sessionScope.account.getAddress()}" value="${sessionScope.account.getAddress()}"></div>
                                </div>

                                <div class="mt-5 text-center">
                                    <input class="btn btn-primary profile-button editmodee${sessionScope.account.getUser_id()}" type="submit" value="Save" style="width: auto;"/>
                                    <input class="btn btn-primary profile-button editmodee${sessionScope.account.getUser_id()}" type="button" value="Cancel" style="width: auto;"
                                           onclick="Cancel_onclickk(${sessionScope.account.getUser_id()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="exampleModalToggle" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="form-container">
                <div class="title signup"style="width: 100%;">New password

                    <p style="font-size: 18px;">Enter your new password</p>
                </div>
                <div class="form-inner">
                    <form action="/swp/resetpass" method="POST" class="signup">
                        <input type="hidden" name="email" value="${sessionScope.account.getEmail()}"/>
                        <input type="hidden" name="choise" value="reset"/>
                        <p class="text-danger">${requestScope.messp}</p>
                        <div class="input-box">
                            <input
                                class="p-inputtttp"
                                name="pass"
                                type="password"
                                spellcheck="false"
                                required
                                />
                            <label for="">Current Password</label>
                            <i class="fa-solid fa-eye-slash toggleeeep"></i>
                        </div>

                        <div class="input-box">
                            <input
                                class="p-inputtt"
                                name="npass"
                                type="password"
                                spellcheck="false"
                                required
                                id="pswrd_1"
                                />
                            <label for="">New Password</label>
                            <i class="fa-solid fa-eye-slash toggleee"></i>
                        </div>

                        <div class="input-box">
                            <input
                                class="p-inputttt"
                                name="repass"
                                type="password"
                                spellcheck="false"
                                required
                                id="pswrd_2"
                                />
                            <label for="">Comfirm New Password</label>
                            <i class="fa-solid fa-eye-slash toggleeee"></i>
                        </div>
                        <div class="field btn">
                            <div class="btn-layer"></div>
                            <input type="submit" value="Change"/>
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

    hideEditModeeControlss();
    Cancel_onclickk(${sessionScope.account.getUser_id()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');
    Cancel_onclickk(${sessionScope.account.getActive()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');
</script>      
<script>
    const pswrd_1 = document.querySelector("#pswrd_1");
    const pswrd_2 = document.querySelector("#pswrd_2");
    const errorText = document.querySelector(".text-danger");
    const showBtn = document.querySelector(".show");
    const btn = document.querySelector("button");
    btn.onclick = function () {
        if (pswrd_1.value != pswrd_2.value && pswrd_1.value != null && pswrd_2.value != null) {
            errorText.style.display = "block";
            errorText.classList.remove("matched");
            errorText.textContent = "Error! Confirm Password Not Match";
            return false;
        } else if (pswrd_1.value == pswrd_2.value && pswrd_1.value != null && pswrd_2.value != null) {
            errorText.style.display = "block";
            errorText.classList.add("matched");
            errorText.textContent = "Nice! Confirm Password Matched";
            return false;
        }
    }


    const toggleee = document.querySelector(".toggleee"),
            inputtt = document.querySelector(".p-inputtt");
    const toggleeee = document.querySelector(".toggleeee"),
            inputttt = document.querySelector(".p-inputttt");
    const toggleeeep = document.querySelector(".toggleeeep"),
            inputtttp = document.querySelector(".p-inputtttp");
    toggleee.addEventListener("click", () => {
        if (inputtt.type === "password") {
            inputtt.type = "text";
            toggleee.classList.replace("fa-eye-slash", "fa-eye");
        } else {
            toggleee.classList.replace("fa-eye", "fa-eye-slash");
            inputtt.type = "password";
        }
    });
    toggleeee.addEventListener("click", () => {
        if (inputttt.type === "password") {
            inputttt.type = "text";
            toggleeee.classList.replace("fa-eye-slash", "fa-eye");
        } else {
            toggleeee.classList.replace("fa-eye", "fa-eye-slash");
            inputttt.type = "password";
        }
    });
    toggleeeep.addEventListener("click", () => {
        if (inputtttp.type === "password") {
            inputtttp.type = "text";
            toggleeeep.classList.replace("fa-eye-slash", "fa-eye");
        } else {
            toggleeeep.classList.replace("fa-eye", "fa-eye-slash");
            inputtttp.type = "password";
        }
    });

</script>