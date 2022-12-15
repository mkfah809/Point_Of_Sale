var toppingBtn = document.querySelector("#toppingBtn");
var orderId = document.getElementById("orderById")
var pizzaId = document.getElementById("pizzaId")
var toppingName = document.getElementById("toppingName")
pizzaAddBtn.focus()


toppingBtn.addEventListener('click', () => {
	let topping = {
		'name': toppingName.value,
		pizzas: [
			{
				'pizzaId': pizzaId.value,
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
})



