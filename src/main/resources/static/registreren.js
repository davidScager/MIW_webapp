//strength meter by: https://gabrieleromanato.name/javascript-password-strength-meter-with-the-zxcvbn-library
'use strict';

class PasswordMeter {
    constructor(selector) {
        this.wrappers = document.querySelectorAll(selector);
        if(this.wrappers.length > 0) {
            this.init(this.wrappers);
        }
    }
    init(wrappers) {
        wrappers.forEach(wrapper => {
            let bar = wrapper.querySelector('.password-meter-bar');
            let input = wrapper.previousElementSibling;

            input.addEventListener('keyup', () => {
                let value = input.value;
                bar.classList.remove('level0', 'level1', 'level2', 'level3', 'level4');
                let result = zxcvbn(value);
                let cls = `level${result.score}`;
                bar.classList.add(cls);
            }, false);
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const passwordMeter = new PasswordMeter('.password-meter-wrap');
}, false);
//end strength meter

//checkForAddress: Remi de Boer
function checkForAddress() {
    let regex = new RegExp(/[1-9]{4}[A-Za-z]{2}$/i);
    let pc = document.querySelector('#postalCode').value;
    let nr = document.querySelector('#houseNr').value;

    console.log('pc is valid: ' + regex.test(pc));

    let formData = `postcode=${pc}&number=${nr}`;
    if (regex.test(pc) && nr) {
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
    document.querySelector('#residence').value = address.city;
    document.querySelector('#streetName').value = address.street;
}
//end checkForAddress

//author: David Scager
function register(){
    console.log("method call register()");
    let email = document.querySelector('#email').value;
    let password = document.querySelector('#password').value;
    fetch('http://localhost:8080/register/request',
        {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                user: {
                    bsn: document.querySelector('#bsn').value,
                    fullName: {
                        firstName: document.querySelector('#firstName').value,
                        infix: document.querySelector('#infix').value,
                        surname: document.querySelector('#surname').value
                    },
                    dateOfBirth: document.querySelector('#dateOfBirth').value,
                    userAddress: {
                        postalCode: document.querySelector('#postalCode').value,
                        houseNr:document.querySelector('#houseNr').value,
                        addition: document.querySelector('#addition').value,
                        streetName: document.querySelector('#streetName').value,
                        residence: document.querySelector('#residence').value
                    },
                    email: email
                },
                password: password
            })
        })
        .then(response => {
            console.log(response);
            if (response.ok){
                afterRegister();
            } else {
                window.location.replace('http://localhost:8080/register/failed');
            }
        })
        .catch((error) => {
            console.error('Foutje', error);
            alert("Registratie mislukt");
        })
}

function checkEmail(){
    let emailInput = document.querySelector('#email');
    let email = emailInput.textContent
    let regExp = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i);
    if (!regExp.test(email)){
        emailInput.style.borderColor = "red";
    }
    emailInput.style.borderColor = "black";
}

function checkPassword() {
    let passwordInput = document.querySelector('#password');
    let password = passwordInput.textContent;
    if (password.length < 8) {
        passwordInput.style.borderColor = "red";
    }
    passwordInput.style.borderColor = "black";
}

function afterRegister() {
    document.querySelector('#form').style.visibility = 'hidden';
    document.querySelector('#regButton').style.visibility = 'hidden';
    document.querySelector('#regInfo').innerHTML = 'Er is een bevestigingsmail naar het opgegeven email adres gestuurd. ' +
        '\nBevestig je registratie a.u.b. binnen 30 minuten.'
}
