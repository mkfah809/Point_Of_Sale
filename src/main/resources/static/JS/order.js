pizzaAddBtn.focus()
var orderId = document.getElementById("orderById")
var pizzaPrice = document.getElementById("pizzaPrice")

var addToppingBtn = document.getElementsByClassName("toppingBtn")
var pizzaIdByClassName = document.querySelectorAll('.pizzaId')
var toppingName = document.querySelectorAll('.toppingName');
var deleteToppingBtn = document.querySelectorAll(".toppingDeleteBtn")

for (let i = 0; i < toppingName.length; i++) {
	deleteToppingBtn[i].style.display = "none";
	addToppingBtn[i].addEventListener('click', () => {
		toppingName[i].style.backgroundColor = "#50D050";
		addToppingBtn[i].style.display = "none"
		let topping = {
			name: toppingName[i].value,
			pizzas: [
				{
					'pizzaId': pizzaIdByClassName[i].value,
					orders: [
						{
							'orderId': orderId.value
						}
					]
				}
			]
		}



		console.log("topping object::: ", JSON.stringify(topping))


		fetch("/customer/{custId}/order/{orderId}/pizza/{pizzaId}", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'X-CSRF-TOKEN': document.getElementById('csrf').value
			},
			body: JSON.stringify(topping)
		})

		deleteToppingBtn[i].style = 'block'; //show button
		deleteTopping(i, topping)

	})



}


function deleteTopping(i, topping) {
	deleteToppingBtn[i].addEventListener('click', () => {

		deleteToppingBtn[i].style.display = 'none'; // hide button
		addToppingBtn[i].style = "block"
		toppingName[i].style.backgroundColor = "#ffffff";


		fetch("/delete-Topping/{pizzaId}/{toppingId}", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'X-CSRF-TOKEN': document.getElementById('csrf').value
			},
			body: JSON.stringify(topping)
		})
	})
}


