const urlParam = new URLSearchParams(window.location.search)
const title = document.getElementById("title");
const totalItems = document.getElementById("total-items")
const stockName = document.getElementById("stock-group-name");
const description = document.getElementById("stock-group-description");
const tbody = document.getElementById("tbody");
const deleteStock = document.getElementById("deleteStock");
var deleteButton = document.getElementById("delete");
var cancelButton = document.getElementById("cancel");
const searchByNameField = document.getElementById("searchByNameField");
const searchByNameBtn = document.getElementById("searchByName");
const alphaSort = document.getElementById("alphaSort");
var items

alphaSort.addEventListener("click", toggleIcon)
searchByNameBtn.addEventListener("click", findByName)
document.addEventListener("DOMContentLoaded", refresh())


deleteButton.addEventListener("click", function () {
    var confirmacaoModal = document.getElementById("confirmacaoModal");
    confirmacaoModal.style.display = "none";
    destroy();
});

cancelButton.addEventListener("click", function () {
    var confirmacaoModal = document.getElementById("confirmacaoModal");
    confirmacaoModal.style.display = "none";
});

deleteStock.addEventListener("click", function (event) {
    event.preventDefault();
    var confirmacaoModal = document.getElementById("confirmacaoModal");
    confirmacaoModal.style.display = "block";
});


function createTdWithIcons(condition, idItem) {
    let btn = document.createElement("button");
    let icon = document.createElement("i")
    switch (condition) {
        case "ENTER":
            let a = document.createElement("a");
            a.className = "btn btn-primary rounded-5";
            icon.className = "bi bi-box-arrow-in-right text-light";
            a.href = "http://127.0.0.1:8080/pages/item.html?id=" + idItem;
            a.appendChild(icon);
            return a;
    }
}

function refresh(test, obj) {
    if (test == true) {
        createTable(obj)
        return
    }
    fetch("http://127.0.0.1:8080/stock-group/find/byId?id=" + urlParam.get("id"))
        .then((response) => {
            if (!response.ok) {
                throw new Error("error no response" + response.status);
            }
            console.log(response)
            console.log(response.json)
            return response.json();
        })
        .then((data) => {
            let object = data;
            title.textContent = object.name
            stockName.textContent = object.name
            description.textContent = object.description
            totalItems.textContent = "TOTAL DE ITENS: " + object.items.length

            let a = document.getElementById("addItem");

            a.href = "createItem.html?id=" + urlParam.get("id");

            let itemsObj = object.items;
            items = itemsObj;
            createTable(itemsObj)





        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });
}

function createTable(obj, orgToZA) {
    tbody.innerHTML = ""
    if (orgToZA) {
        alphaOrderSort(obj, false)
    } else {
        alphaOrderSort(obj, true)
    }
    for (let i = 0; i < obj.length; i++) {
        let tr = document.createElement("tr");

        let tdName = document.createElement("td");
        let tdUnitType = document.createElement("td");
        let tdAmount = document.createElement("td");
        let tdEnter = document.createElement("td")

        tdEnter.className = "text-center";

        let item = obj[i];
        tdName.textContent = item.name;
        tdUnitType.textContent = item.unitType;
        tdAmount.textContent = item.amount;
        tdEnter.appendChild(createTdWithIcons("ENTER", item.id));
        tr.appendChild(tdName);
        tr.appendChild(tdUnitType);
        tr.appendChild(tdAmount);
        tr.appendChild(tdEnter)

        tbody.appendChild(tr);
    }
}

function destroy() {
    fetch("http://127.0.0.1:8080/stock-group/delete?idGroup=" + urlParam.get("id"))
        .then((response) => {
            if (!response.ok) {
                throw new Error("error no response" + response.status);
            }

            console.log(response)
            console.log(response.json)
            return response.json();
        })
        .then((data) => {
        })
        .catch((error) => {
            window.location.href = "http://127.0.0.1:8080/index.html";
        });
}

function findByName() {
    console.log("entrou")
    fetch("http://127.0.0.1:8080/item/find/byName?idGroup=" + urlParam.get("id") + "&name=" + searchByNameField.value)
        .then((response) => {
            if (!response.ok) {
                throw new Error("error no response" + response.status);
            }
            console.log(response)
            console.log(response.json)
            return response.json();
        })
        .then((data) => {
            console.log(data)
            let object = data;
            items = object;
            refresh(true, object)
        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });
}

function toggleIcon() {
    let icon = document.getElementById("sortIcon");

    if (icon.classList.contains("bi-sort-alpha-down")) {
        icon.classList.remove("bi-sort-alpha-down");
        createTable(items, true)
        icon.classList.add("bi-sort-alpha-down-alt");
    } else {
        icon.classList.remove("bi-sort-alpha-down-alt");
        createTable(items, false)
        icon.classList.add("bi-sort-alpha-down");
    }
}

function alphaOrderSort(obj, boolArg) {
    if (boolArg) {
        obj.sort(function (a, b) {
            return a.name.localeCompare(b.name);
        });
    } else {
        obj.sort(function (a, b) {
            return b.name.localeCompare(a.name);
        });
    }
}


