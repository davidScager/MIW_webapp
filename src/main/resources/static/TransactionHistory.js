
function setHeights(){
    var new_height = $( window ).height();
    new_height = new_height -400
    $(".scroll_tbody").height(new_height);
}



function loadTransactionOverview() {
    setHeights();

    $('#selectable > tbody').empty();

    var transaction_row = '';
    var koersStijging = { 'up': function( x, y) { return Math.round(((y-x)/ x) * 100)}};

    let token = localStorage.getItem("token");
    fetch(`http://localhost:8080/transaction/transactionhistory`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            "Authorization": "Bearer " + token,
        }
    })
        .then(response => response.json())
        .then(data => {
            for (i in data) {
                console.log( "json row");
                console.log(JSON.stringify(data[i]));
                transaction_row += '<tr id="asset_row" class="ui-widget-content transaction_row">';
                transaction_row += ' <td>'+data[i].asset.name+'</td>';
                transaction_row += ' <td>'+data[i].asset.abbreviation+'</td>';
                transaction_row += ' <td>'+data[i].numberOfAssetsTraded+'</td>';
                transaction_row += ' <td>'+data[i].assetTransactionRate+'</td>';
                transaction_row += ' <td>'+data[i].timestamp+'</td>';
                transaction_row += ' <td>'+data[i].aankoopVerkoop+'</td>';
                transaction_row += ' <td>'+koersStijging['up'](data[i].assetTransactionRate, data[i].asset.valueInUsd)+'%'+ '</td>';
                transaction_row += '</tr>';

            }
            console.log(transaction_row);
            $('#selectable > tbody').append(transaction_row);
        })
}
