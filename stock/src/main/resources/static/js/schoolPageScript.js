const urlParam = new URLSearchParams(window.location.search)
const title = document.getElementById("title");
const totalPedidos = document.getElementById("total-pedidos")
const schoolName = document.getElementById("school-name");
const tbody = document.getElementById("tbody");
const searchByNameField = document.getElementById("searchByNameField");
const searchByNameBtn = document.getElementById("searchByName");
const alphaSort = document.getElementById("alphaSort");

document.addEventListener("DOMContentLoaded", refresh());

function createTdWithIcons(condition, idItem) {
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

function refresh() {
    fetch("http://127.0.0.1:8080/school/find/byId?id=" + urlParam.get("id"))
        .then((response) => {
            if (!response.ok) {
                throw new Error("error no response" + response.status);
            }
            return response.json();
        })
        .then((data) => {
            let object = data;
            title.textContent = object.name
            schoolName.textContent = object.name
            totalPedidos.textContent = "TOTAL DE PEDIDOS: " + object.orders.length

            let a = document.getElementById("addOrder");

            a.href = "createorder.html?id=" + urlParam.get("id");

            let ordersObj = object.orders;
            orders = ordersObj;
            createTable(object)





        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });
}

function createTable(obj, orgToZA) {
    console.log(obj)
    tbody.innerHTML = ""
    if (orgToZA) {
        alphaOrderSort(obj, false)
    } else {
        alphaOrderSort(obj, true)
    }
    for (let i = 0; i < obj.length; i++) {
        let tr = document.createElement("tr");

        let tdName = document.createElement("td");
        let tdDate = document.createElement("td");
        let tdEnter = document.createElement("td")

        tdEnter.className = "text-center";

        let order = obj[i];
        tdName.textContent = order.name;
        tdDate.textContent = order.date;
        tdEnter.appendChild(createTdWithIcons("ENTER", order.id));
        tr.appendChild(tdName);
        tr.appendChild(tdDate);
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

