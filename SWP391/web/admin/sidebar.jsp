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
        <ul class="sidebar-menu">
            <li>
                <a href="/swp/admin">
                    <i class="fa fa-dashboard"></i>
                    <span>Dashboard</span>
                </a>
            </li>
            <li>
                <a href="/swp/admin/userslist">
                    <i class="fa-solid fa-address-book"></i>
                    <span>Users list</span>
                </a>
            </li>
            <li>
                <a href="/swp/admin/settingslist">
                    <i class="fa-solid fa-gear"></i>
                    <span>Settings list</span>
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
