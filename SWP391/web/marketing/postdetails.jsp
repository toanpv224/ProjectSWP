<%@page import="dal.CategoryDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!Doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Marketing - Post Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/style.css">
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <script src="../ckeditor/ckeditor.js"></script>
        <link rel="stylesheet" href="../css/stylepostdetailmarketing.css"/>
    </head>
    <body>
        <div class="d-flex wrapper-login">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <section class="container-lg my-5 container">
                    <h1>Post Details</h1>
                    <form class="" id="frm" action="postdetails" method="post">
                        <input type="hidden" value="update" name="action"/>
                        <input type="text" name="postId" value="${requestScope.post.post_id}" hidden/>
                    <div class="p-3 bg-white rounded shadow-sm row">
                        <div class="left col-md-6">
                            <div class="post-thumbnail" onmousemove="thumbnailHover();" onmouseout="thumbnailOut();">
                                <img src="../${requestScope.post.thumbnail}" class="img-thumbnail" aria-label="thumbnail"/>
                                <div>
                                    <div class="thumbnail-action">
                                        <span data-bs-toggle="modal" data-bs-target="#view-thumbnail">

                                            <i class="fa-solid fa-eye"></i>
                                        </span>
                                        <span id="upload-thumbnail">

                                            <i class="fa-solid fa-upload"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="right col-md-6">
                            <div class="action flex">
                                <div class="flex-1"></div>

                                <button type="button" class="edit-btn btn btn-secondary btn-sm " onclick="editPostInformation();" aria-label="edit-post" id="edit-post">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                    Edit
                                </button>
                                <button type="button" class="edit-btn btn btn-secondary btn-sm " aria-label="cancel-edit" style="display: none;">
                                    <i class="fa-solid fa-x"></i>Cancel
                                </button>
                                <button type="button" class="edit-btn btn btn-secondary btn-sm " aria-label="save-post" style="display: none;">
                                    <i class="fa-solid fa-pen-to-square"></i>
                                    Save
                                </button>
<!--                                <button type="button" class="view-btn btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#viewPost" id="view-post">
                                    <i class="fa-solid fa-eye"></i>View
                                </button>-->

                            </div>
                            <div class="flex">                             
                                <div class="flex input-fields post-title">
                                    <label for="post-title">Title</label>
                                    <input id="post-title" name="title" value="${requestScope.post.title}" disabled="true" aria-label="changable-fields"/>
                                </div>
                            </div>
                            <div class="flex">
                                <div class="input-fields post-cate">                                    
                                    <label for="post-category">Category</label>
                                    <select disabled="true" class="post-category" aria-label="changable-fields" onchange="changeCategory();" id="post-cate-select" name="categoryId">
                                        <c:forEach items="${requestScope.postCategoryList}" var="i">
                                            <option value="${i.category_id}" ${requestScope.post.postSubCategory.category.category_id eq i.category_id?"selected":""}>${i.category_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="input-fields post-sub-cate">                                    
                                    <label for="post-sub-cate">Sub Category</label>
                                    <select disabled="true" class="post-sub-cate" aria-label="changable-fields" id="post-sub-cate-select" name="subCategoryId">
                                        <c:forEach items="${requestScope.postSubcategoryList}" var="i">
                                            <option aria-label="new-post-sub-cate" value="${i.id}" ${requestScope.post.postSubCategory.id eq i.id?"selected":""}>${i.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="flex">
                                <div class="flex input-fields post-public-date">                                    
                                    <label for="post-publication-date">Publication date</label>
                                    <input id="post-publication-date" name="publicDate" value="<fmt:formatDate value="${requestScope.post.publication_date}" type="both" dateStyle="short" timeStyle="short"/>" readonly/>
                                </div>
                            </div>
                            <div class="flex">                                
                                <div class="flex input-fields post-update-date">                                    
                                    <label for="post-updated-date">Updated date</label>
                                    <input type="text" id="post-updated-date" name="updatedDate" value="<fmt:formatDate value="${requestScope.post.updated_date}" type="both" dateStyle="short" timeStyle="short"/>" readonly/>
                                </div>
                            </div>
                            <div class="flex">
                                <div class="flex input-fields post-subtitle">                                    
                                    <label for="post-subtitle">Subtitle</label>
                                    <input id="post-subtitle" name="subtitle" value="${requestScope.post.sub_title}" disabled="true" aria-label="changable-fields"/>
                                </div>
                            </div>
                            <div class="flex">
                                <div class="flex" style="margin-left: 10px;
                                     margin-top: 10px;">                                    
                                    <div class="form-check form-switch">
                                        <label class="form-check-label" for="product-feature">Show</label>
                                        <input class="form-check-input" type="checkbox" role="switch" id="product-feature" ${requestScope.post.featured ? "checked":""} disabled="true" aria-label="changable-fields" name="featured"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <h3 id="post-content">Post content</h3>
                            <div class="details">
                                <textarea id="post-detail" name="postContent">
                                    ${requestScope.post.post_details}
                                </textarea>
                            </div>
                        </div>
                    </div>
                </form>

                <!-- Modal -->
                <div class="modal fade" id="viewPost" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-scrollable modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">View</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <h1 id="preview-title">${requestScope.post.title}</h1>
                                <div class="flex flex-column post-view-brief-infor">
                                    <div class="post-view-category">
                                        <div>

                                            <span>Category: </span><span id="preview-cate" value-holder="${requestScope.post.postSubCategory.category.category_id}">${requestScope.post.postSubCategory.category.category_name}</span>
                                        </div>
                                        <div>

                                            <span>Sub Category </span><span id="preview-sub-cate" value-holder="${requestScope.post.postSubCategory.id}">${requestScope.post.postSubCategory.name}</span>
                                        </div>
                                    </div>
                                    <div class="flex">

                                        <div class="post-view-date">

                                            <span>Published date </span><span>${requestScope.post.publication_date}</span>
                                        </div>
                                        <div class="post-view-date">

                                            <span>Updated date </span><span><fmt:formatDate value="${requestScope.post.updated_date}" type="both"/></span>
                                        </div>
                                    </div>
                                    <div class="flex">
                                        <div class="post-view-subtitle">
                                            <h2 id="preview-subtitle">${requestScope.post.sub_title}</h2>
                                        </div>
                                    </div>
                                    <div id="preview-featured" value-holder="${requestScope.post.featured}"></div>
                                </div>
                                <div id="preview-post-detail">
                                    ${requestScope.post.post_details}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="view-thumbnail" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-scrollable modal-lg">
                        <div class="modal-content">
                            <div>
                                <img src="../${requestScope.post.thumbnail}" class="img-thumbnail" id="thumbnail-view"/>
                            </div>
                        </div>
                    </div>
                </div>

                <div data-bs-toggle="modal" data-bs-target="#preview-thumbnail" id="abc">
                </div>
                <!-- Modal -->
                <div class="modal fade" id="preview-thumbnail" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Do you want to use this picture as new thumbnail</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <img src="" id="preview-thumbnail-image"/>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="close">Close</button>
                                <button type="button" class="btn btn-primary" aria-label="save">Save changes</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="change-thumbnail-container">
                    <form id="change-thumbnail-frm" enctype="multipart/form-data">
                        <input type="file" hidden onchange="updateThumbnail(event);" name="thumbnail"/>
                        <input type="text" name="postId" value="${requestScope.post.post_id}" hidden/>
                        <input type="text" name="action" value="changeThumbnail" hidden/>
                    </form>
                </div>
            </section>
            <div class="input-fields post-sub-cate">                                    
                <select id="original-subcate" hidden>
                    <c:forEach items="${requestScope.postSubcategoryList}" var="i">
                        <option aria-label="new-post-sub-cate" value="${i.id}" ${requestScope.post.postSubCategory.id eq i.id?"selected":""}>${i.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="../js/marketingpost.js"></script>
    <script>
        <c:if test="${requestScope.view != null}">
                            $(document).ready(function () {
                                document.querySelector('#view-post').click();
                            });
        </c:if>
        <c:if test="${requestScope.edit != null}">
                            $(document).ready(function () {
                                document.querySelector('#edit-post').click();
                            });
        </c:if>
    </script>
</html>