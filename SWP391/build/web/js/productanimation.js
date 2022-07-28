function zoomIn(items){
  items.style.transform = "scale(1.15)";
  items.style.transition = "all 0.5s ease-in-out";
}
function zoomOut(items){
  items.style.transform = "scale(1)";
  items.style.transition = "all 0.5s ease-in-out";
}
function displayProductAction(items){
    var actionContainer = items.querySelector('.buy-form-container');
    actionContainer.style.height ="80px";
    actionContainer.style.visibility = "visible";
}
function HideProductAction(items){
    var actionContainer = items.querySelector('.buy-form-container');
    actionContainer.style.height ="0";
    actionContainer.style.visibility = "hidden";
}