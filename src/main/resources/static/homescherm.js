let table = document.getElementById("LiveRate")
let assets
const url = "/transaction/bankassets";

window.onload = loadLiveRate();

function loadLiveRate() {
    fetch(url)
        .then(response => response.json())
        .then(data => {
            assets = data
            Object.entries(assets).forEach((asset) => {
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
                    table.appendChild(row)
                    }
                )
            }
        )
}
