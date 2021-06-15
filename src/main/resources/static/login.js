window.onload = checkForLogout()

let emailElement
let email
let passwordElement
let password
let errorMessage

function gatherInput() {
    emailElement = document.querySelector("#email")
    email = emailElement.value
    passwordElement = document.querySelector("#password")
    password = passwordElement.value;
    errorMessage = document.getElementById("loginError")
}

function checkForLogout() {
    if (localStorage.getItem("token") !== null) {
        clearLocalStorage()
        alert("U bent succesvol uitgelogd")
    }
}

function clearLocalStorage() {
    localStorage.clear()
}

function loadReset() {
    console.log(localStorage.getItem("passwordreset"));
    if (localStorage.getItem("passwordreset") === "yes") {
        document.getElementById("login_als_reset").style.visibility = "visible"
        localStorage.clear()
    } else {
        document.getElementById("login_als_reset").style.visibility = "invisible"
    }
}

function checkEmailNotEmpty() {
    if (email === "") {
        emailElement.style.boxShadow = "0 0 3px #CC0000"
        errorMessage.innerText = "Velden mogen niet leeg zijn"
        errorMessage.style.visibility = "visible"
    }
}

function checkPasswordNotEmpty() {
    if (password === "") {
        passwordElement.style.boxShadow = "0 0 3px #CC0000"
        errorMessage.innerText = "Velden mogen niet leeg zijn"
        errorMessage.style.visibility = "visible"
    }
}

function getEmailValidator() {
    let regex = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i);
    console.log('email is valide: ' + regex.test(email));
    return regex
}

function setupFetchMethod() {
    return fetch("http://localhost:8080/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: email,
            password: password
        })
    })
}

function handleResponse(response) {
    console.log(response)
    if (response.ok) {
        let token = response.headers.get("Authorization")
        console.log("Token na inlog: " + token)
        localStorage.setItem("token", token)
        window.location.replace("http://localhost:8080/homeSchermIngelogd.html")
    } else {
        emailElement.style.boxShadow = "0 0 3px #CC0000"
        passwordElement.style.boxShadow = "0 0 3px #CC0000"
        errorMessage.innerText = "Combinatie gebruikersnaam en wachtwoord klopt niet"
        errorMessage.style.visibility = "visible"
    }
}

function sendData() {
    gatherInput()
    checkEmailNotEmpty()
    checkPasswordNotEmpty()
    let emailValidator = getEmailValidator()
    if (emailValidator.test(email)) {
        setupFetchMethod().then(response => {
            handleResponse(response)
        })
    } else {
        if (email !== "") {
            alert("Voer een geldig email adres in")
            emailElement.style.boxShadow = "0 0 3px #CC0000"
        }
    }
}