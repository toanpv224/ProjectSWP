<%-- 
    Document   : login
    Created on : May 25, 2022, 7:55:53 PM
    Author     : tretr
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <title>Online Shopping</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--<link rel="stylesheet" href="css/stylelogin.css" />-->
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />

        <link rel="stylesheet" href="css/stylelogin.css" />
    </head>
    <body>
        <div class="overlay-login" id="divOne">
            <div class="wrapper-login" id="wrapper">
                <a class="close" href="#a" id="close">&times;</a>
                <div class="title-text-login">
                    <c:if test="${requestScope.signmess == null}">
                        <div class="title login">Login Form</div>
                    </c:if>
                    <c:if test="${requestScope.signmess != null}">
                        <div class="title login" style="margin-left: -50%;">Login Form</div>
                    </c:if>
                    <div class="title signup">Signup Form</div>
                </div>
                <div class="form-container">
                    <div class="slide-controls">
                        <c:if test="${requestScope.signmess == null}">
                            <input type="radio" name="slide" id="login" checked />
                            <input type="radio" name="slide" id="signup" />
                        </c:if>
                        <c:if test="${requestScope.signmess != null}">
                            <input type="radio" name="slide" id="login"  />
                            <input type="radio" name="slide" id="signup" checked/>
                        </c:if>
                        <label for="login" class="slide login">Login</label>
                        <label for="signup" class="slide signup">Signup</label>
                        <div class="slider-tab"></div>
                    </div>
                    <div class="form-inner">
                        <c:if test="${requestScope.signmess == null}">
                            <form action="login" class="login" method="post">
                            </c:if>
                            <c:if test="${requestScope.signmess != null}">
                                <form action="login" class="login" method="post" style="margin-left: -50%;">   
                                </c:if>
                                <p class="text-danger">${requestScope.mess}</p>
                                <div class="input-box">
                                    <input name="user" type="text" value="${cookie.userC.value}" spellcheck="false" required />
                                    <label for="">Email</label>
                                </div>
                                <div class="input-box">
                                    <input class="p-input" name="pass" value="${cookie.passC.value}" type="password" spellcheck="false" required />
                                    <label for="">Password</label>
                                    <i class="uil uil-eye-slash toggle"></i>
                                </div>
                                <div class="pass-link" style="display: flex;justify-content: space-between" >
                                    <div>
                                        <input name="remember" type="checkbox" class="form-check-input" id="checkBoxRemember" ${cookie.rememC.value eq "on" ? "checked" : ""} value="on"/>
                                        <label class="form-check-label" for="checkBoxRemember">Remember me</label>
                                    </div>
                                    <div>
                                        <a href="forgot">Fogot Pasword?</a>
                                    </div>
                                </div>
                                <div class="field btn">
                                    <div class="btn-layer"></div>
                                    <input type="submit" value="Login" />
                                </div>
                                <div class="signup-link">
                                    Not a member? <a href="">Signup now</a>
                                </div>
                            </form>
                            <form action="register" method="post" class="signup" >
                                <p class="text-danger">${requestScope.signmess}</p>

                                <div class="input-box">
                                    <input name="fullname" type="text" spellcheck="false" value="${requestScope.acc.getFull_name()}" required />
                                    <label for="">Fullname</label>
                                </div>
                                <div style="margin-bottom: 10px;">
                                    Gender
                                    <select name="gender">
                                        <option value="true" selected>Male</option>
                                        <option value="false">Female</option>
                                    </select>
                                </div>
                                <div class="input-box">
                                    <input name="address" type="text" spellcheck="false" value="${requestScope.acc.getAddress()}" required />
                                    <label for="">Address</label>
                                </div>
                                <div class="input-box">
                                    <input name="mail" type="text" spellcheck="false" value="${requestScope.acc.getEmail()}" required />
                                    <label for="">Email</label>
                                </div>
                                <div class="input-box">
                                    <input name="phone" type="text" spellcheck="false" value="${requestScope.acc.getPhone()}" required />
                                    <label for="">Phone Number</label>
                                </div>
                                <div class="input-box">
                                    <input name="user" type="text" spellcheck="false" value="${requestScope.acc.getUsername()}" required />
                                    <label for="">Username</label>
                                </div>
                                <div class="input-box">
                                    <input class="p-input1" name="pass" type="password" spellcheck="false" required />
                                    <label for="">Password</label>
                                    <i class="uil uil-eye-slash toggle1"></i>
                                </div>           
                                <div class="input-box">
                                    <input class="p-input2" name="repass" type="password" spellcheck="false" required />
                                    <label for="">Confirm password</label>
                                    <i class="uil uil-eye-slash toggle2"></i>
                                </div>

                                <div class="field btn">
                                    <div class="btn-layer"></div>
                                    <input type="submit" value="Signup" />
                                </div>
                            </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            const loginText = document.querySelector(".title-text .login");
            const loginUserData = document.querySelector('.login input[name="user"]');
            const loginPassData = document.querySelector('.login input[name="pass"]');
            const signupFullnameData = document.querySelector('.signup input[name="fullname"]');
            const signupAddressData = document.querySelector('.signup input[name="address"]');
            const signupPhoneData = document.querySelector('.signup input[name="mail"]');
            const signupMailData = document.querySelector('.signup input[name="phone"]');
            const signupUserData = document.querySelector('.signup input[name="user"]');
            const signupPassData = document.querySelector('.signup input[name="pass"]');
            const signupRePassData = document.querySelector('.signup input[name="repass"]');
            const loginForm = document.querySelector("form.login");
            const loginBtn = document.querySelector("label.login");
            const signupBtn = document.querySelector("label.signup");
            const signupLink = document.querySelector("form .signup-link a");
            signupBtn.onclick = () => {
                loginUserData.value = '';
                loginPassData.value = '';
                document.getElementById("checkBoxRemember").checked = false;
                loginForm.style.marginLeft = "-50%";
                loginText.style.marginLeft = "-50%";
                document.querySelector("div .wrapper-login").style.maxWidth = "650px";
            };
            loginBtn.onclick = () => {
                signupFullnameData.value = '';
                signupAddressData.value = '';
                signupMailData.value = '';
                signupPhoneData.value = '';
                signupUserData.value = '';
                signupPassData.value = '';
                signupRePassData.value = '';
                loginForm.style.marginLeft = "0%";
                loginText.style.marginLeft = "0%";
                document.querySelector("div .wrapper-login").style.maxWidth = "450px";

            };
            signupLink.onclick = () => {
                signupBtn.click();
                return false;
            };


            const toggle = document.querySelector(".toggle"),
                    input = document.querySelector(".p-input");
            const toggle1 = document.querySelector(".toggle1"),
                    input1 = document.querySelector(".p-input1");
            const toggle2 = document.querySelector(".toggle2"),
                    input2 = document.querySelector(".p-input2");

            toggle.addEventListener("click", () => {
                if (input.type === "password") {
                    input.type = "text";
                    toggle.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    toggle.classList.replace("uil-eye", "uil-eye-slash");
                    input.type = "password";
                }
            });

            toggle1.addEventListener("click", () => {
                if (input1.type === "password") {
                    input1.type = "text";
                    toggle1.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    toggle1.classList.replace("uil-eye", "uil-eye-slash");
                    input1.type = "password";
                }
            });

            toggle2.addEventListener("click", () => {
                if (input2.type === "password") {
                    input2.type = "text";
                    toggle2.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    toggle2.classList.replace("uil-eye", "uil-eye-slash");
                    input2.type = "password";
                }
            });


            if (${requestScope.signmess != null}) {
                document.querySelector(".overlay-login").style.visibility = "visible";
                document.querySelector(".overlay-login").style.opacity = "1";
            }

        </script>
    </body>
</html>