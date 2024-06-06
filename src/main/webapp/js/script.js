function fetchPost(url, formData) {
	fetch(url, {
		method: 'POST',
		body: formData
	})
		.then(response => {
			if (response.ok) {
				if (response.redirected) {
					windows.location.href = response.url;
				} else {
					return response.text();
				}
			}
			console.log(response);
			throw new Error('Network response was not ok.');
		})
		.then(responseHtml => {
			document.querySelector('.content').innerHTML = responseHtml;
		})
		.catch(error => {
			console.error('Error:', error.message);
		});
}
function toggleEdit(table_class) {
    var fields = document.querySelectorAll(`.${table_class} td span.changable,.${table_class} td span.identity`);
    fields.forEach(function(field) {
        if (field.classList.contains('identity')) {
            var span = document.createElement('span');
            var input = document.createElement('input');
            input.setAttribute('value', field.innerText);
            span.innerText = field.innerText;
            input.setAttribute('type', 'hidden');
            input.setAttribute('style','display:none');
            input.setAttribute('name', field.getAttribute('name'));
            field.innerHTML = '';
            field.appendChild(span);
            field.appendChild(input);
        } else {
            var input = document.createElement('input');
            input.setAttribute('value', field.innerText);
            input.setAttribute('type', 'text');
            input.required=true
            input.setAttribute('name', field.getAttribute('name'));
            field.innerHTML = '';
            field.appendChild(input);
        }
    });
    document.querySelector('#editButton').style.display = 'none';
    document.querySelector('#saveButton').style.display = 'block';
}
function saveForm(table_class, route) {
    var jsonObject = {};
    var fields = document.querySelectorAll(`.${table_class} input`);
    var isValid = true;

    fields.forEach(function(field) {
        jsonObject[field.name] = field.value;
        if (field.hasAttribute('required') && !field.value.trim()) {
            isValid = false;
        }
    });

    if (!isValid) {
        alert('Please fill in all required fields.'); 
        return;
    }

    var jsonData = JSON.stringify(jsonObject);
    document.querySelector(`#editButton`).style.display = 'block';
    document.querySelector(`#saveButton`).style.display = 'none';
    fetchPost(route, jsonData);
}


function submitForm(event) {
	event.preventDefault();
	var form = event.target;
	var formData = new FormData(form);
	var jsonObject = {};
	formData.forEach(function(value, key) {
		jsonObject[key] = value;
	});
	var jsonData = JSON.stringify(jsonObject);
	var url = form.action;
	fetchPost(url, jsonData);
}
var queryButtons = document.querySelectorAll('.get-btn');
queryButtons.forEach(function(button) {
	button.addEventListener('click', function() {
		var contentDiv = document.querySelector('.content');
		var buttonId = this.getAttribute('id');
		var url = "/Continental/app/" + buttonId;
		fetch(url)
			.then(response => {
				if (response.redirected) {
					window.location.href = response.url;
				}
				else {
					return response.text()
				}
			})
			.then(data => {
				contentDiv.innerHTML = data;
			})
			.catch(error => {
				console.error('Error fetching content:', error);
			});
	});
});
var allButtons = document.querySelectorAll('button');
allButtons.forEach(function(button) {
	button.addEventListener('click', function() {
		allButtons.forEach(function(btn) {
			btn.classList.remove('selected');
		});
		this.classList.add('selected');
	});
});
var togglerButtons = document.querySelectorAll('.side-nav-part-toggler');
togglerButtons.forEach(function(button) {
	button.addEventListener('click', function() {
		var targetId = this.getAttribute('data-target');
		var targetElement = document.querySelector('.' + targetId);
		togglerButtons.forEach(function(toggler) {
			var otherTargetId = toggler.getAttribute('data-target');
			if (otherTargetId !== targetId) {
				var otherTargetElement = document.querySelector('.' + otherTargetId);
				otherTargetElement.classList.add('hidden');
			}
		});
		targetElement.classList.toggle('hidden');
	});
});
var topNavButtons = document.querySelectorAll('.top-nav-button, .non-toggled');
topNavButtons.forEach(function(button) {
	button.addEventListener('click', function() {
		var subButtons = document.querySelectorAll('.side-nav-part-opt');
		subButtons.forEach(function(subButton) {
			subButton.classList.add('hidden');
		});
	});
});

function load_statement(flag, id) {
	const page_form = document.getElementById(id);
	console.log(page_form);
	if (flag === true) {
		page_form.pageNumber.value = "1";
	}
	let formData = new FormData(page_form);
	let jsonObject = {};
	formData.forEach(function(value, key) {
		jsonObject[key] = value;
	});
	let jsonData = JSON.stringify(jsonObject);
	console.log(jsonData);
	let url = page_form.action;
	fetchPost(url, jsonData);
}
function postAccountView(accountNumber){
	let jsonObject = {};
	jsonObject["accountNumber"]=""+accountNumber;
	let jsonData = JSON.stringify(jsonObject);
	let url = "employee/view-account"
	fetchPost(url, jsonData);
}
function close(previous){
	fetch("/"+previous)
	.then(response=>{
		if(response.ok){
			return response.text()
		}
	})
	.then(responseText=>{
		document.querySelector(".content").innerHTML = responseText;
	})
}
