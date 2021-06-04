function resetPassword() {
    let mailRegex = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i);
    let backEndPoint = "http://localhost:8080/reset/resetpassword";
    let websiteRedirect = "http://localhost:8080/confirmed.html";
    let arrayOfInserts = ["email"];

    let dataHasWrongInput = false;
    for (var i=0; i < arrayOfInserts.length; i++) {
       dataHasWrongInput = (document.querySelector('#' + arrayOfInserts[i]).addEventListener('focusout', checkInserts(arrayOfInserts[i], mailRegex, dataHasWrongInput)));
    }

    if (dataHasWrongInput === false) {
        sendData(insertToCheck, backEndPoint, websiteRedirect);
    }

}


//algemene functie om velden in formulieren te checken op legitimiteit
function checkInserts(insert, regex) {
    let insertToCheck = document.querySelector('#'+insert).value
    console.log('insert is valide: ' + regex.test(insertToCheck)+ insertToCheck);
    if (!regex.test(insertToCheck)) {
        //warning
        return true;
    }
}

function sendData(dataForBackEnd, backEndPoint, websiteRedirect) {
    let formData = `${dataForBackEnd}`
    fetch(backEndPoint, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)  // moet worden omgezet naar een string
    })
        .then(response => {
            console.log(response)
            return response.json()
        })
        .then(window.location.replace(websiteRedirect))
        .catch((error) => {
            console.error('Foutje', error);
        });
}