# TiketKu
TiketKu is a cinema ticket booking website that allows users to book movie tickets online and make payments through various secure payment methods. This website has been developed with `payment gateway` integration to ensure safe and easy transactions.

## Technologies Used
- **Frontend:** HTML, CSS, JavaScript, Bootstrap, Thymeleaf
- **Backend:** Spring Boot
- **Database:** MySQL
- **Payment Gateway:** Midtrans
- **Version Control:** Git
- **Security:** Spring Security using JPA

# Documentation
## Authentication
Endpoint : POST/auth/registration
Request Body :
``` json
{
    "fullname" : "user",
    "email" : "user@gmail.com",
    "phone" : "082266880412",
    "username" : "user",
    "address" : "Indonesia",
    "password" : "user1234"
}
```

Response Body (Success) :
```json
{
    "data" : "Ok"
}
```

Response Body (Failed) :
```json
{
    "data" : "Failed"
}
```

Endpoint : POST/auth/login

Request Body :
``` json
{
    "username" : "user",
    "password" : "user1234"
}
```

Response Body (Success) :
```json
{
    "data" : "Ok"
}
```
Response Body (Failed) :
```json
{
    "data" : "Failed"
}
```

Endpoint : POST/auth/forgotPassword
Endpoint : POST/auth/resetPassword
Endpoint : POST/auth/checkEmail
Endpoint : POST/auth/checkUsername

## Studio
## Genre
## Room
## Profile
## Film
## Schedule
## Schedule Room
## Orders






