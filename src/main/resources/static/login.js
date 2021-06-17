window.onload = checkForLogout()

const urlAddress = "http://miw-team-2.nl";
const doLogin = urlAddress + "/login";
const homePage = urlAddress + "/HomeSchermIngelogd.html"

let emailElement
let email
let passwordElement
let password
let loginInfoMessage
let passwordEmpty

function clearInput(){
    gatherInput()
    emailElement.value=""
    passwordElement.value=""
    emailElement.style.boxShadow = "none"
    passwordElement.style.boxShadow = "none"
    loginInfoMessage.style.visibility = "hidden"
}

function gatherInput() {
    emailElement = document.querySelector("#email")
    email = emailElement.value
    passwordElement = document.querySelector("#password")
    password = passwordElement.value;
    loginInfoMessage = document.getElementById("loginInfo")
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
    gatherInput()
    console.log(localStorage.getItem("passwordreset"));
    if (localStorage.getItem("passwordreset") === "yes") {
        loginInfoMessage.innerText = "Uw wachtwoord is gereset. Log in om door te gaan."
        loginInfoMessage.style.color = "lawngreen"
        loginInfoMessage.style.visibility = "visible"
        localStorage.clear()
    } else {
        loginInfoMessage.style.visibility = "invisible"
    }
}

function checkFieldNotEmpty(field, element){
    if (field === ""){
        if(element === "email"){
            emailElement.style.boxShadow = "0 0 3px #CC0000"
        } else {
            passwordElement.style.boxShadow = "0 0 3px #CC0000"
            passwordEmpty = true
        }
        loginInfoMessage.innerText = "Velden mogen niet leeg zijn"
        loginInfoMessage.style.visibility = "visible"
    }
}

function getEmailValidator() {
    let regex = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i);
    console.log('email is valide: ' + regex.test(email));
    return regex
}

function setupFetchMethod() {
    return fetch(doLogin, {
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
        window.location.replace(homePage)
    } else if(!passwordEmpty){
        emailElement.style.boxShadow = "0 0 3px #CC0000"
        passwordElement.style.boxShadow = "0 0 3px #CC0000"
        loginInfoMessage.innerText = "Combinatie gebruikersnaam en wachtwoord klopt niet"
        loginInfoMessage.style.visibility = "visible"
    }
    passwordEmpty = false
}

function sendData() {
    gatherInput()
    checkFieldNotEmpty(email, "email")
    checkFieldNotEmpty(password, "password")
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