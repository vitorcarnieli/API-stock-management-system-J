const divForShowStockGroups = document.getElementById("stock-group");

document.addEventListener("DOMContentLoaded", refresh());


function isUpperCase(str) {
    return str === str.toUpperCase();
}

function refresh() {
    //TODO IMPLEMENTAR O LINK COLOCAR FIND ALL
    fetch("http://127.0.0.1:8080/stock-group/find/all")
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
        for (let i = 0; i < data.length; i++) {
            let object = data[i];

            let title = object.name;
            let description = object.description;
            let itemsLenght = object.items.length;

            const aBtn = document.createElement("a")
            aBtn.className = "col-3 btn m-3 btn-success";
            aBtn.href = "http://127.0.0.1:8080/pages/stockGroup.html?id="+object.id;

            const h = document.createElement("h5");
            
            if(isUpperCase(title)) {
                title = title.toLowerCase();
                h.textContent = title;
                h.style.textTransform = "uppercase";
            } else {
                h.textContent = title;
            }

            const pDescription = document.createElement("p");

            if(isUpperCase(description)) {
                descriptione = description.toLowerCase();
                pDescription.textContent = description;
                pDescription.style.textTransform = "uppercase";
            } else {
                pDescription.textContent = description;
            }

            pDescription.className = "text-start";
            pDescription.style.fontSize = "12px";

            const pAmountItems = document.createElement("p");
            pAmountItems.textContent = "Contém " + itemsLenght + " itens."
            pAmountItems.className = "text-start fst-italic";


            aBtn.appendChild(h);
            aBtn.appendChild(pAmountItems);
            aBtn.appendChild(pDescription)
            divForShowStockGroups.appendChild(aBtn);

        }
    })
    .catch((error) => {
        console.error('Erro ao fazer a solicitação:', error);
    });
}

function passToNextWindow() {
    return 
}