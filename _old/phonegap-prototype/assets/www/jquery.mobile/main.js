function init() {
	document.addEventListener("deviceready", deviceReady, true);
	delete init;
}

function deviceReady() {    
 	$("#loginForm").on("submit", toHome);
 	$("#register").center();
 	$("#register").on("click", register);
 	
}

function toHome(){
    window.location = "home.html";
}

function register(){
    window.location = "register.html";
}

jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + 
                                                $(window).scrollLeft()) + "px");
    return this;
}