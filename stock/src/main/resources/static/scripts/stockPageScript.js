const stockName = document.getElementById("stockName"); //TODO: colocar nome do stockGroup
const ul = document.getElementById("ul"); //TODO: colocar todos os items e o link para a modal deles
const tbody = document.getElementById("tbody");
const trashBtn = document.getElementById("trashBtn");
const actionBtn = document.getElementById("actionBtn");
const all = document.getElementById("all"); //TODO: implementar click do mouse select un
const high = document.getElementById("high"); //TODO: implementar click do mouse select un
const low = document.getElementById("low"); //TODO: implementar click do mouse select un
const urlParam = new URLSearchParams(window.location.search);
const myModal = new bootstrap.Modal(document.getElementById('myModal'));



all.addEventListener("click", function () {
    select(all, high, low)
});

high.addEventListener("click", function () {
    select(high, all, low)
});

low.addEventListener("click", function () {
    select(low, high, all)
});

document.addEventListener("DOMContentLoaded", function() {
    refresh();
});

actionBtn.addEventListener("click", function() {
    let c1 = document.getElementById("campo1");
    let c2 = document.getElementById("campo2");
    let c3 = document.getElementById("campo3");
    let c4 = document.getElementById("campo4");
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

function constructorAll(object) {
    trashBtn.classList.add("disabled");
    trashBtn.classList.remove("btn-danger");
    trashBtn.classList.remove("text-white");
    actionBtn.classList.remove("disabled");

    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
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
    ul.appendChild(d)

    stockName.textContent = object.name;

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

        let tr = document.createElement("tr");

        tr.appendChild(td1);
        tr.appendChild(td2);

        tbody.appendChild(tr);


        let li = document.createElement("li");
        li.classList = "p-1 ms-3 my-3 entitys d-block";
        let aa = document.createElement("a");
        aa.href = "";
        aa.textContent = data.name;
        aa.classList = "link-light fst-italic d-block";
        li.appendChild(aa);

        ul.appendChild(li);
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
        console.log(data)
        constructorAll(data);
    })
}