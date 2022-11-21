var phoneField = document.getElementById('phone')


phoneField.focus()
phoneField.select()

function isPhone(phoneField) {
	return new RegExp('/^\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})$/').test(phoneField);
}