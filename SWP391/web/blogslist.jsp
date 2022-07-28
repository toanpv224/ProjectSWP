<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!Doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Blogs List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/poststyle.css">
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <div class="header">
            <c:import url="navbar.jsp"></c:import>
            </div>
            <div class="container-lg">
                <div class="row">
                    <div class="col-9">
                        <div class="p-3 pb-5 mb-3 h-100 bg-white rounded shadow-sm position-relative">
                            <div class="p-2 bg-light rounded mb-3">
                                <select id="select-posts-order" name="orderby" class="form-select" aria-label="Default select example" style="width: fit-content;" onchange="changeOrderPost()">
                                    <option value="publication_date" ${requestScope.orderOption eq "publication_date" ? "selected" : ""}>Newest posts</option>
                                    <option value="updated_date" ${requestScope.orderOption eq "updated_date" ? "selected" : ""}>Most recently updated</option>
                                </select>
                            </div>

                            <div class="row">
                            <c:forEach var="post" items="${requestScope.postsList}">
                                <div class="col-6 mb-3">
                                    <div class="card post">
                                        <a class="post-thumbnail" href="post?id=${post.post_id}">
                                            <img src="${post.thumbnail}" class="card-img-top" alt="...">
                                        </a>
                                        <div class="card-body">
                                            <h6 class="card-title mb-3">${post.title}</h6>
                                            <!--<h6 class="card-subtitle text-muted" style="font-size: .85rem">${post.sub_title}</h6>-->
                                        </div>
                                        <div class="mx-2 mb-2 text-muted opacity-75 d-flex justify-content-end">
                                            <fmt:formatDate value="${post.publication_date}" pattern="HH:mm dd/MM/yyyy"/>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="position-absolute bottom-0 start-0 end-0">
                            <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item <c:if test="${requestScope.pageNumber <= 1}">disabled</c:if>">
                                        <a class="page-link<c:if test="${requestScope.pageNumber > 1}"> cursor-pointer" onclick="movePage(${requestScope.pageNumber-1})</c:if>">Previous</a>
                                    </li>
                                    <li class="page-item"><a class="page-link">${requestScope.pageNumber}</a></li>
                                    <li class="page-item <c:if test="${requestScope.pageNumber >= requestScope.numberPage}">disabled</c:if>">
                                        <a class="page-link<c:if test="${requestScope.pageNumber < requestScope.numberPage}"> cursor-pointer" onclick="movePage(${requestScope.pageNumber+1})</c:if>">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
                <div class="col-3">
                    <c:import url="blogsider.jsp"></c:import>
                    </div>
                </div>
            </div>
        <c:import url="footer.jsp"></c:import>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    
    <script src="js/blogslist.js"></script>
</html>