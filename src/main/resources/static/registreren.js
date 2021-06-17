//checkForAddress: Remi de Boer
function checkForAddress() {
    let regex = new RegExp(/[1-9]{4}[A-Za-z]{2}$/i);
    let pc = document.querySelector('#postalCode').value;
    let nr = document.querySelector('#houseNr').value;
    let pcApiUrl = "https://postcode.tech/api/v1/postcode?";
    let myPcApiToken = 'Bearer 5382a25d-6bbe-4b44-885e-7b4d3d0b5dff';

    console.log('pc is valid: ' + regex.test(pc));

    let formData = `postcode=${pc}&number=${nr}`;
    if (regex.test(pc) && nr) {
        fetch(pcApiUrl + formData,
            {
                method: 'GET',
                headers: {
                    'Authorization': myPcApiToken
                }
            })
            .then(response => response.json())
            .then(json => processAddress(json))
            .catch((error) => { console.error('could not perform GET request', error)});
    }
}

function processAddress(json){
    console.log(json);
    let address = json;
    document.querySelector('#residence').value = address.city;
    document.querySelector('#streetName').value = address.street;
}
//end checkForAddress

//author: David Scager
//start registration request
const regReqUrl = '/register/request';
const regFailUrl = '/register/failed';
let bsn;
let firstName;
let infix;
let surname;
let dateOfBirth;
let postalCode;
let houseNr;
let addition;
let streetName;
let residence;
let email;
let password;
let repassword;
let emailFlag = false;
let passwordFlag = false;
let repasswordFlag = false;

function getFormData(){
    bsn = document.querySelector('#bsn').value;
    firstName = document.querySelector('#firstName').value;
    infix = document.querySelector('#infix').value;
    surname = document.querySelector('#surname').value;
    dateOfBirth = document.querySelector('#dateOfBirth').value;
    postalCode = document.querySelector('#postalCode').value;
    houseNr = document.querySelector('#houseNr').value;
    addition = document.querySelector('#addition').value;
    streetName = document.querySelector('#streetName').value;
    residence = document.querySelector('#residence').value;
    email = document.querySelector('#email').value;
    password = document.querySelector('#password').value;
    repassword = document.querySelector('#repassword').value;
}

function setupFetch() {
    return fetch(regReqUrl,
        {
            method: 'POST',
            headers: {
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
                        postalCode: postalCode ,
                        houseNr: houseNr,
                        addition: addition,
                        streetName: streetName,
                        residence: residence
                    },
                    email: email
                },
                password: password
            })
        });
}

function handleResponse(response) {
    console.log(response)
    if (response.status === 202) {
        afterRegister();
    } else {
        window.location.replace(regFailUrl);
    }
}

function register(){
    console.log("method call register()");
    if (!checkAnyEmpty() && emailFlag && passwordFlag && repasswordFlag) {
        getFormData();
        setupFetch()
            .then(response => handleResponse(response))
            .catch((error) => {
                console.error('Foutje', error.toString());
                alert("Registratie mislukt");
            });
    } else {
        document.querySelector('#FieldEmptyError').style.visibility = "visible";
    }
}

function afterRegister() {
    document.querySelector('#form').style.visibility = 'hidden';
    document.querySelector('#regButton').style.visibility = 'hidden';
    document.querySelector('#regInfo').innerHTML = 'Er is een bevestigingsmail naar het opgegeven email adres gestuurd. ' +
        '\nBevestig je registratie a.u.b. binnen 30 minuten.';
    document.querySelector('#EmailError').style.visibility = "hidden";
    document.querySelector('#PasswordError').style.visibility = "hidden";
    document.querySelector('#FieldEmptyError').style.visibility = "hidden";
}
//end registration request

//start before register checks
function showErrorMessage(input, errorType) {
    input.style.borderColor = "red";
    errorType.style.visibility = "visible";
}

function hideErrorMessage(input, errorType) {
    input.style.borderColor = "";
    errorType.style.visibility = "hidden";
}

function checkEmail(){
    let emailInput = document.querySelector('#email');
    let emailError = document.querySelector('#EmailError');
    let regExp = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i);
    if (!regExp.test(emailInput.value) && emailInput.value) {
        showErrorMessage(emailInput, emailError);
        emailFlag = false;
    } else {
        hideErrorMessage(emailInput, emailError);
        emailFlag = true;
    }
}

function checkPassword() {
    let passwordInput = document.querySelector('#password');
    let password = passwordInput.value;
    let passwordError = document.querySelector('#PasswordError');
    if (password && password.length < 8) {
        passwordError.innerHTML = "Wachtwoord is te zwak";
        showErrorMessage(passwordInput, passwordError);
        passwordFlag = false;
    } else {
        hideErrorMessage(passwordInput, passwordError);
        passwordFlag = true;
    }
}

function checkRepassword() {
    let passwordInput = document.querySelector('#password');
    let password = passwordInput.value;
    let repasswordInput = document.querySelector('#repassword');
    let repassword = repasswordInput.value;
    let passwordError = document.querySelector('#PasswordError')
    if (password && repassword !== password) {
        passwordError.innerHTML = "Wachtwoorden zijn niet gelijk";
        showErrorMessage(repasswordInput, passwordError);
        repasswordFlag = false;
    } else {
        hideErrorMessage(repasswordInput, passwordError);
        repasswordFlag = true;
    }
}

function checkAnyEmpty(){
    let data = [
        document.forms["registrationForm"]["bsn"],
        document.forms["registrationForm"]["firstName"],
        document.forms["registrationForm"]["surname"],
        document.forms["registrationForm"]["dateOfBirth"],
        document.forms["registrationForm"]["postalCode"],
        document.forms["registrationForm"]["houseNr"],
        document.forms["registrationForm"]["streetName"],
        document.forms["registrationForm"]["residence"],
        document.forms["registrationForm"]["email"],
        document.forms["registrationForm"]["password"],
        document.forms["registrationForm"]["repassword"]
        ];
    let anyEmpty;
    data.forEach(item => {
        if (!item.value){
            anyEmpty = true;
            item.style.borderColor = "red";
        } else {
            item.style.borderColor = "";
        }
    });
    return anyEmpty;
}
//end before register checks
