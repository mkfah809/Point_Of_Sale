var checkedValues = document.querySelectorAll("input[type=checkbox][name=settings]");
var checkList = document.getElementById("toppingId")
var orderId = document.getElementById("orderId").innerText
var pizzaBtn = document.getElementById("pizzaBtn")
var qty = document.getElementById("qty")
var size = document.getElementById("size")
var howCook = document.getElementById("howCook")
var pizzaComment = document.getElementById("pizzaComment")
var	toppings = []
var pizza
var	newArray = []
console.log("orderId", parseInt(orderId))
console.log("qty ", qty.value)
console.log("size ", size.value)
console.log("howCook", howCook.value)
console.log("pizzaComment", pizzaComment.value)



checkedValues.forEach(
checkList.addEventListener('change', function() {
	
				toppings = Array.from(checkboxes) // Convert checkboxes to an array to use filter and map.
					.filter(i => {
						if(i.checked) {
							alert(i.value,"added")
						
						} else {
						
						}
				})
})
			)
	

//	pizzaBtn.addEventListener("click", () => {
//	
//	
//				postOrder()
//				
//		
//	})
//
//async function postOrder(order) {
//	 		order = {
//					'orderId': parseInt(orderId),
//					pizzas: [
//						{
//							'qty': qty.value,
//							'size': size.value,
//							'howCook': howCook.value,
//							'pizzaComment': pizzaComment.value
//						}
//
//					]
//				}
//			fetch("/customer/{custId}/order/{orderId}", {
//			method: 'POST',
//			headers: {
//				'Content-Type': 'application/json',
//				'X-CSRF-TOKEN': document.getElementById('csrf').value
//					},
//			body: JSON.stringify(order)
//				})	
//		}		

	// go check the size
	// if size.sm (set price to 8) -- if size.lg(set price to 11)
	
	//go check the checkboxes
	
	// forEach loop to get every single checked value with fetch get method 
	// addEventListener(change)
	// go ask the server about this topping(price) and put it into a model.
	
	
	
	//	go check the qty
	// take the qty.value * by 8 or 11 
	
	// 


//checkboxes.forEach(
//
//	function getTopping(checkbox) {
//		checkbox.addEventListener('change', function() {
//			toppings = Array.from(checkboxes) // Convert checkboxes to an array to use filter and map.
//				.filter(i => i.checked) // Use Array.filter to remove unchecked checkboxes.
//				.map(i => i.value)
//
//
//			toppings.map(function(toppingValue) { // Use Array.map to extract only the checkbox values from the array of objects.
//
//				let order = {
//					'orderId': orderId,
//					pizza: {
//						pizzaId: pizzaId
//					}
//					,
//					'topping': {
//						'name': toppingValue
//					}
//				}
//
//
//
//
//				//			let pizza = {
//				//				'pizzaId': pizzaId,
//				//				'topping': {
//				//					'name': toppingValue
//				//				}
//				//			}
//				//
//				//			let topping = {
//				//				'name' : toppingValue
//				//			}
//
//				fetch("/customer/{custId}/order/{orderId}", {
//					method: 'POST',
//					headers: {
//						'Content-Type': 'application/json',
//						'X-CSRF-TOKEN': document.getElementById('csrf').value
//					},
//					body: JSON.stringify(order)
//				})
//
//			})
//
//		})
//	});