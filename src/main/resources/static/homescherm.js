let bankTable = document.getElementById("LiveRate")
let bankAssets

function loadPageHome() {
    loadLiveRate();
}

function loadLiveRate() {
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

                        tdAsset.innerHTML = `${asset[1].assetName}`
                        tdValueToday.innerHTML = `${asset[1].assetUSDValue}`
                        tdValueYesterday.innerHTML = `${asset[1].getAssetUSDValueYesterday}`

                        row.appendChild(tdAsset)
                        row.appendChild(tdValueToday)
                        row.appendChild(tdValueYesterday)
                        bankTable.appendChild(row)
                    }
                )
            }
        )
}
