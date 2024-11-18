<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chart Stack</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/stock-market-view/stylesheet.css"></c:url>">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="stack-container">
    <p class="stack-text">Stack</p>
</div>
<div class="chart-container">
    <p class="chart-text">Chart</p>
</div>

<div class="main-container">
    <div class="currency-container">
        <div class="currency-text-container">
            <p class="currency-text">Currency</p>
        </div>
        <div class="top-information-container">
            <p class="information-text">Information</p>
            <p class="value-text">Value</p>
        </div>
        <div class="information-container">
            <p class="information-text">Ticker</p>
            <p id="tickerValue" class="value-text">BTC</p>
        </div>
        <div class="information-container">
            <p class="information-text">Trade Time</p>
            <p id="tradeTime" class="value-text">BTC</p>
        </div>
        <div class="information-container">
            <p class="information-text">High Price</p>
            <p id="highValue" class="value-text">BTC</p>
        </div>
        <div class="information-container">
            <p class="information-text">Mid Price</p>
            <p id="midValue" class="value-text">BTC</p>
        </div>
        <div class="information-container">
            <p class="information-text">Low Price</p>
            <p id="lowValue" class="value-text">BTC</p>
        </div>
        <div class="information-container">
            <p class="information-text">Volume</p>
            <p id="volumeValue" class="value-text">BTC</p>
        </div>
        <div class="information-container">
            <p class="information-text">Trades Done</p>
            <p id="tradesValue" class="value-text">BTC</p>
        </div>
    </div>
    <div class="graph-container">
        <div class="graph-text-container">
            <div class="dropdown">
                <button class="dropdown-btn" id="dropdown-btn">Dropdown</button>
                <div class="dropdown-content">
                    <button class="dropdown-content-btn" id="price">Price</button>
                    <button class="dropdown-content-btn" id="volume">Volume</button>
                    <button class="dropdown-content-btn" id="tradesDone">Trades</button>
                </div>
            </div>
            <p id="graph-text" class="graph-text">Development Graph</p>
            <div class="radio-buttons">
                <label class="radio-button">
                    <input id="radio1" type="radio" name="option" value="1">
                    <span class="radio-label">1D</span>
                </label>
                <label class="radio-button">
                    <input id="radio2" type="radio" name="option" value="2">
                    <span class="radio-label">7D</span>
                </label>
                <label class="radio-button">
                    <input id="radio3" type="radio" name="option" value="3">
                    <span class="radio-label">1M</span>
                </label>
            </div>
        </div>
        <div class="graph">
            <canvas id="chart"></canvas>
        </div>
    </div>
</div>

<script href="<c:url value="/resources/css/stock-market-view/stylesheet.css"></c:url>"></script>
</body>
</html>