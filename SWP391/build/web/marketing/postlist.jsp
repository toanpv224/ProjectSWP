<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post_List</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!--CSS-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/style.css"/>

        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"/>
    </head>
    <body>

        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>

                <div class="container-lg">
                    <!--content--> 
                    <div class="p-3 rounded bg-white my-5">
                        <form id="post_list" action="searchpostlist" method="get">               
                            <!-- header  -->
                            <div data-v-b2f6143e="" data-v-75520c46="" id="product-filter-card" class="product-filter-card">

                                <!--                           <div class="search-container">
                                                                  <div class="input-group">
                                                                      <input value="" id="search" name="search" type="text" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" />
                                                                      <button type="button" onclick="SubmitForm(1)" class="btn btn-outline-primary">search</button>
                                                                  </div>      
                                                           </div>-->
                            <c:forEach items="${requestScope.categories}" var="category">
                                <!--get data-->
                                <c:forEach items="${category.subcategories}" var="c"> 
                                    <input hidden="" cat="${category.category_id}"  subcat="${c.id}" value="${c.name}" id="cid" name="cid"/>
                                </c:forEach>
                            </c:forEach>

                            <div class="filter-container">
                                <div class="text-center">

                                    <h3>Filter</h3>
                                </div>
                                <div class="search_list row">
                                    <div class="col-1 search-item">
                                      
                                    </div>
                                    
                                    <div class="col-3 search-item">
                                        <label for="category">Category:</label>
                                        <select class="form-select form-select-sm" name="category" id="category" aria-label=".form-select-sm example" onchange="cat(1)">
                                            <option value="0" selected>All</option>  <!--default-->
                                            <c:forEach items="${requestScope.categories}" var="category">
                                                <option value="${category.category_id}" >${category.category_name}</option>
                                                <!--get data-->
                                            </c:forEach>
                                        </select>
                                    </div>
                                      <div class="col-3 search-item">
                                            <label for="category">Sub Category:</label>
                                            <select class="form-select form-select-sm" name="sub_category" id="sub_category" aria-label=".form-select-sm example" onchange="SubmitForm(1)">
                                                <option value="0" >All</option>
                                            </select>
                                        </div>
                                    <div class="col-3 search-item">
                                        <label for="author">Author</label>
                                        <select class="form-select form-select-sm" name="author" id="author" aria-label=".form-select-sm example" onchange="SubmitForm(1)">
                                            <option value="0" >All</option>
                                            <c:forEach items="${requestScope.authors}" var="author">
                                                <option value="${author.user_id}">${author.username}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    
                                </div>
                            
                                                         
                                                                            
                                     
                                    <!-- NavBar  -->
                                    <input hidden value="-1" name="feature" id="feature_input">

                                    <div class="product-list-main" >
                                        <div class="py-2 px-3 position-sticky top-0 bg-light my-3 rounded" style="top: 56px; z-index: 1000;">
                                            <div class="d-flex justify-content-between">
                                                <div id="list_category" class="d-flex">
                                                    <label class="me-3 cursor-pointer">
                                                        <input type="radio" name="poststatus-radio" value="-1" onclick="CheckFeature(this)" checked hidden>
                                                        <div>All of post</div>
                                                    </label>
                                                    <label class="me-3 cursor-pointer">
                                                        <input type="radio" name="poststatus-radio" value="1" onclick="CheckFeature(this)" hidden>
                                                        <div>Activating</div>
                                                    </label>
                                                    <label class="me-3 cursor-pointer">
                                                        <input type="radio" name="poststatus-radio" value="0" onclick="CheckFeature(this)" hidden>
                                                        <div>Hidden</div>
                                                    </label>
                                                </div>
                                                <!--search nav-->
                                                <div class="search-nav">
                                                    <div class="input-group rounded">
                                                        <input type="search" id="search" name="search" oninput="SubmitForm(1)"
                                                               class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" />
                                                        <span class="input-group-text border-0" id="search-addon">
                                                            <i class="fas fa-search"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                                <!-------------------->
                                                <div class="add-post">
                                                    <button type="button"  onclick="window.location.href = 'addpost'" class="btn btn-success">Add New Post  +</button>
                                                </div>
                                            </div>
                                        </div>
                                        <!--Content-->
<!--                                        <div id="content">-->
                                            <table  id="content" class="table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>
                                                             <i value="1" class="sort-item fa-solid fa-arrow-down-a-z sort-active" onclick="Checksort(this)"></i>   
                                                            ID
                                                        </th>
                                                        <th> <i value="2" class="sort-item fa-solid fa-arrow-down-a-z " onclick="Checksort(this)"></i>
                                                            Title
                                                        </th>
                                                        <th> <i value="3" class="sort-item fa-solid fa-arrow-down-a-z " onclick="Checksort(this)"></i>
                                                            Author
                                                        </th>
                                                        <th> <i value="4" class="sort-item fa-solid fa-arrow-down-a-z " onclick="Checksort(this)"></i>
                                                            Status
                                                        </th>
                                                        <input hidden id="sort_input" name="sort" value="1">
                                                        <th></th>
                                                    </tr>
                                                </thead>
                                                <tbody id="listpost ">
                                               
                                                    <c:forEach items="${requestScope.posts}" var="p">
                                                        <tr>
                                                            <td>${p.post_id}</td>
                                                            <td class="w-50">
                                                                <div class="row">
                                                                    <div class="col-3 image-item">
                                                                        <a href="postdetails?id=${p.post_id}">

                                                                            <img src="../${p.thumbnail}" />
                                                                        </a>
                                                                    </div>
                                                                    <div class="col-9 title">
                                                                        <p class="title-detail">
                                                                            ${p.title}
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td>${p.author}</td>
                                                            <td>
                                                                <select class="form-select form-select-sm" id="feature_item" style=" width: 65%; height: 40px;font-size: 13px;" onchange="ChangeFeature(${p.post_id}, $(this).children('option:selected').val())">
                                                                    <option value="1" style="font-size: 13px;" <c:if test="${p.featured}">selected</c:if>>Show</option>
                                                                    <option value="0" style="font-size: 13px;" <c:if test="${!p.featured}">selected</c:if>>Hide</option>
                                                                    </select>
                                                                </td>
                                                                <td class="col-2 action-container">
                                                                    <button type="button" class="edit-btn btn btn-secondary btn-sm ">
                                                                        <i class="fa-solid fa-pen-to-square"></i>
                                                                        <a href="postdetails?id=${p.post_id}&action=edit">Edit</a>
                                                                </button>
                                                                <button type="button" class=" view-btn btn btn-primary btn-sm ">
                                                                    <i class="fa-solid fa-eye"></i><a href="postdetails?id=${p.post_id}&action=view">View</a></button>
                                                            </td>
                                                        </tr>   
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <footer id="footer">
                                                <div class="pagination">
                                                    <div class="content-paging content-paging-footer" name="page">
                                                        <div class="title-paging"> <span>Page ${requestScope.curpage} of ${requestScope.maxpage}<span></div>
                                                                    <nav class="" aria-label="...">
                                                                        <ul class="pagination">
                                                                            <c:if test="${requestScope.curpage!=1}">
                                                                                <li class="page-item" >
                                                                                    <a class="page-link" value="${requestScope.curpage-1}" onclick="Paging(${requestScope.curpage-1})" >Previous</a>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:if test="${requestScope.curpage==1}">
                                                                                <li class="page-item disabled">
                                                                                    <a class="page-link" value="${requestScope.curpage}" >Previous</a>
                                                                                </li>
                                                                            </c:if>

                                                                            <select class="select-paginate" id="paging" onchange="SubmitForm($('#paging').children('option:selected').val())">
                                                                                <c:forEach begin="1" end="${requestScope.maxpage}" var="i">
                                                                                    <option value="${i}"
                                                                                            <c:if test="${requestScope.curpage==i}">selected</c:if>
                                                                                            >${i}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                            <c:if test="${requestScope.curpage!=requestScope.maxpage}">
                                                                                <li class="page-item">
                                                                                    <a class="page-link" onclick="Paging(${requestScope.curpage+1})" value="${requestScope.curpage+1}" >Next</a>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:if test="${requestScope.curpage==requestScope.maxpage}">
                                                                                <li class="page-item disabled">
                                                                                    <a class="page-link" value="${requestScope.curpage}" >Next</a>
                                                                                </li>
                                                                            </c:if>
                                                                        </ul>
                                                                    </nav>
                                                                    </div>
                                                                    </div>
                                                                    </footer>

                                                                    <!--</div>-->
                                                                    </div>

                                                                    </div>
                                                                    </div>
                                                                    </form>      
                                                                    </div>
                                                                    </div>
                                                                    </div>
                                                                    </div>
                                                                    <script src="../js/marketing/post_list.js"></script>
                                                                    <!--Ajax-->
                                                                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
                                                                    </body>
                                                                    </html>