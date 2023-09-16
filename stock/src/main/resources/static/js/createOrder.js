const name = document.getElementById("name");
const description = document.getElementById("description");
const formCreate = document.getElementById("form-create");
const formAdd = document.getElementById("form-add");
const tContainer = document.getElementById("table-container");
const tBody = document.getElementById("tbody")
const selectItems = document.getElementById("items");
const amountItem = document.getElementById("amount");
const btnAddItem = document.getElementById("add");
var itemsPresent = []


document.addEventListener("DOMContentLoaded", createPage);
btnAddItem.addEventListener("click", addItem);
















function createPage() {
    fetch("http://127.0.0.1:8080/stock-group/find/all")
    .then((response) => {
        if (!response.ok) {throw new Error("error no response" + response.status);}
        return response.json();
    })
    .then((data) => {
        console.log(data)
        for (let i = 0; i < data.length; i++) {
            let object = data[i];

            let optionStockName = document.createElement("option");
            optionStockName.textContent = object.name;
            optionStockName.disabled = true;
            selectItems.appendChild(optionStockName);

            for (let ii = 0; ii < object.items.length; ii++) {
                let item = object.items[ii]
                let optionItemName = document.createElement("option");
                optionItemName.textContent = item.name;
                optionItemName.value = [object.name, item.name, object.id]; 
                selectItems.appendChild(optionItemName);
            }   
        }
    })
    .catch((error) => {
        console.error('Erro em createPage(): ', error);
    });
}

function addItem() {
    let selectValue = selectItems.value.split(",");
    let amountValue = amountItem.value;
    
    console.log("selectItems: " + selectValue[2]);
    console.log("itemsPresent: " + itemsPresent);

    for (let i = 0; i < itemsPresent.length; i++) {
        
        
    }

    if(selectValue != "" && amountValue != "") {
        tContainer.classList.remove("modal");
        let tdName = document.createElement("td");
        let tdStock = document.createElement("td");
        let tdAmount = document.createElement("td");
        let tdDelete = document.createElement("td");

        tdName.textContent = selectValue[0];
        tdStock.textContent = selectValue[1];
        tdAmount.textContent = amountValue;

        let btnDelete = document.createElement("button");
        btnDelete.className = "btn btn-primary rounded-5"
        let iIcon = document.createElement("i");
        iIcon.className = "bi bi-trash3 text-white"
        btnDelete.appendChild(iIcon);
        tdDelete.appendChild(btnDelete)

        let tr = document.createElement("tr");

        tr.appendChild(tdName);
        tr.appendChild(tdStock);
        tr.appendChild(tdAmount);
        tr.appendChild(tdDelete);

        tBody.appendChild(tr);
        selectValue.push(parseInt(amountValue));
        itemsPresent.push(Array.of(selectValue));
    } else {
    }

}