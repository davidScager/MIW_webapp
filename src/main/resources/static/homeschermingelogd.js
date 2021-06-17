window.onload = checkToken()

function checkToken() {
    let token = localStorage.getItem("token")
    getUser(token)
    console.log("Token is opgehaald voor token " + token)
    return token
}

function getUser(token) {
    fetch("http://localhost:8080/checkuser", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + token
        }
    }).then(response => response.json())
        .then(json => nameUser(json))
}

function nameUser(json) {
    let userName = json.fullName.firstName;
    console.log(userName);
    document.getElementById("welkomgebruiker").innerText = "Welkom " + userName;
}


