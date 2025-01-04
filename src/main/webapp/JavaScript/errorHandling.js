export function showError(message) {
    const errorMsgDiv = document.getElementById("errorMsgDiv");
    errorMsgDiv.innerHTML = "";
    const errorParagraph = document.createElement("p");
    errorParagraph.textContent = message;
    errorParagraph.style.color = "red";
    errorMsgDiv.appendChild(errorParagraph);
}

export function clearError() {
    const errorMsgDiv = document.getElementById("errorMsgDiv");
    errorMsgDiv.innerHTML = "";
}
