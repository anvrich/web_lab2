import {showError, clearError} from './errorHandling.js';

export function validateY(yValue) {
    if (!/^-?\d+(\.\d+)?$/.test(yValue) || yValue < -5 || yValue > 3) {
        showError("Y должно быть числом от -5 до 3.");
        return false;
    }
    clearError();
    return true;
}

export function validateR(rValue) {
    if (!/^-?\d+(\.\d+)?$/.test(rValue) || rValue < 1 || rValue > 4) {
        showError("R должно быть числом от 1 до 4.");
        return false;
    }
    clearError();
    return true;
}

export function validateX(xValues) {
    if (xValues.length === 0) {
        showError("Необходимо выбрать хотя бы одно значение X.");
        return false;
    }
    clearError();
    return true;
}
