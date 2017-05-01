// Stock add
$('#addStock').click((e) => {
    let action;
    if($('#buy').hasClass("active")) {
        action = "buy";
    } else {
        action = "sell";
    }
    // Get timestamp
    let time = + new Date();
    let ticker = $('#ticker').val();
    let shares = $('#shares').val();
    let port = $('.port.active')[0].innerText;
    let data = {
        port: port,
        time: time,
        action: action,
        ticker: ticker,
        shares: shares
    }
    let valid = true; // TODO actually validate
    if (valid) {
        $.post('/stockAction', data, (res) => {
            let resData = JSON.parse(res);
            if (resData["success"]) {
                $('#addStockModal').modal('hide');
                getStocks($('.port.active')[0].innerText);
            } else {
                $('#stockError')[0].innerText = "ERROR: " + resData["error"];
            }
        });
    } else {
        // TODO: Show validation error
    }
    return false;
});

$(function () {
  $('#pastAction').change(function () {                
     $('#time').toggle(this.checked);
  }).change(); //ensure visible state matches initially
});
