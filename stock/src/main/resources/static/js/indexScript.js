const divForShowStockGroups = document.getElementById("stock-group");
const divForShowInstitution = document.getElementById("institution-group");
const downloadBtn = document.getElementById("downloadReport");

document.addEventListener("DOMContentLoaded", refresh);
console.log("btn")
console.log(downloadBtn)
downloadBtn.addEventListener("click", function () {
    fetch("http://localhost:8080/stock-group/report", {
        method: 'GET',
    })
        .then((response) => {
            if (response.ok) {
                return response.json(); // Converte a resposta em JSON
            } else {
                throw new Error('Erro ao gerar relatório');
            }
        })
        .then((data) => {
            const [filePath, fileName] = data; // Desestrutura a lista retornada
            const normalizedPath = filePath.replace(/\\/g, '/'); // Substitui as barras invertidas por barras normais
            console.log(fileName)
            console.log(normalizedPath + fileName)
            const url = normalizedPath + fileName; // Cria a URL completa do arquivo
            const aaa = document.createElement('a');
            aaa.href = "file:///" + normalizedPath;
            aaa.download = fileName;
            aaa.click();
        })
        .catch((error) => {
            console.error('Erro:', error);
        });
})

function isUpperCase(str) {
    return str === str.toUpperCase();
}

//TODO: IMPLEMENTAR RELATÓRIO, PROVAVELMENTE UM GERAÇÃO DE PDF OU QUALQUER DOCUMENTO DO TIPO NO BACK

function refresh() {
    fetch("http://localhost:8080/stock-group")
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
                aBtn.className = "col-3 btn m-3 text-white";
                aBtn.style = "background-color: #FF9CA9;"
                aBtn.href = "http://localhost:8080/pages/stockGroup.html?id=" + object.id;

                const h = document.createElement("h5");

                if (isUpperCase(title)) {
                    title = title.toLowerCase();
                    h.textContent = title;
                    h.style.textTransform = "uppercase";
                } else {
                    h.textContent = title;
                }

                const pDescription = document.createElement("p");

                if (isUpperCase(description)) {
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
    fetch("http://localhost:8080/institution/find/all")
        .then((response) => {
            console.log(response)
            console.log(response.json)
            return response.json();
        })
        .then((data) => {
            console.log(data)
            for (let i = 0; i < data.length; i++) {
                let object = data[i];

                let title = object.name;

                const aBtn = document.createElement("a")
                aBtn.className = "col-3 btn m-3 text-white";
                aBtn.style = "background-color: #FF9CA9;"
                aBtn.href = "http://localhost:8080/pages/institutionPage.html?id=" + object.id;

                const h = document.createElement("h5");

                if (isUpperCase(title)) {
                    title = title.toLowerCase();
                    h.textContent = title;
                    h.style.textTransform = "uppercase";
                } else {
                    h.textContent = title;
                }


                const pAmountRequests = document.createElement("p");
                pAmountRequests.textContent = "Contém " + object.orders.length + " pedidos."
                pAmountRequests.className = "text-start fst-italic";


                aBtn.appendChild(h);
                aBtn.appendChild(pAmountRequests);
                divForShowInstitution.appendChild(aBtn);

            }
        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });

}

function passToNextWindow() {
    return
}