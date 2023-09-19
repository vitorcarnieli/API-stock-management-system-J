const urlParam = new URLSearchParams(window.location.search)
const orderName = document.getElementById("orderName");
const description = document.getElementById("description");
const tbody = document.getElementById("tbody");

document.addEventListener("DOMContentLoaded", createPage())

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
        orderName.textContent = object.name
        description.textContent = object.description
        createTable(object);
    })
    .catch((error) => {
        console.error('Erro ao fazer a solicitação:', error);
    });
}

function createTable(data) {
    console.log(data)
    for (let i = 0; i < data.requests.length; i++) {
        let request = data.requests[i];
        
        let tr = document.createElement("tr");
        let tdItem = document.createElement("td");
        let tdStockGroup = document.createElement("td");
        let tdAmount = document.createElement("td");

        
    }


}