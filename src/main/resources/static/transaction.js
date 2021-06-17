let myAssetsTable = document.getElementById("MyTable");
let bankTable = document.getElementById("BankTable")
let token = localStorage.getItem("token");
let myAssets
let bankAssets
let userId



function newTransaction(seller, buyer) {
    console.log(userId)
    console.log(userId)
    let token = localStorage.getItem("token");
    let assetToSell = document.querySelector(`#assetToSell`).value
    let assetToBuy = document.querySelector(`#assetTobuy`).value
    let amount = document.querySelector(`#numberOfAssets`).value
    let triggerValue = document.querySelector(`#valueToBuyOrSellAt`).value
    if (triggerValue === null) {
        triggerValue = 0;
    } else if (triggerValue !== null){
        if (seller === 0){}
        seller == 1
    }
    if (amount === null) {
        let amountfield = document.querySelector(`#numberOfAssets`)
        amountfield.style.boxShadow = "0 0 3px #CC0000"
        amountfield.value = `Vult dit veld aub`
    }
    let transactiedata = {
        seller: seller,
        buyer: buyer,
        numberOfAssets: amount,
        assetSold: assetToSell,
        assetBought: assetToBuy,
        triggerValue: triggerValue,
        username: "iemand",
        transactioncost: 0
    }

    fetch(`http://localhost:8080/transaction/createtransaction`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            "Authorization": token
        },
        body: JSON.stringify(transactiedata)
    })}

document.getElementById(`executeBuyTransaction`).addEventListener(`click`,
    function (){
    let sellerbox = document.querySelector(`#seller`)
        let seller = sellerbox.options[sellerbox.selectedIndex].value
    let buyer = userId;
    newTransaction(seller, buyer)
    })

document.getElementById(`executeSellTransaction`).addEventListener(`click`,
    function (){
    let seller = userId;
    let buyer = 1;
    newTransaction(seller, buyer)
    })

document.getElementById('transactioncost').addEventListener(`click`,
    function () {
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
    function () {
        document.querySelector('.bg-model').style.display = 'none';
    })

document.querySelector(`#buybutton`).addEventListener(`click`,
    function () {
        let selectToBuy = document.querySelector(`#assetTobuy`)
        let selectfotsale = document.querySelector(`#assetToSell`)

        Object.entries(myAssets).forEach((asset) => {
            let opt = document.createElement(`option`)
            opt.value = `${asset[1].abbreviation}`
            opt.innerHTML = `${asset[1].assetName}`
            selectToBuy.appendChild(opt)
        })


        Object.entries(bankAssets).forEach((asset) => {
            let opt = document.createElement(`option`)
            opt.value = `${asset[1].abbreviation}`
            opt.innerHTML = `${asset[1].assetName}`
            selectfotsale.appendChild(opt)
        })
        document.querySelector('.sell').style.display = 'block';
        document.querySelector('.buyTransaction').style.display = 'block';
        document.querySelector(`.sellTransaction`).style.display = 'none';

        buyorsell()
    })

function buyorsell() {
    document.querySelector('.buyOrSell').style.display = 'none';
    document.querySelector('.titeltransaction').style.display = 'block';
    document.querySelector('.transactioninpufields').style.display = 'block';
}

document.querySelector(`#sellbutton`).addEventListener(`click`,
    function () {
    let selectfotsale = document.querySelector(`#assetToSell`)
    let selectToBuy = document.querySelector(`#assetTobuy`)
        Object.entries(bankAssets).forEach((asset) => {
            let opt = document.createElement(`option`)
            opt.value = `${asset[1].abbreviation}`
            opt.innerHTML = `${asset[1].assetName}`
            selectToBuy.appendChild(opt)
        })

        Object.entries(myAssets).forEach((asset) => {
            let opt = document.createElement(`option`)
            opt.value = `${asset[1].abbreviation}`
            opt.innerHTML = `${asset[1].assetName}`
            console.log(opt.value)
            selectfotsale.appendChild(opt)
        })
        document.querySelector('.triggerBuyOrSell').style.display = 'inline';
        document.querySelector(`#buyer`).value = 1;
        document.querySelector(`.sellTransaction`).style.display = 'block';
        document.querySelector('.buyTransaction').style.display = 'none';

        buyorsell()
    })

function loadPage() {
    loadMyAssets();
    loadAssetsBank();
    getUserID()
}

function getUserID() {
    fetch("http://localhost:8080/transaction/userid", {
        method: `GET`,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            "Authorization": token
        }
    }).then(response => response.json()).then(data => {
        Object.entries(data).forEach(([k, v]) => {
            userId = v;
        })
        console.log(userId.value)
    })
}

function loadMyAssets() {
    fetch("http://localhost:8080/transaction/myassets", {
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
    fetch("http://localhost:8080/transaction/bankassets")
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
