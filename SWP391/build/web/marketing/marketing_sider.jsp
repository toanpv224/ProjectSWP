<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"crossorigin="anonymous"></script>
        <!-- ======= Icons used for dropdown (you can use your own) ======== -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <link rel="stylesheet" href="css/marketing_sider.css">
        <link rel="stylesheet" href="css/post_list.css">
        <style type="text/css"></style>
    </head>
    <body class="bg-light">
                    <aside class="row"> 
                        <!-- ============= COMPONENT ============== -->
                        <nav class="sidebar card py-2 mb-4">
                            <ul class="nav flex-column" id="nav_accordion">
                                <li class="nav-item " style="margin-left: 40px" >
                                    <a class="nav-link" href="#"> Dashboard</a>
                                </li>
                                <li class="nav-item has-submenu">
                                    <div class="nav-item_icon">
                                        <i class="fa-solid fa-cart-arrow-down"></i>
                                    </div>
                                    <div class="nav-item_content">
                                        <a class="nav-link nav-item_content__title" href="#"> Product Manager <i class="bi small bi-caret-down-fill"></i> </a>
                                        <ul class="submenu collapse">
                                            <li><a class="nav-link" href="#">All </a></li>
                                            <li><a class="nav-link" href="#">Add product </a></li>
                                            <li><a class="nav-link" href="#">product Hided </a> </li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="nav-item has-submenu">
                                    <div class="nav-item_icon">
                                        <i class="fa-solid fa-blog"></i>
                                    </div>
                                    <div class="nav-item_content">
                                        <a class="nav-link nav-item_content__title" href="#"> Post Manager <i class="bi small bi-caret-down-fill"></i> </a>
                                        <ul class="submenu collapse">
                                            <li><a class="nav-link" href="#">All </a></li>
                                            <li><a class="nav-link" href="#">Add Post </a></li>
                                            <li><a class="nav-link" href="#">Post Hided </a> </li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="nav-item has-submenu">
                                    <div class="nav-item_icon">
                                    </div>
                                    <div class="nav-item_content" style="margin-left: 14px">
                                        <a class="nav-link nav-item_content__title" href="#"> Slider Manager <i class="bi small bi-caret-down-fill"></i> </a>
                                        <ul class="submenu collapse">
                                            <li><a class="nav-link" href="#">All </a></li>
                                            <li><a class="nav-link" href="#">Add Slider </a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="nav-item has-submenu">
                                    <div class="nav-item_icon" style="margin-left: 40px">
                                        <!-- <i class="fa-solid fa-cart-arrow-down"></i> -->
                                    </div>
                                    <div class="nav-item_content">
                                        <a class="nav-link nav-item_content__title" href="#"> Customer Manager <i class="bi small bi-caret-down-fill"></i> </a>
                                        <ul class="submenu collapse">
                                            <li><a class="nav-link" href="#">All </a></li>
                                    </div>
                                </li>
                                <li class="nav-item has-submenu">
                                    <div class="nav-item_icon">

                                        <i class="fa-solid fa-message"></i>
                                    </div>
                                    <div class="nav-item_content">
                                        <a class="nav-link nav-item_content__title" href="#"> Feedbacks Manager <i class="bi small bi-caret-down-fill"></i> </a>
                                        <ul class="submenu collapse">
                                            <li><a class="nav-link" href="#">All </a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="nav-item" >
                                    <a class="nav-link" href="#" style="margin-left: 40px;"> Other link </a>
                                </li>
                            </ul>
                        </nav>
                        <!-- ============= COMPONENT END// ============== -->	
                    </aside>



        <script src="js/marketing_sider.js"></script>
    </body>
</html>
