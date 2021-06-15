let myAssetsTable = document.getElementById("MyTable");
let bankTable = document.getElementById("BankTable")
let token = localStorage.getItem("token");
let myAssets
let bankAssets

function newTransaction(){
    let token = localStorage.getItem("token");
    let seller = document.querySelector(`#seller`).value
    console.log(seller)
    let buyer = document.querySelector(`#buyer`).value
    let assetToSell = document.querySelector(`#assetToSell`).value
    let assetToBuy = document.querySelector(`#assetTobuy`).value
    let amount = document.querySelector(`#numberOfAssets`).value
    let triggerValue = document.querySelector(`#valueToBuyOrSellAt`).value
    if (triggerValue === null){
        triggerValue = 0;
    }
    fetch(`http://localhost:8080/transaction/createtransaction`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            "Authorization": token
        },
        body: JSON.stringify({
            seller: seller,
            buyer: buyer,
            numberOfAssets: amount,
            assetSold: assetToSell,
            assetBought: assetToBuy,
            triggerValue: triggerValue,
            username: "iemand",
            transactioncost: 0
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

document.querySelector(`#sellbutton`).addEventListener(`click`,
    function (){
    let selectfotsale = document.querySelector(`#assetToSell`)

    Object.entries(myAssets).forEach((asset) => {
        let opt = document.createElement(`option`)
        opt.value = `${asset[1].abbreviation}`
        opt.innerHTML = `${asset[1].assetName}`
        selectfotsale.appendChild(opt)
    })
        let selectToBuy = document.querySelector(`#assetTobuy`)
        Object.entries(bankAssets).forEach((asset) => {
            let opt = document.createElement(`option`)
            opt.value = `${asset[1].abbriviation}`
            opt.innerHTML = `${asset[1].assetName}`
            selectToBuy.appendChild(opt)
        })
        document.querySelector(`#seller`).value = 0
        document.querySelector('.triggerBuyOrSell').style.display = 'flex';
    })

document.querySelector(`#buybutton`).addEventListener(`click`,
    function (){
        let selectfotsale = document.querySelector(`#assetToSell`)
        Object.entries(bankAssets).forEach((asset) => {
            let opt = document.createElement(`option`)
            opt.value = `${asset[1].abbriviation}`
            opt.innerHTML = `${asset[1].assetName}`
            selectfotsale.appendChild(opt)
        })
        let selectToBuy = document.querySelector(`#assetTobuy`)
        Object.entries(myAssets).forEach((asset) => {
            let opt = document.createElement(`option`)
            opt.value = `${asset[1].abbriviation}`
            opt.innerHTML = `${asset[1].assetName}`
            console.log(opt.value)
            selectToBuy.appendChild(opt)
        })
        document.querySelector(`#buyer`).value = 0
    })

function loadPage(){
    loadMyAssets();
    loadAssetsBank();
}

function loadMyAssets() {
    fetch("http://localhost:8080/transaction/transactionorder", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            "Authorization": token
        },
    })
        .then(response => response.json())
        .then(data => {
                console.log(data)
            myAssets = data
                Object.entries(myAssets).forEach((assets) => {
                    /*if (assets[1].assetName == "Dollar"){
                        let rowDollar = document.createElement(`tr`)
                        let tdBanksaldo = document.createElement(`td`).innerHTML = `Banksaldo`
                        let tdValue = document.createElement(`td`).innerHTML = `${assets[1].avalable} Dollar`
                        rowDolar.appendChild(tdBanksaldo)
                        rowDollar.appendChild(tdValue)
                        bankTable.appendChild(rowDollar)
                    }*/
                        let row = document.createElement(`tr`)
                        let tdAsset = document.createElement(`td`)
                        let tdValueToday = document.createElement(`td`)
                        let tdAmount = document.createElement(`td`)

                        tdAsset.innerHTML = `${assets[1].assetName}`
                        tdAmount.innerHTML = `${assets[1].avalable}`
                        tdValueToday.innerHTML = `${assets[1].assetUSDValue}`

                        row.appendChild(tdAsset)
                        row.appendChild(tdAmount)
                        row.appendChild(tdValueToday)

                        myAssetsTable.appendChild(row)
                    }
                )
            }
        )
}

function loadAssetsBank() {
    fetch("http://localhost:8080/transaction/assetoverviewfrombank")
        .then(response => response.json())
        .then(data => {
                bankAssets = data
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
