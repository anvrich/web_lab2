<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Проверка попадания точки</title>
    <link rel="stylesheet" href="main.css">
</head>
<body>
<div class="form-container">
    <header>
        <h1>Проверка попадания точки в график</h1>
        <h2>Собитов Анвархон Акмалович <span>P3210</span></h2>
        <h2>Вариант - <span>5135318</span></h2>
    </header>
    <div class="form-graph-container">
        <div id="errorMsgDiv"></div>
        <form id="myForm">
            <div class="form-group">
                <h3>X</h3>
                <div class="form-values">
                    <label><input type="checkbox" class="x" value="-5">-5</label>
                    <label><input type="checkbox" class="x" value="-4">-4</label>
                    <label><input type="checkbox" class="x" value="-3">-3</label>
                    <label><input type="checkbox" class="x" value="-2">-2</label>
                    <label><input type="checkbox" class="x" value="-1">-1</label>
                    <label><input type="checkbox" class="x" value="0">0</label>
                    <label><input type="checkbox" class="x" value="1">1</label>
                    <label><input type="checkbox" class="x" value="2">2</label>
                    <label><input type="checkbox" class="x" value="3">3</label>
                </div>
            </div>

            <div class="form-group">
                <h3>Y</h3>
                <div class="form-values">
                    <input type="text" name="y" placeholder="от -5 до 3">
                </div>
            </div>

            <div class="form-group">
                <h3>R</h3>
                <div class="form-values">
                    <input type="text" name="r" placeholder="от 1 до 4">
                </div>
            </div>
            <div class="form-actions">
                <button class="checkButton buttonLetter" type="submit">Выполнить проверку</button>
            </div>
    </form>
        <div id="graff">
        <svg height="300" width="300">
            <!--                Оси-->
            <line stroke="black" x1="150" x2="150" y1="300" y2="0"></line>
            <text x="160" y="12">Y</text>
            <line stroke="black" x1="0" x2="300" y1="150" y2="150"></line>
            <text x="286" y="135">X</text>
            <!--                Промежутки-->
            <line stroke="black" x1="147" x2="153" y1="30" y2="30"></line>
            <text x="160" y="35">R</text>
            <line stroke="black" x1="147" x2="153" y1="90" y2="90"></line>
            <text x="160" y="95">R/2</text>
            <line stroke="black" x1="147" x2="153" y1="210" y2="210"></line>
            <text x="160" y="215">-R/2</text>
            <line stroke="black" x1="147" x2="153" y1="270" y2="270"></line>
            <text x="160" y="275">-R</text>
            <line stroke="black" x1="30" x2="30" y1="147" y2="153"></line>
            <text x="23" y="140">-R</text>
            <line stroke="black" x1="90" x2="90" y1="147" y2="153"></line>
            <text x="83" y="140">-R/2</text>
            <line stroke="black" x1="210" x2="210" y1="147" y2="153"></line>
            <text x="202" y="140">R/2</text>
            <line stroke="black" x1="270" x2="270" y1="147" y2="153"></line>
            <text x="264" y="140">R</text>
            <!--                Стрелочки-->
            <line stroke="black" x1="300" x2="290" y1="150" y2="146"></line>
            <line stroke="black" x1="300" x2="290" y1="150" y2="154"></line>
            <line stroke="black" x1="150" x2="146" y1="0" y2="10"></line>
            <line stroke="black" x1="150" x2="154" y1="0" y2="10"></line>
            <circle id="point" cx="150" cy="150" r="5" fill="green"></circle>

            <polygon fill="dodgerblue" fill-opacity="0.5" points="150 150, 150 90, 270 90, 270 150"></polygon>
            <polygon fill="dodgerblue" fill-opacity="0.5" points="150 150, 90 150, 150 210"></polygon>
            <path fill="dodgerblue" fill-opacity="0.5" d="M 150 150 L 90 150 A 60 60 0 0 1 150 90 Z"></path>
        </svg>
    </div>
    </div>
    <div class="results-container">
    <jsp:include page="table.jsp"/>
    <input type="button" id="clearButton" value="Очистить таблицу">
    </div>
</div>
<script type="module" src="JavaScript/main.js" defer></script>
</body>
</html>
