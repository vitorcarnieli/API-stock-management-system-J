const urlParam = new URLSearchParams(window.location.search)
const orderName = document.getElementById("orderName");
const observation = document.getElementById("description");
const tbody = document.getElementById("tbody");
const deleteOrder = document.getElementById("deleteItem");
const deleteButton = document.getElementById("delete");
const cancelButton = document.getElementById("cancel")

document.addEventListener("DOMContentLoaded", createPage());

deleteOrder.addEventListener("click", function (event) {
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
    fetch("http://127.0.0.1:8080/order/find/by/id?id=" + urlParam.get("id"))
    .then((response) => {
        if (!response.ok) {
            throw new Error("error no response" + response.status);
        }
        return response.json();
    })
    .then((data) => {
        let object = data;
        console.log(data)
        orderName.textContent = object.name
        observation.textContent = object.observation
        createTable(object);
    })
    .catch((error) => {
        console.error('Erro ao fazer a solicitação:', error);
    });
}

function createTable(data) {
    let requests = data.requests;
    for (let i = 0; i < requests.length; i++) {
        let request = requests[i]
        let tr = document.createElement("tr");

        let tdName = document.createElement("td");
        tdName.textContent = request.itemName;
        let tdStockGroup = document.createElement("td");
        tdStockGroup.textContent = request.stockGroupItemName;
        let tdAmount = document.createElement("td");
        tdAmount.textContent = request.requiredAmount;

        tr.appendChild(tdName);
        tr.appendChild(tdStockGroup);
        tr.appendChild(tdAmount);

        tbody.appendChild(tr); 
    }
}

function destroy() {
    fetch("http://127.0.0.1:8080/order/delete?id=" + urlParam.get("id"), 
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
            window.location.href = "http://127.0.0.1:8080/pages/schoolPage.html?id=" + data.id;
        })
        .catch((error) => {
        });
}