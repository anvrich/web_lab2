document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("myForm");
    const errorMsgDiv = document.getElementById("errorMsgDiv");
    const tableBody = document.querySelector(".jsTableRes");
    const clearButton = document.getElementById("clearButton");
    const svg = document.querySelector("svg");


    const yInput = form.querySelector("input[name=y]");
    const rInput = form.querySelector("input[name=r]");

    yInput.addEventListener("input", () => {
        yInput.value = yInput.value.replace(",", ".");
    })

    rInput.addEventListener("input", () => {
        rInput.value = rInput.value.replace(',', '.');
    });

    if (errorMsgDiv.innerHTML.trim() === "") {
        errorMsgDiv.style.backgroundColor = "#e6ffe6";
        errorMsgDiv.style.borderColor = "green";
    }

    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        errorMsgDiv.innerHTML = "";
        updateErrorMsgStyle();

        const xValues = Array.from(document.querySelectorAll(".x:checked"))
            .map(input => parseFloat(input.value));
        const yValue = parseFloat(yInput.value.replace(',', '.'));  // Преобразуем при отправке
        const rValue = parseFloat(rInput.value.replace(',', '.'));

        if (xValues.length === 0) {
            showError("Необходимо выбрать хотя бы одно значение X.");
            return;
        }

        if (isNaN(yValue) || yValue < -5 || yValue > 3) {
            showError("Y должно быть числом в диапазоне от -5 до 3.");
            return;
        }

        if (isNaN(rValue) || rValue < 1 || rValue > 4) {
            showError("R должно быть числом в диапазоне от 1 до 4.");
            return;
        }

        for (const x of xValues) {
            const params = `x=${x}&y=${yValue}&r=${rValue}`;
            try {
                const response = await fetch(`/lab2-1.0-SNAPSHOT/controller?${params}`, { method: "GET" });
                if (response.ok) {
                    const result = await response.json();
                    addRowToTable(result);
                    plotPoint(result.x, result.y, result.r, result.hit);
                } else {
                    showError("Ошибка на сервере. Попробуйте позже.");
                }
            } catch (error) {
                showError("Не удалось отправить запрос. Проверьте соединение.");
            }
        }
    });

    clearButton.addEventListener("click", async () => {
        try {
            const response = await fetch("/lab2-1.0-SNAPSHOT/controller?clear=true", { method: "GET" });
            if (response.ok) {
                tableBody.innerHTML = "";
                clearGraphPoints();
            } else {
                showError("Не удалось очистить таблицу.");
            }
        } catch (error) {
            showError("Ошибка при очистке таблицы.");
        }
    });

    svg.addEventListener("click", async (event) => {
        const rValue = parseFloat(rInput.value.replace(',', '.'));

        if (!rValue || isNaN(rValue) || rValue < 1 || rValue > 4) {
            showError("Задайте радиус (R) в диапазоне от 1 до 4 перед кликом по графику.");
            return;
        }

        const rect = svg.getBoundingClientRect();
        const scale = 120 / rValue;

        const x = ((event.clientX - rect.left - 150) / scale).toFixed(2);
        const y = (-(event.clientY - rect.top - 150) / scale).toFixed(2);

        try {
            const response = await fetch(`/lab2-1.0-SNAPSHOT/controller?x=${x}&y=${y}&r=${rValue}`, { method: "GET" });
            if (response.ok) {
                const result = await response.json();
                addRowToTable(result);
                plotPoint(result.x, result.y, result.r, result.hit);
            } else {
                showError("Ошибка на сервере. Попробуйте позже.");
            }
        } catch (error) {
            showError("Ошибка при отправке запроса.");
        }
    });

    function addRowToTable(result) {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${document.querySelectorAll(".jsTableRes tr").length + 1}</td>
            <td>${result.x}</td>
            <td>${result.y}</td>
            <td>${result.r}</td>
            <td>${result.hit ? "Да" : "Нет"}</td>
            <td>${result.time}</td>
            <td>${result.execution.toFixed(2)} микросекунд</td>
        `;
        tableBody.appendChild(row);
    }

    function plotPoint(x, y, r, hit) {
        const svgWidth = 300;
        const svgHeight = 300;
        const centerX = svgWidth / 2;
        const centerY = svgHeight / 2;
        const scale = 120 / r;

        const plotX = centerX + x * scale;
        const plotY = centerY - y * scale;

        const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        point.setAttribute("cx", plotX);
        point.setAttribute("cy", plotY);
        point.setAttribute("r", 3);
        point.setAttribute("fill", hit ? "green" : "red");
        point.setAttribute("class", "graph-point");

        svg.appendChild(point);
    }

    function clearGraphPoints() {
        const points = document.querySelectorAll(".graph-point");
        points.forEach(point => point.remove());
    }

    function showError(message) {
        const errorParagraph = document.createElement("p");
        errorParagraph.textContent = message;
        errorParagraph.style.color = "darkred";
        errorParagraph.style.fontWeight = "bold";
        errorMsgDiv.appendChild(errorParagraph);

        updateErrorMsgStyle();
    }

    function updateErrorMsgStyle() {
        if (errorMsgDiv.innerHTML.trim() === "") {
            errorMsgDiv.style.backgroundColor = "#e6ffe6";
            errorMsgDiv.style.borderColor = "green";
        } else {
            errorMsgDiv.style.backgroundColor = "#ffe6e6";
            errorMsgDiv.style.borderColor = "darkred";
        }
    }
});
