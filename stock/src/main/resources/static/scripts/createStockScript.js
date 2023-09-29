console.log(2)
const form = document.getElementById("form");

form.addEventListener("submit", function (event) {
    event.preventDefault();
    createStockGroup();

})

function createStockGroup() {
    const name = document.getElementById("name");
    const description = document.getElementById("description");

    fetch("http://localhost:8080/stock-group/create",
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
            console.log(res);
            alert(name.value + " Criado com sucesso!")
            window.location.href = "http://localhost:8080/index.html";
        })
        .catch(function (res) { console.log(res) })

}