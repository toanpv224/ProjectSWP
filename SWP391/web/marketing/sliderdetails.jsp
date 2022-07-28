<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="slider" value="${requestScope.slider}"></c:set>
    <!Doctype html>
    <html lang="en">
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
            <title>Marketing - Slider Details</title>
            <link rel="stylesheet" href="../css/style.css">
            <link rel="stylesheet" href="../css/slider.css">
            <script src="https://kit.fontawesome.com/3c84cb624f.js" crossorigin="anonymous"></script>
        </head>
        <body>
            <div class="d-flex">
            <c:import url="/marketing/sidebar.jsp"></c:import>
                <div class="p-3 rounded bg-white container-lg my-5">
                    <div class="d-flex justify-content-between sticky-0 bg-white pt-3 border-bottom border-2 mb-3 pb-1">
                        <div>
                            <form id="form-change-slider-image" enctype="multipart/form-data">
                                <input type="text" name="slider_id" value="${slider.id}" hidden>
                            <input type="text" name="action" value="change_inmage" hidden>
                            <label>
                                <a class="btn btn-outline-primary cursor-pointer">Change Slider Image</a>
                                <input type="file" name="input-slider-image" onchange="uploadSliderImage(event)" hidden>
                            </label>
                        </form>
                    </div>
                    <div>
                        <div class="form-check form-switch form-switch-md">
                            <p>Featured</p>
                            <input class="featured-btn form-check-input" style="cursor: pointer" type="checkbox" role="switch" id="sliderStatusSwitch" ${slider.status eq "1" ? "checked" : "" } onchange="${slider.status eq "1" ? "hideSlider" : "showSlider" }(${slider.id})"/>
                            <label class="form-check-label" for="sliderStatusSwitch"></label>
                        </div>
                    </div>
                </div>
                <div class="slider">
                    <div class="slider-image mb-3">
                        <img class="rounded" src="../${slider.imagePath}" alt="">
                    </div>
                    <div class="mb-3 d-flex">
                        <h3>Slider information</h3>
                        <a class="btn btn-light ms-3 d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#modal-change-slider-information"><span>Edit</span></a>
                    </div>
                    <div class="slider-information">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Title</span>
                            <input type="text" class="form-control" placeholder="Slider Title" value="${slider.title}" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">BackLinks</span>
                            <input type="text" class="form-control" placeholder="Slider Title" value="${slider.url}" readonly>
                        </div>
                        <div class="input-group">
                            <span class="input-group-text">Notes</span>
                            <textarea class="form-control" placeholder="Slider Notes" name="content" readonly></textarea>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div>
        <div id="modal-change-slider-information" class="modal modal-lg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6 class="modal-title">Change Slider Information</h6>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="form-change-slider-information">
                            <div class="input-group mb-3">
                                <span class="input-group-text">Title</span>
                                <input type="text" class="form-control" placeholder="Slider Title" name="title" value="${slider.title}" >
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text">BackLinks</span>
                                <input type="text" class="form-control" placeholder="Slider Title" name="backlink" value="${slider.url}" >
                            </div>
                            <div class="input-group">
                                <span class="input-group-text">Notes</span>
                                <textarea class="form-control" placeholder="Slider Notes" name="notes"></textarea>
                            </div>
                        </form>
                        <h5 id="alert-modal-change-slider-information"></h5>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="Close">Close</button>
                        <a class="btn btn-primary"  onclick="editSliderInformation(${slider.id})">Save changes</a>
                    </div>
                </div>
            </div>
        </div>
        <div id="modal-change-slider-image" class="modal modal-lg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h6 class="modal-title">Slider image will look like:</h6>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="slider">
                            <div class="slider-image">
                                <img src="" name="slider-image-preview">
                            </div>
                        </div>
                        <h5 id="alert-modal-change-slider-image"></h5>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="Close">Close</button>
                        <a class="btn btn-primary" onclick="editSliderImage(${slider.id})">Save</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <script src="../js/textarea_auto_resize.js"></script>
    <script src="../js/marketing/sliderdetails.js"></script>
</body>
</html>