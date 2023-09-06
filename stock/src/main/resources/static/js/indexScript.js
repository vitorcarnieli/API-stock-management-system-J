const divForShowStockGroups = document.getElementById("stock-group");

document.addEventListener("DOMContentLoaded", refresh());


function isUpperCase(str) {
    let strU = str.toUpperCase()
    return str === strU;
}

function refresh() {
    //TODO IMPLEMENTAR O LINK COLOCAR FIND ALL
    fetch("http://127.0.0.1:8080/stock-group/getNames")
    .then((response) => {
        if (!response.ok) {
            throw new Error("error no response" + response.status);
        }
        console.log(response)
        console.log(response.json)
        return response.json();
    })
    .then((data) => {
        for (let i = 0; i < data.length; i++) {
            console.log(i)
            if (i >= data.length || i + 1 >= data.length || i % 2 != 0) {
                // Verificar se os elementos existem antes de usá-los
                continue;
            }
            console.log(data)
            var title = data[i];
            var description = data[i+1];

            const aBtn = document.createElement("a")
            aBtn.className = "col-3 btn m-3 btn-success";
            

            const h = document.createElement("h5");
            
            console.log(title)
            console.log(description)
            if(isUpperCase(title)) {
                title = title.toLowerCase();
                h.textContent = title;
                h.style.textTransform = "uppercase";
            } else {
                h.textContent = title;
            }

            const p = document.createElement("p");
            
            if(isUpperCase(description)) {
                description = description.toLowerCase();
                p.textContent = description;
                p.style.textTransform = "uppercase";
            } else {
                p.textContent = description;
            }
        
            p.style.fontSize = "10px";
            p.className = "text-start";
            aBtn.appendChild(h);
            aBtn.appendChild(p);
            divForShowStockGroups.appendChild(aBtn);
        }        
    })
    .catch((error) => {
        console.error('Erro ao fazer a solicitação:', error);
    });
}