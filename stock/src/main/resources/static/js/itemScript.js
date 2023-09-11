const urlParam = new URLSearchParams(window.location.search);
const itemName = document.getElementById("itemName");
const description = document.getElementById("description");
const unitType = document.getElementById("unitType");
const amount = document.getElementById("amount");
const form = document.getElementById("form");
const tbody = document.getElementById("tbody");
const title = document.getElementById("title");
var object
var amountValue

document.addEventListener("DOMContentLoaded", createPage());

form.addEventListener("submit", function(e) {
    e.preventDefault();
    if(amount.value < 0) {
        alert("QUANTIDADE MENOR QUE 0");
    } else {
        submit();
    }
})

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
            createTable()
        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });
}
 
function createTable() {
    console.log(object)
    tbody.innerHTML = ""
    for (let i = 0; i < object.changes.length; i++) {
        let change = object.changes[i];
        let tr = document.createElement("tr");
        tr.className = "text-center";

        let tdChange = document.createElement("td");
        let tdDate = document.createElement("td");
        let tdHour = document.createElement("td");
        
        let valuesChanges = change.amount;

        if(valuesChanges[0] == "c") {
            tdChange.textContent = "Criou com " + valuesChanges.replace("c","");
            tdChange.className = "text-primary";
        } else if(parseInt(valuesChanges) > 0) {
            valuesChanges = "Adicionou +" + valuesChanges;
            tdChange.textContent = valuesChanges;
            tdChange.className = "text-success";
        } else {
            valuesChanges = "Retirou " + valuesChanges;
            tdChange.textContent = valuesChanges;
            tdChange.className = "text-danger";
        }



        let dateHour = change.date.split(" T ");

        tdDate.textContent = dateHour[0];
        tdHour.textContent = dateHour[1];

        tr.appendChild(tdChange);
        tr.appendChild(tdDate);
        tr.appendChild(tdHour);

        tbody.appendChild(tr);

    }
}

function submit() {
    console.log(urlParam.get("id"))
    let value;
    if(amount.value < amountValue) {
        value = -(amountValue - amount.value);
    } else {
        value = amount.value - amountValue;
    }

    object.changes.forEach((change) => {
        change.amount = parseInt(change.amount);
    });

    console.log("http://127.0.0.1:8080/item/add/changes?idItem=" + urlParam.get("id") +"&change="+value)
    console.log(object.changes.length)
    fetch("http://127.0.0.1:8080/item/add/changes?idItem=" + urlParam.get("id") +"&change="+value,
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(object),
            method: "POST",
        })
        .then(function (res) {
            createTable();
            if (!res.ok) {
                throw new Error("Erro na solicitação POST: " + res.status);
            }
            return res.json();
        })
        .catch(function (res) { 
            createTable();
            console.log(object.changes.length)
 
        })
}
