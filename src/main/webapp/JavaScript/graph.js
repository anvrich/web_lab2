import {clearError, showError} from './errorHandling.js';
import { validateR } from './validation.js';

export function addRowToTable(result) {
    const tableBody = document.querySelector(".jsTableRes");
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${document.querySelectorAll(".jsTableRes tr").length + 1}</td>
        <td>${result.x}</td>
        <td>${result.y}</td>
        <td>${result.r}</td>
        <td>${result.hit ? "Да" : "Нет"}</td>
        <td>${result.time}</td>
        <td>${result.execution + " микросекунд"}</td>
    `;
    tableBody.appendChild(row);
}

export function seePoint(x, y, r, hit) {
    const svg = document.querySelector("svg");
    const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    point.setAttribute("cx", 150 + x * 120 / r);
    point.setAttribute("cy", 150 - y * 120 / r);
    point.setAttribute("r", 3);
    point.setAttribute("fill", hit ? "green" : "red");
    point.setAttribute("class", "graph-point");

    point.setAttribute("data-r", r);

    svg.appendChild(point);
    filterPointsByRadius(parseFloat(document.querySelector("input[name='r']").value));  // Фильтрация точек

}

export function graphClick() {
    const svg = document.querySelector("svg");
    const rInput = document.querySelector("input[name='r']");

    svg.addEventListener("click", async (event) => {
        const r = parseFloat(rInput.value.replace(',', '.'));
        clearError();

        if (!validateR(r)) {
            return;
        }
        const rect = svg.getBoundingClientRect();
        const scale = 120 / r;
        const x = ((event.clientX - rect.left - 150) / scale).toFixed(2);
        const y = (-(event.clientY - rect.top - 150) / scale).toFixed(2);
        if (x < -5 || x > 3) {
            showError("X должен быть в диапазоне от -5 до 3.");
            return;
        }
        if (y < -5 || y > 3) {
            showError("Y должен быть в диапазоне от -5 до 3.");
            return;
        }
        try {
            const response = await fetch(`/lab2-1.0-SNAPSHOT/controller?x=${x}&y=${y}&r=${r}`, {
                method: "GET"
            });
            if (response.ok) {
                const result = await response.json();
                addRowToTable(result);
                seePoint(result.x, result.y, result.r, result.hit);
            } else {
                showError("Ошибка на сервере. Попробуйте позже.");
            }
        } catch (error) {
            showError("Ошибка при отправке запроса.");
        }
    });
}

export function clearGraphPoints() {
    const points = document.querySelectorAll(".graph-point");
    points.forEach(point => point.remove());
}

export function filterPointsByRadius(rValue) {
    const points = document.querySelectorAll(".graph-point");
    points.forEach(point => {
        const pointRadius = parseFloat(point.getAttribute("data-r"));
        if (pointRadius === rValue) {
            point.style.display = "block";
        } else {
            point.style.display = "none";
        }
    });
}