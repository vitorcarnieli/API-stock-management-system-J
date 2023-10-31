const urlParam = new URLSearchParams(window.location.search);

// criação de página
const instituitionName = document.getElementById("instituitionName");
const ul = document.getElementById("ul");
const tbody = document.getElementById("tbody")
const deleteConfirm = document.getElementById("deleteConfirm");
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const trashBtn = document.getElementById("trashBtn");

// criação de página

//modal de criar order
const createOrderModal = new bootstrap.Modal(document.getElementById("createOrderModal"));
const nameOrderModal = document.getElementById("nameItem");
const descriptionOrderModal = document.getElementById("observation");
const actionBtn = document.getElementById("actionBtn");
const itemSelect = document.getElementById("items");
const tbodyModal = document.getElementById("tbodyModal");
const addItemInOrder = document.getElementById("add");
const addBtn = document.getElementById("add");
const amountItem = document.getElementById("amountItem");
const deleteModalBtn = document.getElementById("deleteModalBtn");
const observation = document.getElementById("observation");
const saveChanges = document.getElementById("saveChanges")
//modal de criar order

/*

*/

nameOrderModal.addEventListener("input", function() {
	disabledSalveChanges();
})

function disabledSalveChanges() {
	console.log(nameOrderModal.value.trim())
	if (nameOrderModal.value.trim() != "" && tbodyModal.children.length != 0) {
		saveChanges.disabled = false;
	} else {
		saveChanges.disabled = false;
	}
}

trashBtn.addEventListener("click", function() {
	deleteModal.show();
})

saveChanges.addEventListener("click", function() {
	createOrder();
})


deleteConfirm.addEventListener("click", function() {
	let checkboxes = document.querySelectorAll("input[type='checkbox']")
	checkboxes.forEach(function(checkbox) {
		if (checkbox.checked) {

			let requests = [];
			fetch("http://localhost:8080/order/" + checkbox.value)
				.then((response) => {
					if (!response.ok) {
						throw new Error("Erro na resposta: " + response.status);
					}
					return response.json();
				})
				.then((data) => {
					for (let i = 0; i < data.requests.length; i++) {
						let request = data.requests[i];
						requests.push(request);
					}


					for (let i = 0; i < data.requests.length; i++) {
						console.log(requests)
						let request = data.requests[i];

						let id = request.itemId;
						let change = request.requiredAmount;
						let instituitionId = data.institutionId
						console.log("http://localhost:8080/item/back-changes?idIt=" + parseInt(id) + "&idIn=" + parseInt(instituitionId) + "&c=" + parseInt(change))
						fetch("http://localhost:8080/item/back-changes?idIt=" + parseInt(id) + "&idIn=" + parseInt(instituitionId) + "&c=" + parseInt(change),
							{
								headers: {
									'Accept': 'application/json',
									'Content-Type': 'application/json'
								},
								method: "POST",

							})
							.then(function(res) {

							})
							.catch(function(res) {
							})
					}


					fetch("http://localhost:8080/order/" + checkbox.value, {
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
				})
			console.log("aaaa" + requests)

		}
	});
})


amountItem.addEventListener("input", function() {
	if (parseInt(amountItem.value) > parseInt(amountItem.max)) {
		amountItem.value = amountItem.max;
	}
	if (amountItem.value == "" || parseInt(amountItem.value) < 1) {
		addBtn.classList.add("disabled");
		return;
	} else {
		addBtn.classList.remove("disabled");
		return;
	}
})

document.addEventListener("DOMContentLoaded", refresh());

itemSelect.addEventListener("change", function() {
	amountItem.disabled = false;
})

addBtn.addEventListener("click", function() {
	let children = tbodyModal.children;
	for (let i = 0; i < children.length; i++) {
		let child = children[i];
		if (itemSelect.value.split("*")[1] == child.id) {
			if (parseInt(child.children[1].textContent) + parseInt(amountItem.value) > parseInt(amountItem.max)) {
				child.children[1].textContent = amountItem.max;
				itemSelect.value = "";
				amountItem.value = "";
				amountItem.placeholder = "";
				addBtn.classList.add("disabled");
				amountItem.disabled = true;
				return;
			}
			child.children[1].textContent = parseInt(child.children[1].textContent) + parseInt(amountItem.value);
			itemSelect.value = "";
			amountItem.value = "";
			amountItem.placeholder = "";
			addBtn.classList.add("disabled");
			amountItem.disabled = true;
			return;
		}
	}
	let tr = document.createElement("tr");
	tr.id = itemSelect.value.split("*")[1];
	tr.classList = "text-center";

	let tdNameItem = document.createElement("td");
	tdNameItem.textContent = itemSelect.value.split("*")[0];

	let tdAmountItem = document.createElement("td");
	tdAmountItem.textContent = amountItem.value;

	let tdDeleteItem = document.createElement("td");
	let btndel = document.createElement("button");
	btndel.addEventListener("click", function() {
		let father = btndel.closest("tr");
		console.log(father)
		let children = tbodyModal.children;
		for (let i = 0; i < children.length; i++) {
			let child = children[i];
			if (child.id == father.id) {
				tbodyModal.removeChild(child);
				for (let i = 0; i < itemSelect.children.length; i++) {
					let child2 = itemSelect.children[i];
					if (child2.id == father.id) {
						child2.disabled = false;
					}
				}
			}
		}
	})
	btndel.classList = "btn btn-danger"
	btndel.id = "deleteModalBtn";
	let i = document.createElement("i");
	i.classList = "bi bi-trash3";
	btndel.appendChild(i);
	tdDeleteItem.appendChild(btndel);

	let e = document.getElementById(itemSelect.value.split("*")[1]);
	let maxMinusNowValue = parseInt(amountItem.max) - parseInt(amountItem.value);
	console.log(maxMinusNowValue)
	console.log
	if (maxMinusNowValue <= 0) {
		e.disabled = true;
		e.alt = "maxima quantidade requerida"
	} else {
		e.disabled = false;
	}
	tr.appendChild(tdNameItem);
	tr.appendChild(tdAmountItem);
	tr.appendChild(tdDeleteItem);
	tbodyModal.appendChild(tr);
	itemSelect.value = "";
	amountItem.value = "";
	amountItem.placeholder = "";
	addBtn.classList.add("disabled");
	amountItem.disabled = true;
	disabledSalveChanges()

})

actionBtn.addEventListener("click", function() {
	disabledSalveChanges()
	
	while (items.firstChild) {
		
		items.removeChild(items.firstChild);
	}
	
	
	fetch("http://localhost:8080/stock-group")
		.then((response) => {
			if (!response.ok) {
				throw new Error("Erro na resposta: " + response.status);
			}
			return response.json();
		})
		.then((data) => {
			amountItem.ariaPlaceholder = "";
			addBtn.classList.add("disabled");
			let selectItem = document.createElement("option");
			selectItem.textContent = "Selecione um item";
			selectItem.selected = true;
			selectItem.disabled = true;
			itemSelect.appendChild(selectItem)
			for (let i = 0; i < data.length; i++) {
				let s = data[i];
				let stockNameOpt = document.createElement("option");
				stockNameOpt.classList = "bg-black text-light";
				stockNameOpt.style.backgroundColor = "black";
				stockNameOpt.disabled = true;
				stockNameOpt.textContent = "Estoque: " + s.name;
				itemSelect.appendChild(stockNameOpt);
				for (let j = 0; j < data[i].items.length; j++) {
					const item = data[i].items[j];
					let itemOpt = document.createElement("option");
					itemOpt.textContent = item.name;
					itemOpt.value = item.name + "*" + item.id;
					itemOpt.id = item.id;
					itemOpt.addEventListener("click", function() {
						let max = item.amount;
						amountItem.placeholder = "Máx: " + max;
						amountItem.min = 1;
						amountItem.max = max;
					})
					itemSelect.appendChild(itemOpt);
				}


			}

		})
	createOrderModal.show();
});

function refresh() {
	fetch("http://localhost:8080/institution/" + urlParam.get("id"))
		.then((response) => {
			if (!response.ok) {
				throw new Error("Erro na resposta: " + response.status);
			}
			return response.json();
		})
		.then((data) => {
			constructPage(data);
		})
}

function constructPage(data) {

	constructLateralBarAndHeader(data);
	constructMainTable(data);

	while (tbodyModal.firstChild) {
		tbodyModal.removeChild(tbodyModal.firstChild);
	}
	nameOrderModal.value = "";
	observation.value = "";
}

function constructLateralBarAndHeader(object) {
	while (ul.firstChild) {
		ul.removeChild(ul.firstChild);
	}
	instituitionName.textContent = object.name;

	let d = document.createElement("div");
	d.classList = "text-center p-3 ";
	let i = document.createElement("i");//bi bi-inbox
	i.classList = "bi bi-truck text-white fs-3 p-0 m-0";
	let text = document.createElement("span");
	text.textContent = " Pedidos";
	text.classList = "text-white h1 fw-light p-0 m-0";
	d.appendChild(i);
	d.appendChild(text);
	ul.appendChild(d);
	object.orders.sort(function(a, b) {
		return a.name < b.name ? -1 : a.name > b.name ? 1 : 0;
	});
	for (let i = 0; i < object.orders.length; i++) {

		let item = object.orders[i];

		let li = document.createElement("li");
		li.classList = " mb-2 text-center entitys item";
		li.id = item.id
		let aa = document.createElement("p");
		aa.textContent = " " + item.name;
		aa.classList = "link-light link-underline-opacity-0 fs-6 d-inline-block ms-2";
		let i2 = document.createElement("i");
		i2.classList = "bi bi-archive text-white p-0 m-0";
		i2.style.fontSize = "13px";
		li.appendChild(i2);
		li.appendChild(aa);
		ul.appendChild(li);
	}

}

function constructMainTable(object) {
	console.log(tbody)
	while (tbody.firstChild) {
		tbody.removeChild(tbody.firstChild);
	}

	let td1 = document.createElement("td");
	td1.textContent = "Selecionar";
	td1.classList = "text-muted";
	td1.style.fontSize = "14px";

	let td2 = document.createElement("td");
	td2.textContent = "Nome";
	td2.classList = "text-muted";
	td2.style.fontSize = "14px";

	let td3 = document.createElement("td");
	td3.textContent = "Data / Hora";
	td3.classList = "text-muted";
	td3.style.fontSize = "14px";

	let trHeader = document.createElement("tr");
	trHeader.classList = "text-center";
	trHeader.appendChild(td1);
	trHeader.appendChild(td2);
	trHeader.appendChild(td3);
	trHeader.classList = "border-bottom border-secondary";
	tbody.appendChild(trHeader);

	if (object.orders.length === 0) {
		let noneItem = document.createElement("tr");
		let td1 = document.createElement("td");

		let td2 = document.createElement("td");
		td2.textContent = "Nenhum pedido encontrado";
		td2.classList = "text-danger";
		td2.classList.add("fs-5");

		let td3 = document.createElement("td");

		noneItem.classList = "text-center";
		noneItem.appendChild(td1);
		noneItem.appendChild(td2);
		noneItem.appendChild(td3);
		tbody.appendChild(noneItem);
	}
	object.orders.sort();
	for (let i = 0; i < object.orders.length; i++) {
		let item = object.orders[i];

		let tdCheckbox = document.createElement("td");
		tdCheckbox.classList.add("text-center");
		let inp = document.createElement("input");
		inp.value = item.id;
		inp.type = "checkbox";
		tdCheckbox.appendChild(inp);


		let tdItemName = document.createElement("td");
		tdItemName.classList = "fs-5 entitys-items item";
		tdItemName.id = item.id;
		let a = document.createElement("span");
		a.textContent = item.name;
		a.classList = "text-primary"
		tdItemName.appendChild(a);


		let tdDate = document.createElement("td");
		tdDate.classList.add("fs-5");
		tdDate.textContent = item.dateFormated.replace("T", " ").replace(/-/g, "/");
		tdDate.classList = "text-muted";




		let trBody = document.createElement("tr");

		trBody.appendChild(tdCheckbox);
		trBody.appendChild(tdItemName);
		trBody.appendChild(tdDate);
		console.log("aaaaaaaaaaa")

		tbody.appendChild(trBody);
		console.log(tbody)
	}
	let checkboxes = document.querySelectorAll("input[type='checkbox']")
	checkboxes.forEach(function(checkbox) {
		checkbox.addEventListener('change', function() {
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

	let items = document.querySelectorAll(".item")
	items.forEach(function(item) {
		item.addEventListener('click', function() {
			constructOrderModal(item.id);
			console.log('chegou')
		});
	});
}

function createOrder() {

	let object = {
		institutionId: parseInt(urlParam.get("id")),
		nameOrder: nameOrderModal.value == "" ? null : nameOrderModal.value,
		descriptionOrder: descriptionOrderModal.value,
		itemsId: [],
		amounts: []
	}
	for (let i = 0; i < tbodyModal.children.length; i++) {
		object.itemsId.push(parseInt(tbodyModal.children[i].id));
		object.amounts.push(parseInt(tbodyModal.children[i].children[1].textContent));

	}
	fetch("http://localhost:8080/order/create",
		{
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
			method: "POST",
			body: JSON.stringify(object)
		})
		.then(function(res) {
			let id = "";
			createOrderModal.hide();
			refresh();
			fetch("http://localhost:8080/institution/find/orders/" + urlParam.get("id"))
				.then((response) => {
					if (!response.ok) {
						throw new Error("Erro na resposta: " + response.status);
					}
					return response.json();
				})
				.then((data) => {
					console.log(data)
					let len = data.length;
					id = data[len - 1].id;
					fetch("http://localhost:8080/order/proof/" + id)
						.then((response) => {
							if (!response.ok) {
								throw new Error("Erro na resposta: " + response.status);
							}
							return response.json();
						})
						.then((data) => {
							console.log("deu");
						})
				})
		})
		.catch(function(res) { console.log(res) })




}

function constructOrderModal(id) {
	fetch("http://localhost:8080/order/" + id)
		.then((response) => {
			if (!response.ok) {
				throw new Error("Erro na resposta: " + response.status);
			}
			return response.json();
		})
		.then((data) => {
			console.log(data)
			let tbodyOrder = document.getElementById("tbodyOrder");
			while (tbodyOrder.firstChild) {
				tbodyOrder.removeChild(tbodyOrder.firstChild);
			}
			let name = document.getElementById("nameOrder");
			let orderModal = new bootstrap.Modal(document.getElementById("orderModal"));
			let observationOrder = document.getElementById("observationOrder");
			name.value = data.name;
			name.disabled = true;
			observationOrder.value = data.observation;
			observationOrder.disabled = true;

			for (let i = 0; i < data.requests.length; i++) {
				let request = data.requests[i];

				let tr = document.createElement("tr");

				let tdS = document.createElement("td");
				tdS.textContent = request.stockGroupItemName;

				let tdN = document.createElement("td");
				tdN.textContent = request.itemName;

				let tdA = document.createElement("td");
				tdA.textContent = request.requiredAmount;

				tr.appendChild(tdS);
				tr.appendChild(tdN);
				tr.appendChild(tdA);

				tbodyOrder.appendChild(tr);

			}
			orderModal.show();
		})
}
