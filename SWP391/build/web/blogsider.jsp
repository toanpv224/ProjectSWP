<%@page import="dal.PostDAO"%>
<%@page import="dal.ProductDAO"%>
<%@page import="dal.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    CategoryDAO category_dao = new CategoryDAO();
    PostDAO post_dao = new PostDAO();
%>
<div id="sider" class="scrollbox" style="position: sticky; top: 100px; bottom: 0; max-height: calc(100vh - 100px);">
    <div class="scrollbox-content rounded">
        <form method="post" action="blogslist" id="form-blog-sider" class="bg-white p-3 rounded shadow-sm mb-3">
            <div class="search-container mb-3">
                <input id="productSeachKey" class="form-control" type="text" placeholder="Search Post" aria-label="default input example" name="key" id="productSeachKey" value="${requestScope.key}">
            </div>
            <div class="categories-list-container">
                <div class="text-center mb-2">
                    <h5 class="mb-0 font-weight-bold">Categories</h5>
                </div>
                <div class="form-check sider-form-check">
                    <div class="category-panel"> 
                        <p class="category-container"> 
                            <a href="blogslist" class="category-title">All</a>
                        </p>
                    </div>
                    <c:set var="checked" value="${requestScope.checked}" />

                    <c:forEach var="category" items="<%= category_dao.getPostCategory()%>">
                        <div class="category-panel">
                            <p class="category-container"> 
                                <a href="blogslist?categoryId=${category.category_id}" class="category-title">${category.category_name}</a>
                                <a class="btn collapse-btn" data-bs-toggle="collapse" href="#multiCollapse${category.category_id}" role="button" aria-expanded="false" aria-controls="multiCollapse${category.category_id}" onclick="changeState(this);" <c:if test="${category.category_id == requestScope.categoryIdParent.category_id}">id="category-active"</c:if>>+</a>
                                </p>
                                <div class="row">
                                    <div class="col">
                                        <div class="collapse multi-collapse" id="multiCollapse${category.category_id}">
                                        <ul class="sub-category-list">
                                            <c:forEach items="${category.subcategories}" var="k">
                                                <li class="sub-category-item" <c:if test="${k.id == requestScope.subCategoryId}">id="sub-category-active"</c:if>>
                                                    <a href="blogslist?subCategoryId=${k.id}">${k.name}</a>

                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="hidden-input">
                <input type="hidden" value="1" name="page" id="page"/>
                <input type="hidden" value="${requestScope.orderOption}" name="orderOption" id="order-option"/>
                <c:if test="${requestScope.categoryId != null}"><input type="hidden" value="${requestScope.categoryId}" name="categoryId"></c:if>
                <c:if test="${requestScope.subCategoryId != null}"><input type="hidden" value="${requestScope.subCategoryId}" name="subCategoryId"></c:if>
                </div>
            </form>
            <div class="bg-white p-2 rounded shadow-sm mb-3">
                <div class="p-2 text-center">
                    <h5 class="mb-0 font-weight-bold">Latest Posts</h5>
                </div>
                <div class="flex flex-column">
                <c:forEach var="post" items="<%= post_dao.getPosts(1, 1, 6)%>">
                    <div class="latest-posts">
                        <div class="mb-3 post d-flex">
                            <div class="card-body" style="width: 60%">
                                <a href="post?id=${post.post_id}" title="${post.title}">
                                    <span class="card-title mb-3 line-limit-2" style="font-size: .8rem">${post.title}</span>
                                </a>
                            </div>
                            <div style="width: 40%">
                                <a class="post-thumbnail" href="post?id=${post.post_id}">
                                    <img src="${post.thumbnail}" class="card-img-top rounded" alt="...">
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="bg-white p-3 rounded shadow-sm static-contact-container col-12 d-flex justify-content-center flex-column mb-3" style="text-align: center;">
            <span class="bg-white rounded-pill py-1 px-2"><span style="font-weight: bold;">Contact:</span> +0123456789</span>
            <div>
                <!--<span>Contact link:</span>-->
                <a href="#"><i class="fab fa-facebook"></i></a>
                <a href="#"><i class="fab fa-instagram"></i></a>
                <a href="#"><i class="fab fa-twitter"></i></a>
            </div>
        </div>
    </div>
</div>
