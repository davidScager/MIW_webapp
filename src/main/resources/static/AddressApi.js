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
    document.querySelector('#streetName').value = address.street;
    document.querySelector('#residence').value = address.city;
}
//end checkForAddress