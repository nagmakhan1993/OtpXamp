document.addEventListener('DOMContentLoaded', () => {
	const form = document.getElementById('addmissionForm');

	form.addEventListener('submit', async function(event) {
		event.preventDefault();
		const formData = {
			cName: document.getElementsByName('cName')[0].value,
			fatherName: document.getElementsByName('fatherName')[0].value,
			motherName: document.getElementsByName('motherName')[0].value,
			dob: document.getElementById('dob').value,
			aadhaarNo: document.getElementById('aadhaarNo').value,
			add1: document.getElementsByName('add1')[0].value,
			add2: document.getElementsByName('add2')[0].value,
			city: document.getElementsByName('city')[0].value,
			district: document.getElementsByName('district')[0].value,
			state: document.getElementsByName('state')[0].value,
			pincode: document.getElementsByName('pincode')[0].value,
			cNum: document.getElementsByName('cNum')[0].value,
			email: document.getElementsByName('email')[0].value,
			userName: document.getElementsByName('userName')[0].value,
			password: document.getElementsByName('password')[0].value,
			cpassword: document.getElementsByName('cpassword')[0].value,
			gender: document.querySelector('input[name="gender"]:checked').value
		};
		console.log("Selected gender: " + formData.gender); 
		if (formData.password !== formData.cpassword) {
			console.log('Password not matched');
			return false;
		} else {
			console.log('Password matched!!');
			console.log('formData:  ', formData);
			try {
				console.log(' data passing in create user api');
				const apiresponse = await fetch('http://localhost:8080/candidate/addCandidate', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify(formData)

				});
				console.log('response:  ', apiresponse);
				if (apiresponse.ok) {
					// Parse the JSON response
					const responsedata = await response.json();
					console.log('esponse data', responsedata);
					return window.location.href = 'http://localhost:8080/home/successRegisterUser';

				} else {
					console.error('registration failed', response.status);
				}
			} catch (error) {
				console.error('Error during registration', error);
			}
		}
	});
});

