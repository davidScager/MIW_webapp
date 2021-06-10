
function newTransaction(){
    let seller = document.querySelector(`#seller`).value
    let buyer = document.querySelector(`#buyer`).value
    let assetToSell = document.querySelector(`#assetToSell`).value
    let assetToBuy = document.querySelector(`#assetTobuy`).value
    let amount = document.querySelector(`#numberOfAssets`).value
    fetch(`http://localhost:8080/transaction/createntransaction?seller=${seller}&
            buyer=${buyer}&numberOfAssets=${amount}&assetSold=${assetToSell}&assetBought=${assetToBuy}`)
}

function transactionCost(){
    let assetToBuy = document.querySelector(`#assetTobuy`).value
    let amount = document.querySelector(`#numberOfAssets`).value
    fetch(`http://localhost:8080/transaction/transactioncost?numberOfAssets=${amount}&assetBought=${assetToBuy}`)
        .then(response => response.json())
        .then(data => {
            console.log(data)

            document.getElementById("#transactioncostfield").value = data
        })
}

function loadPage(){
    loadMyAssets();
    loadAssetsBank();
}

function loadMyAssets() {
    let bankTable = document.getElementById("MyTable")
    fetch("http://localhost:8080/transaction/myavalableassetstosell?portfolioId=102")
        .then(response => response.json())
        .then(data => {
                console.log(data)
                Object.entries(data).forEach(([k, v]) => {
                        console.log(`${k}`)
                        let row = document.createElement(`tr`)
                        let tdAsset = document.createElement(`td`)
                        let tdValueToday = document.createElement(`td`)
                        let tdAmount = document.createElement(`td`)

                        tdAsset.innerHTML = `${k[1].name}`
                        tdAmount.innerHTML = `${v}`
                        tdValueToday.innerHTML = `${k[1].valueInUsd}`

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