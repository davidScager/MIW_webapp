
    function setHeights() {
    var new_height = $(window).height();
    new_height = new_height - 400
    $(".scroll_tbody").height(new_height);
}

    function setNewDate() {
        let year  = new Date().getFullYear();
        let month = new Date().getMonth();
        let day   = new Date().getDate();
        let date  = new Date(year - 1, month, day);
        storeDate(date);
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
    let portfolioValueHistorical = 0;

    let token = localStorage.getItem("token");
    fetch(`http://localhost:8080/portfolioreturns`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            "Authorization": "Bearer " + token,
        }
    })
        .then(response => response.json())
        .then(data => {
            for (i in data) {
                console.log( "json row");
                console.log(JSON.stringify(data[i]));

                if(data[i].asset.abbreviation !== 'USD') {


                    getHistoricalRate(data[i].asset.abbreviation);
                    let historischeKoers = localStorage.getItem("historischekoers");
                    // localStorage.removeItem("historischekoers");
                    console.log(historischeKoers);
                    portfolio_row += '<tr id="portfolio_row" class="ui-widget-content portfolio_row">';
                    portfolio_row += '<td>' + data[i].asset.name + '</td>';
                    portfolio_row += '<td>' + data[i].asset.abbreviation + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].asset.valueYesterday, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].asset.valueLastWeek, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].asset.valueLastMonth, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](data[i].lastTrade, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td>' + koersStijging['up'](historischeKoers, data[i].asset.valueInUsd) + '%' + '</td>';

                    portfolio_row += '</tr>';

                    portfolioValueNu = portfolioValueNu + (data[i].asset.valueInUsd * data[i].amount);
                    portfolioValueYesterday = portfolioValueYesterday + (data[i].asset.valueYesterday * data[i].amount);
                    portfolioValueLastWeek = portfolioValueLastWeek + (data[i].asset.valueLastWeek * data[i].amount);
                    portfolioValueLastMonth = portfolioValueLastMonth + (data[i].asset.valueLastMonth * data[i].amount);
                    portfolioValueHistorical = portfolioValueHistorical + (historischeKoers * data[i].amount);

                }

            }
            for(i in data) {
                if(data[i].asset.abbreviation === 'USD') {
                    portfolio_row +='<tr>'
                    portfolio_row += '<td>'+'bank saldo  '+'</td>';
                    portfolio_row += '<td>' + data[i].asset.abbreviation + '</td>';
                    portfolio_row += '<td>' + data[i].amount + '</td>'
                    portfolio_row +='<tr>'

                    portfolioValueYesterday = portfolioValueYesterday + data[i].amount;
                    portfolioValueLastWeek = portfolioValueLastWeek + data[i].amount;
                    portfolioValueLastMonth = portfolioValueLastMonth + data[i].amount;
                    portfolioValueHistorical = portfolioValueHistorical + data[i].amount;

                }
            }


    portfolio_row +='<tr>'
    portfolio_row += '<td>'+'totale portefeuille  '+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueNu)+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueYesterday)+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueLastWeek)+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueLastMonth)+'</td>';
    portfolio_row += '<td>'+'$' + Math.round(portfolioValueHistorical)+'</td>';
    portfolio_row += '<td>'+ '   '+'</td>';
    portfolio_row += '<td>'+ '   '+'</td>';
    portfolio_row += '</tr>';
    portfolio_row +='<tr>'
    portfolio_row += '<td>'+'  (stijging/daling)    '+'</td>';
    portfolio_row += '<td>'+ '       '+'</td>';
    portfolio_row += ' <td>'+koersStijging['up'](portfolioValueYesterday, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td>'+koersStijging['up'](portfolioValueLastWeek, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td>'+koersStijging['up'](portfolioValueLastMonth, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td>'+koersStijging['up'](portfolioValueHistorical, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += '</tr>';

    console.log(portfolioValueNu);
    console.log(portfolioValueLastWeek);
    console.log(portfolio_row);
    $('#selectable > tbody').append(portfolio_row);

        })
    };

    function computeDate(){
        let date = new Date(document.querySelector("#historie").value);
        storeDate(date);

        loadPortfolioReturns();
    }

    function storeDate(date){
        let year  = date.getFullYear();
        let month = (date.getMonth() + 1).toString().padStart(2, "0");
        let day   = date.getDate().toString().padStart(2, "0");
        let dateString = '' +day + '-' + month + '-' + year + '';

        localStorage.setItem("hisotrischedatum", dateString);
    }

    function getHistoricalRate(name) {
        let assetCode = name;
        let date = localStorage.getItem("hisotrischedatum");
    fetch(`http://localhost:8080/getHistoryValue?assetname=` + assetCode + `&date=` + date, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {localStorage.setItem("historischekoers", data)})
    }



