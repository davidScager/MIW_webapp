function loadReset() {
    console.log(localStorage.getItem("passwordreset"));
    if (localStorage.getItem("passwordreset") === "yes") {
        document.getElementById("login_als_reset").style.visibility="visible"
    localStorage.clear()
    } else {
        document.getElementById("login_als_reset").style.visibility="invisible"
    }
}

function sendData() {

    let emailElement = document.querySelector("#email")
    let email = emailElement.value
    let passwordElement = document.querySelector("#password")
    let password = passwordElement.value;
    let errorMessage = document.getElementById("loginError")


    if(email === ""){
        emailElement.style.boxShadow = "0 0 3px #CC0000"
        errorMessage.innerText = "Velden mogen niet leeg zijn"
        errorMessage.style.visibility = "visible"
    }

    if(password === ""){
        passwordElement.style.boxShadow = "0 0 3px #CC0000"
        errorMessage.innerText = "Velden mogen niet leeg zijn"
        errorMessage.style.visibility = "visible"
    }


    let regex = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i);

    console.log('email is valide: ' + regex.test(email));

    let pass;

    if (regex.test(email)) {
        fetch("http://localhost:8080/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: email,
                password: password
            })
        })
            .then(response => {
                console.log(response)
                if(response.ok){
                    let token = response.headers.get("Authorization")
                    console.log(token)
                    localStorage.clear()
                    localStorage.setItem("token", token)
                    window.location.replace("http://localhost:8080/homeSchermIngelogd.html")
                } else {
                    emailElement.style.boxShadow = "0 0 3px #CC0000"
                    passwordElement.style.boxShadow = "0 0 3px #CC0000"
                    errorMessage.innerText = "Combinatie gebruikersnaam en wachtwoord klopt niet"
                    errorMessage.style.visibility = "visible"
                }
            })
            .catch((error) => {

            });


        } else {
        if(email !== ""){
            alert("Voer een geldig email adres in")
            emailElement.style.boxShadow = "0 0 3px #CC0000"
        }
        }
}