const title = document.getElementById("title");
const totalItems = document.getElementById("total-items")
const stockName = document.getElementById("stock-group-name");
const description = document.getElementById("stock-group-description");
const tbody = document.getElementById("tbody")

const urlParam = new URLSearchParams(window.location.search)
console.log(urlParam)

document.addEventListener("DOMContentLoaded", refresh())


function refresh() {
    fetch("http://127.0.0.1:8080/stock-group/find/byId?id="+urlParam.get("id"))
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
            let object = data;
            title.textContent = object.name
            stockName.textContent = object.name
            description.textContent = object.description
            totalItems.textContent = "TOTAL DE ITENS: " + object.items.length
            
            
            
            console.log(object.items.length)
            
            for (let ii = 0; ii < object.items.length; ii++) {
                let tr = document.createElement("tr");
                let tdName = document.createElement("td");
                let tdUnitType = document.createElement("td");
                let tdAmount = document.createElement("td");
                let tdMore = document.createElement("td");
                let tdLess = document.createElement("td");
                let item = object.items[ii];
                console.log(item)
                tdName.textContent = item.name;
                tdUnitType.textContent = item.unitType;
                tdAmount.textContent = item.amount;
                tdMore.textContent = "+";
                tdLess.textContent = "-";
                tr.appendChild(tdName);
                tr.appendChild(tdUnitType);
                tr.appendChild(tdAmount);
                tr.appendChild(tdMore);
                tr.appendChild(tdLess);

                tbody.appendChild(tr);
                
            }



            
    })
    .catch((error) => {
        console.error('Erro ao fazer a solicitação:', error);
    });
}

