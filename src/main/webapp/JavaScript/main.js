import {filterPointsByRadius, graphClick} from './graph.js';
import {clearError, showError} from './errorHandling.js';
import { addRowToTable, clearGraphPoints, seePoint } from './graph.js';
import { validateY, validateR, validateX } from './validation.js';

document.addEventListener("DOMContentLoaded", () => {
    const rows = document.querySelectorAll(".jsTableRes tr");
    rows.forEach(row => {
        const cells = row.querySelectorAll("td");
        const x = parseFloat(cells[1].textContent);
        const y = parseFloat(cells[2].textContent);
        const r = parseFloat(cells[3].textContent);
        const hit = cells[4].textContent === "Да";
        seePoint(x, y, r, hit);
    });
    graphClick();
});

const form = document.getElementById("myForm");
const yInput = form.querySelector("input[name=y]");
const rInput = form.querySelector("input[name=r]");
const clearButton = document.getElementById("clearButton");
const tableBody = document.querySelector(".jsTableRes");

rInput.addEventListener("input", () => {
    const rValue = parseFloat(rInput.value.replace(',', '.'));
    if (isNaN(rValue) || rValue < 1 || rValue > 4) {
        showError("R должно быть числом от 1 до 4.");
        return;
    }
    clearError();
    filterPointsByRadius(rValue);
});
yInput.addEventListener("input", () => {
    yInput.value = yInput.value.replace(",", ".");
});
rInput.addEventListener("input", () => {
    rInput.value = rInput.value.replace(",", ".");
});

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const xValues = Array.from(document.querySelectorAll(".x:checked"))
        .map(input => parseFloat(input.value));
    const yValue = parseFloat(yInput.value.replace(',', '.'));
    const rValue = parseFloat(rInput.value.replace(',', '.'));

    if (!validateX(xValues) || !validateY(yValue) || !validateR(rValue)) {
        return;
    }

    for (const x of xValues) {
        const params = `x=${x}&y=${yValue}&r=${rValue}`;
        try {
            const response = await fetch(`/lab2-1.0-SNAPSHOT/controller?${params}`, { method: "GET" });
            if (response.ok) {
                const result = await response.json();
                addRowToTable(result);
                seePoint(result.x, result.y, result.r, result.hit);
            } else {
                showError("Ошибка на сервере.");
            }
        } catch (error) {
            showError("Ошибка сети.");
        }
    }
});

clearButton.addEventListener("click", async () => {
    try {
        const response = await fetch("/lab2-1.0-SNAPSHOT/controller?clear", { method: "GET" });
        if (response.ok) {
            tableBody.innerHTML = "";
            clearGraphPoints();
        } else {
            showError("Ошибка очистки таблицы.");
        }
    } catch (error) {
        showError("Ошибка сети." + error);
    }
});
