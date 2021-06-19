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
let addressFlag = false;
let allFieldsFilledFlag = true;

function register(){
    console.log("method call register()");
    if (finalCheck()) {
        getFormData();
        setupFetch()
            .then(response => handleResponse(response))
            .catch((error) => {
                console.error('Foutje', error.toString());
                alert("Registratie mislukt");
            });
    } else {
        resetFlags();
        console.log("flags reset")
        document.querySelector('#FieldEmptyError').style.visibility = "visible";
    }
}

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

function getJsonBody() {
    return{
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
    };
}

function setupFetch() {
    return fetch(regReqUrl,
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(getJsonBody())
        });
}

function handleResponse(response) {
    console.log(response)
    if (response.status === 200) {
        afterRegister();
    } else {
        window.location.replace(regFailUrl);
    }
}

function afterRegister() {
    resetFlags();
    document.querySelector('#form').style.visibility = 'hidden';
    document.querySelector('#regButton').style.visibility = 'hidden';
    document.querySelector('#regInfo').innerHTML = 'Er is een bevestigingsmail naar het opgegeven email adres gestuurd. ' +
        '\nBevestig je registratie a.u.b. binnen 30 minuten.';
    document.querySelector('#EmailError').style.visibility = "hidden";
    document.querySelector('#PasswordError').style.visibility = "hidden";
    document.querySelector('#FieldEmptyError').style.visibility = "hidden";
}
//end registration request

//start register checks
function resetFlags() {
    allFieldsFilledFlag = true;
    emailFlag = false;
    passwordFlag = false;
    repasswordFlag = false;
    addressFlag = false;
}

function finalCheck() {
    checkEmail();
    checkPassword();
    checkRepassword();
    checkAnyEmpty();
    checkPcApiResponse();
    let flags = [emailFlag, passwordFlag, repasswordFlag, addressFlag, allFieldsFilledFlag];
    console.log(flags);
    return flags.every(item =>{return item === true;});
}

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
    let regExp = new RegExp(/^[^ ]+@[^ ]+\.[a-z]{2,3}$/i);
    if (emailInput.value != null && !regExp.test(emailInput.value)) {
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
    let passwordError = document.querySelector('#PasswordError');
    if (repassword !== password) {
        passwordError.innerHTML = "Wachtwoorden zijn niet gelijk";
        showErrorMessage(repasswordInput, passwordError);
        repasswordFlag = false;
    } else {
        hideErrorMessage(repasswordInput, passwordError);
        repasswordFlag = true;
    }
}

function checkPcApiResponse(){
    let streetInput = document.querySelector('#streetName');
    let residenceInput = document.querySelector('#residence');
    if (streetInput.value === 'undefined' || residenceInput.value === 'undefined') {
        addressFlag = false;
        streetInput.style.borderColor = "red";
        residenceInput.style.borderColor = "red";
    } else {
        addressFlag = true;
        streetInput.style.borderColor = "";
        residenceInput.style.borderColor = "";
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
    data.forEach(item => {
        if (!item.value){
            allFieldsFilledFlag = false;
            item.style.borderColor = "red";
        } else {
            item.style.borderColor = "";
        }
    });
}
//end before register checks
