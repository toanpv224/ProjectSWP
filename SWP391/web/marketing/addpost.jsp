<%-- 
    Document   : ckeditor
    Created on : June 23, 2022, 7:55:53 PM
    Author     : tretr
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="utf-8">
        <title>Post_List</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <!--CSS-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/style.css"/>
        <link rel="stylesheet" href="../css/post_list.css"/>
        <!--CK finder-->
        <script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
        <script type="text/javascript" src="../ckfinder/ckfinder.js"></script>
        <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>
     
        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <div class="my-5 container-lg">
                    <!--content--> 
                    <div class="p-3 bg-white rounded">
                        <form id="post_list" action="addpost" method="post" enctype="multipart/form-data">               
                            <!-- header  -->
                            <div id="product-filter-card" class="product-filter-card">

                                <div class="container list-input">
                                    <div class="text-center"> <h1>Create new post</h1></div>

                                    <div class="list-input list-input__title col-12">
                                        <div class="col-2 ceate-title">
                                            <p>Title</p>
                                        </div>

                                        <div class="col-1 ceate-title">

                                        </div>
                                        <div class="col-9 ceate-title">
                                            <input id="title-add" name="title" oninput="checktitle()" required type="text" />
                                            <span id="title-err" class="err">Title is not empty</span>
                                        </div>
                                    </div>
                                    <div class="list-input list-input__title col-12">
                                        <div class="col-2 ceate-title">
                                            <p>Author</p>
                                        </div>

                                        <div class="col-1 ceate-title">

                                        </div>
                                        <div class="col-9 ceate-title">
                                            <input  readonly name="autitle-addthor" type="text" value="${sessionScope.account.username}" />
                                        
                                    </div>
                                </div>
                                    <div class="category-container">
                                        <div class="list-input list-input__title col-1 category-content" >
                                            <div class="col-2 ceate-title">
                                                <p>Category</p>
                                            </div>
                                            
                                            <div class="col-9 ceate-title category-content" >
                                                <select name="category" id="category-select"
                                                        onchange="checkCategoryOption()" required>
                                                    <option  selected disabled>Select</option>
                                                    <c:forEach items="${requestScope.categories}" var="category"> 
                                                        <option value="${category.category_id}">${category.category_name}
                                                        </option>
                                                        <c:forEach items="${category.subcategories}" var="s">
                                                            <!--<input hidden name="sub_cat" catID="${category.category_id}" subCatID="${s.id}" value="${s.name}">-->
                                                        </c:forEach>
                                                    </c:forEach>
                                                    <!--<option value="0" onclick="openCategoryForm()" >Add More</option>-->
                                                </select>
                                                <div class="separate-cat">-</div>
                                            </div>
                                        </div>
                                        
                                        <div class="list-input list-input__title col-3 sub-cat-container">
                                            <div class="col-2 ceate-title">
                                                <p>Sub-category</p>
                                            </div>
                                            <div class="col-9 ceate-title category-content" id="category-content">
                                                <select name="sub_category" id="subcategory-select"
                                                        onchange="checkcategory();" required>
                                                    <option  selected disabled>Select</option>
                                                   
                                                </select>
                                            </div>
                                            <span id="category-err" class=" err err-category">Category is not empty</span>
                                        </div>
                             </div>

                  

                                <div class="list-input list-input__title col-12">
                                    <div class="col-2 ceate-title">
                                        <p>Feature</p>
                                    </div>

                                    <div class="col-1 ceate-title">

                                    </div>
                                    <div class="col-9 ceate-title">
                                        <select name="feature" id="feature-select" required>
                                            <option value="1">Show</option>
                                            <option value="0">Hide</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="list-input list-input__title col-12 thumbnail-create">
                                    <div class="col-2 ceate-title">
                                        <p>Upload Thumbnail</p>
                                    </div>

                                    <div class="col-1 ceate-title">

                                    </div>
                                    <div class="col-9 ceate-title">
                                        <input required type="file" id="imageFile"
                                               onchange="chooseFile(this)"
                                               name="thumbnail" accept="image/gif, image/jpeg, image/png"/>
                                        <span id="thumbnail-err" class="err">Thumbnail is not empty</span>
                                    </div>
                                </div>
                                <div class="list-input list-input__title col-12 thumbnail-container">

                                    <img src="" alt="" id="thumbnail-create" >
                                </div>            
                                <div class="list-input list-input__title col-12 content-create__container">
                                    
                                    <div class="ceate-title content-create__title">
                                        <p>Post details</p>
                                        <span id="content-err" class="err">Content is not empty</span>

                                        <div>
                                            <div  class="content-create__detail">
                                                <textarea id="ckeditorArea" required></textarea>
                                                <input id="content" type="hidden" name="content"required />
                                                <button type="button" class="btn btn-primary" onclick="saveData()">Submit</button>
                                                <!--<button type="submit" class="btn btn-primary">Submit</button>-->
                                            </div>
                                        </div>            

                                    </div> 
                                    </form>
                        <c:forEach items="${requestScope.categories}" var="category"> 
                                                        
                                                        <c:forEach items="${category.subcategories}" var="s">
                                                            <input hidden name="sub_cat" cat_id="${category.category_id}" subcat_id="${s.id}" value="${s.name}">
                                                        </c:forEach>
                                                    </c:forEach>
                                </div>
                            </div>














                            <!--        <form id="formEditor" method="post" action="addlist">
                                        <textarea id="ckeditorArea"></textarea>
                                        <input id="content" type="hidden" name="content" />
                                        <input type="button" value="Save" onclick="saveData()"/>
                                    </form>-->

<!--<div>${content}</div>-->
                            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      
                            <script type="text/javascript">
                                var editor = CKEDITOR.replace('ckeditorArea'
                                        ,
                                        {
                                            filebrowserBrowseUrl: '../ckfinder/ckfinder.html',
                                            filebrowserImageBrowseUrl: '../ckfinder/ckfinder.html?type=Images',
                                            filebrowserFlashBrowseUrl: '../ckfinder/ckfinder.html?type=Flash',
                                            filebrowserUploadUrl: '../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                                            filebrowserImageUploadUrl: '../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                                            filebrowserFlashUploadUrl: '../ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
                                        });

                        //        var editor = CKEDITOR.replace('ckeditorArea');
                        //        CKFinder.setupCKEditor(editor, './ckfinder/');
                         
                                
                                function saveData() {
                                    const post = editor.getData();
                                        if(validate()) {
                                            document.querySelector('#content').value = post;
                                            document.querySelector('#post_list').submit();
                                        }
                                }
                                    //choose filse
                                    function chooseFile(fileInput) {
                                        if (fileInput.files && fileInput.files[0]) {
                                            console.log("123");
                                            var reader = new FileReader();
                                            reader.onload = function (e) {
                                                $('#thumbnail-create').attr('src', e.target.result);
                                            }
                                            reader.readAsDataURL(fileInput.files[0]);
                                        }
                                        checkthubnail();
                                    }
                                    function checktitle(){
                                          var title = document.getElementById('title-add').value;
                                          if (title == '') {
                                        $('#title-err').show();
//                                        $('#title-add').focus();
                                        check=false;
                                        }else{
                                            $('#title-err').hide();
                                              return true;
                                        }
                                    }
                                    function checkcategory(){
                                         var category = $('#category-select').children("option:selected").val();
                                    var sub_category = $('#subcategory-select').children("option:selected").val();
                                      if (category == 'Select'||sub_category=='Select') {
//                                        $('#category-select').children("option:selected").focus();
                                        $('#category-err').show();
                                           check=false;
                                        }else{
                                             $('#category-err').hide();
                                             return true;
                                        }
                                    }
                                    function checkthubnail(){
                                        var thumbnail = document.getElementById('imageFile').value;
                                        if (thumbnail == '') {
//                                        $('#imageFile').focus();
                                         $('#thumbnail-err').show();
                                           check=false;
                                        }else{
                                            $('#thumbnail-err').hide();
                                            return true;
                                        }
                                    }
                                    function checkpost(){
                                         const post = editor.getData();
                                           if (post.trim('') == '') {
                                            $('#content-err').show();
                                             check=false;
                                        }
                                      else{
                                          $('#content-err').hide();
                                          return true;
                                      }
                                    }
                                    
                                    function validate() {
                                      var check=true;
                                      if(!checkcategory()){
                                          check=false;
                                      }
                                      if(!checkpost()){
                                          check=false;
                                      }
                                      if(!checkthubnail()){
                                          check=false;
                                      }
                                      if(!checktitle()){
                                          check=false;
                                      }
                                      return check;
                                }
                                function checkCategoryOption(){
                                    var category_id = $('#category-select').children("option:selected").val();
                                    var arr=document.getElementsByName('sub_cat'); //element of subcategory
                                    $("#subcategory-select option").remove();
                                    for (var i = 0; i < arr.length; i++) {
                                        if(arr[i].getAttribute('cat_id')==category_id){
                                            var option = $('<option/>');
                                            option.attr({ 'value': arr[i].getAttribute('subcat_id') }).text(arr[i].value);
                                            $('#subcategory-select').append(option);
                                        }
                                    }
                                    checkcategory();
                                }
                            </script>
                         </body>
                            </html>