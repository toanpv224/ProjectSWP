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
        <link rel="stylesheet" href="../css/customerlist.css"/>
        <link rel="stylesheet" href="../css/addcustomer.css"/>
          <!--font-awesome-->
        <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
        <div class="container-lg">
            <!--content--> 
         <div class=" form-container col-11 p-3 bg-white rounded my-5">
                <div class="form-header">
                    <h1>Customer Details:</h1>
                </div>
             <form class="row" id="add-customer" 
                   action="/swp/marketing/addcustomer"
                   method="post"
                   enctype="multipart/form-data"
                   >
             <div class=" form-body ">
                     
                 
    <div class=" form-body_input col-7" >
        <span class="mess">${requestScope.mess}</span>
            <div class="content-form row">
                <div class="mb-3 col-11">
                <label for="exampleFormControlInput1" class="form-label">User Name</label>
                <input value="${requestScope.cus.username}" required type="text" class="form-control" name="username"
                        id="exampleFormControlInput1" placeholder="UserName">
              </div>
            </div>
           <div class="content-form row city-item ">
                <div class=" col-5 mb-3">
            <label for="exampleFormControlInput1" class="form-label">Full Name</label>
            <input    value="${requestScope.cus.full_name}"  
                    required type="text" class="form-control" name="fullname"
                        id="exampleFormControlInput1" placeholder="Full Name">
              </div>
                <div class=" col-1 mb-3 separate-city">
              </div>
                <div class=" col-5 mb-3">
                    <label for="exampleFormControlInput1" class="form-label">Phone</label>
                    <input  value="${requestScope.cus.phone}"
                    required type="text" class="form-control" name="phone"
                        id="exampleFormControlInput1" placeholder="Phone">
              </div>
            </div>    
        
            <div class="content-form row ">
                <div class="mb-3 col-11">
                <label for="exampleFormControlInput1" class="form-label">Email</label>
                <input required value="${requestScope.cus.email}"
                       type="text" class="form-control" name="email" id="exampleFormControlInput1" placeholder="Email">
              </div>
            </div>
            <div class="content-form row">
                <div class="mb-3 col-11">
                <label for="exampleFormControlInput1" class="form-label">Address</label>
                <input required  value="${requestScope.cus.address}"
                       type="text" class="form-control" name="address" id="exampleFormControlInput1" placeholder="Address">
              </div>
            </div>
            <div class="content-form row city-item">
                <div class=" col-5 mb-3">
                <label for="exampleFormControlInput1" class="form-label">Country</label>
                <input required
                       value="${requestScope.cus.country}"
                       type="text" class="form-control" name="country" id="exampleFormControlInput1" placeholder="Country">
              </div>
                <div class=" col-1 mb-3 separate-city">
                    <h4>-</h4>
              </div>
                <div class=" col-5 mb-3">
                <label for="exampleFormControlInput1" class="form-label">City</label>
                <input type="text" required ${requestScope.cus.city}
                       class="form-control" name="city" id="exampleFormControlInput1" placeholder="City">
              </div>
            </div>
            <div class="content-form row city-item gender-item">
                <div class=" col-5 mb-3">
            <label for="gender" class="form-label">Gender</label>
            <select id="gender"  class="form-select" name="gender" aria-label="Default select example">
                <option value="1"<c:if test="${requestScope.cus.gender}"> selected</c:if>
                        >Male</option>
                <option value="0"
                 <c:if test="${!requestScope.cus.gender}"> selected</c:if>       
                        >Female</option>
              </select>
              </div>
                <div class=" col-1 mb-3 separate-city">
              </div>
                <div class=" col-5 mb-3">
                <label for="feature" class="form-label">Status</label>
                <select  id="feature" class="form-select" name="featured" aria-label="Default select example">
                <option 
                    <c:if test="${requestScope.cus.feature}">selected</c:if>
                    value="1">Active</option>
                <option 
                    <c:if test="${!requestScope.cus.feature}">selected</c:if>
                    value="0">Inactive</option>
              </select>
              </div>
            </div>
         
        
            <div class="content-form row submit-item">
                <div class=" col-1 mb-3">
                    
                </div>
                <div class=" col-2 mb-3">
                    <button type="submit" class="btn btn-success">Submit</button>
                </div>
                <div class=" col-1 mb-3">
                    <button type="button" class="btn btn-secondary" style="width: fit-content;">Cancel</button>
                </div>
            </div>
         </div>   
                 <div class="col-5 form-body-image">
                 <div class="avatar-upload">
                     <h5>Upload Avartar</h5>
                   <div class="avatar-edit">
                       <input  type='file' name="imageUpload"  value="C:\\fakepath\\8acc.png"
                               id="imageUpload"
                               accept="image/gif, image/jpeg, image/png" 
                               onchange="ChangeAvt(this)"/>
                              
                       <label for="imageUpload"></label>
                   </div>
                   <div class="avatar-preview ">
                     <img class="profile-user-img img-responsive img-circle" id="imagePreview" src="../images/account-images/acc.png" alt="User profile picture">
                   </div>
                    
                 </div>
                    
                 </div>
         </div>
             </form>
       </div>   
       </div>
    <script src="../js/customerlist.js"> </script>
         <!--Ajax-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </body>
</html>