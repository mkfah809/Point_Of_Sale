function create_tr(tableBody) {
	tableBody = document.getElementById("table_body"),
		first_tr = tableBody.firstElementChild
	trClone = first_tr.cloneNode(true);

	tableBody.append(trClone)
	clean_first_tr(tableBody.firstElementChild);
}


function clean_first_tr(firstTr){

	let children = firstTr.children;
	children = Array.isArray(children) ? children : Object.values(children)
	children.forEach(x => {
		if (x !== firstTr.lastElementChild) {
			x.firstElementChild.value = '';
		}
	});
}


function remove_tr(This) {
	if (This.closest('tbody').childElementCount == 1) {
		alert("You don't have permission to delete this")
	} else {
		This.closest('tr').remove();
	}
}


