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
    console.log(selectValue)
    
    let children = tBody.children;
    for (let i = 0; i < children.length; i++) {
        if(children[i].id == selectValue[2]) {
            let amountRow = document.getElementById("amountRow");
            let finalAmount = parseInt(amountRow.textContent) + parseInt(amountValue);
            amountRow.textContent = finalAmount;
            return
        }
        
    }


    if(selectValue != "" && amountValue != "") {
        tContainer.classList.remove("modal");
        let tdName = document.createElement("td");
        tdName.id = "nameRow";
        let tdStock = document.createElement("td");
        tdStock.id = "stockRow"
        let tdAmount = document.createElement("td");
        tdAmount.id = "amountRow"
        let tdDelete = document.createElement("td");

        tdName.textContent = selectValue[1];
        tdStock.textContent = selectValue[0];
        tdAmount.textContent = amountValue;

        let btnDelete = document.createElement("button");
        btnDelete.className = "btn btn-danger rounded-5"
        let iIcon = document.createElement("i");
        iIcon.className = "bi bi-trash3 text-white"
        btnDelete.appendChild(iIcon);
        tdDelete.appendChild(btnDelete)

        let tr = document.createElement("tr");
        tr.id = selectValue[2]

        tr.appendChild(tdName);
        tr.appendChild(tdStock);
        tr.appendChild(tdAmount);
        tr.appendChild(tdDelete);

        tBody.appendChild(tr);

    } else {
    }

}