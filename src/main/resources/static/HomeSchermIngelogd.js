window.onload = checkToken()

function checkToken(){
    let token = localStorage.getItem("token")
    console.log("Token is opgehaald voor token " + token)
    return token
}

