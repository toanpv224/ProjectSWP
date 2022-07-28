<%-- 
    Document   : uploadimage
    Created on : Jun 27, 2022, 10:07:46 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="upload" enctype="multipart/form-data" method="post">
            <input type="file" id="image" name="image">
            <button type="submit">submit</button>
        </form>
    </body>
</html>
