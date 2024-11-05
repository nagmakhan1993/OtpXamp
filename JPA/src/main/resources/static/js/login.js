document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    
    form.addEventListener('submit', async function(event)  {
        event.preventDefault();         
       
		const email = document.getElementById('email').value;
		            const password = document.getElementById('password').value;

		            // Create the payload for the login API
		            const loginPayload = {
		                email: email,
		                password: password
		            };

		            try {
		                // Send a POST request to the Spring Boot API
		                const response = await fetch('http://localhost:8080/auth/login', {
		                    method: 'POST',
		                    headers: {
		                        'Content-Type': 'application/json'
		                    },
		                    body: JSON.stringify(loginPayload)
		                });

		                if (response.ok) {
		                    // Parse the JSON response
		                    const data = await response.json();

		                    // Store the JWT token in localStorage (or sessionStorage)
		                    localStorage.setItem('jwtToken', data.jwtToken);  // Or sessionStorage.setItem('jwtToken', data.jwtToken);
							localStorage.setItem('username', data.username);
		                    // Optionally, log the user information
		                    console.log('User logged in with token:', data.jwtToken);
							window.location.href = 'http://localhost:8080/home/userHomePage';
							return fetch('http://localhost:8080/home/userHomePage', {
								//console.log('this api');
							      method: 'GET',
							      headers: {
							        'Authorization': `Bearer ${data.jwtToken}`,  // Add token to Authorization header
							        'Content-Type': 'application/json'
							      }
							    });							
								  if (response.ok) {
									const newdata = await response.json();
									console.log('response', newdata);
							      return newdata;
								    // Parse second API response as JSON
							    } else {
							      return response.text().then(errorMessage => {
							        throw new Error(`Second API request failed: ${errorMessage}`);
							      });
							    }
							
		                } else {
		                    // Handle login error (e.g., wrong credentials)
		                    console.error('Login failed', response.status);
		                }
		            } catch (error) {
		                console.error('Error during login', error);
		            }
					});    
});