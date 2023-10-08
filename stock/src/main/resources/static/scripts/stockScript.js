const stockName = document.getElementById("stockName");
const ul = document.getElementById("ul");
const tbody = document.getElementById("tbody");
const trashBtn = document.getElementById("trashBtn");
const actionBtn = document.getElementById("actionBtn");
const all = document.getElementById("all");
const available = document.getElementById("available");
const missing = document.getElementById("missing");
const register = document.getElementById("register");
const urlParam = new URLSearchParams(window.location.search);
const deleteConfirm = document.getElementById("deleteConfirm");
const myModal = new bootstrap.Modal(document.getElementById('myModal'));
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const searchByNameField = document.getElementById("searchByNameField");
const itemModal = new bootstrap.Modal(document.getElementById('itemModal'));

//PAINEL DE ITEMS
const tbodyModal = document.getElementById("tbodyModal");
const name = document.getElementById("nameItem");
const description = document.getElementById("descriptionItem");
const unitType = document.getElementById("unitTypeItem");
const amount = document.getElementById("amountItem");

var dataAmount;

const saveChanges = document.getElementById("saveChanges");
/* 

TODO: ;


*/

amount.addEventListener("input", function () {
    saveChanges.disabled = true;
    console.log(amount.value);
    if(parseInt(amount.value) < 0 || amount.value[0] == "-") {
        return;
    }
    if (dataAmount != amount.value) {
        saveChanges.disabled = false;
    } else {
        saveChanges.disabled = true;
    }
})

saveChanges.addEventListener("click", function () {
    let amountItem = document.getElementById("amountItem");
    let id = saveChanges.value;
    if (amountItem.value != dataAmount) {
        if (amount.value < dataAmount) {
            saveChange(id, -(dataAmount - amount.value));
        } else {
            saveChange(id, amount.value - dataAmount);

        }
    }

    console.log("passou")
})

searchByNameField.addEventListener("input", function () {
    searchByName(searchByNameField.value);
})

all.addEventListener("click", function () {
    select(all, available, missing);
    refresh("all");
});

available.addEventListener("click", function () {
    select(available, all, missing);
    refresh("available");
});

missing.addEventListener("click", function () {
    select(missing, available, all)
    refresh("missing");
});

document.addEventListener("DOMContentLoaded", function () {
    refresh("all")
});

trashBtn.addEventListener("click", function () {
    deleteModal.show()
});

deleteConfirm.addEventListener("click", function () {
    let checkboxes = document.querySelectorAll("input[type='checkbox']")
    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {


            fetch("http://localhost:8080/item/" + checkbox.value, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            })
                .then(response => {
                    if (!response.ok) {
                        alert("Erro ao deletar, por favor, tente novamente. (Caso o erro persista, entre em contato com o suporte)");
                        throw new Error('Erro na solicitação DELETE');
                    }
                    refreshReturn();
                    trashBtn.classList.add("disabled");
                    trashBtn.classList.remove("btn-danger");
                    trashBtn.classList.remove("text-white");
                    deleteModal.hide();
                })
                .catch(error => {
                    console.error('Erro na solicitação DELETE:', error);
                });

        }
    });
})

register.addEventListener("click", function () {
    let c1 = document.getElementById("name");
    let c2 = document.getElementById("description");
    let c3 = document.getElementById("unitType");
    let c4 = document.getElementById("amount");
    let object = {
        name: c1.value,
        description: c2.value,
        unitType: c3.value,
        amount: c4.value
    }
    fetch("http://localhost:8080/stock-group/addItem/" + urlParam.get("id"),
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify(object)
        })
        .then(function (res) {
            myModal.hide()


            refreshReturn();
        })
        .catch(function (res) { console.log(res) })


});

actionBtn.addEventListener("click", function () {
    let c1 = document.getElementById("name");
    let c2 = document.getElementById("description");
    let c3 = document.getElementById("unitType");
    let c4 = document.getElementById("amount");
    c1.value = "";
    c2.value = "";
    c3.value = "";
    c4.value = "";

    myModal.show();
})

function select(field, neighboringField1, neighboringField2) {
    if (field.classList.contains("select")) {
        return;
    } else {
        field.classList.add("select");
        field.classList.remove("unselect");
        neighboringField1.classList.remove("select");
        neighboringField1.classList.add("unselect");
        neighboringField2.classList.remove("select");
        neighboringField2.classList.add("unselect");
    }
}

function constructLateralBarAndHeader(object) {
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }

    stockName.textContent = object.name;

    let d = document.createElement("div");
    d.classList = "text-center p-3 ";
    let i = document.createElement("i");//bi bi-inbox
    i.classList = "bi bi-inboxes text-white fs-3 p-0 m-0";
    let text = document.createElement("span");
    text.textContent = " Itens";
    text.classList = "text-white h1 fw-light p-0 m-0";
    d.appendChild(i);
    d.appendChild(text);
    ul.appendChild(d);

    for (let i = 0; i < object.items.length; i++) {

        let item = object.items[i];

        let li = document.createElement("li");
        li.classList = "p-2 mb-2 text-center entitys item";
        li.id = item.id
        let aa = document.createElement("p");
        aa.textContent = " " + item.name;
        aa.classList = "link-light link-underline-opacity-0 fs-6 d-inline-block ms-2";
        let i2 = document.createElement("i");
        i2.classList = "bi bi-inbox text-white p-0 m-0";
        i2.style.fontSize = "13px";
        li.appendChild(i2);
        li.appendChild(aa);
        ul.appendChild(li);
    }

}

function constructTableItems(itemsArr) {
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
    td3.textContent = "Quantidade";
    td3.classList = "text-muted";
    td3.style.fontSize = "14px";

    let trHeader = document.createElement("tr");
    trHeader.classList = "text-center";
    trHeader.appendChild(td1);
    trHeader.appendChild(td2);
    trHeader.appendChild(td3);
    trHeader.classList = "border-bottom border-secondary";
    tbody.appendChild(trHeader);

    if (itemsArr.length === 0) {
        let noneItem = document.createElement("tr");
        let td1 = document.createElement("td");

        let td2 = document.createElement("td");
        td2.textContent = "Nenhum item encontrado";
        td2.classList = "text-danger";
        td2.classList.add("fs-5");

        let td3 = document.createElement("td");

        noneItem.classList = "text-center";
        noneItem.appendChild(td1);
        noneItem.appendChild(td2);
        noneItem.appendChild(td3);
        tbody.appendChild(noneItem);
    }

    for (let i = 0; i < itemsArr.length; i++) {
        let item = itemsArr[i];

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


function refresh(opt) {
    fetch("http://localhost:8080/stock-group/" + urlParam.get("id"))
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erro na resposta: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            constructLateralBarAndHeader(data);
            console.log("entrou aqui")
            if (opt === "all") {
                constructTableItems(data.items)
            } else if (opt === "available") {
                constructTableItems(workItems("available", data.items));
            } else if (opt === "missing") {
                constructTableItems(workItems("missing", data.items));
            }
        })
}

function workItems(opt, arr) {
    let returnArr = [];
    if (opt === "available") {
        for (let i = 0; i < arr.length; i++) {
            let item = arr[i];
            if (item.amount >= 1) {
                returnArr.push(item);
            }
        }
    } else if (opt === "missing") {
        for (let i = 0; i < arr.length; i++) {
            let item = arr[i];
            if (item.amount < 1) {
                returnArr.push(item);
            }
        }
    }

    return returnArr;
}

function searchByName(string) {

    if (string.trim() !== "") {
        fetch("http://localhost:8080/item/i/" + string)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Erro na resposta: " + response.status);
                }
                return response.json();
            })
            .then((data) => {
                if (all.classList.contains("select")) {
                    constructTableItems(data);
                } else if (available.classList.contains("select")) {
                    constructTableItems(workItems("available", data));
                } else if (missing.classList.contains("select")) {
                    constructTableItems(workItems("missing", data));
                }
            })
            .catch((error) => {
                console.error('Erro ao fazer a solicitação:', error);
            });
    } else {
        refreshReturn();
    }
}

function refreshReturn() {
    if (all.classList.contains("select")) {
        refresh("all");
    } else if (available.classList.contains("select")) {
        refresh("available");
    } else if (missing.classList.contains("select")) {
        refresh("missing");
    }
}

function constructTbodyModal(id, trueToAltSortC) {
    let name = document.getElementById("nameItem");
    let description = document.getElementById("descriptionItem");
    let unitType = document.getElementById("unitTypeItem");
    let amount = document.getElementById("amountItem");
    let tbodyModal = document.getElementById("tbodyModal");
    while (tbodyModal.firstChild) {
        tbodyModal.removeChild(tbodyModal.firstChild);
    }

    name.value = "";
    description.value = "";
    unitType.value = "";
    amount.value = "";

    fetch("http://localhost:8080/item/" + id)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erro na resposta: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            saveChanges.disabled = true;
            dataAmount = data.amount;
            name.value = data.name;
            name.disabled = true;
            description.value = data.description;
            description.disabled = true;
            unitType.value = data.unitType;
            unitType.disabled = true;
            amount.value = data.amount;
            saveChanges.value = data.id

            var rowArray = [];

            for (let i = 0; i < data.changes.length; i++) {
                let change = data.changes[i];
                let tr = document.createElement("tr");
                tr.className = "text-center";

                let tdChange = document.createElement("td");
                let valuesChanges = change.amount;
                let dateHour = change.date.split(" T ");

                if (valuesChanges.includes(",")) {
                    let arrSchool = valuesChanges.split(",")
                    tdChange.textContent = "Retirado " + arrSchool[1] + " para " + arrSchool[0];
                    tdChange.className = "text-info";
                }
                else if (valuesChanges[0] == "c") {
                    tdChange.textContent = "Criou com " + valuesChanges.replace("c", "");
                    tdChange.className = "text-primary";
                } else if (parseInt(valuesChanges) > 0) {
                    valuesChanges = "Adicionou +" + valuesChanges;
                    tdChange.textContent = valuesChanges;
                    tdChange.className = "text-success";
                } else {
                    valuesChanges = "Retirou " + valuesChanges;
                    tdChange.textContent = valuesChanges;
                    tdChange.className = "text-danger";
                }

                var row = {
                    textContent: tdChange,
                    date: dateHour[0]
                };
                rowArray.push(row);
            }

            if (trueToAltSortC) {
                rowArray.sort(function (a, b) {
                    var dateA = a.date;
                    var dateB = b.date;
                    return dateA.localeCompare(dateB);
                });
            } else {
                rowArray.sort(function (a, b) {
                    var dateA = a.date;
                    var dateB = b.date;
                    return dateB.localeCompare(dateA);
                });
            }

            for (let i = 0; i < rowArray.length; i++) {
                let row = rowArray[i];
                let dateTime = row.date.split("T");

                let date = dateTime[0];
                date = date.replace(/-/g, "/");
                let time = dateTime[1];

                let tdDate = document.createElement("td");
                let tdHour = document.createElement("td");

                tdDate.textContent = date;
                tdHour.textContent = time

                let tr = document.createElement("tr");
                tr.appendChild(row.textContent);
                tr.appendChild(tdDate);
                tr.appendChild(tdHour);

                tbodyModal.appendChild(tr);
            }



        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });
}

function saveChange(id, change) {
    fetch("http://localhost:8080/item/add-changes?id=" + id + "&c=" + change,
        {
            
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
        
        })
        .then(function (res) {
            refreshReturn();
            constructTbodyModal(id);
        })
        .catch(function (res) {
        })

}