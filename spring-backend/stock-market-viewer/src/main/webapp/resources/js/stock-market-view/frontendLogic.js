const url = 'http://localhost:8080'

let priceButton = true;
let volumeButton = false;
let tradesButton = false;

const graphText = document.getElementById("graph-text")
const radio1 = document.getElementById("radio1")
const radio2 = document.getElementById("radio2")
const radio3 = document.getElementById("radio3")

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

// Getting values from db and displaying it in the currency display

const tickerValueParagraph = document.getElementById('tickerValue');
const tradeTimeParagraph = document.getElementById('tradeTime');
const highValueParagraph = document.getElementById('highValue');
const midValueParagraph = document.getElementById('midValue');
const lowValueParagraph = document.getElementById('lowValue');
const volumeValueParagraph = document.getElementById('volumeValue');
const tradesValueParagraph = document.getElementById('tradesValue');

function getCryptoObjects() {
    fetch(url + '/api/cryptoObjects')
        .then(response => response.json())
        .then(data => {
            //ticker value
            tickerValueParagraph.textContent = data[data.length-1].ticker;

            //trade time
            tradeTimeParagraph.textContent = data[data.length-1].date;

            //high value
            const highValue = data[data.length - 1].high;
            highValueParagraph.textContent = parseFloat(highValue).toFixed(2);

            //mid value
            const midValue = data[data.length - 1].mid;
            midValueParagraph.textContent = parseFloat(midValue).toFixed(2);

            //low value
            const lowValue = data[data.length - 1].low;
            lowValueParagraph.textContent = parseFloat(lowValue).toFixed(2);

            //volume value
            const volumeValue = data[data.length - 1].volume;
            volumeValueParagraph.textContent = parseFloat(volumeValue).toFixed(2);

            //trades value
            tradesValueParagraph.textContent = data[data.length - 1].tradesDone;

        })
        .catch(error => console.error('Error fetching crypto data:', error));
}
getCryptoObjects();
setInterval(getCryptoObjects, 1200);

function getCryptoObjectsPriceGraph(timeInterval) {
    fetch(url + '/api/cryptoObjectsPriceGraph?timeInterval=' + timeInterval)
        .then(response => response.json())
        .then(data => {
            const prices = data.map(item => item.mid);
            const times = data.map(item => item.time);

            // Update chart data
            newChart.data.datasets[0].data = prices;
            newChart.data.labels = times;
            newChart.update();

        })
        .catch(error => console.error('Error fetching crypto data:', error));
}

function getCryptoObjectsVolumeGraph(timeInterval) {
    fetch(url + '/api/cryptoObjectsVolumeGraph?timeInterval=' + timeInterval)
        .then(response => response.json())
        .then(data => {
            const volume = data.map(item => item.volume);
            const times = data.map(item => item.time);

            // Update chart data
            newChart.data.datasets[0].data = volume;
            newChart.data.labels = times;
            newChart.update();

        })
        .catch(error => console.error('Error fetching crypto data:', error));
}

function getCryptoObjectsTradesGraph(timeInterval) {
    fetch(url + '/api/cryptoObjectsTradesGraph?timeInterval=' + timeInterval)
        .then(response => response.json())
        .then(data => {
            const trades = data.map(item => item.trades);
            const times = data.map(item => item.time);

            // Update chart data
            newChart.data.datasets[0].data = trades;
            newChart.data.labels = times;
            newChart.update();

        })
        .catch(error => console.error('Error fetching crypto data:', error));
}

function getRadioInput() {
    document.querySelectorAll('input[type="radio"]').forEach(radioButton => {
        radioButton.addEventListener('change', () => {
            const selectedValue = radioButton.value;
            if (priceButton === true) {
                getCryptoObjectsPriceGraph(selectedValue);
                newChart.update();
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

let intervalID = null;
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
    intervalID = setInterval(getRadioInput, 1200);
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
