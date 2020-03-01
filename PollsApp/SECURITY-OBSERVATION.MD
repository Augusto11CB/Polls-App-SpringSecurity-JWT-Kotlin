* The API registers new users with their name, username, email and password.

* The  API lets users log in using their username/email and password. 
    * After validating user’s credentials, the API should generate a JWT authentication token and return the token in the response.
    * The clients will send the generated JWT in the `Authorization` header of all the requests to access any protected resources.
    
* The following items will be accessible to everyone:
    * login page
    * signup page
    * static resources (image, scripts, stylesheets)
    
* Configure Spring security to throw a 401 unauthorized error if a client tries to access a protected resource without a valid JWT token.
    
* Configure Role-based Authorization to protect resources on the server.
