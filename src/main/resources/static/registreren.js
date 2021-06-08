function register(){
    console.log("method call register()");
    let bsn = document.querySelector('#bsn').value;
    let firstName = document.querySelector('#firstName').value;
    let infix = document.querySelector('#infix').value;
    let surname = document.querySelector('#surname').value;
    let dateOfBirth = document.querySelector('#dateOfBirth').value;
    let postalCode = document.querySelector('#postalCode').value;
    let houseNr = document.querySelector('#houseNr').value;
    let addition = document.querySelector('#addition').value;
    let streetName = document.querySelector('#streetName').value;
    let residence = document.querySelector('#residence').value;
    let email = document.querySelector('#email').value;
    let password = document.querySelector('#password').value;
    console.log();
    fetch('http://localhost:8080/register/client',
        {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                bsn: bsn,
                firstName: firstName,
                infix: infix,
                surname: surname,
                dateOfBirth: dateOfBirth,
                postalCode: postalCode,
                houseNr: houseNr,
                addition: addition,
                streetName: streetName,
                residence: residence,
                email: email,
                password: password
            })
        })
        .then(response => {
            console.log(response)
            if(response.ok){
                //window.location.replace()
                return response.json()
            }
        })
        .catch((error) => {
            console.error('Foutje', error);
            alert("Registratie mislukt")
        })
}

function sendToken(token) {
    fetch('http://localhost:8080/register/finalize',
        {
            method: 'POST',
            headers: {
                'Authorization': token
            }
        })
        .then(response => {
            console.log(response)
            if(response.ok){
                window.location.replace('http://localhost:8080/LoginController.html')
                alert("Registratie voltooid. Je kunt nu inloggen.")
                return response.json()
            }
        })
        .catch((error) => {
            console.error('Foutje', error);
            alert("Registratie mislukt")
        })
}
