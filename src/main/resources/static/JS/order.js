var checkboxes = document.querySelectorAll("input[type=checkbox][name=settings]");
var toppings = []


// Use Array.forEach to add an event listener to each checkbox.
checkboxes.forEach(function(checkbox) {
	checkbox.addEventListener('change', function() {
		toppings =
			Array.from(checkboxes) // Convert checkboxes to an array to use filter and map.
				.filter(i => i.checked) // Use Array.filter to remove unchecked checkboxes.
				.map(i => i.value) // Use Array.map to extract only the checkbox values from the array of objects.


		toppings.map(function(i) {

			let topping = {
				'name': i
			}

			console.log(topping)

			fetch("/addItem/To/order/{orderId}/{custId}/{pizzaId}", {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'X-CSRF-TOKEN': document.getElementById('csrf').value
				},
				body: JSON.stringify(topping)
			})


		})





	})
});