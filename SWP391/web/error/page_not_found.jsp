<%-- 
    Document   : page not found
    Created on : Jul 5, 2022, 12:04:06 AM
    Author     : Duy Phuong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page not Found</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="../css/style.css">
        <style>
            #notfound {
                position: relative;
                height: 100vh;
            }
            .absolute-center {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
        </style>
    </head>
    <body>
        <div id="notfound">
            <div class="absolute-center">
                <div class="p-3 bg-white rounded shadow-sm" style="width: 1300px;">
                    <div class="text-center"><h1 style="font-size: 10rem;" >404</h1></div>
                    <div class="text-center"><h3>Page not found</h3></div>
                    <div class="d-flex justify-content-center mt-3">
                        <button onclick="history.back()" class="btn btn-light me-3">Go Back</button>
                        <a href="/swp/home" class="btn btn-primary">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
