let priceButton = true;
let volumeButton = false;
let tradesButton = false;
const tradeTimesLastDay = formatTimestamps(tradeTimesNonFormatLastDay)
const tradeTimesLastWeek = formatTimestamps(tradeTimesNonFormatLastWeek)
const tradeTimesLastMonth = formatTimestamps(tradeTimesNonFormatLastMonth)

const graphText = document.getElementById("graph-text")
const radio1 = document.getElementById("radio1")

const chart = document.getElementById("chart").getContext('2d');

const chartData = {
    labels: [],
    datasets: [{
        label: "",
        data: [],
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
    }]
};

var newChart = new Chart(chart, {
    type: 'line',
    data: chartData,
});
window.addEventListener('load', function() {
    getCryptoObjectsPriceGraph();
});

const tickerValueParagraph = document.getElementById('tickerValue');
const tradeTimeParagraph = document.getElementById('tradeTime');
const highValueParagraph = document.getElementById('highValue');
const midValueParagraph = document.getElementById('midValue');
const lowValueParagraph = document.getElementById('lowValue');
const volumeValueParagraph = document.getElementById('volumeValue');
const tradesValueParagraph = document.getElementById('tradesValue');

function getCryptoObjects() {
    console.log(tickerValueLastDay)
    tickerValueParagraph.textContent = tickerValueLastDay[tickerValueLastDay.length-1];
    let mostRecentTradeTime = tradeTimesLastDay[tradeTimesLastDay.length-1];
    tradeTimeParagraph.textContent = mostRecentTradeTime.toString();
    highValueParagraph.textContent = highValuesLastDay[highValuesLastDay.length - 1].toFixed(2);
    midValueParagraph.textContent = midValuesLastDay[midValuesLastDay.length-1].toFixed(2);
    lowValueParagraph.textContent = lowValuesLastDay[lowValuesLastDay.length-1].toFixed(2);
    volumeValueParagraph.textContent = volumesLastDay[volumesLastDay.length-1].toFixed(2);
    tradesValueParagraph.textContent = tradesDoneLastDay[tradesDoneLastDay.length-1];
 }

getCryptoObjects();

function formatTimestamps(timestamps) {
    const formattedTimestamps = timestamps.map(timestamp => {
        const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false };
        const formattedDateTime = new Date(timestamp).toLocaleString('en-US', options);
        const [date, time] = formattedDateTime.split(', '); // splitting date and time
        const [formattedDate, formattedTime] = date.split('/'); // splitting day, month, and year
        return `${formattedTime}/${formattedDate}/${time}`; // concatenating with a new line in between
    });
    return formattedTimestamps;
}
function getCryptoObjectsPriceGraph(timeInterval) {
    if(timeInterval == 1){
        newChart.data.datasets[0].data = midValuesLastDay;
        newChart.data.labels = tradeTimesLastDay;
        newChart.update();
    }else if(timeInterval == 2){
        newChart.data.datasets[0].data = midValuesLastWeek;
        newChart.data.labels = tradeTimesLastWeek;
        newChart.update();
    }else{
        newChart.data.datasets[0].data = midValuesLastMonth;
        newChart.data.labels = tradeTimesLastMonth;
        newChart.update();
    }
}

function getCryptoObjectsTradesGraph(timeInterval) {
    if(timeInterval == 1){
        newChart.data.datasets[0].data = tradesDoneLastDay;
        newChart.data.labels = tradeTimesLastDay;
        newChart.update();
    }else if(timeInterval == 2){
        newChart.data.datasets[0].data = tradesDoneLastWeek;
        newChart.data.labels = tradeTimesLastWeek;
        newChart.update();
    }else{
        newChart.data.datasets[0].data = tradesDoneLastMonth;
        newChart.data.labels = tradeTimesLastMonth;
        newChart.update();
    }
}

function getCryptoObjectsVolumeGraph(timeInterval) {
    if(timeInterval == 1){
        newChart.data.datasets[0].data = volumesLastDay;
        newChart.data.labels = tradeTimesLastDay;
        newChart.update();
    }else if(timeInterval == 2){
        newChart.data.datasets[0].data = volumesLastWeek;
        newChart.data.labels = tradeTimesLastWeek;
        newChart.update();
    }else{
        newChart.data.datasets[0].data = volumesLastMonth;
        newChart.data.labels = tradeTimesLastMonth;
        newChart.update();
    }
}

function getRadioInput() {
    document.querySelectorAll('input[type="radio"]').forEach(radioButton => {
        radioButton.addEventListener('change', () => {
            const selectedValue = radioButton.value;
            if (priceButton === true) {
                getCryptoObjectsPriceGraph(selectedValue);
            }
            if (volumeButton === true) {
                getCryptoObjectsVolumeGraph(selectedValue);
                newChart.update();
            }
            if (tradesButton === true) {
                getCryptoObjectsTradesGraph(selectedValue);
                newChart.update();
            }
        });
    });
}

document.getElementById('price').addEventListener('click', function() {
    selectOption('Price');
    priceButton = true;
    volumeButton = false;
    tradesButton = false;
    graphText.textContent = "Price Development"

    newChart.data.datasets[0].label = "Price in USD";
    newChart.update();
    getCryptoObjectsPriceGraph(1);
    radio1.setAttribute('checked', 'checked')

    clearInterval(intervalID);
});

document.getElementById('volume').addEventListener('click', function() {
    selectOption('Volume');
    volumeButton = true;
    priceButton = false;
    tradesButton = false;
    graphText.textContent = "Volume Development"
    radio1.setAttribute('checked', 'checked')

    newChart.data.datasets[0].label = "Volume";
    newChart.update();

    getCryptoObjectsVolumeGraph(1);

    clearInterval(intervalID);
    intervalID = setInterval(getRadioInput, 1200);
});

window.addEventListener('load', function() {
    getCryptoObjectsPriceGraph();
    getRadioInput();
});

setTimeout(function(){
    location.reload();
}, 300000);

document.getElementById('tradesDone').addEventListener('click', function() {
    selectOption('Trades');
    tradesButton = true;
    volumeButton = false;
    priceButton = false;
    graphText.textContent = "Trade Development"
    radio1.setAttribute('checked', 'checked')

    newChart.data.datasets[0].label = "Trades done";
    newChart.update();
    getCryptoObjectsTradesGraph(1);

    clearInterval(intervalID);
    intervalID = setInterval(getRadioInput, 1200);
});

function selectOption(option) {
    document.getElementById('dropdown-btn').innerText = option;
}
