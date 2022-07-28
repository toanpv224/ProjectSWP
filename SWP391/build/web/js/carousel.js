const sliders = document.querySelectorAll(".carousel");
sliders.forEach(s => {
    const firstCarouselCaption = s.querySelector(".carousel-indicators button");
    const firstSlider = s.querySelector(".carousel-inner .carousel-item");
        firstCarouselCaption.classList.add("active");
        firstSlider.classList.add("active");
});