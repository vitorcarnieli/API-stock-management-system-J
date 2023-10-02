const stockName = document.getElementById("stockName"); //TODO: colocar nome do stockGroup
const ul = document.getElementById("ul"); //TODO: colocar todos os items e o link para a modal deles
const tbody = document.getElementById("tbody");
const trashBtn = document.getElementById("trashBtn");
const actionBtn = document.getElementById("actionBtn");
const all = document.getElementById("all"); //TODO: implementar click do mouse select un
const high = document.getElementById("high"); //TODO: implementar click do mouse select un
const low = document.getElementById("low"); //TODO: implementar click do mouse select un
const register = document.getElementById("register");
const urlParam = new URLSearchParams(window.location.search);
const deleteConfirm = document.getElementById("deleteConfirm");
const myModal = new bootstrap.Modal(document.getElementById('myModal'));
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));




all.addEventListener("click", function () {
    select(all, high, low);
    refresh();
});

high.addEventListener("click", function () {
    select(high, all, low);
    refresh();
});

low.addEventListener("click", function () {
    select(low, high, all)
    refresh();
});

document.addEventListener("DOMContentLoaded", function() {
    refresh();
});

trashBtn.addEventListener("click", function() {
    deleteModal.show()
});

deleteConfirm.addEventListener("click", function() {
    fet
})

register.addEventListener("click", function() {
    let c1 = document.getElementById("name");
    let c2 = document.getElementById("description");
    let c3 = document.getElementById("unitType");
    let c4 = document.getElementById("amount");
    let object = {
        name:c1.value,
        description:c2.value,
        unitType:c3.value,
        amount:c4.value
    }
    fetch("http://localhost:8080/stock-group/addItem/" + urlParam.get("id"),
    {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(object)
    })
    .then(function (res) {
        myModal.hide()


        refresh();
        
    })
    .catch(function (res) { console.log(res) })


});

actionBtn.addEventListener("click", function() {
    let c1 = document.getElementById("name");
    let c2 = document.getElementById("description");
    let c3 = document.getElementById("unitType");
    let c4 = document.getElementById("amount");
    c1.value = "";
    c2.value = "";
    c3.value = "";
    c4.value = "";

    myModal.show();
})

function select(field, neighboringField1, neighboringField2) {
    if (field.classList.contains("select")) {
        return;
    } else {
        field.classList.add("select");
        field.classList.remove("unselect");
        neighboringField1.classList.remove("select");
        neighboringField1.classList.add("unselect");
        neighboringField2.classList.remove("select");
        neighboringField2.classList.add("unselect");
    }
}

function constructLateralBar(object) {
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }
    
    let d = document.createElement("div");
    d.classList = "text-center";
    let i = document.createElement("i");
    i.classList = "bi bi-collection text-white fs-4 mx-3";
    let text = document.createElement("span");
    text.textContent = "Itens";
    text.classList = "text-white h2 mb-5 mt-3 text-center"
    d.appendChild(i)
    d.appendChild(text)
    ul.appendChild(d);

    for (let i = 0; i < object.items.length; i++) {
        
        let li = document.createElement("li");
        li.classList = "p-1 ms-3 my-3 entitys d-block";
        let aa = document.createElement("a");
        aa.href = "";
        aa.textContent = object.items[i].name;
        aa.classList = "link-light fst-italic d-block";
        li.appendChild(aa);
        
        ul.appendChild(li);
        

    }
    
}

function constructorAll(object) {
    trashBtn.classList.add("disabled");
    trashBtn.classList.remove("btn-danger");
    trashBtn.classList.remove("text-white");
    actionBtn.classList.remove("disabled");

    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
    
    
    stockName.textContent = object.name;
    let trHeader = document.createElement("tr");
    let td1 = document.createElement("td");
    td1.textContent = "Selecionar";
    td1.classList = "text-muted";
    td1.style.fontSize = "14px";
    
    let td2 = document.createElement("td");
    td2.textContent = "Nome";
    td2.classList = "text-muted";
    td2.style.fontSize = "14px";

    let td3 = document.createElement("td");
    td3.textContent = "Quantidade";
    td3.classList = "text-muted";
    td3.style.fontSize = "14px";
    
    trHeader.classList = "text-center";
    trHeader.appendChild(td1);
    trHeader.appendChild(td2);
    trHeader.appendChild(td3);
    tbody.appendChild(trHeader)
    

    
    for (let i = 0; i < object.items.length; i++) {
        
        let data = object.items[i];

        let td1 = document.createElement("td");
        td1.classList.add("text-center");

        let inp = document.createElement("input");
        inp.value = data.id;
        inp.type = "checkbox";

        td1.appendChild(inp);

        let td2 = document.createElement("td");
        td2.classList.add("fs-5");
        let a = document.createElement("a");
        a.textContent = data.name;
        a.href = "";
        td2.appendChild(a);

        let td3 = document.createElement("td");
        td3.classList.add("fs-5");
        td3.textContent = data.amount;
        td3.classList = "text-muted";

        let tr = document.createElement("tr");

        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);

        tbody.appendChild(tr);

    }

    var checkboxes = document.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            // Verifique se o checkbox atual está marcado
            if (this.checked) {
                trashBtn.classList.remove("disabled");
                trashBtn.classList.add("btn-danger");
                trashBtn.classList.add("text-white");

                checkboxes.forEach(function(otherCheckbox) {
                    if (otherCheckbox !== checkbox) {
                        otherCheckbox.checked = false;
                    }
                });
            } else {
                trashBtn.classList.add("disabled");
                trashBtn.classList.remove("btn-danger");
                trashBtn.classList.remove("text-white");



            }
        });
    });
}


function refresh() {
    fetch("http://localhost:8080/stock-group/" + urlParam.get("id"))
    .then((response) => {
        if (!response.ok) {
            throw new Error("Erro na resposta: " + response.status);
        }
        return response.json();
    })
    .then((data) => {
        constructLateralBar(data);
        if (high.classList.contains("select")) {
            console.log(data)

            for(let i = 0; i < data.items.length; i++) {
                let item = data.items[i];
                if(item.amount <= 0) {
                    data.items.pop(i,1);
                }
            }
            constructorAll(data);

        } else if (low.classList.contains("select")) {

            for(let i = 0; i < data.items.length; i++) {
                let item = data.items[i];
                console.log(item.amount > 0)
                console.log(item)
                if(item.amount > 0) {
                    console.log("entrou no " + i)
                    data.items.splice(i,1);
                }
            }
            constructorAll(data);
            var capeta = []
            capeta.pop()
        } else {

            constructorAll(data);
        }
        
    })
}
