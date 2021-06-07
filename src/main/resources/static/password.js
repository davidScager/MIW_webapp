function initiateReset() {
    const password = new Password(new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i),
        "http://localhost:8080/reset/resetpassword", "http://localhost:8080/confirmed.html", ["email"]
    )
    password.resetPassword()
}

class Password {
    dataHasWrongInput
    mailRegex
    backEndPoint
    websiteRedirect
    arrayOfInserts

    constructor(mailRegex, backEndPoint, websiteRedirect, arrayOfInserts) {

        this.mailRegex = mailRegex;
        this.backEndPoint = backEndPoint;
        this.websiteRedirect = websiteRedirect;
        this.arrayOfInserts = arrayOfInserts;
        this.dataHasWrongInput = false;
    }

resetPassword() {

    for (var i=0; i < this.arrayOfInserts.length; i++) {
       document.querySelector('#' + this.arrayOfInserts[i]).addEventListener('focusout', this.checkInserts(this.arrayOfInserts[i]));
       console.log(this.dataHasWrongInput)
    }

    if (this.dataHasWrongInput === false) {
        this.sendData(document.querySelector('#'+(this.arrayOfInserts)[0]).value, this.backEndPoint, this.websiteRedirect);
    }
}

//algemene functie om velden in formulieren te checken op legitimiteit
checkInserts(insert) {
    let insertToCheck = document.querySelector('#'+insert).value
    console.log('insert is valide: ' + this.mailRegex.test(insertToCheck)+ insertToCheck);
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
            body: JSON.stringify({email: dataForBackEnd})
        })
            .then(response => {
                console.log(response)
                return response.json()
            })
            .then(window.location.replace(this.websiteRedirect))
            .catch((error) => {
                console.error('Foutje', error);
            });
    }
}