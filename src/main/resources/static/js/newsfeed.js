function addActiveClass() {
    var category = document.getElementById("category").textContent;
    if(category == "") {
        return;
    }
    var categories = document.getElementsByClassName("nav-link");
    for (var index = 0;index < categories.length;index++) {

        if (categories[index].innerHTML.toLowerCase().includes(category)) {
            categories[index].classList.add("active");
        }
    }
}

function addALternateImageSource() {
    var images = document.getElementsByTagName("img");
    for (let index = 0; index < images.length; index++) {
        images[index].addEventListener("error", function() {
        	console.log("error");
            this.setAttribute("src", "https://howfix.net/wp-content/uploads/2018/02/sIaRmaFSMfrw8QJIBAa8mA-article.png");
        })
        
    }
}

addActiveClass();
addALternateImageSource();
