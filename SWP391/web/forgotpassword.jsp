<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Fogot Password</title>
        <link rel="stylesheet" href="css/forgotpassword.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    </head>
    <body>
        <div class="wrapper-login">
            <header>Fogot Password</header>
            <p>Enter your email address</p>

            <form action="forgot" method="POST">
                <div class="field email">
                    <div class="input-area">
                        <p class="text-danger">${requestScope.mess}</p>
                        <div class="input-box">
                            <input name="mail" type="text" spellcheck="false" required />
                            <label for="">Email</label>
                            <i class="error error-icon fas fa-exclamation-circle"></i>
                        </div>
                    </div>
                </div>
                <input type="submit" value="Submit">
            </form>
        </div>
    </body>
</html>