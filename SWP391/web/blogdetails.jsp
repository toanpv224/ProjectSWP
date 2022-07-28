<%@page import="dal.AccountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="post" value="${requestScope.post}"></c:set>
<c:set var="author" value="${requestScope.author}"></c:set>
    <!Doctype html>
    <html lang="en">
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Blog Details</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
            <link rel="stylesheet" href="css/style.css">
            <!-- ===== Boxicons CSS ===== -->
            <link href="https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css" rel="stylesheet">
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
                        <div class="py-3 px-5 bg-white rounded shadow-sm mb-3">
                            <h3>${post.title}</h3>
                        <div class="border-bottom border-top text-muted px-3 py-2 d-flex justify-content-between opacity-75">
                            <fmt:formatDate value="${post.publication_date}" pattern="HH:mm dd/MM/yyyy"/>
                            <div><span>${author.full_name}</span></div>
                        </div>
                        <div class="border-start border-dark mt-3 ps-3">
                            ${post.sub_title}
                        </div>
                        <div class="mt-3">
                            ${post.post_details}
                        </div>
                    </div>
<!--                    <div class="py-3 px-5 bg-white rounded shadow-sm mb-3">
                        <h3>Comment</h3>
                        <div class="rounded border p-2">
                            <div class="" id="comment-container">

                            </div>
                            <c:if test="${sessionScope.account == null}">
                                <button class="btn btn-light w-100">Login</button>
                            </c:if>
                            <c:if test="${sessionScope.account != null}">
                                <div class="d-flex">
                                    <img src="
                                         ${sessionScope.account.getImage_url() eq null ? "https://media.istockphoto.com/vectors/user-avatar-profile-icon-black-vector-illustration-vector-id1209654046?k=20&m=1209654046&s=612x612&w=0&h=Atw7VdjWG8KgyST8AXXJdmBkzn0lvgqyWod9vTb2XoE=":sessionScope.account.getImage_url()}
                                         " class="img-thumbnail rounded me-1" style="height: 50px; width: 50px;">
                                    <div class="form-floating" style="flex-grow: 1">
                                        <input type="text" name="textMessage" class="form-control" id="floatingInput" placeholder="Enter your Comment" onkeydown="commentEventListener(event)">
                                        <label for="floatingInput">Send Comment</label>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>-->
                </div>
                <div class="col-3">
                    <c:import url="blogsider.jsp"></c:import>
                    </div>
                </div>
            </div>
        <c:import url="footer.jsp"></c:import>
        </body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
        <script>//
//            function commentEventListener(event) {
//                if (event.key === 'Enter') {
//                    sendMessage();
//                }
//            }
//
//            var commentArea = document.getElementById('comment-container');
//            var textMessage = document.querySelector('input[name="textMessage"]');
//
//            var host = window.location.host;
//            var websocket = new WebSocket("ws:" + host + "/swp/chatRoomServer/" + ${post.post_id});
//            websocket.onopen = function (message) {
//                processOpen(message);
//            };  
//            websocket.onmessage = function (message) {
//                processMessage(message);
//            };
//            websocket.onclose = function (message) {
//                processClose(message);
//            };
//            websocket.onerror = function (message) {
//                processError(message);
//            };
//
//
//            function processOpen(message) {
//websocket.send(${sessionScope.account.user_id});
//            }
//            function processMessage(message) {
//                console.log(message.data)
//                var message_split = message.data.split("|");
//                var image = message_split[0];
//                var name = message_split[1];
//                var mess = message_split[2];
//                if(image === 'null') image = 'https://media.istockphoto.com/vectors/user-avatar-profile-icon-black-vector-illustration-vector-id1209654046?k=20&m=1209654046&s=612x612&w=0&h=Atw7VdjWG8KgyST8AXXJdmBkzn0lvgqyWod9vTb2XoE='
//                commentArea.innerHTML+=
//                    '<div class="d-flex mb-3 ms-2">'
//                        +'<img src="'+image+'" class="img-thumbnail rounded me-1" style="height: 50px; width: 50px;">'
//                        +'<div class="border rounded px-3 p-1" style="flex-grow: 1">'
//                            +'<div class="text-muted" style="font-size: .8rem;">'+name+'</div>'
//                            +'<div>'+mess+'</div>'
//                        +'</div>'
//                    +'</div>'
//                ;
//            }
//            function processClose(message) {
//                console.log(message.data);   
//            }
//            function processError(message) {
//
//            }
//            function sendMessage() {
//                if (typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN) {
//                    websocket.send(${sessionScope.account.user_id} + '|' + textMessage.value);
//                    textMessage.value = "";
//                }
//            }
//    </script>
</html>