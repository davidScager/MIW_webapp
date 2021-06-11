document.getElementById('postalCode').addEventListener('focusout', checkForAddress);
document.getElementById('houseNr').addEventListener('focusout', checkForAddress);

function checkForAddress() {
    let regex = new RegExp(/[1-9]{4}[A-Za-z]{2}$/i);
    let pc = document.querySelector('#postalCode').value;
    let nr = document.querySelector('#houseNr').value;

    console.log('pc is valid: ' + regex.test(pc));

    let formData = `postcode=${pc}&number=${nr}`;
    if (regex.test(pc) && nr) {
        /*warning: `` not ''*/
        fetch("https://postcode.tech/api/v1/postcode?" + formData,
            {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer 5382a25d-6bbe-4b44-885e-7b4d3d0b5dff'
                }
            })
            .then(response => response.json())
            .then(json => processAddress(json))
            .catch((error) => { console.error('could not GET request', error)});
    }
}

function processAddress(json){
    console.log(json);
    let address = json;
    document.getElementById('residence').value = address.city;
    document.getElementById('streetName').value = address.street;
}

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
    fetch('http://localhost:8080/register/request',
        {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                user: {
                    bsn: bsn,
                    fullName: {
                        firstName: firstName,
                        infix: infix,
                        surname: surname
                    },
                    dateOfBirth: dateOfBirth,
                    userAddress: {
                        postalCode: postalCode,
                        houseNr: houseNr,
                        addition: addition,
                        streetName: streetName,
                        residence: residence
                    },
                    email: email
                },
                password: password
            })
        })
        .then(response => {
            console.log(response)
        })
        .catch((error) => {
            console.error('Foutje', error);
            alert("Registratie mislukt")
        })
}

function afterRegister() {
    document.getElementById('form').style.visibility = 'hidden';
    document.getElementById('regButton').style.visibility = 'hidden';
    document.getElementById('regInfo').innerHTML = 'Er is een bevestigingsmail naar het opgegeven email adres gestuurd. ' +
        '\nBevestig je registratie a.u.b. binnen 30 minuten.'
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
