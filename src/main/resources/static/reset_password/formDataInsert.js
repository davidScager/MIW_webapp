function getToken() {
    const url = window.location;
    const token = new URLSearchParams(url.search).get('Authorization');
    localStorage.setItem("token", token);
    fetch("http://localhost:8080/reset/createnewpassword", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({token: token})
    })
        .then(response => response.json())
        .then(data => {
            showPage(data);
        })
        .catch((error) => {
            console.error('Foutje', error);
        });
    localStorage.clear();
    localStorage.setItem("passwordreset", "yes");

}

function showPage(data) {
    if (data === false) {
        window.location.replace("http://localhost:8080/resetdenied.html");
    }
}



let passwordMatches = false;

function initiateReset() {
    const email = new FormDataInsert(new RegExp(/^[^ ]+@[^ ]+\.[a-z]{2,3}$/),
        "http://localhost:8080/reset/resetpassword", ["email"])

    email.resetPassword();
}

function initiateNewPassword() {
    const passwordReset = new FormDataInsert(new RegExp(/^.{8,100}$/), "http://localhost:8080/reset/setnewpassword",
         ["password"])
    console.log(passwordMatches)
    if (passwordMatches === true) {
        passwordReset.resetPassword();
    }
}

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

        if( email == "")
        {
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
    var x = document.getElementById(password);
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
    var y = document.getElementById(confirmpassword);
    if (y.type === "password") {
        y.type = "text";
    } else {
        y.type = "password";
    }
}

class FormDataInsert {
    dataHasWrongInput
    mailRegex
    backEndPoint
    arrayOfInserts

    constructor(mailRegex, backEndPoint, arrayOfInserts) {

        this.mailRegex = mailRegex;
        this.backEndPoint = backEndPoint;
        this.arrayOfInserts = arrayOfInserts;
        this.dataHasWrongInput = false;
    }

resetPassword() {

    for (var i=0; i < this.arrayOfInserts.length; i++) {
       document.querySelector('#' + this.arrayOfInserts[i]).addEventListener('focusout', this.checkInserts(this.arrayOfInserts[i]));
       console.log(this.dataHasWrongInput)
    }

    if (this.dataHasWrongInput === false) {
        this.sendData(document.querySelector('#'+(this.arrayOfInserts)[0]).value, this.backEndPoint);
    }
}

//algemene functie om velden in formulieren te checken op legitimiteit
checkInserts(insert) {
    let insertToCheck = document.querySelector('#'+insert).value
    console.log('insert is valide: ' + this.mailRegex.test(insertToCheck)+ insertToCheck);
    if (!this.mailRegex.test(insertToCheck)) {
        this.dataHasWrongInput = true;
    }
}

sendData(dataForBackEnd) {
        fetch(this.backEndPoint, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({insert: dataForBackEnd})
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                }
            })
            .then()
            .catch((error) => {
                console.error('Foutje', error);
            });
        localStorage.setItem("passwordreset", "yes");

    }
}