var pizzaAddBtn = document.getElementById("pizzaAddBtn")
pizzaAddBtn.focus()
var settleBtn = document.getElementById('settleBtn')
var orderId = document.getElementById("orderById")
var pizzaSize = document.getElementById("size")
var pizzaQty = document.getElementById("qty")
var howCook = document.getElementById("howCook")
var comment = document.getElementById("comment")
var orderMethod = null
var orderType = null
var pizza = null
var pizzaPrice = null
var price = null

var pizzaIdByClassName = document.querySelectorAll('.pizzaId')
var priceList = document.querySelectorAll('.priceList')
var toppingName = document.querySelectorAll('.toppingName');
var toppingPrice = document.querySelectorAll('.toppingPrice')
var toppings = []
let totalPrice = null





pizzaAddBtn.addEventListener("click", () => {

	sessionStorage.clear('toppings')
	refresh()
	if (pizzaSize.value === "Lg") {
		pizzaPrice = 11.99
	} else {
		pizzaPrice = 8.99
	}

	price = pizzaPrice * parseInt(pizzaQty.value);

	getCheckedToppings()
	setPizza()
	sessionIsEmpty()
	fetchPizza()




})


totalPrice = calculateFinalPrice() //without topping price yet
console.log(totalPrice)
let totalPriceText = totalPrice;
document.getElementById("finalPrice").innerHTML = "Total Price: " + totalPriceText;




settleBtn.addEventListener('click', () => {



	let orderType = document.getElementsByName('orderType')
	let orderMethod = document.getElementsByName('orderMethod')


	for (let i = 0; i < orderType.length; i++) { // Delivery OR PickUp
		if (orderType[i].checked) {
			console.log(orderType[i].value)
			orderType = orderType[i].value
		}
	}

	for (let i = 0; i < orderMethod.length; i++) { // PickUp OR CASH
		if (orderMethod[i].checked) {
			console.log(orderMethod[i].value)
			orderMethod = orderMethod[i].value
		}
	}









	let order = {
		'orderId': orderId.value,
		'finalPrice': totalPrice,
		'orderMethod': orderMethod,
		'type': orderType
	}
	console.log(JSON.stringify(order))
	fetch('/order-price/customer/{custId}/order/{orderId}', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'X-CSRF-TOKEN': document.getElementById('csrf').value

		},
		body: JSON.stringify(order)
	}).then((response) => response.json())
})




function calculateFinalPrice() {
	let prices = []

	for (let i = 0; i < priceList.length; i++) {
		console.log(parseFloat(priceList[i].innerHTML))
		prices.push(parseFloat(priceList[i].innerHTML))
	}

	let totalPrice = 0;
	for (let i = 0; i < prices.length; i++) {

		totalPrice += prices[i];
	}

	return totalPrice.toFixed(2)
}

function refresh() {
	setTimeout(function() {
		location.reload()
	}, 100);
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
			sessionStorage.setItem('toppings', toppings);
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
				'orderId': orderId.value,
			}
		],
		toppings: [
			{
				'name': sessionStorage.getItem('toppings')
			}
		]

	}
	return pizza
}




function sessionIsEmpty() {
	if (sessionStorage.length == 0) {
		sessionStorage.clear('toppings')
		pizza.toppings.pop(0)

		return true
	}

}

function fetchPizza() {
	fetch('/post-pizza/customer/{custId}/order/{orderId}', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'X-CSRF-TOKEN': document.getElementById('csrf').value

		},
		body: JSON.stringify(pizza)
	}).then((response) => response.json())
}
