const urlParam = new URLSearchParams(window.location.search);
const itemName = document.getElementById("itemName");
const description = document.getElementById("description");
const unitType = document.getElementById("unitType");
const amount = document.getElementById("amount");
const form = document.getElementById("form");
const tbody = document.getElementById("tbody");
const title = document.getElementById("title");
var deleteButton = document.getElementById("delete");
const destroyButton = document.getElementById("deleteItem");
var cancelButton = document.getElementById("cancel");
var sortIcon = document.getElementById("sortIcon");
var on = true
var records;
var amountValue

//TODO: IMPLEMENTAR ADD ITEM NA PAGINA DE ESTOQUE

function updateObject() {
    return fetch("http://127.0.0.1:8080/item/find/byId?id=" + urlParam.get("id"))
        .then((response) => {
            if (!response.ok) {
                throw new Error("error no response" + response.status);
            }
            return response.json()
        })
        .then(data => {
            return data;

        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });
}

sortIcon.addEventListener("click", toggleIcon)


document.addEventListener("DOMContentLoaded", function () {
    createPage();
});

destroyButton.addEventListener("click", function (event) {
    event.preventDefault();
    var confirmacaoModal = document.getElementById("confirmacaoModal");
    confirmacaoModal.style.display = "block";
});

deleteButton.addEventListener("click", function () {
    //TODO: IMPLEMENTAR DELETAR ITEM
    var confirmacaoModal = document.getElementById("confirmacaoModal");
    confirmacaoModal.style.display = "none";
    destroy();
});


cancelButton.addEventListener("click", function () {
    var confirmacaoModal = document.getElementById("confirmacaoModal");
    confirmacaoModal.style.display = "none";
});




function createPage() {
    fetch("http://127.0.0.1:8080/item/find/byId?id=" + urlParam.get("id"))
        .then((response) => {
            if (!response.ok) {
                throw new Error("error no response" + response.status);
            }
            return response.json();
        })
        .then((data) => {
            object = data;
            amountValue = object.amount;
            
            title.textContent = object.name;
            itemName.textContent = object.name;
            description.textContent = object.description;
            unitType.textContent = object.unitType;
            amount.value = object.amount;
            changesList = object.changes;
            createTable(data, on)
        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });
}



form.addEventListener("submit", function (e) {
    e.preventDefault();
    if (amount.value < 0) {
        alert("QUANTIDADE MENOR QUE 0");
    } else {
        submit();
    }
})

function createTable(data, trueToAltSortC) {

    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }

    var rowArray = [];

    for (let i = 0; i < data.changes.length; i++) {
        let change = data.changes[i];
        let tr = document.createElement("tr");
        tr.className = "text-center";
        
        let tdChange = document.createElement("td");
        let valuesChanges = change.amount;
        let dateHour = change.date.split(" T ");
        
        
        if (valuesChanges[0] == "c") {
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
    
    if(trueToAltSortC) {
        rowArray.sort(function(a, b) {
            var dateA = a.date;
            var dateB = b.date;
            return dateA.localeCompare(dateB);
          });
    } else {
        rowArray.sort(function(a, b) {
            var dateA = a.date;
            var dateB = b.date;
            return dateB.localeCompare(dateA);
          });
    }

    for (let i = 0; i < rowArray.length; i++) {
        let row = rowArray[i];
        console.log(row)
        let dateTime = row.date.split("T");

        let date = dateTime[0];
        date = date.replace(/-/g,"/");
        let time = dateTime[1];

        let tdDate = document.createElement("td");
        let tdHour = document.createElement("td");

        tdDate.textContent = date;
        tdHour.textContent = time
        
        let tr = document.createElement("tr");
        console.log("foi")
        tr.appendChild(row.textContent);
        tr.appendChild(tdDate);
        tr.appendChild(tdHour);

        tbody.appendChild(tr);
    }


}

function submit() {
    updateObject().then((data) => {
        let value;
        if (amount.value < amountValue) {
            value = -(amountValue - amount.value);
        } else {
            value = amount.value - amountValue;
        }
        if (value == 0 || value == parseInt(amount.value)) {
            console.log("retornou")
            return;
        }


        fetch("http://127.0.0.1:8080/item/add/changes?idItem=" + urlParam.get("id") + "&change=" + value,
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
                method: "POST",
            })
            .then(function (res) {
                return res.json()
            })  
            .catch(function (res) {

                createPage();
            })

    })
}

function destroy() {
    console.log("oi")
    fetch("http://127.0.0.1:8080/item/delete?idItem=" + urlParam.get("id"), 
    {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "DELETE",
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("error no response" + response.status);
            }
            return response.json();
        })
        .then((data) => {
            window.location.href = "http://127.0.0.1:8080/pages/stockGroup.html?id=" + data.id;
        })
        .catch((error) => {
        });
}

function toggleIcon() {
    if(on) {
        on = false;
    } else {
        on = true;
    }
    let icon = document.getElementById("sortIcon");


    if (icon.classList.contains("bi-sort-down")) {
        icon.classList.remove("bi-sort-down");
        icon.classList.add("bi-sort-down-alt");
    } else {
        icon.classList.remove("bi-sort-down-alt");
        icon.classList.add("bi-sort-down");
    }
    createPage();
}
