<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="home">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="productslist">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="blogslist">Blogs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="feedback">Feedbacks</a>
                </li>
            </ul>
            <ul class="navbar-nav mb-2 mb-lg-0">
                <c:if test="${sessionScope.account == null}">
                    <li class="nav-item dropdown me-2">
                        <a class="nav-link" href="#divOne" class="first-button">Login</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.account != null }">

                    <li class="nav-item dropdown me-2">
                        <div class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="/swp/${sessionScope.account.getImage_url()}" class="img-thumbnail" style="width: 40px;height: 40px; border-radius: 50%; margin: -0.5rem 0">
                            My Account
                        </div>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="#divThree" onclick="Cancel_onclick(${sessionScope.account.getActive()}, '${sessionScope.account.getFull_name()}',${sessionScope.account.isGender()}, '${sessionScope.account.getPhone()}', '${sessionScope.account.getCity()}', '${sessionScope.account.getCountry()}', '${sessionScope.account.getAddress()}');">Profile</a></li>
                            <li><a class="dropdown-item" href="myorders">My Orders</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#divTwo">Change password</a></li>
                            <li><a class="dropdown-item" href="logout">Log out</a></li>
                        </ul>
                    </li>
                </c:if>

                <li class="nav-item align-items-center">
                    <a href="showcart" class="btn btn-outline-primary position-relative"><i class="fa-solid fa-cart-shopping me-1"></i>My Cart</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<c:import url="login.jsp"></c:import>
<c:import url="changepassword.jsp"></c:import>
<c:import url="userfrofile.jsp"></c:import>
