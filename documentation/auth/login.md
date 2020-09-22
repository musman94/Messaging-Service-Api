# Logs in a user

Logs in a user so that they can start using the service.

**URL** : `/api/v1/auth/login`

**Method** : `POST`

**Auth required** : Yes

**Permissions required** : None

**Data constraints**

Provide the login information of the user.

```json
{
    "username":  "[string]",
    "password":  "[string]"
}
```

**Data example** All fields must be sent.

```json
{
    "username": "musman",
    "password": "wrrp2"
}
```

## Success Response

**Condition** : If everything is OK

**Code** : `200 OK`

**Content example**

Payload contains the jwt token to authorize the user for later requests.   

```json
{
    "status": "OK",
    "payload": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtdXNtYW4iLCJleHAiOjE2MDA4MDk2OTYsImlhdCI6MTYwMDc3MzY5Nn0.DrQxI7_N_EO3MHqq10a5iJBOYTtopAOoiAiR4UbnjtY"
}
```

## Error Responses

**Condition** : If the username doesn't exist.

**Code** : `404 NOT FOUND`

**Content example**

```json
{
    "errors": {
        "errorMessage": "USER musman not found"
    }
}
```

### Or

**Condition** : If a wrong password is supplied.

**Code** : `401 UNAUTHORIZED`

**Content example**

```json
{
    "message": "Incorrect username or password"
}
```

### Or

**Condition** : If any of the fields are missing.

**Code** : `400 BAD REQUEST`

