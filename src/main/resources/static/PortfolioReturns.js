

    function setHeights() {
    var new_height = $(window).height();
    new_height = new_height - 400
    $(".scroll_tbody").height(new_height);
}


    function loadPortfolioReturns() {
    setHeights();

    $('#selectable > tbody').empty();
    let portfolio_row = '';
    let koersStijging = { 'up': function( x, y) { return Math.round(((y-x)/ x) * 100)}};
    let portfolioValueNu = 0;
    let portfolioValueYesterday = 0;
    let portfolioValueLastWeek = 0;
    let portfolioValueLastMonth = 0;

    let token = localStorage.getItem("token");
    fetch(`http://localhost:8080/portfolioreturns`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            "Authorization": "Bearer " + token,
        },
    })
        .then(response => response.json())
        .then(data => {
            for (i in data) {
                console.log( "json row");
                console.log(JSON.stringify(data[i]));
                if(data[i].asset.abbreviation !== 'USD') {
                    portfolio_row += '<tr id="asset_row" class="ui-widget-content portfolio_row">';
                    portfolio_row += '<td>' + data[i].asset.name + '</td>';
                    portfolio_row += '<td>' + data[i].asset.abbreviation + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].asset.valueYesterday, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].asset.valueLastWeek, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].asset.valueLastMonth, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].lastTrade, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += '</tr>';

                    portfolioValueNu = portfolioValueNu + (data[i].asset.valueInUsd * data[i].amount);
                    portfolioValueYesterday = portfolioValueYesterday + (data[i].asset.valueYesterday * data[i].amount);
                    portfolioValueLastWeek = portfolioValueLastWeek + (data[i].asset.valueLastWeek * data[i].amount);
                    portfolioValueLastMonth = portfolioValueLastMonth + (data[i].asset.valueLastMonth * data[i].amount);
                }
                sortTable()

            }
            for(i in data) {
                if(data[i].asset.abbreviation == 'USD') {
                    portfolio_row +='<tr>'
                    portfolio_row += '<td>'+'bank saldo  '+'</td>';
                    portfolio_row += '<td>' + data[i].asset.abbreviation + '</td>';
                    portfolio_row += '<td>' + data[i].amount + '</td>'
                    portfolio_row +='<tr>'

                    portfolioValueYesterday = portfolioValueYesterday + data[i].amount;
                    portfolioValueLastWeek = portfolioValueLastWeek + data[i].amount;
                    portfolioValueLastMonth = portfolioValueLastMonth + data[i].amount;
                }
            }
        })


    portfolio_row +='<tr>'
    portfolio_row += '<td>'+'totale portefeuille  '+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueNu)+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueYesterday)+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueLastWeek)+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueLastMonth)+'</td>';
    portfolio_row += '<td>'+ '   '+'</td>';
    portfolio_row += '<td>'+ '   '+'</td>';
    portfolio_row += '</tr>';
    portfolio_row +='<tr>'
    portfolio_row += '<td>'+'  (stijging/daling)    '+'</td>';
    portfolio_row += '<td>'+ '       '+'</td>';
    portfolio_row += ' <td>'+koersStijging['up'](portfolioValueYesterday, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td>'+koersStijging['up'](portfolioValueLastWeek, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td>'+koersStijging['up'](portfolioValueLastMonth, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += '</tr>';

    console.log(portfolioValueNu);
    console.log(portfolioValueLastWeek);
    console.log(portfolio_row);
    $('#selectable > tbody').append(portfolio_row);
};


        // Toevoegen requestParam voor datum, en welke actie moet er worden ondernomen met de data.
        // function getReturnFromGivenDate() {
        //     fetch(`http://localhost:8080/getHistoryValue`, {
        //         method: 'GET',
        //         headers: {
        //
        //         },
        //     })
        //         .then(response => response.json())
        //         .then()
        //
        // }


