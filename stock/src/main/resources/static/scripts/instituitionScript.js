const instituitionName = document.getElementById("instituitionName");
const ul = document.getElementById("ul");
const urlParam = new URLSearchParams(window.location.search);

document.addEventListener("DOMContentLoaded", refresh());


function refresh() {
    fetch("http://localhost:8080/institution/" + urlParam.get("id"))
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erro na resposta: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            constructPage(data);
        })
}

function constructPage(data) {
    constructLateralBarAndHeader(data);
    constructMainTable(data);
}

function constructLateralBarAndHeader(object) {
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }

    instituitionName.textContent = object.name;

    let d = document.createElement("div");
    d.classList = "text-center p-3 ";
    let i = document.createElement("i");//bi bi-inbox
    i.classList = "bi bi-truck text-white fs-3 p-0 m-0";
    let text = document.createElement("span");
    text.textContent = " Pedidos";
    text.classList = "text-white h1 fw-light p-0 m-0";
    d.appendChild(i);
    d.appendChild(text);
    ul.appendChild(d);

    for (let i = 0; i < object.orders.length; i++) {

        let item = object.orders[i];

        let li = document.createElement("li");
        li.classList = "p-2 mb-2 text-center entitys item";
        li.id = item.id
        let aa = document.createElement("p");
        aa.textContent = " " + item.name;
        aa.classList = "link-light link-underline-opacity-0 fs-6 d-inline-block ms-2";
        let i2 = document.createElement("i");
        i2.classList = "bi bi-archive text-white p-0 m-0";
        i2.style.fontSize = "13px";
        li.appendChild(i2);
        li.appendChild(aa);
        ul.appendChild(li);
    }

}

function constructMainTable(object) {
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }

    let td1 = document.createElement("td");
    td1.textContent = "Selecionar";
    td1.classList = "text-muted";
    td1.style.fontSize = "14px";

    let td2 = document.createElement("td");
    td2.textContent = "Nome";
    td2.classList = "text-muted";
    td2.style.fontSize = "14px";

    let td3 = document.createElement("td");
    td3.textContent = "Data / Hora";
    td3.classList = "text-muted";
    td3.style.fontSize = "14px";

    let trHeader = document.createElement("tr");
    trHeader.classList = "text-center";
    trHeader.appendChild(td1);
    trHeader.appendChild(td2);
    trHeader.appendChild(td3);
    trHeader.classList = "border-bottom border-secondary";
    tbody.appendChild(trHeader);

    if (object.orders.length === 0) {
        let noneItem = document.createElement("tr");
        let td1 = document.createElement("td");

        let td2 = document.createElement("td");
        td2.textContent = "Nenhum pedido encontrado";
        td2.classList = "text-danger";
        td2.classList.add("fs-5");

        let td3 = document.createElement("td");

        noneItem.classList = "text-center";
        noneItem.appendChild(td1);
        noneItem.appendChild(td2);
        noneItem.appendChild(td3);
        tbody.appendChild(noneItem);
    }

    for (let i = 0; i < object.orders.length; i++) {
        let item = object.orders[i];

        let tdCheckbox = document.createElement("td");
        tdCheckbox.classList.add("text-center");
        let inp = document.createElement("input");
        inp.value = item.id;
        inp.type = "checkbox";
        tdCheckbox.appendChild(inp);


        let tdItemName = document.createElement("td");
        tdItemName.classList = "fs-5 entitys-items item";
        tdItemName.id = item.id;
        let a = document.createElement("span");
        a.textContent = item.name;
        a.classList = "text-primary"
        tdItemName.appendChild(a);


        let tdAmount = document.createElement("td");
        tdAmount.classList.add("fs-5");
        tdAmount.textContent = item.amount;
        tdAmount.classList = "text-muted";

        let trBody = document.createElement("tr");

        trBody.appendChild(tdCheckbox);
        trBody.appendChild(tdItemName);
        trBody.appendChild(tdAmount);

        tbody.appendChild(trBody);
    }
    let checkboxes = document.querySelectorAll("input[type='checkbox']")
    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                trashBtn.classList.remove("disabled");
                trashBtn.classList.add("btn-danger");
                trashBtn.classList.add("text-white");

                checkboxes.forEach(function (otherCheckbox) {
                    if (otherCheckbox !== checkbox) {
                        otherCheckbox.checked = false;
                    }
                });
            } else {
                trashBtn.classList.add("disabled");
                trashBtn.classList.remove("btn-danger");
                trashBtn.classList.remove("text-white");
            }
        });
    });

    let items = document.querySelectorAll(".item")
    items.forEach(function (item) {
        item.addEventListener('click', function () {
            constructTbodyModal(item.id);
            itemModal.show();
        });
    });
}
