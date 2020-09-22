# Registers a user

Registers a user with a username if the username is not already taken.

**URL** : `/api/v1/auth/signUp`

**Method** : `POST`

**Auth required** : No

**Permissions required** : None

**Data constraints**

Provide name of Account to be created.

```json
{
    "username":  "[string]",
    "password":  "[string]",
    "firstName": "[string]",
    "lastName":  "[string]"
}
```

**Data example** All fields must be sent.

```json
{
    "username": "musman",
    "password": "wrrp2",
    "firstName": "Muhammad",
    "lastName": "Usman"
}
```

## Success Response

**Condition** : If everything is OK and the username is not taken.

**Code** : `200 OK`

**Content example**

```json
{
    "status": "OK",
    "payload": {
        "username": "musman",
        "firstName": "Muhammad",
        "lastName": "Usman"
    },
    "message": "User registered successfully"
}
```

## Error Responses

**Condition** : If the username is already taken.

**Code** : `409 CONFLICT`

**Content example**

```json
{
    "status": "ALREADY_EXISTS",
    "errors": {
        "message": "USER musman already exists"
    }
}
```

### Or

**Condition** : If of the any fields are missing.

**Code** : `400 BAD REQUEST`

