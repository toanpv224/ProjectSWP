<%@page import="java.util.List"%>
<%@page import="model.Slider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Online shopping system</title>

        <!--bootstrap-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/stylehomepage.css">
        <!-- ===== Boxicons CSS ===== -->
        <link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css" rel="stylesheet"/>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        <style>
            .star-widget{
                display: inline-block;
            }
            .star-widget input{
                display: none;
            }
            .star-widget label{
                font-size: 40px;
                color: #ccc;
                padding: 10px;
                float: right;
                transition: all 0.2s ease;
            }
            input:not(:checked) ~ label:hover,
            input:not(:checked) ~ label:hover ~ label{
                color: #fd4;
            }
            input:checked ~ label{
                color: #fd4;
            }
            input#rate-5:checked ~ label{
                color: #fe7;
            }
            .form-label{
                display: block;
            }
        </style>
    </head>

    <body>
        <div class="header">
            <c:import url="navbar.jsp"></c:import>
            </div>
            <div class="container-lg">
                <div id="carouselExampleCaptions" class="carousel slide mb-3" data-bs-ride="false">

                </div>
                <div class="row mb-3">
                    <div class="col-3">
                        <div class="sider">
                        <c:import url="sider.jsp"></c:import>
                        </div>
                    </div>
                    <div class="col-9" style="background-color: var(--background-primary-color);border-radius: var(--bs-border-radius);">
                        <div class="content">

                        <c:if test="${sessionScope.choice}">
                            <form action="feedback?choice=1" method="POST" enctype="multipart/form-data">
                                <c:if test="${sessionScope.account == null }">
                                    <input type="hidden" name="userId" value="1">
                                    <input type="hidden" name="productId" value="1">
                                </c:if>
                                <c:if test="${sessionScope.account != null }">
                                    <input type="hidden" name="userId" value="${sessionScope.account.getUser_id()}">
                                    <input type="hidden" name="productId" value="${requestScope.productID}">
                                </c:if>

                            </c:if>
                            <c:if test="${!sessionScope.choice}">
                                <form action="feedback?choice=0" method="POST" enctype="multipart/form-data">
                                    <c:if test="${sessionScope.account == null }">
                                        <input type="hidden" name="userId" value="1">
                                        <input type="hidden" name="productId" value="1">
                                    </c:if>
                                    <c:if test="${sessionScope.account != null }">
                                        <input type="hidden" name="userId" value="${sessionScope.account.getUser_id()}">
                                        <input type="hidden" name="productId" value="${requestScope.productID}">
                                    </c:if>
                                </c:if>

                                <br>
                                <div class="mb-3">
                                    <label for="exampleFormControlInput1" class="form-label">Full name</label>
                                    <c:if test="${sessionScope.account == null }">
                                        <input type="text" name="name" class="form-control" id="exampleFormControlInput1">
                                    </c:if>
                                    <c:if test="${sessionScope.account != null }">
                                        <input class="form-control" name="name" type="text" value="${sessionScope.account.getFull_name()}" disabled readonly>
                                    </c:if>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label for="inputPassword4" class="form-label">Phone</label>
                                        <c:if test="${sessionScope.account == null }">
                                            <input type="text" name="phone"  placeholder="+84" class="form-control" id="inputPassword4" />
                                        </c:if>
                                        <c:if test="${sessionScope.account != null }">
                                            <input class="form-control" name="phone" type="text" value="${sessionScope.account.getPhone()}" disabled readonly>
                                        </c:if>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="inputEmail4" class="form-label" style="margin-bottom: 16px;">Gender</label>
                                        <c:if test="${sessionScope.account == null }">
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="gender" id="inlineRadio1" value="1" >
                                                <label class="form-check-label" for="inlineRadio1">Male</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="gender" id="inlineRadio2" value="0" >
                                                <label class="form-check-label" for="inlineRadio2">Female</label>
                                            </div>
                                        </c:if>
                                        <c:if test="${sessionScope.account != null }">
                                            <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="gender" id="inlineRadio1" value="1" disabled readonly <c:if test="${sessionScope.account.isGender()}">checked="true"</c:if>>
                                                <label class="form-check-label" for="inlineRadio1">Male</label>
                                            </div>
                                            <div class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="gender" id="inlineRadio2" value="0" disabled readonly <c:if test="${!sessionScope.account.isGender()}">checked="true"</c:if>>
                                                <label class="form-check-label" for="inlineRadio2">Female</label>
                                            </div>
                                    </c:if>
                                </div>
                                </div><br>
                                <div class="mb-3">
                                    <label for="exampleFormControlInput1" class="form-label">Email address</label>
                                    <c:if test="${sessionScope.account == null }">
                                        <input type="email" name="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com">
                                    </c:if>
                                    <c:if test="${sessionScope.account != null }">
                                        <input class="form-control" name="email" type="text" value="${sessionScope.account.getEmail()}" disabled readonly>
                                    </c:if>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleFormControlInput1" class="form-label">Vote</label>
                                    <div class="star-widget">
                                        <input type="radio" name="rate" id="rate-1" value="5" >
                                        <label for="rate-1" class="fas fa-star"></label>
                                        <input type="radio" name="rate" id="rate-2" value="4">
                                        <label for="rate-2" class="fas fa-star"></label>
                                        <input type="radio" name="rate" id="rate-3" value="3">
                                        <label for="rate-3" class="fas fa-star"></label>
                                        <input type="radio" name="rate" id="rate-4" value="2">
                                        <label for="rate-4" class="fas fa-star"></label>
                                        <input type="radio" name="rate" id="rate-5" value="1" checked="">
                                        <label for="rate-5" class="fas fa-star"></label>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="formFileMultiple" class="form-label">Upload image</label>
                                    <input type="file" name="file" class="form-control" id="inputGroupFile01">
                                </div>
                                <div class="mb-3">
                                    <label for="exampleFormControlTextarea1" class="form-label">Feedback</label>
                                    <textarea class="form-control" name="feedback" id="exampleFormControlTextarea1" rows="4"></textarea>
                                </div>
                                <div class="d-grid gap-2 col-6 mx-auto">
                                    <button class="btn btn-primary" type="submit">Submit</button>
                                </div>
                                <br>
                            </form>
                    </div>  
                </div>
            </div>
        </div>
        <div class="footer">
            <c:import url="footer.jsp"></c:import>
        </div>
        <!--        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>-->
        <script src="js/carousel.js"></script>
        <script>
            const btn = document.querySelector("button");
            const post = document.querySelector(".post");
            const widget = document.querySelector(".star-widget");
            const editBtn = document.querySelector(".edit");
            btn.onclick = () => {
                widget.style.display = "none";
                post.style.display = "block";
                editBtn.onclick = () => {
                    widget.style.display = "block";
                    post.style.display = "none";
                };
                return false;
            };
        </script>
    </body>

</html>