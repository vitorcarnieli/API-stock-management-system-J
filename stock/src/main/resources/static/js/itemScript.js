const urlParam = new URLSearchParams(window.location.search);
const itemName = document.getElementById("itemName");
const description = document.getElementById("description");
const unitType = document.getElementById("unitType");
const amount = document.getElementById("amount");
const form = document.getElementById("form");
const tbody = document.getElementById("tbody");
var object;

document.addEventListener("DOMContentLoaded", createPage());


console.log("test")

form.addEventListener("submit", function(e) {
    e.preventDefault();
    submit();
})

function createPage() {
    fetch("http://127.0.0.1:8080/item/find/byId?id=" + urlParam.get("id"))
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
            object = data;
            
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
    tbody.innerHTML = ""
    for (let i = 0; i < object.changes.length; i++) {
        let change = object.changes[i];
        let tr = document.createElement("tr");
        tr.className = "text-center";

        let tdChange = document.createElement("td");
        let tdDate = document.createElement("td");
        let tdHour = document.createElement("td");

        let valuesChanges = change[0]
        if(valuesChanges[0] == "c") {
            valuesChanges = "Criou com " + valuesChanges.replace(/[c.0]/g, "");
            tdChange.textContent = valuesChanges;
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



        let dateHour = change[1].split(" T ");

        tdDate.textContent = dateHour[0];
        tdHour.textContent = dateHour[1];

        tr.appendChild(tdChange);
        tr.appendChild(tdDate);
        tr.appendChild(tdHour);

        tbody.appendChild(tr);

    }
}

function submit() {

}