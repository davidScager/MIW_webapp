

function resetPassword() {
    console.log("hoi")
    document.querySelector('#email').addEventListener('focusout', checkEmailPart());
}

function checkEmailPart(){
    let regex = new RegExp(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i);

    let email = document.querySelector('#email').value
    console.log('email is valide: ' + regex.test(email));

    if(regex.test(email)) {
        // var data = { postcode: postcode, nr: huisnummer};
        // stuur data als form data ipv json, want backend accepteert alleen form
        let formData = `${email}`
        console.log(formData);
        window.location.replace("http://localhost:8080/confirmed.html");

        fetch("http://localhost:8080/reset/resetpassword", {
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
            .then()
            .catch((error) => {
                console.error('Foutje', error);
            });
    }
}