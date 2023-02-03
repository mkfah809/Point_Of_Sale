var pizzaAddBtn = document.getElementById('pizzaAddBtn')
var settleBtn = document.getElementById('settleBtn')
var orderType = document.getElementsByName('orderType')
var orderMethod = document.getElementsByName('orderMethod')

var orderId = document.getElementById('orderId')
var pizzaSize = document.getElementById('size')
var pizzaQty = document.getElementById('qty')
var howCook = document.getElementById('howCook')
var comment = document.getElementById('comment')


var pizza = null
var pizzaSizePrice = null
var price = null
let totalPrice = null

var pizzaIdByClassName = document.querySelectorAll('.pizzaId')
var priceList = document.querySelectorAll('.priceList')
var toppingName = document.querySelectorAll('.toppingName');

var toppingPrice = document.querySelectorAll('.toppingPrice')

let toppings = []


// Check if toppings are empty
if (toppingName.length === 0) {
	let errorText = "Ask your Boss to add toppings"
	document.getElementById("emptyTopping").innerHTML = errorText;
	pizzaAddBtn.disabled = true;
} else {
	document.getElementById("emptyTopping").style.display = "none"
}

pizzaAddBtn.focus()

pizzaAddBtn.addEventListener("click", () => {

	sessionStorage.clear('toppings')
	refresh()
	if (pizzaSize.value === "Lg") {
		pizzaSizePrice = 11.99
	} else {
		pizzaSizePrice = 8.99
	}
	price = pizzaSizePrice * parseInt(pizzaQty.value);

	getCheckedToppings()
	setPizza()
	sessionIsEmpty()
	fetchPizza()
	refresh()
})




totalPrice = calculateFinalPrice() //without topping price yet
console.log(totalPrice)
let totalPriceText = totalPrice;
document.getElementById("finalPrice").innerHTML = "Total Price: " + totalPriceText;




settleBtn.addEventListener('click', () => {
	let orderMethodisChecked = setOrderPayMethod()
	let orderTypeIsChecked = setOrderType()

	if (orderMethodisChecked == true & orderTypeIsChecked == true) {
		postUpdateOrder()
		//		document.getElementById("finalPrice").innerHTML = "Total Price: " + 0.0;
		//		document.getElementById("confirmation").innerHTML = "Notify the customer, they will recieve an email with confirmation number";

	} else {
		document.getElementById("finalPrice").innerHTML = "Choose Order Type/ Method"
	}
	refresh()

})

function postUpdateOrder() {

	let order = {
		'orderId': orderId.value,
		'finalPrice': totalPrice,
		'orderMethod': orderMethod,
		'type': orderType
	}


	fetch('/order-price/customer/{custId}/order/{orderId}', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'X-CSRF-TOKEN': document.getElementById('csrf').value

		},
		body: JSON.stringify(order)
	}).then((response) => response.json())
}


function setOrderPayMethod() {
	for (let i = 0; i < orderMethod.length; i++) { // PickUp OR CASH
		if (orderMethod[i].checked) {
			console.log(orderMethod[i].value)
			orderMethod = orderMethod[i].value
			document.getElementById("orderMethod").innerHTML = "Order Type: " + orderMethod;
			return true;
		}
	}
}

function setOrderType() {
	for (let i = 0; i < orderType.length; i++) { // Delivery OR PickUp
		if (orderType[i].checked) {
			console.log(orderType[i].value)
			orderType = orderType[i].value
			document.getElementById("orderType").innerHTML = "Order Type: " + orderType;
			return true;
		}
	}
}

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

			toppings.push(toppingName[i].value)
		}
	}
	sessionStorage.setItem('toppings', toppings);
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
