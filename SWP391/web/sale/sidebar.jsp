<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="left-side">
    <section class="sidebar">
        <div class="user-panel flex">
            <div class="pull-left image">
                <img src="/swp/${sessionScope.account.image_url}" style="width: 45px; height: 45px;">
            </div>
            <div class="pull-left info">
                Hello, ${sessionScope.account.full_name}
            </div>
        </div>
<!--        <form class="sidebar-form">
            <div class="flex sidebar-form-container">
                <input type="text" placeholder="search"/>
                <button><i class="fas fa-search"></i></button>
                
            </div>
        </form>-->
        <ul class="sidebar-menu">
            <li>
                <a href="/swp/sale">
                    <i class="fa fa-dashboard"></i>
                    <span>Dashboard</span>
                </a>
            </li>
            <li>
                <a href="/swp/sale/orderslist">
                    <i class="fas fa-shopping-cart"></i>
                    <span>Orders List</span>
                </a>
            </li>
            <li>
                <a href="/swp/logout">
                    <i class="fas fa-sign-out"></i>
                    <span>Sign out</span>
                </a>
            </li>
        </ul>
    </section>
</aside>
