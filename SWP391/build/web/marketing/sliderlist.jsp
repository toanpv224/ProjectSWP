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
        <!--<link rel="stylesheet" href="../css/post_list.css"/>-->
        <link rel="stylesheet" href="../css/sliderlist.css"/>
          <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
        <div class="container-lg">
            <!--content--> 
            <div class="p-3 bg-white rounded my-5">
                <form id="post_list" action="searchpostlist" method="get">               
                           <div class="filter-container">
                       <input hidden value="-1" name="feature" id="feature_input">
                       <div data-v-75520c46="" class="product-list-main" >
                           <div data-v-dff31658="" data-v-75520c46="" class="shopee-fixed-top-card">
                               <div class="py-2 px-3 position-sticky top-0 bg-light my-3 rounded" style="top: 56px; z-index: 1000;">
                                               <div class="d-flex justify-content-between">
                                                   <div id="list_category" class="d-flex">
                                                       <label class="me-3 cursor-pointer">
                                                        <input type="radio" name="poststatus-radio" value="-1" onclick="CheckFeature(this)" checked hidden>
                                                        <div>All of Slider</div>
                                                    </label>
                                                       <label class="me-3 cursor-pointer">
                                                        <input type="radio" name="poststatus-radio" value="1" onclick="CheckFeature(this)" hidden>
                                                        <div>Showing</div>
                                                    </label>
                                                    <label class="me-3 cursor-pointer">
                                                        <input type="radio" name="poststatus-radio" value="0" onclick="CheckFeature(this)" hidden>
                                                        <div>Hidden</div>
                                                    </label>
                                                   </div>
                                                   <!--search box-->
                                                   <div class="search-nav">
                                                     <div class="input-group rounded">
                                                         <input type="search" class="form-control rounded search-nav-bar" oninput="SubmitForm(1)"
                                                                id="search" placeholder="Search" aria-label="Search" aria-describedby="search-addon" />
                                                         <select name="option_search" id="option_search" onchange="SubmitForm(1)">
                                                                    <option value="1">Title</option>
                                                                    <option value="2">Backlinks</option>
                                                                </select>
                                                   </div>
                                                   </div>
<!--                                                   <div class="add-post">
                                                       <button type="button"  onclick="window.location.href='addslider'" class="btn btn-success">Add New   +</button>
                                                   </div>-->
                                                   <div class="add-post">
                                                   </div>
                                               </div>
                               </div>
                               <div data-v-dff31658="" class="fixed-placeholder" style="display: none; height: 54px;">
                               </div>
                           </div>
                            <!--Content-->
                            <div data-v-75520c46="" class="row main-content " id="content">
                                 <!--header-->
                           <div class="row navbar-content">
                               <div class="col-2"style="margin-left: 34px" >ID</div>
                               <div class="col-4">Title</div>
                               <div class="col-2">Backlink</div>
                               <div class="col-2">Status</div>
                           </div>
                              <!--list-item-->
                            <div class="list-post_container ">
                                <!--a post-->
                                <ul class="list-post" id="listpost">
                                <c:forEach items="${requestScope.sliders}" var="s">
                                    <li>
                                <div class="row item-detail">
                                    <div class="col-1">
                                        ${s.id}
                                    </div>
                                    <div class="col-5 title-item">
                                        <div class="col-3 image-item">
                                            <img src="../${s.imagePath}" />
                                        </div>
                                        <div class="col-1">
                                            
                                        </div>
                                        <div class="col-7 title">
                                            <p class="title-detail">
                                               ${s.title}
                                            </p>
                                        </div>
                                    </div>
                                               <div class="col-2"><a href="${s.url}">${s.url}</a></div>
                                    <div class="col-2">
                                        <select id="feature_item" style=" width: 65%; height: 40px;font-size: 13px;" onchange="ChangeFeature(${s.id},$(this).children('option:selected').val())">
                                            <option value="1" style="font-size: 13px;"
                                                    <c:if test="${s.status==1}">selected</c:if>
                                                    >
                                                Show
                                            </option>
                                            <option value="0" style="font-size: 13px;" 
                                            <c:if test="${s.status==0}">selected</c:if>
                                                    >
                                                Hide
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-2 action-container">
                                      <a type="button" class=" view-btn btn btn-primary btn-sm " href="slider?id=${s.id}">
                                                                        <i class="fa-solid fa-eye"></i>View more</a>
<!--                                      <button type="button" class=" view-btn btn btn-primary btn-sm ">
                                          <i class="fa-solid fa-eye"></i>View</button>-->
                                    </div>
                                </div>
                                    </li>   
                            </c:forEach>
                                </ul>
                           </div>
                              <!--//footer-->
                              <footer>
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
                            
                       </div>
                      </div>
                         
            </div>
        </form>      
         </div>
       </div>
       </div>
    </body>
    <script src="../js/slider_list.js"> </script>
         <!--Ajax-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</html>