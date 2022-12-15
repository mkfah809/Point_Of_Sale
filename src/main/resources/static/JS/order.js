const toppingBtn = document.getElementById("toppingBtn");
var orderId = document.getElementById("orderById")
var pizzaId = document.getElementById("pizzaId")
var toppingName = document.getElementById("toppingName")


pizzaAddBtn.focus()



toppingBtn.addEventListener('click', () => {
	let topping = {
		'name': toppingName.value,
		pizzas: [
			{
				'pizzaId': pizzaId.value
			}
		]
	}

	var csrf = document.getElementById('csrf')

	console.log("topping object::: ", JSON.stringify(topping))


	fetch("/customer/{custId}/order/{orderId}/pizza/{pizzaId}", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'X-CSRF-TOKEN':  csrf.value
		},
		body: JSON.stringify(topping)
	})
})



