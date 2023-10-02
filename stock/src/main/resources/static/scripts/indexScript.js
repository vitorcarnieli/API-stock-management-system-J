const ul = document.getElementById("ul");
const stockGroup = document.getElementById("stockGroup");
const instituitions = document.getElementById("instituitions");
const tbody = document.getElementById("tbody");
const actionBtn = document.getElementById("actionBtn");
const trashBtn = document.getElementById("trashBtn");
const createStock = document.getElementById("createStock");
const nome = document.getElementById("newName");
const description = document.getElementById("newDescription");
const modal = new bootstrap.Modal(document.getElementById('modalForm'));
const modalInstituition = new bootstrap.Modal(document.getElementById('modalInstituition'));

//TODO: Implementar modal ao clickar em visao geral


document.addEventListener("DOMContentLoaded", function () {
    refresh();

})

trashBtn.addEventListener("click", function () {

    let checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {

            fetch("http://localhost:8080/stock-group/delete?idGroup=" + checkboxes[i].value)
                .then((response) => {

                })
                .then((data) => {
                    refresh();
                })
                .catch((error) => {
                });

        }
    }

})

nome.addEventListener("input", function() {
    if(nome.value != "") {
        createStock.classList.remove("disabled");
    } else {
        createStock.classList.add("disabled");
    }
})

stockGroup.addEventListener("click", function () {
    select(stockGroup, instituitions)
    refresh();
});
instituitions.addEventListener("click", function () {
    select(instituitions, stockGroup)
    refresh();
});

actionBtn.addEventListener("click", function() {
    if(stockGroup.classList.contains("select")) {
        modal.show();
    } else {
        let checkboxes = document.querySelectorAll('input[type="checkbox"]');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                console.log("http://127.0.0.1:8080/institution/" + checkboxes[i].value)
                fetch("http://127.0.0.1:8080/institution/" + checkboxes[i].value)
                    .then((response) => {
                        return response.json();
                    })
                    .then((data) => {
                        let modalInstituitionBody = document.getElementById("modalInstituitionBody");
                        while (modalInstituitionBody.firstChild) {
                            modalInstituitionBody.removeChild(modalInstituitionBody.firstChild);
                        }

                        //

                        let instituitionName = document.getElementById("instituitionName");
                        instituitionName.textContent = data.name;

                        //

                        let divResponsible = document.createElement("div");
                        divResponsible.classList = "form-floating mb-3";
                        
                        let inputResponsibleName = document.createElement("input");
                        inputResponsibleName.type = "text";
                        inputResponsibleName.classList = "form-control";
                        inputResponsibleName.disabled = true;
                        inputResponsibleName.id = "responsible";
                        inputResponsibleName.value = data.responsible;

                        let labelResposible = document.createElement("label");
                        labelResposible.for = "responsible";
                        labelResposible.textContent = "Responsável";
                        divResponsible.appendChild(inputResponsibleName);
                        divResponsible.appendChild(labelResposible);

                        //

                        let divEmail = document.createElement("div");
                        divEmail.classList = "form-floating mb-3";
                        
                        let inputEmail = document.createElement("input");
                        inputEmail.type = "email";
                        inputEmail.classList = "form-control";
                        inputEmail.disabled = true;
                        inputEmail.id = "email";
                        inputEmail.value = data.email;

                        let labelEmail = document.createElement("label");
                        labelEmail.for = "email";
                        labelEmail.textContent = "E-mail";
                        divEmail.appendChild(inputEmail);
                        divEmail.appendChild(labelEmail);

                        //

                        let divPhone = document.createElement("div");
                        divPhone.classList = "form-floating mb-3";
                        
                        let inputPhone = document.createElement("input");
                        inputPhone.type = "text";
                        inputPhone.classList = "form-control";
                        inputPhone.disabled = true;
                        inputPhone.id = "phone";
                        inputPhone.value = data.contatcPhone;

                        let labelPhone = document.createElement("label");
                        labelPhone.for = "phone";
                        labelPhone.textContent = "Telefone de contato";
                        divPhone.appendChild(inputPhone);
                        divPhone.appendChild(labelPhone);

                        //

                        let divAdress = document.createElement("div");
                        divAdress.classList = "form-floating mb-3";
                        
                        let inputAdress = document.createElement("input");
                        inputAdress.type = "text";
                        inputAdress.classList = "form-control";
                        inputAdress.disabled = true;
                        inputAdress.id = "adress";
                        inputAdress.value = data.adress;

                        let labelAdress = document.createElement("label");
                        labelAdress.for = "adress";
                        labelAdress.textContent = "Endereço";
                        divAdress.appendChild(inputAdress);
                        divAdress.appendChild(labelAdress);

                        //

                        modalInstituitionBody.appendChild(divResponsible);
                        modalInstituitionBody.appendChild(divEmail);
                        modalInstituitionBody.appendChild(divPhone);
                        modalInstituitionBody.appendChild(divAdress);
                        modalInstituitionBody.classList = "p-3";

                    })
                    .catch((error) => {
                        console.log(error);
                    });
            }
        }

        modalInstituition.show();
    }
})

createStock.addEventListener("click", function() {
    let name = document.getElementById("newName");
    let description = document.getElementById("newDescription");
    if(name.value != "") {
        fetch("http://localhost:8080/stock-group",
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify({
                    name: name.value,
                    description: description.value,
                })
            })
            .then(function (res) {
                modal.hide()
                name.value = ""
                createStock.classList.add("disabled");

                refresh();
                
            })
            .catch(function (res) { console.log(res) })
    }
})


function select(field, neighboringField) {
    if (field.classList.contains("select")) {
        return;
    } else {
        field.classList.add("select");
        field.classList.remove("unselect");
        neighboringField.classList.remove("select");
        neighboringField.classList.add("unselect");
    }
}

function constructorStockGroups(object) {
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


    for (let i = 0; i < object[0].length; i++) {
        let data = object[0][i];

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
        a.href = "http://127.0.0.1:8080/pages/stockPage.html?id=" + data.id;
        td2.appendChild(a);

        let tr = document.createElement("tr");

        tr.appendChild(td1);
        tr.appendChild(td2);

        tbody.appendChild(tr);
    }

    let i = document.createElement("i");
    i.classList = "bi bi-buildings text-white fs-4 mx-3";
    let text = document.createElement("span");
    text.textContent = "Instituições";
    text.classList = "text-white h3 mb-5 mt-3 text-center"
    ul.appendChild(i)
    ul.appendChild(text)

    actionBtn.textContent = "NOVO ESTOQUE";
    trashBtn.classList.remove("d-none");


    for (let i = 0; i < object[1].length; i++) {
        let data = object[1][i];
        let li = document.createElement("li");
        li.classList = "p-1 ms-3 my-3 entitys d-block";
        let a = document.createElement("a");
        a.href = "";
        a.textContent = data.name;
        a.classList = "link-light fst-italic d-block";
        li.appendChild(a);

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


function constructorInstituitions(object) {
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }

    for (let i = 0; i < object[1].length; i++) {
        let data = object[1][i];

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
    }

    let i = document.createElement("i");
    i.classList = "bi bi-box-seam text-white fs-4 mx-3";
    let text = document.createElement("span");
    text.textContent = "Estoques";
    text.classList = "text-white h2 mb-5 mt-3 text-center"
    ul.appendChild(i)
    ul.appendChild(text)

    actionBtn.textContent = "VISÃO GERAL";
    actionBtn.classList.add("disabled");
    trashBtn.classList.add("d-none");


    for (let i = 0; i < object[0].length; i++) {
        let data = object[0][i];
        let li = document.createElement("li");
        li.classList = "p-1 ms-3 my-3 entitys d-block";
        let a = document.createElement("a");
        a.href = "";
        a.textContent = data.name;
        a.classList = "link-light fst-italic d-block";
        li.appendChild(a);

        ul.appendChild(li);
    }

    var checkboxes = document.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            if (this.checked) {
                actionBtn.classList.remove("disabled");
                checkboxes.forEach(function(otherCheckbox) {
                    if (otherCheckbox !== checkbox) {
                        otherCheckbox.checked = false;
                    }
                });
            } else {
                actionBtn.classList.add("disabled");

            }
        });
    });
}

function refresh() {
    let datas = [];

    fetch("http://localhost:8080/stock-group")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erro na resposta: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            datas.push(data);
    
            return fetch("http://localhost:8080/institution/find/all");
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erro na resposta: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            datas.push(data);
            if(stockGroup.classList.contains("select")) {
                constructorStockGroups(datas)
            } else {
                constructorInstituitions(datas)
            }
        })
        .catch((error) => {
            console.error('Erro ao fazer a solicitação:', error);
        });

}

function newStock() {
    var modal = new bootstrap.Modal(document.getElementById('modalForm'));
}