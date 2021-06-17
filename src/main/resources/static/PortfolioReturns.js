
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

        localStorage.setItem("historischedatum", dateString);
    }

    // function setHistoricalRate(name) {
    //     let date = localStorage.getItem("historischedatum");
    //     fetch(`/getHistoryValue?assetname=` + name + `&date=` + date, {
    //         method: 'GET',
    //         headers: {
    //             'Accept': 'application/json',
    //         }
    //     })
    //         .then(response => response.json())
    //         .then(data => {localStorage.setItem("historischekoers", data), console.log(data)})
    //     console.log(localStorage.getItem("historischekoers"));
    // }

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
    let portfolioValueNuCash = 0;
    let portfolioValueYesterdayCash = 0;
    let portfolioValueLastWeekCash = 0;
    let portfolioValueLastMonthCash = 0;
    let portfolioValueHistoricalCash = 0;

    let gekozendatum = localStorage.getItem("historischedatum").toString();
    console.log(gekozendatum)
    let token = localStorage.getItem("token");
    fetch(`/portfolio/returns?date=` + gekozendatum, {
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

                    // setHistoricalRate(data[i].asset.abbreviation);
                    // let historischeKoers = localStorage.getItem("historischekoers");
                    // console.log(historischeKoers);
                    portfolio_row += '<tr id="portfolio_row" class="ui-widget-content portfolio_row">';
                    portfolio_row += '<td align="left">' + data[i].asset.name + '</td>';
                    portfolio_row += '<td align="left">' + data[i].asset.abbreviation + '</td>';
                    portfolio_row += '<td>' + '</td>';
                    portfolio_row += ' <td align="center">' + koersStijging['up'](data[i].asset.valueYesterday, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td align="center">' + koersStijging['up'](data[i].asset.valueLastWeek, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td align="center">' + koersStijging['up'](data[i].asset.valueLastMonth, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td align="center">' + koersStijging['up'](data[i].lastTrade, data[i].asset.valueInUsd) + '%' + '</td>';
                    portfolio_row += ' <td align="center">' + koersStijging['up'](data[i].historicRate, data[i].asset.valueInUsd) + '%' + '</td>';

                    portfolio_row += '</tr>';

                    portfolioValueNu = portfolioValueNu + (data[i].asset.valueInUsd * data[i].amount);
                    portfolioValueYesterday = portfolioValueYesterday + (data[i].asset.valueYesterday * data[i].amount);
                    portfolioValueLastWeek = portfolioValueLastWeek + (data[i].asset.valueLastWeek * data[i].amount);
                    portfolioValueLastMonth = portfolioValueLastMonth + (data[i].asset.valueLastMonth * data[i].amount);
                    portfolioValueHistorical = portfolioValueHistorical + (data[i].historicRate * data[i].amount);

                }

            }
            for(i in data) {
                if(data[i].asset.abbreviation === 'USD') {
                    portfolio_row +='<tr>'
                    portfolio_row += '<td align="left">'+'bank saldo  '+'</td>';
                    portfolio_row += '<td align="left">' + data[i].asset.abbreviation + '</td>';
                    portfolio_row += '<td align="center">' +'$'+ data[i].amount + '</td>'
                    portfolio_row +='<tr>'

                    portfolioValueNuCash = portfolioValueNu + data[i].amount;
                    portfolioValueYesterdayCash = portfolioValueYesterday + data[i].amount;
                    portfolioValueLastWeekCash = portfolioValueLastWeek + data[i].amount;
                    portfolioValueLastMonthCash = portfolioValueLastMonth + data[i].amount;
                    portfolioValueHistoricalCash = portfolioValueHistorical + data[i].amount;

                }
            }


    portfolio_row +='<tr>'
    portfolio_row += '<td align="right">'+'totale portefeuille waarde'+'</td>';
    portfolio_row += '<td>' + '</td>';
    portfolio_row += '<td align="center">'+'$' + Math.round(portfolioValueNuCash)+'</td>';
    portfolio_row += '<td align="center">'+'$' + Math.round(portfolioValueYesterdayCash)+'</td>';
    portfolio_row += '<td align="center">'+'$' + Math.round(portfolioValueLastWeekCash)+'</td>';
    portfolio_row += '<td align="center">'+'$' + Math.round(portfolioValueLastMonthCash)+'</td>';
    portfolio_row += '<td>' + '</td>';
    portfolio_row += '<td align="center">'+'$' + Math.round(portfolioValueHistoricalCash)+'</td>';
    portfolio_row += '<td>'+ '   '+'</td>';
    portfolio_row += '<td>'+ '   '+'</td>';
    portfolio_row += '</tr>';
    portfolio_row +='<tr>'
    portfolio_row += '<td align="right">'+'  (stijging/daling zonder cash)    '+'</td>';
    portfolio_row += '<td>'   + '</td>';
    portfolio_row += '<td>' + '</td>';
    portfolio_row += ' <td align="center">'+koersStijging['up'](portfolioValueYesterday, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td align="center">'+koersStijging['up'](portfolioValueLastWeek, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td align="center">'+koersStijging['up'](portfolioValueLastMonth, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += ' <td>' + '</td>';
    portfolio_row += ' <td align="center">'+koersStijging['up'](portfolioValueHistorical, portfolioValueNu)+'%'+ '</td>';
    portfolio_row += '</tr>';

    console.log(portfolio_row);
    $('#selectable > tbody').append(portfolio_row);

        })
    };







