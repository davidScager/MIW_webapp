
function newTransaction(){
    let seller = document.querySelector(`#seller`).value
    console.log(seller)
    let buyer = document.querySelector(`#buyer`).value
    let assetToSell = document.querySelector(`#assetToSell`).value
    let assetToBuy = document.querySelector(`#assetTobuy`).value
    let amount = document.querySelector(`#numberOfAssets`).value
    fetch(`http://localhost:8080/transaction/createtransaction`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            seller: seller,
            buyer: buyer,
            numberOfAssets: amount,
            assetSold: assetToSell,
            assetBought: assetToBuy
        })
    })
        .then(response => response.json())
        .then(data => {
            alert(`succesvolle transactie \n${data}`)
        })
}

document.getElementById('transactioncost').addEventListener(`click`,
        function (){
            let assetToBuy = document.querySelector(`#assetTobuy`).value
            let amount = document.querySelector(`#numberOfAssets`).value
            fetch(`http://localhost:8080/transaction/transactioncost?numberOfAssets=${amount}&assetBought=${assetToBuy}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    document.querySelector(`.bg-model`).style.display = `block`;
                    document.getElementById("transactioncostfield").value = data
                });
        });
document.querySelector('.close').addEventListener('click',
    function (){
    document.querySelector('.bg-model').style.display = 'none';
    })

function loadPage(){
    loadMyAssets();
    loadAssetsBank();
}

function loadMyAssets() {
    let bankTable = document.getElementById("MyTable")
    let token = localStorage.getItem("token");
    fetch("http://localhost:8080/transaction/transactionorder", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            "Authorization": "Bearer " + token,
        },
    })
        .then(response => response.json())
        .then(data => {
                console.log(data)
                Object.entries(data).forEach((assets) => {
                        let row = document.createElement(`tr`)
                        let tdAsset = document.createElement(`td`)
                        let tdValueToday = document.createElement(`td`)
                        let tdAmount = document.createElement(`td`)

                        tdAsset.innerHTML = `${assets[1].AssetName}`
                        tdAmount.innerHTML = `${assets[1].available}`
                        tdValueToday.innerHTML = `${assets[1].assetUSDValue}`

                        row.appendChild(tdAsset)
                        row.appendChild(tdAmount)
                        row.appendChild(tdValueToday)

                        bankTable.appendChild(row)
                    }
                )
            }
        )
}

function loadAssetsBank() {
    let bankTable = document.getElementById("BankTable")
    fetch("http://localhost:8080/transaction/assetoverviewfrombank")
        .then(response => response.json())
        .then(data => {
                let bankAssets = data
                Object.entries(bankAssets).forEach((asset) => {
                        console.log(`${asset}`)
                        let row = document.createElement(`tr`)
                        let tdAsset = document.createElement(`td`)
                        let tdValueToday = document.createElement(`td`)
                        let tdValueYesterday = document.createElement(`td`)
                        let tdAmount = document.createElement(`td`)
                        tdAsset.innerHTML = `${asset[1].assetName}`
                        tdValueToday.innerHTML = `${asset[1].assetUSDValue}`
                        tdValueYesterday.innerHTML = `${asset[1].getAssetUSDValueYesterday}`
                        tdAmount.innerHTML = `${asset[1].avalable}`
                        row.appendChild(tdAsset)
                        row.appendChild(tdValueToday)
                        row.appendChild(tdValueYesterday)
                        row.appendChild(tdAmount)
                        bankTable.appendChild(row)
                    }
                )
            }
        )
}
