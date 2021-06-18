function emailValidation() {
    let form = document.getElementById("form");
    let email = document.getElementById("email").value;
    let text = document.getElementById("text");
    let pattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;

    if (email.match(pattern)) {
        form.classList.add("valid")
        form.classList.remove("invalid")
        text.innerHTML = "Uw emailadres is geldig"
        text.style.color = "#03AC13";
    } else {
        form.classList.remove("valid")
        form.classList.add("invalid")
        text.innerHTML = "Voer geldig emailadres in"
        text.style.color = "#f7931a";
    }
    if( email == "") {
        form.classList.remove("valid")
        form.classList.remove("invalid")
        text.innerHTML = ""
        text.style.color = "#f7931a";
    }
}

function passwordValidation() {
    let form = document.getElementById("form");
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirmpassword").value;
    let textblock = document.getElementById("text");
    let passwordblock = document.getElementById("password")
    let pattern = /^.{8,100}$/;

    if (password.match(pattern)) {
        form.classList.add("valid")
        form.classList.remove("invalid")
        textblock.innerHTML = "Wachtwoord is goed"
        textblock.style.color = "#f7931a";
        passwordblock.innerHTML = "Wachtwoord is goed"
        passwordblock.style.color = "#f7931a";
        if (password === confirmPassword) {
            passwordblock.innerHTML = 'matching';
            passwordblock.style.color = "#03AC13";
            textblock.innerHTML = 'matching';
            textblock.style.color = "#03AC13";
            passwordMatches = true;
            console.log(passwordMatches)
        } else {
            passwordblock.innerHTML = 'not matching';
            passwordblock.style.color = "#f7931a";
            textblock.innerHTML = 'not matching';
            textblock.style.color = "#f7931a";
            passwordMatches = false;
            console.log(passwordMatches)
        }
    } else {
        form.classList.remove("valid")
        form.classList.add("invalid")
        text.innerHTML = "Wachtwoord moet minimaal 8 tekens hebben"
        text.style.color = "f7931a";
    }
    if (password === "") {
        form.classList.remove("valid")
        form.classList.remove("invalid")
        textblock.innerHTML = ""
        textblock.style.color = "#f7931a";
    }
}

function togglePassword(password, confirmpassword) {
    let x = document.getElementById(password);
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
    let y = document.getElementById(confirmpassword);
    if (y.type === "password") {
        y.type = "text";
    } else {
        y.type = "password";
    }
}