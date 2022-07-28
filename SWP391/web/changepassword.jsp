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

        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
        <div class="overlay-login" id="divTwo">
            <div class="wrapper-login">
                <a class="close" href="#a" id="close">&times;</a>
                <div class="title signup"style="width: 100%;">New password

                    <p>Enter your new password</p>
                </div>
                <div class="form-container">
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
                                <i class="uil uil-eye-slash toggleeeep"></i>
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
                                <i class="uil uil-eye-slash toggleee"></i>
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
                                <i class="uil uil-eye-slash toggleeee"></i>
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

        <script>
            const pswrd_1 = document.querySelector("#pswrd_1");
            const pswrd_2 = document.querySelector("#pswrd_2");
            const errorText = document.querySelector(".text-danger");
            const showBtn = document.querySelector(".show");
            const btn = document.querySelector("button");
            btn.onclick = function(){
           if(pswrd_1.value != pswrd_2.value){
             errorText.style.display = "block";
             errorText.classList.remove("matched");
             errorText.textContent = "Error! Confirm Password Not Match";
             return false;
           }else{
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
                    toggleee.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    toggleee.classList.replace("uil-eye", "uil-eye-slash");
                    inputtt.type = "password";
                }
            });
            toggleeee.addEventListener("click", () => {
                if (inputttt.type === "password") {
                    inputttt.type = "text";
                    toggleeee.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    toggleeee.classList.replace("uil-eye", "uil-eye-slash");
                    inputttt.type = "password";
                }
            });
            toggleeeep.addEventListener("click", () => {
                if (inputtttp.type === "password") {
                    inputtttp.type = "text";
                    toggleeeep.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    toggleeeep.classList.replace("uil-eye", "uil-eye-slash");
                    inputtttp.type = "password";
                }
            });

        </script>
    </body>
</html>
