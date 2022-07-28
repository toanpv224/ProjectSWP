<%-- 
    Document   : createnewpassword
    Created on : Jun 11, 2022, 9:41:44 PM
    Author     : tretr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="utf-8" />
        <title>Create new password</title>
        <link rel="stylesheet" href="css/create_new_password.css" />
        <link
            rel="stylesheet"
            href="https://unicons.iconscout.com/release/v4.0.0/css/line.css"
            />

        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
        <div class="wrapper-login">
            <header>New password</header>
            <p>Enter your new password</p>

            <div class="form-container">
                <div class="form-inner">
                    <form action="resetpass" method="POST" class="signup">
                        <p class="text-danger">${requestScope.mess}</p>

                        <div class="input-box">
                            <input
                                class="p-input"
                                name="npass"
                                type="password"
                                spellcheck="false"
                                required
                                />
                            <label for="">Password</label>
                            <i class="uil uil-eye-slash toggle"></i>
                        </div>

                        <div class="input-box">
                            <input
                                class="p-inputt"
                                name="repass"
                                type="password"
                                spellcheck="false"
                                required
                                />
                            <label for="">Comfirm Password</label>
                            <i class="uil uil-eye-slash togglee"></i>
                        </div>
                        <div class="field btn">
                            <div class="btn-layer"></div>
                            <input type="hidden" name="email" value="${requestScope.mail}"/>
                            <input type="hidden" name="choise" value="forgot"/>
                            <input type="submit" value="Save" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script>
            const toggle = document.querySelector(".toggle"),
                    input = document.querySelector(".p-input");
            const togglee = document.querySelector(".togglee"),
                    inputt = document.querySelector(".p-inputt");
            toggle.addEventListener("click", () => {
                if (input.type === "password") {
                    input.type = "text";
                    toggle.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    toggle.classList.replace("uil-eye", "uil-eye-slash");
                    input.type = "password";
                }
            });
            togglee.addEventListener("click", () => {
                if (inputt.type === "password") {
                    inputt.type = "text";
                    togglee.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    togglee.classList.replace("uil-eye", "uil-eye-slash");
                    inputt.type = "password";
                }
            });
        </script>
    </body>
</html>
