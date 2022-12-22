var pizzaAddBtn = document.getElementById("pizzaAddBtn")
pizzaAddBtn.focus()

var orderId = document.getElementById("orderById")
//var pizzaPrice = document.getElementById("price")
var pizzaSize = document.getElementById("size")
var pizzaQty = document.getElementById("qty")
var howCook = document.getElementById("howCook")
var comment = document.getElementById("comment")


var pizza = null
var pizzaPrice = null
var pizzaIdByClassName = document.querySelectorAll('.pizzaId')
var toppingName = document.querySelectorAll('.toppingName');
var toppingPrice = document.querySelectorAll('.toppingPrice')
var toppings = []
var price = null

pizzaAddBtn.addEventListener("click", () => {
	sessionStorage.clear('toppings')

	if (pizzaSize.value === "Lg") {
		pizzaPrice = 11.99
	} else {
		pizzaPrice = 8.99
	}





	price = pizzaPrice * parseInt(pizzaQty.value);
	console.log(price)
	getCheckedToppings()
	setPizza()
	fetchPizza()





})

function fetchPizza() {
	if (sessionStorage.length == 0) {
		alert("please check toppings!")
	} else {

		alert("toppings checked")
		fetch('/post-pizza/customer/{custId}/order/', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'X-CSRF-TOKEN': document.getElementById('csrf').value

			},
			body: JSON.stringify(pizza)
		}).then((response) => response.json())
	}
}


function getCheckedToppings() {
	for (let i = 0; i < toppingName.length; i++) {
		if (toppingName[i].checked) {

//
//			for (let index = i; index < toppingPrice.length; index++) {
//
//
//
//				// to get the price of the checked topping
//				if (i == index) {
//					console.log(toppingName[i].value, toppingPrice[index].value)
//				}
//
//			}




			toppings.push(toppingName[i].value)
			sessionStorage.setItem('toppings', JSON.stringify(toppings));
		}

	}
}

function setPizza() {
	pizza = {
		'size': pizzaSize.value,
		'price': price,
		'comment': comment.value,
		'qty': pizzaQty.value,
		'howCooked': howCook.value,
		orders: [
			{
				'orderId': orderId.value
			}
		],
		toppings: [
			{
				'name': sessionStorage.getItem('toppings')
			}
		]

	}
	console.log(JSON.stringify(pizza))

}