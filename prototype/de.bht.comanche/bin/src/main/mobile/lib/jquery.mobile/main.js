function init() {
    document.addEventListener("deviceready", deviceReady, true);
    delete init;
}

function deviceReady() {    
    $("#loginForm").on("submit", toHome);
    
    $("#register").on("click", register);
    
}

function toHome(){
    window.location = "home.html";
}

function register(){
    window.location = "register.html";
}

