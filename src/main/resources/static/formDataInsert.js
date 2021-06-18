const resetPassword = urlAddress + "/reset/resetpassword";
const setNewPassword = urlAddress + "/reset/setnewpassword";
const createNewPassword = urlAddress + "/reset/createnewpassword";
const denied = urlAddress + "/reset/denied";

let passwordMatches = false;

function initiateReset() {
    const email = new FormDataInsert(new RegExp(/^[^ ]+@[^ ]+\.[a-z]{2,3}$/),
        resetPassword, ["email"])

    email.digestInsertedData();
}

function initiateNewPassword() {
    const passwordReset = new FormDataInsert(new RegExp(/^.{8,100}$/), setNewPassword,
         ["password"])
    if (passwordMatches === true) {
        passwordReset.digestInsertedData();
    }
}

class FormDataInsert {
    dataHasWrongInput
    mailRegex
    backEndPoint
    arrayOfInserts

    constructor(mailRegex, backEndPoint, arrayOfInserts) {

        this.mailRegex = mailRegex;
        this.backEndPoint = backEndPoint;
        this.arrayOfInserts = arrayOfInserts;
        this.dataHasWrongInput = false;
    }

digestInsertedData() {
    for (var i=0; i < this.arrayOfInserts.length; i++) {
       document.querySelector('#' + this.arrayOfInserts[i]).addEventListener('focusout', this.checkInserts(this.arrayOfInserts[i]));
    }
    if (this.dataHasWrongInput === false) {
        this.sendData(document.querySelector('#'+(this.arrayOfInserts)[0]).value, this.backEndPoint);
    }
}

checkInserts(insert) {
    let insertToCheck = document.querySelector('#'+insert).value
    if (!this.mailRegex.test(insertToCheck)) {
        this.dataHasWrongInput = true;
    }
}

sendData(dataForBackEnd) {
        fetch(this.backEndPoint, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({insert: dataForBackEnd})
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                }
            })
            .then()
            .catch((error) => {
                console.error('Foutje', error);
            });
        localStorage.setItem("passwordreset", "yes");
    }
}

const url = window.location;
const token = new URLSearchParams(url.search).get('Authorization');

function getToken() {
    localStorage.setItem("token", token);
    fetch(createNewPassword, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({token: token})
    })
        .then(response => response.json())
        .then(data => {
            showPage(data);
        })
        .catch((error) => {
            console.error('Foutje', error);
        });
    localStorage.clear();
    localStorage.setItem("passwordreset", "yes");
}

function showPage(data) {
    if (data === false) {
        window.location.replace(denied);
    }
}

