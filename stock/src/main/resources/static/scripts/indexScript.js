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
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const deleteConfirm = document.getElementById("deleteConfirm");


//TODO: Implementar modal ao clickar em visao geral


document.addEventListener("DOMContentLoaded", function () {
    refresh();

})

trashBtn.addEventListener("click", function () {
    deleteModal.show()
});

deleteConfirm.addEventListener("click", function () {
    let checkboxes = document.querySelectorAll("input[type='checkbox']")
    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {


            fetch("http://192.168.0.157:8080/stock-group/" + checkbox.value, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            })
                .then(response => {
                    if (!response.ok) {
                        alert("Erro ao deletar, por favor, tente novamente. (Caso o erro persista, entre em contato com o suporte)");
                        throw new Error('Erro na solicitação DELETE');
                    }
                    refresh();
                    trashBtn.classList.add("disabled");
                    trashBtn.classList.remove("btn-danger");
                    trashBtn.classList.remove("text-white");
                    deleteModal.hide();
                })
                .catch(error => {
                    console.error('Erro na solicitação DELETE:', error);
                });

        }
    });
})


nome.addEventListener("input", function () {
    if (nome.value != "") {
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



actionBtn.addEventListener("click", function () {
    if (stockGroup.classList.contains("select")) {
        modal.show();
    } else {
        let checkboxes = document.querySelectorAll('input[type="checkbox"]');
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                console.log("http://192.168.0.157/institution/" + checkboxes[i].value)
                fetch("http://192.168.0.157/institution/" + checkboxes[i].value)
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

createStock.addEventListener("click", function () {
    let name = document.getElementById("newName");
    let description = document.getElementById("newDescription");
    if (name.value != "") {
        fetch("http://192.168.0.157:8080/stock-group",
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


        let tdCheckbox = document.createElement("td");
        tdCheckbox.classList.add("text-center");
        let inp = document.createElement("input");
        inp.value = data.id;
        inp.type = "checkbox";
        tdCheckbox.appendChild(inp);


        let tdItemName = document.createElement("td");
        tdItemName.classList = "fs-5 entitys-items item";
        tdItemName.id = data.id;
        let a = document.createElement("span");
        a.textContent = data.name;
        a.classList = "text-primary"
        tdItemName.appendChild(a);

        let trBody = document.createElement("tr");

        trBody.appendChild(tdCheckbox);
        trBody.appendChild(tdItemName);

        tbody.appendChild(trBody);
    }
    let items = document.querySelectorAll(".item")
    items.forEach(function (item) {
        item.addEventListener('click', function () {
            let a = document.createElement("a");
            a.href = "http://192.168.0.157:8080/pages/stock.html?id=" + item.id;
            a.click();
        });
    });
    let d = document.createElement("div");
    d.classList = "text-center p-3 ";
    let i = document.createElement("i");
    i.classList = "bi bi-buildings text-white fs-3 p-0 m-0";
    let text = document.createElement("span");
    text.textContent = " Instituições";
    text.classList = "text-white h3 fw-light p-0 m-0";
    d.appendChild(i);
    d.appendChild(text);
    ul.appendChild(d);

    actionBtn.textContent = "NOVO ESTOQUE";
    trashBtn.classList.remove("d-none");
    actionBtn.classList.remove("d-none");


    for (let i = 0; i < object[1].length; i++) {
        let data = object[1][i];
        let li = document.createElement("li");
        li.classList = "p-2 mb-2 text-center entitys";
        let aa = document.createElement("a");
        aa.href = "http://192.168.0.157:8080/pages/instituition.html?id=" + data.id;
        aa.textContent = " " + data.name;
        aa.classList = "link-light link-underline-opacity-0 fs-6";
        let i2 = document.createElement("i");
        i2.classList = "bi bi-building text-white p-0 m-0";
        i2.style.fontSize = "13px";
        li.appendChild(i2);
        li.appendChild(aa);
        li.addEventListener("click", function () {
            aa.click();
        })
        ul.appendChild(li);
    }
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            // Verifique se o checkbox atual está marcado
            if (this.checked) {
                trashBtn.classList.remove("disabled");
                trashBtn.classList.add("btn-danger");
                trashBtn.classList.add("text-white");

                checkboxes.forEach(function (otherCheckbox) {
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

console.log("aaa");
function constructorInstituitions(object) {
    console.log("aaa");
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }
    while (ul.firstChild) {
        ul.removeChild(ul.firstChild);
    }
    console.log(object)
    for (let i = 0; i < object[1].length; i++) {
        let data = object[1][i];

        let tdCheckbox = document.createElement("td");
        tdCheckbox.classList.add("text-center");


        let tdItemName = document.createElement("td");
        tdItemName.classList = "fs-5 entitys-items item";
        tdItemName.id = data.id;
        let a = document.createElement("span");
        a.textContent = data.name;
        a.classList = "text-primary"
        tdItemName.appendChild(a);

        let trBody = document.createElement("tr");

        trBody.appendChild(tdCheckbox);
        trBody.appendChild(tdItemName);

        tbody.appendChild(trBody);

    }
    let items = document.querySelectorAll(".item")
    items.forEach(function (item) {
        item.addEventListener('click', function () {
            let a = document.createElement("a");
            a.href = "http://192.168.0.157:8080/pages/instituition.html?id=" + item.id;
            a.click();
        });
    });


    let d = document.createElement("div");
    d.classList = "text-center p-3 ";
    let i = document.createElement("i");//bi bi-inbox
    i.classList = "bi bi-box-seam text-white fs-3 p-0 m-0";
    let text = document.createElement("span");
    text.textContent = " Estoques";
    text.classList = "text-white h3 fw-light p-0 m-0";
    d.appendChild(i);
    d.appendChild(text);
    ul.appendChild(d);

    actionBtn.textContent = "VISÃO GERAL";
    actionBtn.classList = "disabled d-none btn rounded-0 text-white fw-bold mx-3";
    trashBtn.classList.add("d-none");


    for (let i = 0; i < object[0].length; i++) {

        let data = object[0][i];
        let li = document.createElement("li");
        li.classList = "p-2 mb-2 text-center entitys";
        let aa = document.createElement("a");
        aa.href = "http://192.168.0.157:8080/pages/stock.html?id=" + data.id;
        aa.textContent = " " + data.name;
        aa.classList = "link-light link-underline-opacity-0 fs-6";
        let i2 = document.createElement("i");
        i2.classList = "bi bi-inboxes text-white p-0 m-0";
        i2.style.fontSize = "13px";
        li.appendChild(i2);
        li.appendChild(aa);
        li.addEventListener("click", function () {
            aa.click()
        })
        ul.appendChild(li);


    }

    var checkboxes = document.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                actionBtn.classList.remove("disabled");
                checkboxes.forEach(function (otherCheckbox) {
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

    fetch("http://192.168.0.157:8080/stock-group")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erro na resposta: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            datas.push(data);

            return fetch("http://192.168.0.157:8080/institution/find/all");
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Erro na resposta: " + response.status);
            }
            return response.json();
        })
        .then((data) => {
            datas.push(data);
            if (stockGroup.classList.contains("select")) {
                console.log(datas)

                constructorStockGroups(datas)
            } else {
                console.log(datas)
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