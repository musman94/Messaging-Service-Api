# Messaging-Service-Api
A simple messaging service backend built using Java and SpringBoot.

## Technologies used
- Java
- SpringBoot
- MongoDB
- Docker

## Running the app
Clone this repository, navigate to the repo's directory and start the service using the following command.

```jshelllanguage
$ docker-compose up 
```

If you need to build the jar again.    
```jshelllanguage
$ docker build -f Dockerfile -t app .
 ```
## Implementation

### Summary
The project is divided into 3 layers, **Controller**, **Service** and **Repository** layers. 

### Controllers
Controllers are responsible for controlling the flow of the application over the HTTP requests. There are 3 types of controllers in this project, **AuthController**, **UserController** and **MessageController**. **AuthController** contains the end points for login and signup functionalites. **UserController** contains the endpoints for blocking a user and getting the login history for a user. **MessageController** contains the endpoints for sending a message and getting the message history of a user.

### Services
The main logic is written in the Service layer. The service layer validates the requests, passes them further to the Repository layer, encapsulates the responses from the Repository layer in their respective objects and sends them back. There are 3 kinds of services, **AuthService**, **UserService** and **MessageService**. **UserService** and **MessageService** interact further with the repository layer depending on the request. **AuthService** is used by the authentication manager to check the username and password details when the user tries to login.

### Repositories
A Repository is an abstraction of the data layer and only it interacts directly with the database. The main benefit of this is to provide an abstraction to the code so that it is not bothered by how the database is implemented. Or if the database was to be changed, the repository layer will keep on working as before and not break. There are 2 kinds of repositories **UserRepository** and **MessageRepository**.

## API
The routes are defined in their respective controllers. You can read more about each of the endpoints by clicking on their names. 

|Name                                                           |route                 
|---------------------------------------------------------------|------------------------------------------------------|
[signUp](documentation/auth/signUp.md)                          |/api/v1/auth.signUp                                   |
[login](documentation/auth/login.md)                            |/api/v1/auth.login                                    |         
[sendMessage](documentation/message/sendMessage.md)             |/api/v1/message/sendMessage                           |
[getMessageHistory](documentation/message/getMessageHistory.md) |/api/v1/message/getMessageHistory?username={username} |             
[block](documentation/user/block.md)                            |/api/v1/user/block                                    |
[getLoginHistory](documentation/user/getLoginHistory.md)        |/api/v1/user/getLoginHistory?username={username}      |


## Authentication
The application uses jwt token based authentication. Once the user logins, they are provided with the jwt token in reponse which they need to provide in any subsequent requests. Otherwise they won't be authorized to request something from the application. The only endpoints that are free from the jwt authorization requirement are the login and signup endpoints. The login endpoint, however still authenticates the user using their username and password. All of the authentication and jwt authrization functionality is present in the `security` package. Furthermore, everytime a user tries to login, the login attempts are saved using the `AuthenticationEventHandler` and the user can see this information by requesting the `getLoginHistory` endpoint.   

## Testing
All of the tests are located in the `src/main/test/java/com.app` package. The test coverage rightnow is 41% as I was only able to test the service layer.

