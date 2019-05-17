"use strict";

let path = "/logistics/orders";

function postForm(formForSend, url) {
    let formData = new FormData(formForSend);
    let requestHeaders = new Headers();
    requestHeaders.append("X-CSRF-TOKEN", formData.get("_csrf").toString());
    requestHeaders.append("Content-Type", "application/json");
    let formDataMap = {};
    formData.forEach((value, key) => {
        formDataMap[key] = value
    });
    let jsonData = JSON.stringify(formDataMap);
    fetch(url, {
        method: "POST",
        body: jsonData,
        headers: requestHeaders
    }).then(response => {
        if (response.status === 200) {
            let formControlCollection = formForSend.getElementsByClassName("form-control");
            for (let i = 0; i < formControlCollection.length; i++) {
                doValid(formControlCollection[i]);
            }
            document.location.reload(true);
        } else if (response.status === 400) {
            response.json().then(jsonFieldErrors => {
                for (let key in jsonFieldErrors) {
                    let elementWithError = formForSend.elements[key];
                    doInvalid(elementWithError);
                    let divInvalidMsgCollection = elementWithError.parentElement
                        .getElementsByClassName("invalid-feedback");
                    for (let i = 0; i < divInvalidMsgCollection.length; i++) {
                        divInvalidMsgCollection[i].remove();
                    }
                    let divInvalidMsg = document.createElement("div");
                    divInvalidMsg.classList.add("invalid-feedback");
                    divInvalidMsg.innerHTML = jsonFieldErrors[key];
                    elementWithError.parentElement.appendChild(divInvalidMsg);
                }
                let formControlCollection = formForSend.getElementsByClassName("form-control");
                for (let i = 0; i < formControlCollection.length; i++) {
                    if (!(formControlCollection[i].name in jsonFieldErrors)) {
                        doValid(formControlCollection[i]);
                    }
                }
            })
        }
    })
        .catch(error => console.error('Error:', error));
    event.preventDefault();
}

let cargoForm = document.forms["addNewCargoForm"];
cargoForm.addEventListener("submit", postCargoForm);

function postCargoForm() {
    postForm(cargoForm, path + "/add/cargoList/add");
}

let cargoEditForm = document.forms["editCargoForm"];
let editedCargoID = -1;

function onEditButtonClick(cargo_id) {
    editedCargoID = cargo_id;
    cargoEditForm.elements["name"].value = document.getElementById("cg_name_" + cargo_id).innerText;
    cargoEditForm.elements["weight"].value = document.getElementById("cg_weight_" + cargo_id).innerText;
    createCitySelectedOption(cargo_id,"cg_loadCity_", "loadingCity");
    createCitySelectedOption(cargo_id,"cg_unloadCity_", "unloadingCity");
}

function createCitySelectedOption(cargo_id, sourceIDPrefix, destSelectName) {
    let optionCity = document.createElement( 'option' );
    let city = document.getElementById(sourceIDPrefix + cargo_id).innerText;
    optionCity.value = city;
    optionCity.text = city;
    optionCity.selected = true;
    cargoEditForm.elements[destSelectName].add(optionCity);
}

cargoEditForm.addEventListener("submit", editCargoForm);

function editCargoForm() {
    let input = document.createElement("input");
    input.type = "hidden";
    input.name = "id";
    input.value = editedCargoID;
    cargoEditForm.appendChild(input);
    postForm(cargoEditForm, path + "/add/cargoList/edit");
    cargoEditForm.removeChild(input);
}

function doValid(element) {
    element.classList.remove("is-invalid");
    element.classList.add("is-valid");
}

function doInvalid(element) {
    element.classList.remove("is-valid");
    element.classList.add("is-invalid");
}

cargoForm.elements["name"].addEventListener("input", checkAddCargoNameInput);
cargoEditForm.elements["name"].addEventListener("input", checkEditCargoNameInput);

function checkAddCargoNameInput() {
    checkCargoNameInput(cargoForm.elements["name"]);
}

function checkEditCargoNameInput() {
    checkCargoNameInput(cargoEditForm.elements["name"]);
}

function checkCargoNameInput(cargoNameInput) {
    let length = cargoNameInput.value.length;
    if (length < 1 || length > 255) {
        doInvalid(cargoNameInput);
    } else {
        doValid(cargoNameInput);
    }
}

cargoForm.elements["weight"].addEventListener("input", checkAddCargoWeightInput);
cargoEditForm.elements["weight"].addEventListener("input", checkEditCargoWeightInput);

function checkAddCargoWeightInput() {
    checkCargoWeightInput(cargoForm.elements["weight"]);
}

function checkEditCargoWeightInput() {
    checkCargoWeightInput(cargoEditForm.elements["weight"]);
}

function checkCargoWeightInput(cargoWeightInput) {
    let val = +cargoWeightInput.value;
    if (!isNaN(val) && val >= 1 && val <= 24000 ) {
        doValid(cargoWeightInput);
    } else {
        doInvalid(cargoWeightInput);
    }
}

cargoForm.elements["cargoLoadCity"].addEventListener("change", checkAddCargoCitySelect);
cargoForm.elements["cargoUnLoadCity"].addEventListener("change", checkAddCargoCitySelect);
cargoEditForm.elements["cargoLoadCity"].addEventListener("change", checkEditCargoCitySelect);
cargoEditForm.elements["cargoUnLoadCity"].addEventListener("change", checkEditCargoCitySelect);

function checkAddCargoCitySelect() {
    checkCargoCitySelect(cargoForm.elements["cargoLoadCity"], cargoForm.elements["cargoUnLoadCity"]);
}

function checkEditCargoCitySelect() {
    checkCargoCitySelect(cargoEditForm.elements["cargoLoadCity"], cargoEditForm.elements["cargoUnLoadCity"]);
}

function checkCargoCitySelect(cargoLoadCitySelect, cargoUnloadCitySelect) {
    if (cargoLoadCitySelect.selectedIndex === cargoUnloadCitySelect.selectedIndex) {
        doInvalid(cargoLoadCitySelect);
        doInvalid(cargoUnloadCitySelect);
    } else {
        doValid(cargoLoadCitySelect);
        doValid(cargoUnloadCitySelect);
    }
}


