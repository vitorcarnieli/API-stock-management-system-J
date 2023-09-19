const urlParam = new URLSearchParams(window.location.search)
const nome = document.getElementById("nome");
const observations = document.getElementById("observations");
const inviteBtn = document.getElementById("invite");
const formCreateOrder = document.getElementById("form");
const formCreate = document.getElementById("form-create");
const formAdd = document.getElementById("form-add");
const tContainer = document.getElementById("table-container");
const tBody = document.getElementById("tbody")
const selectItems = document.getElementById("items");
const amountItem = document.getElementById("amount");
const btnAddItem = document.getElementById("add");
var botoesDeletar = document.querySelectorAll('.btn-danger');
var options = document.getElementsByTagName("option");
var itemsPresent = []

document.addEventListener("DOMContentLoaded", createPage);
btnAddItem.addEventListener("click", addItem);

inviteBtn.addEventListener("click", function(e) {
    e.preventDefault();
    createOrder();
})

selectItems.addEventListener("change", function(e) {
    var indiceSelecionado = selectItems.selectedIndex;
    var opcaoSelecionada = selectItems.options[indiceSelecionado];
    console.log(opcaoSelecionada.value)
    amountItem.placeholder = "Valor máximo: " + opcaoSelecionada.classList[0];
    amountItem.max = parseInt(opcaoSelecionada.classList[0]);
});

nome.addEventListener("input", function(e) {
    if(nome.value != "") {
        inviteBtn.disabled = false;
    } else {
        inviteBtn.disabled = true;
    }
})

tBody.addEventListener('click', function (event) {
    if (event.target.classList.contains('btn-danger')) {
        deleteRow.call(event.target);
        console.log(tBody.rows.length);
        if(tBody && tBody.rows.length == 0) {
            tContainer.classList.add("modal");
            observations.disabled = true;
            observations.value = null
            nome.disabled = true;
            nome.value = null
            inviteBtn.disabled = true;

        } else {
            tContainer.classList.remove("modal");
            observations.disabled = false;
            nome.disabled = false;
            inviteBtn.disabled = false;
        }
    }
});


function createPage() {
    fetch("http://127.0.0.1:8080/stock-group/find/all")
    .then((response) => {
        if (!response.ok) {throw new Error("error no response" + response.status);}
        return response.json();
    })
    .then((data) => {
        for (let i = 0; i < data.length; i++) {
            let stockGroup = data[i];

            let optionStockName = document.createElement("option");
            optionStockName.textContent = stockGroup.name;
            optionStockName.disabled = true;
            selectItems.appendChild(optionStockName);

            for (let ii = 0; ii < stockGroup.items.length; ii++) {
                let item = stockGroup.items[ii]
                let optionItemName = document.createElement("option");
                optionItemName.textContent = item.name;
                optionItemName.value = [stockGroup.name, item.name.replace(/,/g,"."), item.id, stockGroup.id]; 
                optionItemName.classList = item.amount;
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

    let children = tBody.children;
    for (let i = 0; i < children.length; i++) {
        if(children[i].id == selectValue[2]) {
            let amountRow = document.getElementById("amountRow" + selectValue[2]);
            let finalAmount = parseInt(amountRow.textContent) + parseInt(amountValue);
            amountRow.textContent = finalAmount;
            return
        }
        
    }


    if(selectValue != "" && amountValue != "") {
        observations.disabled = false;
        nome.disabled = false;
        tContainer.classList.remove("modal");
        let tdName = document.createElement("td");
        tdName.id = "nameRow" + selectValue[2];
        let tdStock = document.createElement("td");
        tdStock.id = "stockRow" + selectValue[2];
        tdStock.className = selectValue[3]
        let tdAmount = document.createElement("td");
        tdAmount.id = "amountRow" + selectValue[2];
        let tdDelete = document.createElement("td");

        tdName.textContent = selectValue[1];
        tdStock.textContent = selectValue[0];
        tdAmount.textContent = amountValue;

        let btnDelete = document.createElement("button");
        btnDelete.className = "btn btn-danger rounded-5";
        let iIcon = document.createElement("i");
        iIcon.className = "bi bi-trash3 text-white";
        btnDelete.id = selectValue[2];
        btnDelete.appendChild(iIcon);
        tdDelete.appendChild(btnDelete);

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


function createOrder() {
    let tds = tBody.getElementsByTagName("td");
    let finalOrder = [];
    let provisionalOrder = [];

    for (let i = 0; i < tds.length; i++) {
        let td = tds[i];
        if(td.id.includes("nameRow")) {
            provisionalOrder.push(td.id.replace("nameRow",""));
        } else if(td.id.includes("amountRow")) {
            provisionalOrder.push(td.textContent);
        }
        //[]
        if(provisionalOrder.length == 2) {
            finalOrder.push(provisionalOrder);
            provisionalOrder = [];
        }
    }

    let order = {
        name: nome.value,
        observations: observations.value,
        school: urlParam.get("id"),
        requests: finalOrder
        //[itemId, amount]
    }

    console.log(order)
    fetch("http://127.0.0.1:8080/order/create",
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify(order)
        })
        .then(function (res) {
            console.log(res);
            window.location.href = "http://127.0.0.1:8080/pages/schoolPage.html?id=" + urlParam.get("id");
        })
        .catch(function (res) { console.log(res) })
}






function deleteRow() {
    console.log("entrou")
    var row = this.closest('tr');
    if (row) {
      row.remove();
    }
}