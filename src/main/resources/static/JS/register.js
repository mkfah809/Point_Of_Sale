var form = document.getElementById('form');
var username = document.getElementById('username');
var title = document.getElementById('title');
var password = document.getElementById('password');
var confirmPassword = document.getElementById('confirmPassword');
var eyeIcons = document.querySelectorAll('.fa-eye')



async function createEmployee() {
	form.addEventListener('submit', (e) => {
		e.preventDefault();
		if (checkInputs()) {
			fireRequest()
		}
	})
}


async function passwordShowUp() {
	eyeIcons.forEach((eyeIcon) => {
		eyeIcon.addEventListener('click', () => {

			if (eyeIcon.classList.contains('fa-eye')) {
				eyeIcon.classList.replace('fa-eye', 'fa-eye-slash')


				if (eyeIcon.getAttribute('id') === 'passwordEyeIcon') {
					password.type = 'text'
				} else {
					confirmPassword.type = 'text'
				}

			} else {
				eyeIcon.classList.replace('fa-eye-slash', 'fa-eye')
				if (eyeIcon.getAttribute('id') === 'passwordEyeIcon') {
					password.type = 'password'
				} else {
					confirmPassword.type = 'password'
				}
			}
		})
	})
}

function checkInputs() {
	var usernameValue = username.value.trim();
	var titleValue = title.value.trim();
	var passwordValue = password.value.trim();
	var confirmPasswordValue = confirmPassword.value.trim();

	if (usernameValue === '') {
		setErrorFor(username, 'Username cannot be blank')
		username.focus()
		return false
	} else if (usernameValue === true) {
		setErrorFor(username, 'username already exists')
		return false
	} else {
		setSuccessFor(username)
	}
	if (titleValue === 'none') {
		setErrorFor(title, 'title cannot be blank')
		title.focus()
		title.select()
		return false
	} else {
		setSuccessFor(title)
	}

	if (passwordValue === '') {
		setErrorFor(password, 'password cannot be blank')
		password.focus()
		password.select()
		return false
	} else if (!isPassword(passwordValue)) {

		setErrorFor(password, 'Please insert only numbers')
		password.focus()
		password.select()
		return false
	} else {
		setSuccessFor(password)
	}

	if (confirmPasswordValue === '') {
		setErrorFor(confirmPassword, 'password cannot be blank')
		confirmPassword.focus()
		confirmPassword.select()
		return false
	} else if (passwordValue !== confirmPasswordValue) {
		setErrorFor(confirmPassword, 'password does not match')
		return false
	} else {
		setSuccessFor(confirmPassword)
	}



	// submit into database
	return true

}

async function setErrorFor(input, message) {
	const formControl = input.parentElement; //.form-control
	const small = formControl.querySelector('small')
	small.innerText = message;
	formControl.className = 'form-control error'
}

async function setSuccessFor(input) {
	const formControl = input.parentElement; //.form-control
	formControl.className = 'form-control success'
}

function isPassword(password) {
	return new RegExp('[0-9]').test(password);
}


async function loading() {
	window.location.reload();

}
function fireRequest() {
	
	let emp = {
		username: username.value,
		title: title.value,
		password: password.value
	}
	
	fetch("/register/new/employee", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'X-CSRF-TOKEN': document.getElementById('csrf').value
		},
		body: JSON.stringify(emp)
	})
		.then((response) => response.json())
		.then((data) => {
			if (data === true) {
				setErrorFor(username, 'username already exists')
				username.focus()
				username.select()
				
			}

		})

}

passwordShowUp()
createEmployee()
