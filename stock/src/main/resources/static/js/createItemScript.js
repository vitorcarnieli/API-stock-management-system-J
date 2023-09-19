const name = document.getElementById("name");
const description = document.getElementById("description");
const unitType = document.getElementById("unitType");
const amount = document.getElementById("amount");
const form = document.getElementById("form");
const back = document.getElementById("back");



const urlParam = new URLSearchParams(window.location.search)
console.log(urlParam);

form.addEventListener("submit", function (event) {
    event.preventDefault();
    createItem();
});


function createItem() {

    fetch("http://127.0.0.1:8080/stock-group/addItem/" + urlParam.get("id"),
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                name: name.value,
                unitType: unitType.value,
                description: description.value,
                amount: parseInt(amount.value)
            })
        })
        .then(function (res) {
            console.log(res);
            alert(name.value + " Adicionado com sucesso!")
            window.location.href = "http://127.0.0.1:8080/pages/stockGroup.html?id=" + urlParam.get("id");
        })
        .catch(function (res) { console.log(res) })

}

back.addEventListener("mouseover", function() {
    back.classList.add("border");
    back.style.cursor = "pointer";
})

back.addEventListener("mouseout", function() {
    back.classList.remove("border")
    back.style.cursor = "auto";
})

back.addEventListener("click", function(e) {
    console.log('Entrou')
    let a = document.createElement("a");
    a.href = "http://127.0.0.1:8080/pages/stockGroup.html?id=" + urlParam.get("id");
    document.body.appendChild(a);
    a.click();
})